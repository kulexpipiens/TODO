package sk.upjs.ics.todo.gui;

import java.awt.Frame;
import java.util.*;
import javax.swing.ComboBoxModel;
import sk.upjs.ics.todo.modely.FilterTableModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import sk.upjs.ics.todo.dao.Factory;
import sk.upjs.ics.todo.dao.FilterDao;
import sk.upjs.ics.todo.dao.UlohaDao;
import sk.upjs.ics.todo.entity.Filter;
import sk.upjs.ics.todo.entity.Uloha;
import sk.upjs.ics.todo.modely.UlohaTableModel;

public class FiltrovaniaForm extends javax.swing.JDialog {

    private static final UlohaTableModel ulohaTableModel = new UlohaTableModel();
    private static final FilterDao filterDao = Factory.INSTANCE.filterDao();
    private static final UlohaDao ulohaDao = Factory.INSTANCE.ulohaDao();
    private static final FilterTableModel filterTableModel = new FilterTableModel();
    private static ComboBoxModel comboBoxModel;
    private static final TableRowSorter ulohyRowSorter = new TableRowSorter(ulohaTableModel);
    private static final TableRowSorter filtreRowSorter = new TableRowSorter(filterTableModel);

    private JDatePickerImpl datePickerOd;
    private JDatePickerImpl datePickerDo;

    public FiltrovaniaForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        comboBoxModel = Factory.INSTANCE.getKategoryCmbModel();
        initComponents();

        GuiFactory.INSTANCE.centruj(this);

        inicializujPanelOd();
        inicializujPanelDo();

