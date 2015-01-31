package sk.upjs.ics.todo.gui;

import java.awt.Frame;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import sk.upjs.ics.todo.dao.Factory;
import sk.upjs.ics.todo.entity.Kategoria;
import sk.upjs.ics.todo.dao.KategoriaDao;
import sk.upjs.ics.todo.entity.Uloha;
import sk.upjs.ics.todo.dao.UlohaDao;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class AddEditUlohyForm extends javax.swing.JDialog {

    private static final UlohaDao ulohaDao = Factory.INSTANCE.ulohaDao();
    private static final KategoriaDao kategoriaDao = Factory.INSTANCE.kategoriaDao();
    private final ComboBoxModel modelKategorii = Factory.INSTANCE.getKategoryCmbModel();
    private Uloha uloha;
    private JDatePickerImpl datePicker;
    private boolean add = false;

    public AddEditUlohyForm(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        GuiFactory.INSTANCE.centruj(this);
    }    

    // konštruktor na pridanie úlohy
    public AddEditUlohyForm(Frame parent) {
        this(new Uloha(), parent);
        this.add = true;
    }

    // konštruktor na editáciu úlohy, všetky parametre úlohy vyplní do príslušných okienok
    public AddEditUlohyForm(Uloha uloha, Frame parent) {
        this(parent, true);

        inicializujVyberacDatumu();

        this.setTitle("Pridanie/editácia úlohy");

        this.uloha = uloha;
        txtPopis.setText(uloha.getPopis());
        txtNazov.setText(uloha.getNazov());
        if (uloha.getKategoria() != null) {
            cmbKategoria.setSelectedItem(uloha.getKategoria().getNazov());
        } else {
            cmbKategoria.setSelectedItem(" ");
        }

        cmbPriorita.setSelectedItem(uloha.getPriorita());
        txtTrvanie.setText(Integer.toString(uloha.getTrvanie()));

        if (uloha.getDatum() != null) {
            Date datum = uloha.getDatum();
            int den = datum.getDate();
            int mesiac = datum.getMonth();
            int rok = datum.getYear() + 1900;

            datePicker.getModel().setDate(rok, mesiac, den);
            datePicker.getModel().setSelected(true);
            if (datum.getMinutes() < 10) {
                txtMinuty.setText("0" + Integer.toString(datum.getMinutes()));
            } else {
                txtMinuty.setText(Integer.toString(datum.getMinutes()));
            }
            if (datum.getHours() < 10) {
                txtHodiny.setText("0" + Integer.toString(datum.getHours()));
            } else {
                txtHodiny.setText(Integer.toString(datum.getHours()));
            }
        }

    }

    private void inicializujVyberacDatumu() {
        // pridanie okna na vyber datumu
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePicker = new JDatePickerImpl(datePanel);
        datePicker.setBackground(GuiFactory.INSTANCE.getFarbaPozadia());
        vnutornyFrame.add(datePicker);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblZnacka = new javax.swing.JLabel();
        lblZnacka1 = new javax.swing.JLabel();
        lblUloha = new javax.swing.JLabel();
        lblPopis = new javax.swing.JLabel();
        lblPriorita = new javax.swing.JLabel();
        lblKategoria = new javax.swing.JLabel();
        lblDatum = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();
        txtNazov = new javax.swing.JTextField();
        cmbPriorita = new javax.swing.JComboBox();
        cmbKategoria = new javax.swing.JComboBox();
        lblCas = new javax.swing.JLabel();
        txtHodiny = new javax.swing.JTextField();
        txtMinuty = new javax.swing.JTextField();
        lblDvojbodka = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPopis = new javax.swing.JTextArea();
        vnutornyFrame = new javax.swing.JPanel();
        lblTrvanie = new javax.swing.JLabel();
        txtTrvanie = new javax.swing.JTextField();
        lblPozadie = new javax.swing.JLabel();

        lblZnacka.setBackground(new java.awt.Color(204, 153, 255));
        lblZnacka.setFont(new java.awt.Font("Gungsuh", 0, 36)); // NOI18N
        lblZnacka.setText("dori");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(462, 268));
        setResizable(false);
        getContentPane().setLayout(null);

        lblZnacka1.setBackground(new java.awt.Color(204, 153, 255));
        lblZnacka1.setFont(new java.awt.Font("Gungsuh", 0, 36)); // NOI18N
        lblZnacka1.setText("dori");
        getContentPane().add(lblZnacka1);
        lblZnacka1.setBounds(360, 190, 99, 43);

        lblUloha.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        lblUloha.setText("úloha");
        getContentPane().add(lblUloha);
        lblUloha.setBounds(90, 11, 35, 15);

        lblPopis.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        lblPopis.setText("detail");
        getContentPane().add(lblPopis);
        lblPopis.setBounds(22, 42, 35, 15);

        lblPriorita.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        lblPriorita.setText("priorita");
        getContentPane().add(lblPriorita);
        lblPriorita.setBounds(62, 183, 45, 15);

        lblKategoria.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        lblKategoria.setText("kategória");
        getContentPane().add(lblKategoria);
        lblKategoria.setBounds(62, 207, 60, 15);

        lblDatum.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        lblDatum.setText("dátum");
        getContentPane().add(lblDatum);
        lblDatum.setBounds(290, 40, 40, 15);

        btnOk.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        btnOk.setText("OK");
        btnOk.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        getContentPane().add(btnOk);
        btnOk.setBounds(280, 160, 100, 30);

        txtNazov.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        txtNazov.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        txtNazov.setBorder(null);
        txtNazov.setName("úloha"); // NOI18N
        getContentPane().add(txtNazov);
        txtNazov.setBounds(140, 10, 185, 15);

        cmbPriorita.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        cmbPriorita.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Nízka", "Stredná", "Vysoká" }));
        cmbPriorita.setName("priorita"); // NOI18N
        getContentPane().add(cmbPriorita);
        cmbPriorita.setBounds(131, 177, 90, 25);

        cmbKategoria.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        cmbKategoria.setModel(modelKategorii);
        cmbKategoria.setName("kategória"); // NOI18N
        getContentPane().add(cmbKategoria);
        cmbKategoria.setBounds(131, 204, 90, 25);

        lblCas.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        lblCas.setText("čas");
        getContentPane().add(lblCas);
        lblCas.setBounds(239, 104, 22, 15);

        txtHodiny.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        txtHodiny.setText("00");
        txtHodiny.setBorder(null);
        txtHodiny.setName("hodiny"); // NOI18N
        getContentPane().add(txtHodiny);
        txtHodiny.setBounds(265, 104, 26, 15);

        txtMinuty.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        txtMinuty.setText("00");
        txtMinuty.setBorder(null);
        txtMinuty.setName("minúty"); // NOI18N
        getContentPane().add(txtMinuty);
        txtMinuty.setBounds(303, 104, 26, 15);

        lblDvojbodka.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        lblDvojbodka.setText(":");
        getContentPane().add(lblDvojbodka);
        lblDvojbodka.setBounds(295, 104, 4, 15);

        txtPopis.setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBox.background"));
        txtPopis.setColumns(20);
        txtPopis.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtPopis.setLineWrap(true);
        txtPopis.setRows(5);
        txtPopis.setName("detail"); // NOI18N
        jScrollPane1.setViewportView(txtPopis);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 60, 220, 110);

        vnutornyFrame.setBackground(new java.awt.Color(255, 255, 204));
        vnutornyFrame.setFont(new java.awt.Font("Gungsuh", 0, 12)); // NOI18N
        vnutornyFrame.setName("dátum"); // NOI18N
        getContentPane().add(vnutornyFrame);
        vnutornyFrame.setBounds(240, 60, 200, 30);

        lblTrvanie.setText("trvanie:");
        getContentPane().add(lblTrvanie);
        lblTrvanie.setBounds(240, 130, 50, 15);

        txtTrvanie.setName("trvanie"); // NOI18N
        getContentPane().add(txtTrvanie);
        txtTrvanie.setBounds(290, 130, 90, 20);

        lblPozadie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/addeditulohyform.jpg"))); // NOI18N
        getContentPane().add(lblPozadie);
        lblPozadie.setBounds(0, 0, 462, 240);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // ak sú všetky parametre úlohy zadané, úloha sa uloží alebo zedituje
    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (komponentySuNeprazdne()) {
            Date selectedDate = (Date) datePicker.getModel().getValue();
            Date datum = new Date(selectedDate.getYear(),
                    selectedDate.getMonth(), selectedDate.getDate(),
                    Integer.parseInt(txtHodiny.getText()),
                    Integer.parseInt(txtMinuty.getText()));

            uloha.setDatum(datum);
            List<Kategoria> vsetkyKategorie = kategoriaDao.dajVsetky();
            String nazovKat = cmbKategoria.getSelectedItem().toString();
            Kategoria pridavanaKategoria = null;
            for (Kategoria kategoria : vsetkyKategorie) {
                if (kategoria.getNazov().equals(nazovKat)) {
                    pridavanaKategoria = kategoria;
                }
            }
            uloha.setKategoria(pridavanaKategoria);
            uloha.setPopis(txtPopis.getText());
            uloha.setNazov(txtNazov.getText());
            uloha.setStav(false);
            uloha.setPriorita(cmbPriorita.getSelectedItem().toString());
            uloha.setTrvanie(Integer.valueOf(txtTrvanie.getText()));

            if (add) {
                ulohaDao.pridajUlohu(uloha);
            } else {
                ulohaDao.upravUlohu(uloha);
            }
            dispose();
        }
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
            java.util.logging.Logger.getLogger(AddEditUlohyForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddEditUlohyForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddEditUlohyForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddEditUlohyForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddEditUlohyForm dialog = new AddEditUlohyForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox cmbKategoria;
    private javax.swing.JComboBox cmbPriorita;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCas;
    private javax.swing.JLabel lblDatum;
    private javax.swing.JLabel lblDvojbodka;
    private javax.swing.JLabel lblKategoria;
    private javax.swing.JLabel lblPopis;
    private javax.swing.JLabel lblPozadie;
    private javax.swing.JLabel lblPriorita;
    private javax.swing.JLabel lblTrvanie;
    private javax.swing.JLabel lblUloha;
    private javax.swing.JLabel lblZnacka;
    private javax.swing.JLabel lblZnacka1;
    private javax.swing.JTextField txtHodiny;
    private javax.swing.JTextField txtMinuty;
    private javax.swing.JTextField txtNazov;
    private javax.swing.JTextArea txtPopis;
    private javax.swing.JTextField txtTrvanie;
    private javax.swing.JPanel vnutornyFrame;
    // End of variables declaration//GEN-END:variables

    // metóda zistí, či sú vo všetkých okienkach zadané údaje
    private boolean komponentySuNeprazdne() {
        String sprava = new String();
        Boolean podmienka = true;

        if (txtNazov.getText().isEmpty()) {
            sprava = "Vyplňte názov!";
            podmienka = false;
        }
        if (txtPopis.getText().isEmpty()) {
            sprava = "Vyplňte popis!";
            podmienka = false;
        }
        if (cmbKategoria.getSelectedItem().toString().equals(" ")) {
            sprava = "Vyberte kategóriu!";
            podmienka = false;
        }
        if (cmbPriorita.getSelectedItem().toString().equals(" ")) {
            sprava = "Vyberte prioritu!";
            podmienka = false;
        }
        if (datePicker.getJFormattedTextField().getText().isEmpty()) {
            sprava = "Vyberte dátum!";
            podmienka = false;
        }

        if (txtMinuty.getText().isEmpty()) {
            sprava = "Zadajte minúty!";
            podmienka = false;
        }
        if (txtHodiny.getText().isEmpty()) {
            sprava = "Zadajte hodiny!";
            podmienka = false;
        }
        if (!txtMinuty.getText().matches("[0-9\\.]+")
                || txtMinuty.getText().length() > 2) {
            sprava = "Nesprávny formát minút!";
            podmienka = false;
        }
        if (!txtHodiny.getText().matches("[0-9\\.]+")
                || txtHodiny.getText().length() > 2) {
            sprava = "Nesprávny formát hodín!";
            podmienka = false;
        }
        if (!txtTrvanie.getText().matches("-?\\d+")) {
            sprava = "Nesprávny formát travania!";
            podmienka = false;
        }

        if (!podmienka) {
            JOptionPane.showMessageDialog(this, sprava, "Chyba", ERROR_MESSAGE);
        }

        return podmienka;
    }

}
