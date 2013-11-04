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

import de.mulchprod.kajona.languageeditor.gui.TextAreas.TextAreaManager;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;

/**
 *
 * @author sidler
 */
public class GuiTreeSelectionListener implements javax.swing.event.TreeSelectionListener {

    private JTree sourceTree;

    public GuiTreeSelectionListener(JTree sourceTree) {
        this.sourceTree = sourceTree;
    }

    public void valueChanged(TreeSelectionEvent e) {
        //System.out.println("selection in tree changed");

        GuiTreeNode selectedNode = (GuiTreeNode)sourceTree.getLastSelectedPathComponent();
        if(selectedNode != null && selectedNode.isLeaf() && selectedNode.getReferencingTreeNode().getType() == NodeType.KEY) {
            //System.out.println("selected node: "+selectedNode+" / "+sourceTree.getName());

            TextAreaManager.getInstance().updateTextPanelsOnSelectionChanged(selectedNode.getReferencingTreeNode().getReferencingObject(), selectedNode.getReferencingTreeNode().getNodeName());
            
        }
        //load the selected file-Set
    }

}
