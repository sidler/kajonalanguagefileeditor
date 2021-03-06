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

import de.mulchprod.kajona.languageeditor.core.logger.ILELoggingListener;
import de.mulchprod.kajona.languageeditor.gui.KajonaLanguageEditorGuiConsoleView;

/**
 *
 * @author sidler
 */
public class CoreLogListener implements ILELoggingListener {

    private static CoreLogListener instance = null;
    private KajonaLanguageEditorGuiConsoleView viewToUpdate = null;

    StringBuffer logsPassed = new StringBuffer();

    private CoreLogListener() {

    }

    public static CoreLogListener getInstance() {
        if(instance == null) {
            instance = new CoreLogListener();
            
        }

        return instance;
    }

    public void logCallback(String logContent) {
        logsPassed.append(logContent).append('\n');
        if(viewToUpdate != null)
            viewToUpdate.updateTextField(logsPassed.toString());
    }

    public void setViewToUpdate(KajonaLanguageEditorGuiConsoleView viewToUpdate) {
        this.viewToUpdate = viewToUpdate;
        logCallback("");
    }




}
