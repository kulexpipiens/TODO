package sk.ics.upjs.todo.gui;

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

    public Color getFarbaPozadia() {
        return FARBA_POZADIA;
    }

    public VerifikatorVstupov getVerifikatorVstupov() {
        if (verifikator == null) {
            verifikator = new VerifikatorVstupov();
        }
        return verifikator;
    }

    public double getSirkaObrazovky() {
        return SIRKA_OBRAZOVKY;
    }

    public double getVyskaObrazovky() {
        return VYSKA_OBRAZOVKY;
    }

    public void centruj(javax.swing.JFrame okno) {
        int sirkaOkna = okno.getWidth();
        int vyskaOkna = okno.getHeight();
        double umiestnenieX = GuiFactory.INSTANCE.getSirkaObrazovky() / 2
                - sirkaOkna / 2;
        double umiestnenieY = GuiFactory.INSTANCE.getVyskaObrazovky() / 2
                - vyskaOkna / 2;
        okno.setBounds((int) umiestnenieX, (int) umiestnenieY, sirkaOkna, vyskaOkna);
    }

    public void centruj(javax.swing.JDialog okno) {
        int sirkaOkna = okno.getWidth();
        int vyskaOkna = okno.getHeight();
        double umiestnenieX = GuiFactory.INSTANCE.getSirkaObrazovky() / 2
                - sirkaOkna / 2;
        double umiestnenieY = GuiFactory.INSTANCE.getVyskaObrazovky() / 2
                - vyskaOkna / 2;
        okno.setBounds((int) umiestnenieX, (int) umiestnenieY, sirkaOkna, vyskaOkna);
    }

}
