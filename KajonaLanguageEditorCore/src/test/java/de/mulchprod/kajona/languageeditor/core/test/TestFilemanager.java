/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.mulchprod.kajona.languageeditor.core.test;

import de.mulchprod.kajona.languageeditor.core.Filemanager;
import de.mulchprod.kajona.languageeditor.core.Filemanager.CopySourceNotInitializedException;
import de.mulchprod.kajona.languageeditor.core.Filemanager.LanguageCoreNotInitializedException;
import de.mulchprod.kajona.languageeditor.core.config.Configuration.ConfigNotSetException;
import de.mulchprod.kajona.languageeditor.core.filesystem.Filesystem.FolderNotExistingException;
import de.mulchprod.kajona.languageeditor.core.textfile.ILanguageFileSet;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author sidler
 */
public class TestFilemanager {

    Filemanager filemanager;

    @Before
    public void setUpTest() throws ConfigNotSetException, FolderNotExistingException {
        filemanager = new Filemanager();
        filemanager.readProjectFiles();
    }

    @Test
    public void testCopyKey() throws LanguageCoreNotInitializedException, CopySourceNotInitializedException {

        String sourceKey = "";
        ILanguageFileSet sourceSet = null;

        

        outer:
        for(ILanguageFileSet fileSet : filemanager.getLangSets()) {
            for(String key : fileSet.getAllKeys()) {
                sourceKey = key;
                sourceSet = fileSet;
                break outer;
            }
        }

        //Starting to paste right now should end in an exception

        System.out.println("key to copy: "+sourceKey+" @ languageFileSet: "+sourceSet.toString());
        filemanager.copyEntryByKey(sourceSet, sourceKey);

        //and paste it
        ILanguageFileSet targetSet = filemanager.getLangSets().get(filemanager.getLangSets().size()-2);
        System.out.println("pasting before languageFileSet: "+targetSet.toString());

        //get the new target-name
        String newKeyName = targetSet.createNewValidKey(sourceKey);

        filemanager.pasteEntryToFileset(targetSet);


        //validate results
        for(String language : targetSet.getListOfLanguages()) {
            System.out.println("comparing "+sourceKey+"@"+language+" with "+newKeyName);
            assertEquals(sourceSet.getValueForKey(sourceKey, language), targetSet.getValueForKey(newKeyName, language));
        }

        System.out.println("pasting after languageFileSet: "+targetSet.toString());
        
    }

    @Test (expected=CopySourceNotInitializedException.class)
    public void testPasteWithoutKey() throws LanguageCoreNotInitializedException, CopySourceNotInitializedException {
        filemanager.resetCopyKey();

        String sourceKey = "";
        ILanguageFileSet sourceSet = null;

        outer:
        for(ILanguageFileSet fileSet : filemanager.getLangSets()) {
            for(String key : fileSet.getAllKeys()) {
                sourceKey = key;
                sourceSet = fileSet;
                break outer;
            }
        }

        filemanager.pasteEntryToFileset(sourceSet);

    }
}
