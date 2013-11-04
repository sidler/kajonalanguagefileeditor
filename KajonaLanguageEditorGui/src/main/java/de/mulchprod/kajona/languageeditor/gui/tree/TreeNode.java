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

import de.mulchprod.kajona.languageeditor.core.textfile.ILanguageFileSet;
import java.util.ArrayList;

/**
 *
 * @author sidler
 */
public class TreeNode {

    private NodeType type;

    private String nodeName;

    private ArrayList<TreeNode> childNodes = new ArrayList<TreeNode>();
    private TreeNode parentNode = null;

    private ILanguageFileSet referencingObject = null;

    public TreeNode(NodeType type, String nodeName) {
        this.type = type;
        this.nodeName = nodeName;
    }

    public void addChildNode(TreeNode childNode) {
        //test, if not already in list
        if(!childNodes.contains(childNode))
            childNodes.add(childNode);
    }

    public ArrayList<TreeNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(ArrayList<TreeNode> childNodes) {
        this.childNodes = childNodes;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public TreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public ILanguageFileSet getReferencingObject() {
        return referencingObject;
    }

    public void setReferencingObject(ILanguageFileSet referencingObject) {
        this.referencingObject = referencingObject;
    }

    
    

}
