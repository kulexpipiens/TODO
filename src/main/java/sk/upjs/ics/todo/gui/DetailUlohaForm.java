package sk.upjs.ics.todo.gui;

import java.awt.Frame;
import sk.upjs.ics.todo.dao.Factory;
import sk.upjs.ics.todo.dao.UlohaDao;
import sk.upjs.ics.todo.entity.Uloha;

public class DetailUlohaForm extends javax.swing.JDialog {

    private Uloha uloha;
    private final UlohaDao ulohaDao = Factory.INSTANCE.ulohaDao();

    public DetailUlohaForm() {
        initComponents();
    }

    public DetailUlohaForm(Frame parent) {
        this(parent, true);
    }

    public DetailUlohaForm(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        GuiFactory.INSTANCE.centruj(this);
    }

    // konštruktor, vytvorí detailné okno s údajmi o vybranej úlohe
    public DetailUlohaForm(Uloha uloha, Frame parent) {
        this(parent, true);
        this.uloha = uloha;
        lblPopis.setText(uloha.getPopis());
        lblNazov.setText(uloha.getNazov());
        lblPriorita.setText(uloha.getPriorita());

        lblDatum.setText(Integer.toString(uloha.getDatum().getDate()) + '.'
                + Integer.toString(uloha.getDatum().getMonth() + 1) + '.'
                + Integer.toString(uloha.getDatum().getYear() + 1900));
        String hodiny = "";
        String minuty = "";
        if (uloha.getDatum().getHours() < 10) {
            hodiny = "0" + Integer.toString(uloha.getDatum().getHours());
        } else {
            hodiny = Integer.toString(uloha.getDatum().getHours());
        }
        if (uloha.getDatum().getMinutes() < 10) {
            minuty = "0" + Integer.toString(uloha.getDatum().getMinutes());
        } else {
            minuty = Integer.toString(uloha.getDatum().getMinutes());
        }

        lblCas.setText(hodiny + ':' + minuty);

        lblKategoria.setText(uloha.getKategoria().getNazov());
        if (uloha.getStav()) {
            checkBox.setSelected(true);
        } else {
            checkBox.setSelected(false);
        }

        lblTrvanie.setText(Integer.toString(uloha.getTrvanie()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNazov = new javax.swing.JLabel();
        lblZnacka1 = new javax.swing.JLabel();
        lblDatum = new javax.swing.JLabel();
        lblCas = new javax.swing.JLabel();
        lblPriorita = new javax.swing.JLabel();
        lblDatumUkazovatel = new javax.swing.JLabel();
        lblPrioritaUkazovatel = new javax.swing.JLabel();
        lblStavUkazovatel = new javax.swing.JLabel();
        lblTrvanie = new javax.swing.JLabel();
        lblKategoriaUkazovatel = new javax.swing.JLabel();
        checkBox = new javax.swing.JCheckBox();
        btnOk = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lblPopis = new javax.swing.JTextArea();
        lblTrvanieUkazovatel = new javax.swing.JLabel();
        lblKategoria = new javax.swing.JLabel();
        lblPozadie = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Detail úlohy");
        setMinimumSize(new java.awt.Dimension(445, 285));
        getContentPane().setLayout(null);

        lblNazov.setFont(new java.awt.Font("Gungsuh", 1, 12)); // NOI18N
        lblNazov.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        lblNazov.setOpaque(true);
        getContentPane().add(lblNazov);
        lblNazov.setBounds(10, 10, 220, 22);

        lblZnacka1.setBackground(new java.awt.Color(204, 153, 255));
        lblZnacka1.setFont(new java.awt.Font("Gungsuh", 0, 36)); // NOI18N
        lblZnacka1.setText("dori");
        getContentPane().add(lblZnacka1);
        lblZnacka1.setBounds(340, 200, 99, 43);

        lblDatum.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblDatum.setOpaque(true);
        getContentPane().add(lblDatum);
        lblDatum.setBounds(40, 200, 99, 22);

        lblCas.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblCas.setOpaque(true);
        getContentPane().add(lblCas);
        lblCas.setBounds(150, 200, 50, 23);

        lblPriorita.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblPriorita.setOpaque(true);
        getContentPane().add(lblPriorita);
        lblPriorita.setBounds(280, 30, 120, 22);

        lblDatumUkazovatel.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblDatumUkazovatel.setText("do:");
        getContentPane().add(lblDatumUkazovatel);
        lblDatumUkazovatel.setBounds(10, 200, 18, 22);

        lblPrioritaUkazovatel.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblPrioritaUkazovatel.setText("priorita:");
        getContentPane().add(lblPrioritaUkazovatel);
        lblPrioritaUkazovatel.setBounds(280, 10, 99, 19);

        lblStavUkazovatel.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblStavUkazovatel.setText("stav:");
        getContentPane().add(lblStavUkazovatel);
        lblStavUkazovatel.setBounds(280, 160, 90, 20);

        lblTrvanie.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblTrvanie.setOpaque(true);
        getContentPane().add(lblTrvanie);
        lblTrvanie.setBounds(280, 130, 120, 20);

        lblKategoriaUkazovatel.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblKategoriaUkazovatel.setText("kategória:");
        getContentPane().add(lblKategoriaUkazovatel);
        lblKategoriaUkazovatel.setBounds(280, 60, 90, 14);

        checkBox.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        checkBox.setText("Splnená");
        getContentPane().add(checkBox);
        checkBox.setBounds(280, 180, 115, 24);

        btnOk.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        btnOk.setText("OK");
        btnOk.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        getContentPane().add(btnOk);
        btnOk.setBounds(230, 210, 100, 30);

        lblPopis.setEditable(false);
        lblPopis.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        lblPopis.setColumns(20);
        lblPopis.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        lblPopis.setLineWrap(true);
        lblPopis.setRows(5);
        lblPopis.setBorder(null);
        jScrollPane1.setViewportView(lblPopis);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 40, 220, 140);

        lblTrvanieUkazovatel.setText("trvanie:");
        getContentPane().add(lblTrvanieUkazovatel);
        lblTrvanieUkazovatel.setBounds(280, 110, 60, 15);

        lblKategoria.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblKategoria.setOpaque(true);
        getContentPane().add(lblKategoria);
        lblKategoria.setBounds(280, 80, 120, 20);

        lblPozadie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detailform.jpg"))); // NOI18N
        getContentPane().add(lblPozadie);
        lblPozadie.setBounds(0, 0, 430, 250);
        lblPozadie.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (!uloha.getStav() && checkBox.isSelected()) {
            ulohaDao.oznacZaSplnenu(uloha);
        }
        if (uloha.getStav() && !checkBox.isSelected()) {
            ulohaDao.oznacZaNesplnenu(uloha);
        }

        dispose();
    }//GEN-LAST:event_btnOkActionPerformed

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
            java.util.logging.Logger.getLogger(DetailUlohaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetailUlohaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetailUlohaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetailUlohaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DetailUlohaForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOk;
    private javax.swing.JCheckBox checkBox;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCas;
    private javax.swing.JLabel lblDatum;
    private javax.swing.JLabel lblDatumUkazovatel;
    private javax.swing.JLabel lblKategoria;
    private javax.swing.JLabel lblKategoriaUkazovatel;
    private javax.swing.JLabel lblNazov;
    private javax.swing.JTextArea lblPopis;
    private javax.swing.JLabel lblPozadie;
    private javax.swing.JLabel lblPriorita;
    private javax.swing.JLabel lblPrioritaUkazovatel;
    private javax.swing.JLabel lblStavUkazovatel;
    private javax.swing.JLabel lblTrvanie;
    private javax.swing.JLabel lblTrvanieUkazovatel;
    private javax.swing.JLabel lblZnacka1;
    // End of variables declaration//GEN-END:variables

}
