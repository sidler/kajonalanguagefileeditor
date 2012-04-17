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

package de.mulchprod.kajona.languageeditor.core.textfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sidler
 */
public class LanguageFileSetManager {
    

    public ArrayList<LanguageFileSet> generateLanguageFileSet(Map<String, Textfile> fileMap) {

        ArrayList<LanguageFileSet> returnFileSet = new ArrayList<LanguageFileSet>();

        for(Textfile file : fileMap.values()) {
            LanguageFileSet fileSet = getFileSetFromList(file.getModule(), file.getModulepart(), returnFileSet);
            if(fileSet != null) {
                fileSet.addTextfile(file);
            }
            else
                returnFileSet.add(new LanguageFileSet(file));
        }


        return returnFileSet;
    }




    public LanguageFileSet getFileSetFromList(String module, String modulepart, List<LanguageFileSet> set) {
        //LELogger.getInstance().logInfo("searching for: "+module+"/"+modulepart+"/"+area);

        for(LanguageFileSet fileSet : set) {
            if(   fileSet.getModule().equals(module)
               && fileSet.getModulePart().equals(modulepart)) {
                
                return fileSet;
            }
        }

        return null;

    }
    
}
