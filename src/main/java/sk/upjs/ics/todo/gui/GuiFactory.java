package sk.upjs.ics.todo.gui;

import java.awt.Color;
import java.awt.Toolkit;

public enum GuiFactory {

    INSTANCE;

    // farbu pozadia mame v kazdom okne rovnaku, tak ju nastavime len raz
    private final Color FARBA_POZADIA = new Color(253, 251, 151);
    
    // zistime si rozmery obrazovky, aby sme mohli jednotlive okna centrovat
    private final double SIRKA_OBRAZOVKY = Toolkit.getDefaultToolkit().
            getScreenSize().getWidth();
    private final double VYSKA_OBRAZOVKY = Toolkit.getDefaultToolkit().
            getScreenSize().getHeight();

    private VerifikatorVstupov verifikator;

    /**
     * 
     * @return farba pozadia okna
     */
    public Color getFarbaPozadia() {
        return FARBA_POZADIA;
    }

    /**
     * 
     * @return verifikator vstupov (textboxov)
     */
    public VerifikatorVstupov getVerifikatorVstupov() {
        if (verifikator == null) {
            verifikator = new VerifikatorVstupov();
        }
        return verifikator;
    }

    /**
     * Vratime sirku obrazovky
     *
     * @return vrati pocet pixelov
     */
    public double getSirkaObrazovky() {
        return SIRKA_OBRAZOVKY;
    }

    /**
     * Vratime vysku obrazovky
     *
     * @return vrati pocet pixelov
     */
    public double getVyskaObrazovky() {
        return VYSKA_OBRAZOVKY;
    }

    /**
     * Modifikuje okno (JFrame) na zarovanie na stred
     *
     * @param okno okno, ktore modifikujeme
     */
    public void centruj(javax.swing.JFrame okno) {
        int sirkaOkna = okno.getWidth();
        int vyskaOkna = okno.getHeight();
        double umiestnenieX = getSirkaObrazovky() / 2
                - sirkaOkna / 2;
        double umiestnenieY = getVyskaObrazovky() / 2
                - vyskaOkna / 2;
        okno.setBounds((int) umiestnenieX, (int) umiestnenieY, sirkaOkna, vyskaOkna);
    }

    /**
     * Modifikuje okno (JDialog) na zarovanie na stred
     *
     * @param okno okno, ktore modifikujeme
     */
    public void centruj(javax.swing.JDialog okno) {
        int sirkaOkna = okno.getWidth();
        int vyskaOkna = okno.getHeight();
        double umiestnenieX = getSirkaObrazovky() / 2
                - sirkaOkna / 2;
        double umiestnenieY = getVyskaObrazovky() / 2
                - vyskaOkna / 2;
        okno.setBounds((int) umiestnenieX, (int) umiestnenieY, sirkaOkna, vyskaOkna);
    }

}
