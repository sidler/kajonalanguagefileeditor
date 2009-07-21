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
import de.mulchprod.kajona.languageeditor.gui.coreconnector.CoreConnector;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author sidler
 */
public class TextAreaManager {

    private static TextAreaManager instance = null;

    private ArrayList<String> adminLanguages;
    private ArrayList<String> portalLanguages;

    private JPanel textPanel = null;

    private TextAreaManager() {

    }

    public void resetPanel() {
        textPanel.removeAll();
    }

    public static TextAreaManager getInstance() {
        if(instance == null)
            instance = new TextAreaManager();

        return instance;
    }

    
    public void updateTextPanelsOnSelectionChanged(ILanguageFileSet selectedSet, String keySelected) {

        //remove old panes
        textPanel.removeAll();

        //JScrollPane valueScrollPane = new javax.swing.JScrollPane();
        //JScrollPane valueScrollPane2 = new javax.swing.JScrollPane();

        ArrayList<JScrollPane> panesToAdd = new ArrayList<JScrollPane>();
        ArrayList<String> languagesToLoop = new ArrayList<String>();

        //rewrite: only use the languages really available in the file
        languagesToLoop = selectedSet.getListOfLanguages();

        // admin or portal? variant 2: list all langauges for the current area
        //if(selectedSet.getArea().equals("admin"))
        //    languagesToLoop = adminLanguages;
        //else if(selectedSet.getArea().equals("portal"))
        //    languagesToLoop = portalLanguages;


        //a label of status infos
        JScrollPane tempScrollPaneInfo = new javax.swing.JScrollPane();
        JTextField infolabel = new JTextField();
        infolabel.setEditable(false);
        infolabel.setBackground(tempScrollPaneInfo.getBackground());
        infolabel.setBorder(BorderFactory.createEmptyBorder());
        infolabel.setText(selectedSet.getArea()+"/"+selectedSet.getModule()+"/"+selectedSet.getModulePart()+"/"+keySelected);
        tempScrollPaneInfo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        tempScrollPaneInfo.setViewportView(infolabel);
        panesToAdd.add(tempScrollPaneInfo);


        for(String lang : languagesToLoop) {

            JScrollPane tempScrollPane = new javax.swing.JScrollPane();
            
            JLabel label = new JLabel();
            label.setText(lang+":");
            tempScrollPane.setBorder(BorderFactory.createEmptyBorder());
            tempScrollPane.setViewportView(label);
            panesToAdd.add(tempScrollPane);
            

            tempScrollPane = new javax.swing.JScrollPane();

            JTextArea tempTextField = new javax.swing.JTextArea();
            tempTextField.setColumns(20);
            tempTextField.setRows(6);
            tempTextField.setName(lang); 
            tempTextField.setLineWrap(true);
            tempTextField.setText(selectedSet.getValueForKey(keySelected, lang));
            tempTextField.addFocusListener(new TextAreaFocusListener(selectedSet, keySelected, lang));
            tempTextField.setWrapStyleWord(true);
            tempTextField.setFont(tempTextField.getFont().deriveFont(new Float(CoreConnector.getInstance().getFontSize())));

            tempScrollPane.setViewportView(tempTextField);
            panesToAdd.add(tempScrollPane);

        }

        
        
        javax.swing.GroupLayout textValuePanelLayout = new javax.swing.GroupLayout(textPanel);
        textPanel.setLayout(textValuePanelLayout);

        ParallelGroup horizGroup = textValuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);

        for(JScrollPane pane : panesToAdd)
            horizGroup.addComponent(pane, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE);


        textValuePanelLayout.setHorizontalGroup(horizGroup);

        

        ParallelGroup vertGroup = textValuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        SequentialGroup seqGroup = textValuePanelLayout.createSequentialGroup();

        for(JScrollPane pane : panesToAdd) {
            seqGroup.addComponent(pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
            seqGroup.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        }

        textValuePanelLayout.setVerticalGroup(vertGroup.addGroup(seqGroup));
        
    }

    

    public void setScrollContainer(JPanel panel) {
        this.textPanel = panel;
    }

    public void setAdminLanguages(ArrayList<String> adminLanguages) {
        this.adminLanguages = adminLanguages;
    }

    public void setPortalLanguaes(ArrayList<String> portalLanguaes) {
        this.portalLanguages = portalLanguaes;
    }

    

}
