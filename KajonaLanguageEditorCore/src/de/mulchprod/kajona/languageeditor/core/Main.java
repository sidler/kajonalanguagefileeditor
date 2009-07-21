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

import de.mulchprod.kajona.languageeditor.core.Filemanager.LanguageCoreNotInitializedException;
import de.mulchprod.kajona.languageeditor.core.config.Configuration.ConfigNotSetException;
import de.mulchprod.kajona.languageeditor.core.filesystem.Filesystem.FolderNotExistingException;
import de.mulchprod.kajona.languageeditor.core.textfile.ILanguageFileSet;


/**
 *
 * @author sidler
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Filemanager manager = new Filemanager();
            
            manager.readProjectFiles();
            manager.printFiles();

           
            System.out.println("Admin-languages: "+manager.getListOfAdminLanguages());
            System.out.println("Portal-languages: "+manager.getListOfPortalLanguages());
            
            for(ILanguageFileSet fileSet : manager.getPortalSets()) {
                //System.out.println("Keys in "+fileSet.getArea()+"/"+fileSet.getModule()+"/"+fileSet.getModulePart()+":");
                //for(String key : fileSet.getAllKeys())
                    //System.out.println("  --> "+key+" valid in all languages: "+fileSet.isKeyValidForAllLanguages(key));
            }
            
            //key = manager.getPortalSets().get(2).getAllKeys().get(0);
            //manager.getPortalSets().get(2).deleteKeyValue(key);

            /*
            String key = manager.getAdminSets().get(10).getAllKeys().get(0);

            manager.getAdminSets().get(10).updateValue(key, "!!!!new valuqe!!!!", manager.getListOfAdminLanguages().get(0));
            manager.getAdminSets().get(10).updateKeyValue(key, "!!! new key !!!");
            
            
            
            String newKey = "alleJahreWieder";
            String newValue = "hans dampf";
            manager.getPortalSets().get(4).updateValue("neu neu", "test", "en");



            manager.writeProjectFiles();
            */

            
            System.out.println("Admin-languages: "+manager.getListOfAdminLanguages());
            System.out.println("Portal-languages: "+manager.getListOfPortalLanguages());
            System.out.println("Build: "+manager.getBuildVersion());

            System.out.println("Creating new file-set for language fr");
            manager.createNewLanguageSet("pt");
            
            
        } catch (LanguageCoreNotInitializedException ex) {
            System.out.println(ex);
        } catch (FolderNotExistingException ex) {
            System.out.println(ex);
        } catch (ConfigNotSetException ex) {
            System.out.println(ex);
        }
            
        
    }

}
