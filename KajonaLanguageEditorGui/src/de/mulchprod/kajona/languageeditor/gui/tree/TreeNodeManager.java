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
import java.util.HashMap;

/**
 *
 * @author sidler
 */
public class TreeNodeManager {

    private static TreeNodeManager instance = null;

    private HashMap<String, TreeNode> adminModuleNodes = new HashMap<String, TreeNode>();
    private HashMap<String, TreeNode> portalModuleNodes = new HashMap<String, TreeNode>();


    public void resetCacheSets() {
        adminModuleNodes.clear();
        portalModuleNodes.clear();
    }

    private TreeNodeManager() {
    }

    public static TreeNodeManager getInstance() {
        if(instance == null)
            instance = new TreeNodeManager();

        return instance;
    }

    public TreeNode getOrCreateModuleNode(String key, AreaType area) {

        HashMap<String, TreeNode> nodeSet = new HashMap<String, TreeNode>();

        if(area == AreaType.ADMIN)
            nodeSet = adminModuleNodes;
        else if(area == AreaType.PORTAL)
            nodeSet = portalModuleNodes;

        if(nodeSet.containsKey(key)) {
            return nodeSet.get(key);
        }
        else {
            TreeNode node = new TreeNode(NodeType.MODULE, key);
            nodeSet.put(key, node);
            return node;
        }

    }

    public TreeNode getOrCreateNode(String key, TreeNode parentNode) {
        return getOrCreateNode(key, parentNode, null);
    }

    public TreeNode getOrCreateNode(String key, TreeNode parentNode, ILanguageFileSet referencingObject) {

        //loop the child nodes
        ArrayList<TreeNode> childNodes = parentNode.getChildNodes();

        for(TreeNode childNode : childNodes) {
            if(childNode.getNodeName().equals(key)) {
                return childNode;
            }
        }

        //if we reached up till here, no child node was found. create a new one.
        NodeType typeToCreate;
        if(parentNode.getType() == NodeType.MODULE)
            typeToCreate = NodeType.MODULEPART;
        else
            typeToCreate = NodeType.KEY;

        TreeNode newNode = new TreeNode(typeToCreate, key);
        newNode.setReferencingObject(referencingObject);
        newNode.setParentNode(parentNode);
        parentNode.addChildNode(newNode);

        return newNode;

    }

    
}
