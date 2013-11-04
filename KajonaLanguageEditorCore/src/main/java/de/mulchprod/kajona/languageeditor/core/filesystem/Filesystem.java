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

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author sidler
 */
public class Filesystem {

    public ArrayList<File> getPhpFilesInFolder(String sourceFolder) {
        File folder = new File(sourceFolder);
        File[] files = folder.listFiles();

        ArrayList<File> returnList = new ArrayList<File>();
        for(File file : files) {
            if(file.isFile() && file.getName().endsWith(".php"))
                returnList.add(file);
        }

        return returnList;
    }

    public ArrayList<File> getFoldersForFolder(String sourceFolder) throws FolderNotExistingException {
        File folder = new File(sourceFolder);
        
        if(!folder.exists()) {
            throw new FolderNotExistingException(folder.getAbsolutePath());
        }

        File[] folders = folder.listFiles();
        ArrayList<File> returnList = new ArrayList<File>();
        for(File file : folders) {
            if(file.isDirectory() && !file.getName().contains(".svn"))
                returnList.add(file);
        }

        return returnList;
    }

    public class FolderNotExistingException extends Exception {
        public FolderNotExistingException(String cause) {
            super(cause);
        }
    }



}
