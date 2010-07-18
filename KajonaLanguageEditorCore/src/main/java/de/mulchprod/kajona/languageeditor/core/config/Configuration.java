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

package de.mulchprod.kajona.languageeditor.core.config;

import de.mulchprod.kajona.languageeditor.core.CoreBaseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author sidler
 */
public class Configuration {

    private String configFilename = ".kajonalanguageeditor";

    private static Configuration instance = null;
    private Properties propertySet = new Properties();

    private Configuration() {
        //read properties from file
        this.readConfigFromFile();
    }

    

    public static Configuration getInstance() {
        if(instance == null)
            instance = new Configuration();

        return instance;
    }

    private void readConfigFromFile() {
        String userDir = System.getProperty("user.home");
        File configFile = new File(userDir+File.separator+configFilename);
        System.out.println("config File: "+configFile);
        try {
            propertySet = new Properties();
            propertySet.load(new FileReader(configFile));
            
        } catch (FileNotFoundException ex) {
           // Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            //ex.printStackTrace();
        } catch (IOException ex) {
           // Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
           // ex.printStackTrace();
        }

    }

    public void writeConfigToFile() {
        String userDir = System.getProperty("user.home");
        File configFile = new File(userDir+File.separator+configFilename);
        
        try {
            configFile.createNewFile();
            propertySet.store(new FileWriter(configFile), "MulchProductions Kajona LanguageFileEditor. Do not change values below.");

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setProperty (String name, String value) {
        this.propertySet.setProperty(name, value);
    }

    public String getProperty(String name) {
        if(this.propertySet.containsKey(name))
            return this.propertySet.getProperty(name);
        else
            return null;
    }

    public int getMinStringLength() {
        if(propertySet.containsKey("minStringLength"))
            return Integer.parseInt(propertySet.getProperty("minStringLength"));
        else
            return 2;
    }

    public void setMinStringLength(int minStringLength) {
        propertySet.setProperty("minStringLength", minStringLength+"");
    }

    public String getKajonaProjectPath() throws ConfigNotSetException {
        if(!propertySet.containsKey("kajonaProjectPath"))
            throw new ConfigNotSetException("Project path is null");
        return propertySet.getProperty("kajonaProjectPath");
    }

    public void setKajonaProjectPath(String kajonaProjectPath) {
        propertySet.setProperty("kajonaProjectPath", kajonaProjectPath);
    }

    public String getKajonaTextFolder() {
        if(propertySet.containsKey("kajonaTextFolder"))
            return propertySet.getProperty("kajonaTextFolder");
        else
            return "/lang";
    }

    public void setKajonaTextFolder(String kajonaTextFolder) {
        propertySet.setProperty("kajonaTextFolder", kajonaTextFolder);
    }

    public String getAdminFolder() {
        if(propertySet.containsKey("adminFolder"))
            return propertySet.getProperty("adminFolder");
        else
            return "/admin";
    }

    public void setAdminFolder(String adminFolder) {
        propertySet.setProperty("adminFolder", adminFolder);
    }

    public String getPortalFolder() {
        if(propertySet.containsKey("portalFolder"))
            return propertySet.getProperty("portalFolder");
        else
            return "/portal";
    }

    public void setPortalFolder(String portalFolder) {
        propertySet.setProperty("portalFolder", portalFolder);
    }

    
    public class ConfigNotSetException extends CoreBaseException {
        public ConfigNotSetException(String s) { super(s); }
    }
    
}
