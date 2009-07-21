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

/*
 * KajonaLanguageEditorGuiConsoleView.java
 *
 * Created on Feb 4, 2009, 7:19:03 PM
 */

package de.mulchprod.kajona.languageeditor.gui;

import de.mulchprod.kajona.languageeditor.gui.coreconnector.CoreLogListener;

/**
 *
 * @author sidler
 */
public class KajonaLanguageEditorGuiConsoleView extends javax.swing.JDialog {

    /** Creates new form KajonaLanguageEditorGuiConsoleView */
    public KajonaLanguageEditorGuiConsoleView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initInternalComponents();
    }


    private void initInternalComponents() {
        //register with the listener
        CoreLogListener.getInstance().setViewToUpdate(this);
    }

    public void updateTextField(String strToDisplay) {
        this.consoleTextArea.setText(strToDisplay);
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        consoleTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(de.mulchprod.kajona.languageeditor.gui.KajonaLanguageEditorGuiApp.class).getContext().getResourceMap(KajonaLanguageEditorGuiConsoleView.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        consoleTextArea.setColumns(20);
        consoleTextArea.setEditable(false);
        consoleTextArea.setFont(resourceMap.getFont("consoleTextArea.font")); // NOI18N
        consoleTextArea.setRows(5);
        consoleTextArea.setName("consoleTextArea"); // NOI18N
        jScrollPane1.setViewportView(consoleTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                KajonaLanguageEditorGuiConsoleView dialog = new KajonaLanguageEditorGuiConsoleView(new javax.swing.JFrame(), false);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        //System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea consoleTextArea;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