        tblFiltre.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                tblFiltreSelectionValueChanged(e);
            }
        });
        tblUloha.setModel(ulohaTableModel);
        tblUloha.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUlohaMouseClicked(evt);
            }
        });
        tblFiltre.setModel(filterTableModel);
        tblFiltre.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFiltreMouseClicked(evt);
            }
        });
        aktualizujZoznamUloh();
        aktualizujZoznamFiltrov();
    }

    private void inicializujPanelDo() {
        UtilDateModel modelDo = new UtilDateModel();
        JDatePanelImpl datePanelDo = new JDatePanelImpl(modelDo);
        datePickerDo = new JDatePickerImpl(datePanelDo);
        datePickerDo.setBackground(GuiFactory.INSTANCE.getFarbaPozadia());
        panelDo.add(datePickerDo);
    }

    private void inicializujPanelOd() {
        UtilDateModel modelOd = new UtilDateModel();
        JDatePanelImpl datePanelOd = new JDatePanelImpl(modelOd);
        datePickerOd = new JDatePickerImpl(datePanelOd);
        datePickerOd.setBackground(GuiFactory.INSTANCE.getFarbaPozadia());
        panelOd.add(datePickerOd);
    }

    private void tblFiltreSelectionValueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (!tblFiltre.getSelectionModel().isSelectionEmpty()) {
                btnVymaz.setEnabled(true);
                btnUprav.setEnabled(true);
            } else {
                btnVymaz.setEnabled(false);
                btnUprav.setEnabled(false);
            }
        }
    }

    private void tblFiltreMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            datePickerOd.getModel().setSelected(false);
            datePickerDo.getModel().setSelected(false);
            int vybranyRiadok = tblFiltre.getSelectedRow();
            int vybranyIndexVModeli = tblFiltre.convertRowIndexToModel(vybranyRiadok);

            Filter vybranyFilter = filterTableModel.dajPodlaCislaRiadku(vybranyIndexVModeli);

            if (vybranyFilter == null) {
                return;
            }

            cmbKategoria.setSelectedItem(vybranyFilter.getKategoria().getNazov());
            cmbPriorita.setSelectedItem(vybranyFilter.getPriorita());

            if (vybranyFilter.isStav() == false) {
                cmbStav.setSelectedItem("Nesplnená");
            } else {
                cmbStav.setSelectedItem("Splnená");
            }

            if (vybranyFilter.getDatumOd() != null) {
                Date datum = vybranyFilter.getDatumOd();
                int den = datum.getDate();
                int mesiac = datum.getMonth();
                int rok = datum.getYear() + 1900;

                datePickerOd.getModel().setDate(rok, mesiac, den);
                datePickerOd.getModel().setSelected(true);
            }

            if (vybranyFilter.getDatumDo() != null) {
                Date datum = vybranyFilter.getDatumDo();
                int den = datum.getDate();
                int mesiac = datum.getMonth();
                int rok = datum.getYear() + 1900;

                datePickerDo.getModel().setDate(rok, mesiac, den);
                datePickerDo.getModel().setSelected(true);
            }
        }
    }

    private void tblUlohaMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int vybranyRiadok = tblUloha.getSelectedRow();
            int vybratyIndexVModeli = tblUloha.convertRowIndexToModel(vybranyRiadok);

            Uloha vybrataUloha
                    = ulohaTableModel.dajPodlaCislaRiadku(vybratyIndexVModeli);

            if (vybrataUloha == null) {
                return;
            }
            DetailUlohaForm detail = new DetailUlohaForm(vybrataUloha, (Frame) getOwner());
            detail.setVisible(true);

            // ak sme zmenili stav ulohy aby sa to hned prejavilo
            aktualizujZoznamUloh();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblZnacka = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUloha = new javax.swing.JTable();
        lblKategoria = new javax.swing.JLabel();
        lblPriorita = new javax.swing.JLabel();
        lblStav = new javax.swing.JLabel();
        lblDatum = new javax.swing.JLabel();
        lblUvod = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFiltre = new javax.swing.JTable();
        btnPridaj = new javax.swing.JButton();
        btnUprav = new javax.swing.JButton();
        btnVymaz = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbKategoria = new javax.swing.JComboBox();
        cmbPriorita = new javax.swing.JComboBox();
        cmbStav = new javax.swing.JComboBox();
        panelOd = new javax.swing.JPanel();
        panelDo = new javax.swing.JPanel();
        lblPozadie = new javax.swing.JLabel();

        jButton1.setText("OK");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Filtre");
        setMinimumSize(new java.awt.Dimension(642, 280));
        setResizable(false);
        getContentPane().setLayout(null);

        lblZnacka.setBackground(new java.awt.Color(204, 153, 255));
        lblZnacka.setFont(new java.awt.Font("Gungsuh", 0, 36)); // NOI18N
        lblZnacka.setText("dori");
        getContentPane().add(lblZnacka);
        lblZnacka.setBounds(350, 190, 70, 43);

        tblUloha.setBackground(new java.awt.Color(204, 204, 204));
        tblUloha.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tblUloha.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        tblUloha.setModel(ulohaTableModel);
        tblUloha.setGridColor(new java.awt.Color(204, 204, 204));
        tblUloha.setRowSorter(ulohyRowSorter);
        tblUloha.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblUloha);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(3, 11, 201, 200);

        lblKategoria.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblKategoria.setText("kategória");
        getContentPane().add(lblKategoria);
        lblKategoria.setBounds(218, 14, 53, 14);

        lblPriorita.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblPriorita.setText("priorita");
        getContentPane().add(lblPriorita);
        lblPriorita.setBounds(228, 45, 41, 14);

        lblStav.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblStav.setText("stav");
        getContentPane().add(lblStav);
        lblStav.setBounds(242, 74, 24, 14);

        lblDatum.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblDatum.setText("od");
        getContentPane().add(lblDatum);
        lblDatum.setBounds(210, 110, 30, 14);

        lblUvod.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblUvod.setText("vlastné filtre");
        getContentPane().add(lblUvod);
        lblUvod.setBounds(455, 11, 169, 14);

        tblFiltre.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        tblFiltre.setModel(filterTableModel);
        tblFiltre.setRowSorter(filtreRowSorter);
        tblFiltre.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblFiltre);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(460, 30, 163, 128);

        btnPridaj.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        btnPridaj.setText("pridaj..");
        btnPridaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPridajActionPerformed(evt);
            }
        });
        getContentPane().add(btnPridaj);
        btnPridaj.setBounds(461, 170, 90, 26);

        btnUprav.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        btnUprav.setText("uprav..");
        btnUprav.setEnabled(false);
        btnUprav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpravActionPerformed(evt);
            }
        });
        getContentPane().add(btnUprav);
        btnUprav.setBounds(510, 200, 90, 26);

        btnVymaz.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        btnVymaz.setText("vymaž");
        btnVymaz.setEnabled(false);
        btnVymaz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVymazActionPerformed(evt);
            }
        });
        getContentPane().add(btnVymaz);
        btnVymaz.setBounds(560, 170, 70, 26);

        btnOK.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        btnOK.setText("OK");
        btnOK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        getContentPane().add(btnOK);
        btnOK.setBounds(222, 173, 62, 16);
        getContentPane().add(jLabel2);
        jLabel2.setBounds(430, 109, 0, 0);

        jLabel5.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        jLabel5.setText("do");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(210, 140, 14, 14);

        cmbKategoria.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        cmbKategoria.setModel(comboBoxModel);
        getContentPane().add(cmbKategoria);
        cmbKategoria.setBounds(287, 11, 100, 24);

        cmbPriorita.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        cmbPriorita.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Nízka", "Stredná", "Vysoká" }));
        getContentPane().add(cmbPriorita);
        cmbPriorita.setBounds(287, 42, 100, 24);

        cmbStav.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        cmbStav.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Nesplnená", "Splnená" }));
        getContentPane().add(cmbStav);
        cmbStav.setBounds(287, 71, 100, 24);

        panelOd.setBackground(new java.awt.Color(255, 255, 204));
        getContentPane().add(panelOd);
        panelOd.setBounds(240, 100, 200, 30);

        panelDo.setBackground(new java.awt.Color(255, 255, 204));
        getContentPane().add(panelDo);
        panelDo.setBounds(240, 130, 200, 30);

        lblPozadie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/filtrovanieform.png"))); // NOI18N
        getContentPane().add(lblPozadie);
        lblPozadie.setBounds(0, 0, 650, 250);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        List<Uloha> ulohy = ulohaDao.dajVsetky();
        List<Uloha> filtrovaneUlohyKategoriou = new LinkedList<>();
        List<Uloha> filtrovaneUlohyPrioritou = new LinkedList<>();
        List<Uloha> filtrovaneUlohyStavom = new LinkedList<>();
        List<Uloha> filtrovaneUlohyOd = new LinkedList<>();
        List<Uloha> filtrovaneUlohyDo = new LinkedList<>();

        // odfiltruje na zaklade kategorie
        if (!cmbKategoria.getSelectedItem().equals(" ")) {
            for (Uloha uloha : ulohy) {
                if (uloha.getKategoria().getNazov().equals(cmbKategoria.getSelectedItem())) {
                    filtrovaneUlohyKategoriou.add(uloha);
                }
            }
        } else {
            filtrovaneUlohyKategoriou = ulohy;
        }

        // z tych na zaklade kategorie odfiltruje podla priority
        if (!cmbPriorita.getSelectedItem().equals(" ")) {
            if (filtrovaneUlohyKategoriou != null) {
                for (Uloha uloha : filtrovaneUlohyKategoriou) {
                    if (uloha.getPriorita().equals(cmbPriorita.getSelectedItem())) {
                        filtrovaneUlohyPrioritou.add(uloha);
                    }
                }
            }
        } else {
            filtrovaneUlohyPrioritou = filtrovaneUlohyKategoriou;
        }

        if (!cmbStav.getSelectedItem().equals(" ")) {
            if (filtrovaneUlohyPrioritou != null) {
                for (Uloha uloha : filtrovaneUlohyPrioritou) {
                    Boolean testovanaPodmienka = false;
                    if (cmbStav.getSelectedItem().equals("Splnená")) {
                        testovanaPodmienka = true;
                    }
                    if (uloha.getStav() == testovanaPodmienka) {
                        filtrovaneUlohyStavom.add(uloha);
                    }
                }
            }
        } else {
            filtrovaneUlohyStavom = filtrovaneUlohyPrioritou;
        }

        Date datumOd = null;
        Date datumDo = null;

        if (!datePickerOd.getJFormattedTextField().getText().isEmpty()) {
            datumOd = (Date) datePickerOd.getModel().getValue();
        }

        if (!datePickerDo.getJFormattedTextField().getText().isEmpty()) {
            datumDo = (Date) datePickerDo.getModel().getValue();
        }
        if (datumOd != null) {
            for (Uloha uloha : filtrovaneUlohyStavom) {
                if (uloha.getDatum().compareTo(datumOd) >= 0) {
                    filtrovaneUlohyOd.add(uloha);
                }
            }
        } else {
            filtrovaneUlohyOd = filtrovaneUlohyStavom;
        }

        if (datumDo != null) {
            for (Uloha uloha : filtrovaneUlohyOd) {
                if (uloha.getDatum().compareTo(datumDo) <= 0) {
                    filtrovaneUlohyDo.add(uloha);
                }
            }
        } else {
            filtrovaneUlohyDo = filtrovaneUlohyOd;

        }

        // filtrovaneUlohyDo je konecny vyfiltrovany zoznam
        ulohaTableModel.filtruj(filtrovaneUlohyDo);
    }//GEN-LAST:event_btnOKActionPerformed

    private void btnVymazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVymazActionPerformed
        int vybranyRiadok = tblFiltre.getSelectedRow();
        int vybranyIndexVModeli = tblFiltre.convertRowIndexToModel(vybranyRiadok);
        Filter vybratyFilter = filterTableModel.dajPodlaCislaRiadku(vybranyIndexVModeli);

        if (vybratyFilter == null) {
            return;
        }
        int tlacidlo = JOptionPane.showConfirmDialog(this,
                "Naozaj odstrániť?",
                "Odstránenie filtra",
                JOptionPane.YES_NO_OPTION
        );
        if (tlacidlo == JOptionPane.YES_OPTION) {
            filterDao.vymazFilter(vybratyFilter);
            aktualizujZoznamFiltrov();
        }
    }//GEN-LAST:event_btnVymazActionPerformed

    private void btnUpravActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpravActionPerformed
        int vybranyRiadok = tblFiltre.getSelectedRow();
        int vybranyIndexVModeli
                = tblFiltre.convertRowIndexToModel(vybranyRiadok);
        Filter vybranyFilter
                = filterTableModel.dajPodlaCislaRiadku(vybranyIndexVModeli);
        AddEditFiltreForm upravaFiltrovForm
                = new AddEditFiltreForm(vybranyFilter, (Frame) getOwner());
        upravaFiltrovForm.setVisible(true);
        aktualizujZoznamFiltrov();
    }//GEN-LAST:event_btnUpravActionPerformed

    private void btnPridajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPridajActionPerformed
        AddEditFiltreForm upravaFiltrovForm
                = new AddEditFiltreForm((Frame) getOwner());
        upravaFiltrovForm.setVisible(true);
        aktualizujZoznamFiltrov();
    }//GEN-LAST:event_btnPridajActionPerformed

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
            java.util.logging.Logger.getLogger(FiltrovaniaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FiltrovaniaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FiltrovaniaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FiltrovaniaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FiltrovaniaForm dialog = new FiltrovaniaForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnPridaj;
    private javax.swing.JButton btnUprav;
    private javax.swing.JButton btnVymaz;
    private javax.swing.JComboBox cmbKategoria;
    private javax.swing.JComboBox cmbPriorita;
    private javax.swing.JComboBox cmbStav;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDatum;
    private javax.swing.JLabel lblKategoria;
    private javax.swing.JLabel lblPozadie;
    private javax.swing.JLabel lblPriorita;
    private javax.swing.JLabel lblStav;
    private javax.swing.JLabel lblUvod;
    private javax.swing.JLabel lblZnacka;
    private javax.swing.JPanel panelDo;
    private javax.swing.JPanel panelOd;
    private javax.swing.JTable tblFiltre;
    private javax.swing.JTable tblUloha;
    // End of variables declaration//GEN-END:variables

    // refresh
    private void aktualizujZoznamFiltrov() {
        filterTableModel.obnov();
    }

    private void aktualizujZoznamUloh() {
        ulohaTableModel.obnov();
    }

}
