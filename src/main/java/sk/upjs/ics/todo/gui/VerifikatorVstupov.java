package sk.upjs.ics.todo.gui;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Obsahuje metody pre overovanie vstupu a na vyhadzovanie chybovych hlasok.
 * Zdroj pre regularne vyrazy:
 * http://ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-1/
 * Testovanie regularnych vyrazov: http://myregexp.com/
 */
public class VerifikatorVstupov {

    // nadpis, ktory budu mat vsetky chybove hlasky
    private static final String NADPIS_CHYBOVEJ_HLASKY = "Chyba - nesprávny vstup";

    public String getNadpis() {
        return NADPIS_CHYBOVEJ_HLASKY;
    }

    /**
     * @param pole pole, ktore overujeme
     * @return true, ak je neprazdne
     */
    public boolean jeNeprazdny(JTextField pole) {
        return !pole.getText().equals("");
    }

    /**
     * @param pole pole, ktore overujeme
     * @return true, ak je v nom cele KLADNE cislo konvertovatelne na int
     */
    public boolean jeCeleKladneCislo(JTextField pole) {
        return pole.getText().matches("\\d+")
                && Long.valueOf(pole.getText()) <= Integer.MAX_VALUE;
    }

    /**
     *
     * @param pole pole, ktore overujeme
     * @param maxDlzka maximalna dlzka retazca
     * @return true, ak retazec nepresahuje max dlzku
     */
    public boolean jeMaxDlzky(JTextField pole, int maxDlzka) {
        int dlzka = pole.getText().length();
        return dlzka <= maxDlzka;
    }

    /**
     *
     * @param pole pole s heslom, ktore overujeme
     * @param maxDlzka maximalna dlzka retazca
     * @return true, ak heslo nepresahuje max dlzku
     */
    public boolean jeMaxDlzky(JPasswordField pole, int maxDlzka) {
        String heslo = new String(pole.getPassword());
        int dlzka = heslo.length();
        return dlzka <= maxDlzka;
    }

    /**
     *
     * @param pole pole, ktore overujeme
     * @return true, ak je v retazci validny email
     */
    public boolean jeEmail(JTextField pole) {
        // skaredy regularny vyraz, akceptuje velke aj male pismena 
        // a tiez cislice, bodky a podciarkovniky v uzivatelskom mene
        return pole.getText().matches("[a-zA-Z0-9_.]+@[a-zA-z]+[.][a-zA-Z]+");
    }
}
