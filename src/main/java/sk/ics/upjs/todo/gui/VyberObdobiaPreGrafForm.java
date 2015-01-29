package sk.ics.upjs.todo.gui;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import sk.ics.upjs.todo.dao.Factory;
import sk.ics.upjs.todo.entity.Uloha;

public class VyberObdobiaPreGrafForm extends javax.swing.JDialog {

    java.awt.Frame parent;

    private JDatePickerImpl datePickerOd;
    private JDatePickerImpl datePickerDo;

    private static final Color background = new Color(255, 255, 204);

    private final int rok = Calendar.getInstance().get(Calendar.YEAR);
    private final int mesiac = Calendar.getInstance().get(Calendar.MONTH);
    private final int den = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    /**
     * Creates new form VyberObdobiaPreGrafForm
     *
     * @param parent rodicovske okno
     * @param modal modalita
     */
    public VyberObdobiaPreGrafForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        GuiFactory.INSTANCE.centruj(this);

        this.parent = parent;
        getContentPane().setBackground(GuiFactory.INSTANCE.getFarbaPozadia());

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
        lblLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Grafické znázornenie udalostí");
        setResizable(false);

        lblInfo.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblInfo.setText("Zobraziť časový prehľad udalostí:");

        lblDatumOd.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblDatumOd.setText("Dátum od:");

        lblDatumDo.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        lblDatumDo.setText("Dátum do:");

        panelOd.setBackground(new java.awt.Color(255, 255, 204));

        panelDo.setBackground(new java.awt.Color(255, 255, 204));

        btnZobrazGraf.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        btnZobrazGraf.setText("Zobraziť graf...");
        btnZobrazGraf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZobrazGrafActionPerformed(evt);
            }
        });

        btnZavriet.setFont(new java.awt.Font("Gungsuh", 0, 11)); // NOI18N
        btnZavriet.setText("Zavrieť");
        btnZavriet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZavrietActionPerformed(evt);
            }
        });

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo.jpg"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblLogo)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnZavriet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnZobrazGraf))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInfo)
                            .addComponent(lblDatumDo)
                            .addComponent(lblDatumOd)
                            .addComponent(panelOd, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelDo, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lblLogo)
                .addGap(18, 18, 18)
                .addComponent(lblInfo)
                .addGap(18, 18, 18)
                .addComponent(lblDatumOd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lblDatumDo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnZobrazGraf)
                    .addComponent(btnZavriet))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnZavrietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZavrietActionPerformed
        dispose();
    }//GEN-LAST:event_btnZavrietActionPerformed

    private void btnZobrazGrafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZobrazGrafActionPerformed
        Date datumOd = (Date) datePickerOd.getModel().getValue();
        Date datumDo = (Date) datePickerDo.getModel().getValue();

        if (datumOd.after(datumDo)) {
            JOptionPane.showMessageDialog(this,
                    "Datum od nesmie byť za dátumom za!",
                    "Chyba", ERROR_MESSAGE);
            return;
        }

        Calendar calendarOd = Calendar.getInstance();
        calendarOd.setTime(datumOd);

        Calendar calendarDo = Calendar.getInstance();
        calendarDo.setTime(datumDo);

        // poziadame UlohyDao o ulohy, ktore ideme zobrazit v grafe
        List<Uloha> ulohy = Factory.INSTANCE.ulohaDao().dajZCasovehoIntervalu(calendarOd, calendarDo);

        for (Uloha uloha : ulohy) {
            System.out.println(uloha.getNazov());
        }

        // ak nie su v casovom intervale ziadne ulohy, tak budeme zbytocne zobrazovat graf
        if (ulohy.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "V zadanom časovom intervale sa nenechádzajú žiadne udalosti!",
                    "Informácia", INFORMATION_MESSAGE);
            return;
        }

        GrafForm grafForm = new GrafForm(parent, true, ulohy, datumOd, datumDo);
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
            java.util.logging.Logger.getLogger(VyberObdobiaPreGrafForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VyberObdobiaPreGrafForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VyberObdobiaPreGrafForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VyberObdobiaPreGrafForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JLabel lblLogo;
    private javax.swing.JPanel panelDo;
    private javax.swing.JPanel panelOd;
    // End of variables declaration//GEN-END:variables
}
