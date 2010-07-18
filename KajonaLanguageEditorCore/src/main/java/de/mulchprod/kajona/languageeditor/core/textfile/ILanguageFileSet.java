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

import de.mulchprod.kajona.languageeditor.core.textfile.Textfile.KeyNotUniqueException;
import java.util.ArrayList;

/**
 *
 * @author sidler
 */
public interface ILanguageFileSet {

    public boolean updateKeyValue(String oldKey, String newKey) throws KeyNotUniqueException;

    public boolean updateValue(String key, String newValue, String language);

    public boolean createKeyValue(String keyToCreate) throws KeyNotUniqueException;

    public boolean deleteKeyValue(String keyToDelete);


    public ArrayList<String> getAllKeys();
    public String getValueForKey(String key, String language);

    public boolean isKeyValidForAllLanguages(String keyToTest);

    public void createNewFileForNewLanguage(String newLanguage);

    public ArrayList<String> getListOfLanguages();
    
    public String getModule();
    public String getArea();
    public String getModulePart();

}
