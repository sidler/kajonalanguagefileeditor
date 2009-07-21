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

package de.mulchprod.kajona.languageeditor.gui.coreconnector;

import de.mulchprod.kajona.languageeditor.core.Filemanager;
import de.mulchprod.kajona.languageeditor.core.Filemanager.LanguageCoreNotInitializedException;
import de.mulchprod.kajona.languageeditor.core.config.Configuration.ConfigNotSetException;
import de.mulchprod.kajona.languageeditor.core.filesystem.Filesystem.FolderNotExistingException;
import de.mulchprod.kajona.languageeditor.core.logger.ILELoggingListener;
import de.mulchprod.kajona.languageeditor.core.textfile.ILanguageFileSet;
import de.mulchprod.kajona.languageeditor.gui.tree.AreaType;
import de.mulchprod.kajona.languageeditor.gui.tree.GuiTreeNode;
import de.mulchprod.kajona.languageeditor.gui.tree.TreeNode;
import de.mulchprod.kajona.languageeditor.gui.tree.TreeNodeManager;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author sidler
 */
public class CoreConnector {

    private Filemanager filemanager;
    private static CoreConnector instance = null;
    private TreeNodeManager treeNodeManager;

    private boolean bitSetupError = false;

    private CoreConnector() {
        initConnector();
    }

    private void initConnector() {
        filemanager = new Filemanager();

        try {
            filemanager.readProjectFiles();

            treeNodeManager = TreeNodeManager.getInstance();

        } catch (ConfigNotSetException ex) {
            bitSetupError = true;
        } catch (FolderNotExistingException ex) {
            bitSetupError = true;
        }
    }

    private CoreConnector(ILELoggingListener listener) {
        filemanager = new Filemanager();
        filemanager.addLogListener(listener);
        initConnector();
    }

    public void createNewLanguage(String newLanguage) {
        filemanager.createNewLanguageSet(newLanguage);
    }

    public String getCoreBuild() {
        return filemanager.getBuildVersion();
    }

    public boolean needToSave() {
        return filemanager.needToSave();
    }

    public void saveProjectfiles() {
        filemanager.writeProjectFiles();
    }

    public static CoreConnector getInstance()  {
        if(instance == null) {
            instance = new CoreConnector();
        }

        return instance;
    }

    public static CoreConnector getInstance(ILELoggingListener listenerToRegsiter)  {
        if(instance == null) {
            instance = new CoreConnector(listenerToRegsiter);
        }

        return instance;
    }

    public boolean isBitSetupError() {
        return bitSetupError;
    }

    public void registerLogListener(ILELoggingListener listener) {
        filemanager.addLogListener(listener);
    }
    

    public ArrayList<String> getAdminLanguages() {
        return filemanager.getListOfAdminLanguages();
    }

    public ArrayList<String> getPortalLanguages() {
        return filemanager.getListOfPortalLanguages();
    }

    public int getStringMinLength() {
        return filemanager.getStringMinLength();
    }

    public void setStringMinLength(int length) {
        filemanager.setStringMinLength(length);
    }

    public int getFontSize() {
        if(filemanager.getProperty("editorFontSize") != null)
            return Integer.parseInt(filemanager.getProperty("editorFontSize"));
        else
            return 10;
    }

    public void setFontSize(int size) {
        filemanager.setProperty("editorFontSize", size+"");
    }

    public String getProjectPath() {
        return filemanager.getKajonaProjectPath();
    }

    public void setProjectPath(String path) {
        filemanager.setKajonaProjectPath(path);
    }

    public void saveSettings() {
        filemanager.saveSetttings();
    }

    public void initPortalTree(JTree tree) {
        ArrayList<TreeNode> moduleNodes = new ArrayList<TreeNode>();
        try {
            DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode("");
            for (ILanguageFileSet singleSet : filemanager.getPortalSets()) {
                //create module-nodes
                TreeNode moduleNode = treeNodeManager.getOrCreateModuleNode(singleSet.getModule(), AreaType.PORTAL);
                TreeNode modulePartNode = treeNodeManager.getOrCreateNode(singleSet.getModulePart(), moduleNode, singleSet);
                //loop the key-nodes
                for(String key : singleSet.getAllKeys()) {
                    treeNodeManager.getOrCreateNode(key, modulePartNode, singleSet);
                }
                if(!moduleNodes.contains(moduleNode))
                    moduleNodes.add(moduleNode);
                
            }
            for(TreeNode moduleNode : moduleNodes)
                newRoot.add(getInternalHierachyAsGuiNodes(moduleNode));
            
            DefaultTreeModel treemodel = (DefaultTreeModel)tree.getModel();
            treemodel.setRoot(newRoot);
            treemodel.reload();
            
        } catch (LanguageCoreNotInitializedException ex) {
            Logger.getLogger(CoreConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initAdminTree(JTree tree) {
        ArrayList<TreeNode> moduleNodes = new ArrayList<TreeNode>();
        try {
            DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode("");
            for (ILanguageFileSet singleSet : filemanager.getAdminSets()) {
                //create module-nodes
                TreeNode moduleNode = treeNodeManager.getOrCreateModuleNode(singleSet.getModule(), AreaType.ADMIN);
                TreeNode modulePartNode = treeNodeManager.getOrCreateNode(singleSet.getModulePart(), moduleNode, singleSet);
                //loop the key-nodes
                for(String key : singleSet.getAllKeys()) {
                    treeNodeManager.getOrCreateNode(key, modulePartNode, singleSet);
                }
                if(!moduleNodes.contains(moduleNode))
                    moduleNodes.add(moduleNode);

            }
            for(TreeNode moduleNode : moduleNodes)
                newRoot.add(getInternalHierachyAsGuiNodes(moduleNode));

            DefaultTreeModel treemodel = (DefaultTreeModel)tree.getModel();

            treemodel.setRoot(newRoot);
            treemodel.reload();

        } catch (LanguageCoreNotInitializedException ex) {
            Logger.getLogger(CoreConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private GuiTreeNode getInternalHierachyAsGuiNodes(TreeNode moduleRootNode) {
        GuiTreeNode guiModuleNode = new GuiTreeNode(moduleRootNode);

        for(TreeNode modulePartNode : moduleRootNode.getChildNodes()) {
            GuiTreeNode guiModulePartNode = new GuiTreeNode(modulePartNode);
            guiModuleNode.add(guiModulePartNode);

            for(TreeNode keyNode : modulePartNode.getChildNodes()) {
                GuiTreeNode guiKeyNode = new GuiTreeNode(keyNode);
                guiModulePartNode.add(guiKeyNode);
            }
        }

        return guiModuleNode;
    }

}
