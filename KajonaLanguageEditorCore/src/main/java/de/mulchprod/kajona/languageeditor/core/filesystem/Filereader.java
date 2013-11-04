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

package de.mulchprod.kajona.languageeditor.core.filesystem;

import de.mulchprod.kajona.languageeditor.core.logger.LELogger;
import de.mulchprod.kajona.languageeditor.core.textfile.Textentry;
import de.mulchprod.kajona.languageeditor.core.textfile.Textentry.EntryNotSetException;
import de.mulchprod.kajona.languageeditor.core.textfile.Textfile;
import de.mulchprod.kajona.languageeditor.core.textfile.Textfile.FileNotSetException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author sidler
 */
public class Filereader {

    public Textfile generateTextfileFromFile(File sourceFile) {

        //Generate the file itself
        Textfile textfile = new Textfile();
        
        try {
            textfile = getTextfileFromFileObject(sourceFile);
            
            //read source-file
            BufferedReader filereader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), "UTF8"));

            boolean bitEntryIsOpen = false;
            String singleRow = "";
            while(filereader.ready()) {
                String row = filereader.readLine();
                if(row == null)
                    break;

                row = row.trim();

                if(bitEntryIsOpen) {
                    if(row.endsWith(";")) {
                        
                        singleRow += row;
                        textfile.addTextEntry(generateTextentryFromString(singleRow));
                        
                        singleRow = "";
                        bitEntryIsOpen = false;
                    }
                    else
                        singleRow += row;
                }
                else {
                    if(row.startsWith("$lang") || row.startsWith("include")) {
                        if(!row.endsWith(";")) {
                            //entry spans across multiple rows...
                            //disabled, only one-row-entries by convention, so skipping
                            //singleRow = row;
                            //bitEntryIsOpen = true;
                            LELogger.getInstance().logInfo("ERROR: Entry not closing on same line, skipping: \n  "+row);
                        }
                        else
                            textfile.addTextEntry(generateTextentryFromString(row));
                    }
                }

            }

            filereader.close();

            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch(FileNotSetException ex) {
            ex.printStackTrace();
        } catch(EntryNotSetException ex) {
            ex.printStackTrace();
        }



        return textfile;
    }

    private Textentry generateTextentryFromString(String row) throws EntryNotSetException {
        
        Textentry entry = new Textentry();
        entry.generateKeyValuePairFromEntry(row);

        return entry;
    }

    
    private Textfile getTextfileFromFileObject(File sourceFile) throws FileNotSetException {
        Textfile file = new Textfile();
        file.setFileObject(sourceFile);
        file.setUpTextfileFromFileobject();

        return file;
    }

}
