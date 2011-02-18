/*
 * Kajona Language File Editor Core
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

package de.mulchprod.kajona.languageeditor.core;


import de.mulchprod.kajona.languageeditor.core.config.Configuration;
import de.mulchprod.kajona.languageeditor.core.config.Configuration.ConfigNotSetException;
import de.mulchprod.kajona.languageeditor.core.filesystem.Filereader;
import de.mulchprod.kajona.languageeditor.core.filesystem.Filesystem;
import de.mulchprod.kajona.languageeditor.core.filesystem.Filesystem.FolderNotExistingException;
import de.mulchprod.kajona.languageeditor.core.filesystem.Filewriter;
import de.mulchprod.kajona.languageeditor.core.logger.ILELoggingListener;
import de.mulchprod.kajona.languageeditor.core.logger.LELogger;
import de.mulchprod.kajona.languageeditor.core.textfile.ILanguageFileSet;
import de.mulchprod.kajona.languageeditor.core.textfile.LanguageFileSet;
import de.mulchprod.kajona.languageeditor.core.textfile.LanguageFileSetManager;
import de.mulchprod.kajona.languageeditor.core.textfile.Textfile;
import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 *
 * @author sidler
 */
public class Filemanager {


      private ArrayList<LanguageFileSet> adminSets = new ArrayList<LanguageFileSet>();
      private ArrayList<LanguageFileSet> portalSets = new ArrayList<LanguageFileSet>();
      private boolean bitInitialized = false;


      private ILanguageFileSet fileSetMarkedForCopy = null;
      private String keyMarkedForCopy = null;


      public void addLogListener(ILELoggingListener listener) {
          LELogger.getInstance().addLoggingListener(listener);
      }

      public boolean needToSave() {
          for(LanguageFileSet set : adminSets) {
            if(set.needToSave())
                return true;
          }

          for(LanguageFileSet set : portalSets) {
            if(set.needToSave())
                return true;
          }

          return false;
      }


      public void resetCopyKey() {
          this.keyMarkedForCopy = null;
          this.fileSetMarkedForCopy = null;
      }

      /**
       * Copies the passed entry to the clipboard
       * @param sourceFileSet
       * @param keyToCopy
       */
      public void copyEntryByKey(ILanguageFileSet sourceFileSet, String keyToCopy) {
          this.fileSetMarkedForCopy = sourceFileSet;
          this.keyMarkedForCopy = keyToCopy;

          LELogger.getInstance().logInfo("Marked key "+keyToCopy+" for copy, source: "+sourceFileSet);
      }

      
      public boolean pasteEntryToFileset(ILanguageFileSet targetFileSet) throws CopySourceNotInitializedException {

          //load the source-elements
          if(fileSetMarkedForCopy == null || keyMarkedForCopy == null) {
              throw new CopySourceNotInitializedException("No source-key to copy found!");
          }

          LELogger.getInstance().logInfo("pasteing to set: "+targetFileSet);
          
          String newKey = targetFileSet.createNewValidKey(keyMarkedForCopy);
          for(String language : fileSetMarkedForCopy.getListOfLanguages()) {
              String value = fileSetMarkedForCopy.getValueForKey(keyMarkedForCopy, language);

              //paste it
              targetFileSet.updateValue(newKey,  value, language);
          }

          return true;
      }


      /**
       * Creates the files for a new language and places empty strings
       * @param language
       */
      public void createNewLanguageSet(String languageName) {
          LELogger.getInstance().logInfo("Creating new fileSets for language "+languageName);
          //admin
          LELogger.getInstance().logInfo("Starting to create language files for admin");
          createLanguageSetHelper(languageName, adminSets);

          //portal
          LELogger.getInstance().logInfo("Starting to create language files for portal");
          createLanguageSetHelper(languageName, portalSets);
      }

      private void createLanguageSetHelper (String languageName, ArrayList<LanguageFileSet> fileSet) {
          for(LanguageFileSet file : fileSet)
              file.createNewFileForNewLanguage(languageName);
      }

      public String getBuildVersion() {
          ResourceBundle rb = ResourceBundle.getBundle("de.mulchprod.kajona.languageeditor.core.coreprops");
          return rb.getString("buildNumber");
      }

      public void setKajonaProjectPath(String newPath) {
          Configuration.getInstance().setKajonaProjectPath(newPath);
      }

      public String getKajonaProjectPath() {
        try {
            return Configuration.getInstance().getKajonaProjectPath();
        } catch (ConfigNotSetException ex) {
            
        }
        return "";
      }

      public void setProperty(String key, String value) {
          Configuration.getInstance().setProperty(key, value);
      }

      public String getProperty(String name) {
          return Configuration.getInstance().getProperty(name);
      }

      public int getStringMinLength() {
          return Configuration.getInstance().getMinStringLength();
      }

      public void setStringMinLength(int length) {
          Configuration.getInstance().setMinStringLength(length);
      }

