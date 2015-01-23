/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.ics.upjs.todo.gui;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author kulexpipiens
 */
public class VyberObdobiaPreGrafForm extends javax.swing.JDialog {

    java.awt.Frame parent;

    private JDatePickerImpl datePickerOd;
    private JDatePickerImpl datePickerDo;

    private static final Color background = new Color(255, 255, 204);

    private int rok = Calendar.getInstance().get(Calendar.YEAR);
    private int mesiac = Calendar.getInstance().get(Calendar.MONTH);
    private int den = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    /**
     * Creates new form VyberObdobiaPreGrafForm
     *
     * @param parent rodicovske okno
     * @param modal modalita
     */
    public VyberObdobiaPreGrafForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.parent = parent;
        this.setTitle("Grafické znázornenie udalostí");

        nastavOdDoVyberyDatumov();
    }

    private void nastavOdDoVyberyDatumov() {
        nastavOd();
        nastavDo();
    }

    private void nastavDo() {
        UtilDateModel modelDo = new UtilDateModel();
        JDatePanelImpl datePanelDo = new JDatePanelImpl(modelDo);
        datePickerDo = new JDatePickerImpl(datePanelDo);
        datePickerDo.setBackground(background);
        datePickerDo.getModel().setDate(rok, mesiac, den);
        datePickerDo.getModel().setSelected(true);
        panelDo.add(datePickerDo);
    }

    private void nastavOd() {
        UtilDateModel modelOd = new UtilDateModel();
        JDatePanelImpl datePanelOd = new JDatePanelImpl(modelOd);
        datePickerOd = new JDatePickerImpl(datePanelOd);
        datePickerOd.setBackground(background);
        datePickerOd.getModel().setDate(rok, mesiac, den);
        datePickerOd.getModel().setSelected(true);
        panelOd.add(datePickerOd);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblInfo = new javax.swing.JLabel();
        lblDatumOd = new javax.swing.JLabel();
        lblDatumDo = new javax.swing.JLabel();
        panelOd = new javax.swing.JPanel();
        panelDo = new javax.swing.JPanel();
        btnZobrazGraf = new javax.swing.JButton();
        btnZavriet = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblInfo.setText("Zobrazí časový prehľad udalostí:");

        lblDatumOd.setText("Dátum od:");

        lblDatumDo.setText("Dátum do:");

        panelOd.setBackground(new java.awt.Color(255, 255, 204));

        panelDo.setBackground(new java.awt.Color(255, 255, 204));

        btnZobrazGraf.setText("Zobraz graf");
        btnZobrazGraf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZobrazGrafActionPerformed(evt);
            }
        });

        btnZavriet.setText("Zavrieť");
        btnZavriet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZavrietActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblInfo)
                        .addGap(0, 200, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDatumOd)
                            .addComponent(lblDatumDo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelOd, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .addComponent(panelDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnZavriet)
                        .addGap(18, 18, 18)
                        .addComponent(btnZobrazGraf)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInfo)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDatumOd)
                    .addComponent(panelOd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDatumDo)
                    .addComponent(panelDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnZobrazGraf)
                    .addComponent(btnZavriet))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnZavrietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZavrietActionPerformed
        dispose();
    }//GEN-LAST:event_btnZavrietActionPerformed

    private void btnZobrazGrafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZobrazGrafActionPerformed
        GrafForm grafForm = new GrafForm(parent, true,
                (Date) datePickerOd.getModel().getValue(),
                (Date) datePickerDo.getModel().getValue());
        grafForm.setVisible(true);
    }//GEN-LAST:event_btnZobrazGrafActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(VyberObdobiaPreGrafForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VyberObdobiaPreGrafForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VyberObdobiaPreGrafForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VyberObdobiaPreGrafForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VyberObdobiaPreGrafForm dialog = new VyberObdobiaPreGrafForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnZavriet;
    private javax.swing.JButton btnZobrazGraf;
    private javax.swing.JLabel lblDatumDo;
    private javax.swing.JLabel lblDatumOd;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JPanel panelDo;
    private javax.swing.JPanel panelOd;
    // End of variables declaration//GEN-END:variables
}