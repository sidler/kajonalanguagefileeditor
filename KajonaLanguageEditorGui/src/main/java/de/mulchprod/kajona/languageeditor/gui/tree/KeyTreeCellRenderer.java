/*
 * Kajona Language File Editor Gui
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
 *
 * (c) MulchProductions, www.mulchprod.de, www.kajona.de
 *
 */

package de.mulchprod.kajona.languageeditor.gui.tree;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author sidler
 */
public class KeyTreeCellRenderer extends DefaultTreeCellRenderer  {


   
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.setBackgroundNonSelectionColor(Color.WHITE);

        Component parentComp = super.getTreeCellRendererComponent(tree, value, leaf, expanded, leaf, row, hasFocus);
        parentComp.setForeground(Color.BLACK);
        try {
            if(value instanceof GuiTreeNode) {
                TreeNode node = (TreeNode)((GuiTreeNode)value).getReferencingTreeNode();
                if(leaf && node.getType() == NodeType.KEY) {
                    parentComp.setForeground(Color.BLACK);
                    if(!node.getReferencingObject().isKeyValidForAllLanguages(node.getNodeName())) {
                        parentComp.setForeground(Color.RED);
                    }
                }
                else if(node.getType() == NodeType.MODULEPART) {
                    //search the referencing sub-nodes
                    parentComp.setForeground(Color.BLACK);
                    for(TreeNode subNode : node.getChildNodes() ) {
                        if(subNode.getType() == NodeType.KEY) {
                            if(!subNode.getReferencingObject().isKeyValidForAllLanguages(subNode.getNodeName())) {
                                parentComp.setForeground(Color.RED);
                                break;
                            }
                        }
                    }
                }
                else if(node.getType() == NodeType.MODULE) {
                    //search the referencing sub-nodes
                    parentComp.setForeground(Color.BLACK);
                    for(TreeNode partNode : node.getChildNodes()) {
                        for(TreeNode subNode : partNode.getChildNodes() ) {
                            if(subNode.getType() == NodeType.KEY) {
                                if(!subNode.getReferencingObject().isKeyValidForAllLanguages(subNode.getNodeName())) {
                                    parentComp.setForeground(Color.RED);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return parentComp;
    }

    
    @Override
    public Color getBackgroundNonSelectionColor() {
        return Color.WHITE;
    }

    @Override
    public Color getBackgroundSelectionColor() {
        return Color.WHITE;
    }
    
    

}