      public void saveSetttings() {
          Configuration.getInstance().writeConfigToFile();
      }

      public ArrayList<String> getListOfPortalLanguages() {
          ArrayList<String> returnList = new ArrayList<String>();
          for(ILanguageFileSet set : portalSets) {
              for(String language : set.getListOfLanguages())
                  if(!returnList.contains(language))
                      returnList.add(language);
          }

          return returnList;
      }

      public ArrayList<String> getListOfAdminLanguages() {
          ArrayList<String> returnList = new ArrayList<String>();
          for(ILanguageFileSet set : adminSets) {
              for(String language : set.getListOfLanguages())
                  if(!returnList.contains(language))
                      returnList.add(language);
          }

          return returnList;
      }


      public boolean writeProjectFiles() {
          return writeProjectFiles(false);
      }
      
      public boolean writeProjectFiles(boolean forceWrite) {
          Filewriter writer = new Filewriter();
          writer.writeTextfilesToFiles(adminSets, "", forceWrite);
          writer.writeTextfilesToFiles(portalSets, "", forceWrite);

          return false;
      }



      public void readProjectFiles() throws ConfigNotSetException, FolderNotExistingException {

          TreeMap<String, Textfile> adminFiles = new TreeMap<String, Textfile>();
          TreeMap<String, Textfile> portalFiles = new TreeMap<String, Textfile>();



          Configuration config = Configuration.getInstance();
          Filesystem filesystem = new Filesystem();
          LanguageFileSetManager setManager =  new LanguageFileSetManager();

          //set up admin-files
          String adminFolder = config.getKajonaProjectPath()+config.getKajonaTextFolder()+config.getAdminFolder();

          ArrayList<File> moduleFolders = filesystem.getFoldersForFolder(adminFolder);
          for(File singleFolder : moduleFolders) {
              LELogger.getInstance().logInfo("Scanning "+singleFolder.getAbsolutePath());
              ArrayList<File> textFiles = filesystem.getPhpFilesInFolder(singleFolder.getAbsolutePath());
              for(File singleFile : textFiles) {
                  LELogger.getInstance().logInfo(" Found textfile "+singleFile.getName());

                  //set up the file-instance
                  Filereader filereader = new Filereader();
                  Textfile textfile = filereader.generateTextfileFromFile(singleFile, "admin");
                  adminFiles.put(textfile.getSourcePath(), textfile);
              }
          }


          //set up portal-files
          String portalFolder = config.getKajonaProjectPath()+config.getKajonaTextFolder()+config.getPortalFolder();

          moduleFolders = filesystem.getFoldersForFolder(portalFolder);
          for(File singleFolder : moduleFolders) {
              LELogger.getInstance().logInfo("Scanning "+singleFolder.getAbsolutePath());
              ArrayList<File> textFiles = filesystem.getPhpFilesInFolder(singleFolder.getAbsolutePath());
              for(File singleFile : textFiles) {
                  LELogger.getInstance().logInfo(" Found textfile "+singleFile.getName());

                  //set up the file-instance
                  Filereader filereader = new Filereader();
                  Textfile textfile = filereader.generateTextfileFromFile(singleFile, "portal");
                  portalFiles.put(textfile.getSourcePath(), textfile);
              }
          }


          this.adminSets = setManager.generateLanguageFileSet(adminFiles);
          this.portalSets = setManager.generateLanguageFileSet(portalFiles);
          this.bitInitialized = true;
      }

    public ArrayList<? extends ILanguageFileSet> getAdminSets() throws LanguageCoreNotInitializedException {
        if(!bitInitialized)
            throw new LanguageCoreNotInitializedException("Core not initialized, run readProjectFiles() before!");
        
        return this.adminSets;
        
    }

    public ArrayList<? extends ILanguageFileSet> getPortalSets() throws LanguageCoreNotInitializedException {
        if(!bitInitialized)
            throw new LanguageCoreNotInitializedException("Core not initialized, run readProjectFiles() before!");

        return this.portalSets;
    }



    public void printFiles() {
          LELogger.getInstance().logInfo("Dumping files found: ");
          LELogger.getInstance().logInfo("Admin files: ");
          for(LanguageFileSet set : adminSets)
              LELogger.getInstance().logInfo(set+"");
          LELogger.getInstance().logInfo("Portal files: ");
          for(LanguageFileSet set : portalSets)
              LELogger.getInstance().logInfo(set+"");

    }

    public class LanguageCoreNotInitializedException extends CoreBaseException {
        public LanguageCoreNotInitializedException(String cause) {
            super(cause);
        }
    }

    public class CopySourceNotInitializedException extends CoreBaseException {
        public CopySourceNotInitializedException(String cause) {
            super(cause);
        }
    }

    public String getKeyMarkedForCopy() {
        return keyMarkedForCopy;
    }


    
}
