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

package de.mulchprod.kajona.languageeditor.gui.TextAreas;

import de.mulchprod.kajona.languageeditor.core.textfile.ILanguageFileSet;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextArea;

/**
 *
 * @author sidler
 */
public class TextAreaFocusListener extends FocusAdapter {

    ILanguageFileSet fileSet;
    String key;
    String language;

    public TextAreaFocusListener(ILanguageFileSet fileSet, String key, String language) {
        this.fileSet = fileSet;
        this.key = key;
        this.language = language;
    }


    @Override
    public void focusLost(FocusEvent e) {
        //System.out.println("focus lost on "+this.key+"/"+this.language);
        //System.out.println("new value "+((JTextArea)e.getSource()).getText());
        //pass changes to core, pls
        String newValue = ((JTextArea)e.getSource()).getText();

        fileSet.updateValue(key, newValue, language);
    }

}
