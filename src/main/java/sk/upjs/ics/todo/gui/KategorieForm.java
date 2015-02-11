package sk.upjs.ics.todo.gui;

import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import org.springframework.dao.DataIntegrityViolationException;
import sk.upjs.ics.todo.dao.Factory;
import sk.upjs.ics.todo.entity.Kategoria;
import sk.upjs.ics.todo.dao.KategoriaDao;
import sk.upjs.ics.todo.modely.KategoriaTableModel;

public class KategorieForm extends javax.swing.JDialog {

    private static final KategoriaDao kategoriaDao = Factory.INSTANCE.kategoriaDao();

    private static final KategoriaTableModel kategoriaTableModel = new KategoriaTableModel();

    private static final TableRowSorter kategorieRowSorter = new TableRowSorter(kategoriaTableModel);

    // nové okno, kde sa menežujú kategórie
    public KategorieForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        GuiFactory.INSTANCE.centruj(this);

        tblKategoria.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                tblKategoriaSelectionValueChanged(e);
            }
        });
        tblKategoria.setModel(kategoriaTableModel);

        aktualizujZoznamKategorii();
    }

    // ak je nejaká kategória vybratá, umožní stlačenie tlačidiel "vymaž" a "uprav"
    private void tblKategoriaSelectionValueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (!tblKategoria.getSelectionModel().isSelectionEmpty()) {
                btnVymaz.setEnabled(true);
                btnUprav.setEnabled(true);
            } else {
                btnVymaz.setEnabled(false);
                btnUprav.setEnabled(false);
            }
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblKategoria = new javax.swing.JTable();
        btnPridaj = new javax.swing.JButton();
        btnUprav = new javax.swing.JButton();
        btnVymaz = new javax.swing.JButton();
        lblZnacka = new javax.swing.JLabel();
        txtNazov = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPopis = new javax.swing.JTextArea();
        lblPozadie = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Menežovanie kategórií");
        setMinimumSize(new java.awt.Dimension(465, 260));
        setResizable(false);
        getContentPane().setLayout(null);

        tblKategoria.setModel(kategoriaTableModel);
        tblKategoria.setRowSorter(kategorieRowSorter);
        tblKategoria.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblKategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKategoriaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKategoria);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 19, 229, 189);

        btnPridaj.setBackground(new java.awt.Color(204, 204, 204));
        btnPridaj.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        btnPridaj.setText("pridaj");
        btnPridaj.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPridaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPridajActionPerformed(evt);
            }
        });
        getContentPane().add(btnPridaj);
        btnPridaj.setBounds(272, 62, 66, 26);

        btnUprav.setBackground(new java.awt.Color(204, 204, 204));
        btnUprav.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        btnUprav.setText("uprav");
        btnUprav.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnUprav.setEnabled(false);
        btnUprav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpravActionPerformed(evt);
            }
        });
        getContentPane().add(btnUprav);
        btnUprav.setBounds(272, 19, 66, 25);

        btnVymaz.setBackground(new java.awt.Color(204, 204, 204));
        btnVymaz.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        btnVymaz.setText("vymaž");
        btnVymaz.setEnabled(false);
        btnVymaz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVymazActionPerformed(evt);
            }
        });
        getContentPane().add(btnVymaz);
        btnVymaz.setBounds(348, 19, 66, 25);

        lblZnacka.setBackground(new java.awt.Color(204, 153, 255));
        lblZnacka.setFont(new java.awt.Font("Gungsuh", 0, 36)); // NOI18N
        lblZnacka.setText("dori");
        getContentPane().add(lblZnacka);
        lblZnacka.setBounds(360, 180, 70, 43);

        txtNazov.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        txtNazov.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        txtNazov.setBorder(null);
        getContentPane().add(txtNazov);
        txtNazov.setBounds(272, 106, 138, 14);

        txtPopis.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        txtPopis.setColumns(20);
        txtPopis.setLineWrap(true);
        txtPopis.setRows(1);
        jScrollPane2.setViewportView(txtPopis);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(270, 130, 140, 50);

        lblPozadie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/kategorieform.jpg"))); // NOI18N
        lblPozadie.setMaximumSize(new java.awt.Dimension(475, 280));
        lblPozadie.setMinimumSize(new java.awt.Dimension(475, 280));
        lblPozadie.setPreferredSize(new java.awt.Dimension(475, 280));
        getContentPane().add(lblPozadie);
        lblPozadie.setBounds(0, 0, 470, 230);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // pridá novú kategóriu + refresh
    private void btnPridajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPridajActionPerformed
        Kategoria kategoria = new Kategoria();
        kategoria.setNazov(txtNazov.getText().trim());
        kategoria.setPopis(txtPopis.getText().trim());
        if (poliaNepresahujuMaximalnuDlzku(kategoria)
                && neprazdnyNazov(kategoria)
                && neduplicitnaKategoria(kategoria)) {
            kategoriaDao.pridajKategoriu(kategoria);
            aktualizujZoznamKategorii();
        }

    }//GEN-LAST:event_btnPridajActionPerformed

    // upraví vybranú kategóriu + refresh
    private void btnUpravActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpravActionPerformed
        int vybranyRiadok = tblKategoria.getSelectedRow();
        int vybranyIndexVModeli = tblKategoria.convertRowIndexToModel(vybranyRiadok);
        Kategoria vybrataKategoria = kategoriaTableModel.dajPodlaCislaRiadku(vybranyIndexVModeli);
        if (vybrataKategoria == null) {
            return;
        }

        // nesmieme upravovat nazov na prazdny alebo pridlhy
        if (neprazdnyNazov(vybrataKategoria)
                && poliaNepresahujuMaximalnuDlzku(vybrataKategoria)) {
            // ak sme zmenili nazov, overime, ci sme nezmenili na nazov inej 
            // kategorie
            if (!vybrataKategoria.getNazov().trim().equals(txtNazov.getText())) {
                // ak sme zmenili nazov na nazov inej kategorie, nerobime nic
                if (!neduplicitnaKategoria(vybrataKategoria)) {
                    return;
                }
            }
        } else {
            return;
        }
        
        vybrataKategoria.setNazov(txtNazov.getText());
        vybrataKategoria.setPopis(txtPopis.getText());

        kategoriaDao.upravKategoriu(vybrataKategoria);

        aktualizujZoznamKategorii();

        // zoznam kategorii sa aktualizuje, ziadna kategoria vtedy nie je vybrata
        // a teda by v tych textboxoch nemalo byt nic napisane
        txtNazov.setText("");
        txtPopis.setText("");
    }//GEN-LAST:event_btnUpravActionPerformed

    // vymaže vybranú kategóriu s použitím dialógového okna + refresh
    private void btnVymazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVymazActionPerformed
        int vybranyRiadok = tblKategoria.getSelectedRow();
        int vybranyIndexVModeli = tblKategoria.convertRowIndexToModel(vybranyRiadok);
        Kategoria vybrataKategoria = kategoriaTableModel.dajPodlaCislaRiadku(vybranyIndexVModeli);

        if (vybrataKategoria == null) {
            return;
        }
        int tlacidlo = JOptionPane.showConfirmDialog(this,
                "Naozaj odstrániť?",
                "Odstránenie úlohy",
                JOptionPane.YES_NO_OPTION
        );
        if (tlacidlo == JOptionPane.YES_OPTION) {
            try {
                kategoriaDao.vymazKategoriu(vybrataKategoria);
                aktualizujZoznamKategorii();
            } catch (DataIntegrityViolationException e) {
                /* Tato vynimka sa vyhodi ak su v databaze ulohy odkazujuce na 
                 danu kategoriu, aby nebolo mozne vymazat takuto kategoriu. 
                 Inym riesenim by bol kaskadovy delete v databaze. */
                JOptionPane.showMessageDialog(this,
                        "Nemožno vymazať kategóriu, ktorej úlohy alebo filtre existujú!\n"
                        + "Vymažte prv všetky úlohy a filtre obsahujúce danú kategóriu!",
                        "Chyba", ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnVymazActionPerformed

    // parametre vybranej kategórie vpíše do okienok
    private void tblKategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKategoriaMouseClicked
        int vybranyRiadok = tblKategoria.getSelectedRow();
        int vybranyIndexVModeli = tblKategoria.convertRowIndexToModel(vybranyRiadok);
        Kategoria vybrataKategoria = kategoriaTableModel.dajPodlaCislaRiadku(vybranyIndexVModeli);
        txtNazov.setText(vybrataKategoria.getNazov());
        txtPopis.setText(vybrataKategoria.getPopis());
    }//GEN-LAST:event_tblKategoriaMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KategorieForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KategorieForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KategorieForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KategorieForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                KategorieForm dialog = new KategorieForm(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPridaj;
    private javax.swing.JButton btnUprav;
    private javax.swing.JButton btnVymaz;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblPozadie;
    private javax.swing.JLabel lblZnacka;
    private javax.swing.JTable tblKategoria;
    private javax.swing.JTextField txtNazov;
    private javax.swing.JTextArea txtPopis;
    // End of variables declaration//GEN-END:variables

    // refresh
    private void aktualizujZoznamKategorii() {
        kategoriaTableModel.obnov();
    }

    private boolean neprazdnyNazov(Kategoria pridavanaKategoria) {
        if (pridavanaKategoria.getNazov().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vyplň názov!", "Chyba", ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean neduplicitnaKategoria(Kategoria pridavanaKategoria) {
        for (Kategoria kategoria : kategoriaDao.dajVsetky()) {
            if (pridavanaKategoria.getNazov().trim().equals(kategoria.getNazov().trim())) {
                JOptionPane.showMessageDialog(this, "Takáto kategória už existuje!", "Chyba", ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private boolean poliaNepresahujuMaximalnuDlzku(Kategoria kategoria) {
        if (kategoria.getNazov().length() > Kategoria.MAXIMALNA_DLZKA_NAZVU_KATEGORIE) {
            JOptionPane.showMessageDialog(this,
                    "Názov kategórie nesmie presiahnúť "
                    + Kategoria.MAXIMALNA_DLZKA_NAZVU_KATEGORIE + " znakov!",
                    "Chyba", ERROR_MESSAGE);
            return false;
        }
        if (kategoria.getPopis().length() > Kategoria.MAXIMALNA_DLZKA_POPISU) {
            JOptionPane.showMessageDialog(this,
                    "Popis kategórie nesmie presiahnúť "
                    + Kategoria.MAXIMALNA_DLZKA_POPISU + " znakov!",
                    "Chyba", ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
