package sk.ics.upjs.todo.gui;

import java.awt.Color;

public enum GuiFactory {

    INSTANCE;

    // farbu pozadia mame v kazdom okne rovnaku, tak ju nastavime len raz
    private Color farbaPozadia = new Color(253, 251, 151);

    private VerifikatorVstupov verifikator;

    public Color getFarbaPozadia() {
        return farbaPozadia;
    }

    public VerifikatorVstupov getVerifikatorVstupov() {
        if (verifikator == null) {
            verifikator = new VerifikatorVstupov();
        }
        return verifikator;
    }

}
