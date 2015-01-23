package sk.ics.upjs.todo.gui;

import java.awt.Color;

public enum GuiFactory {

    INSTANCE;

    // farbu pozadia mame v kazdom okne rovnaku, tak ju nastavime len raz
    private Color farbaPozadia = new Color(253, 251, 151);

    public Color getFarbaPozadia() {
        return farbaPozadia;
    }

}
