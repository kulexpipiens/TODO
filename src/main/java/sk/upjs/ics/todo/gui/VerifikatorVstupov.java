package sk.upjs.ics.todo.gui;

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
     * @return true, ak je v nom cele KLADNE cislo
     */
    public boolean jeCeleKladneCislo(JTextField pole) {
        return pole.getText().matches("\\d+");
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
     * @param pole pole, ktore overujeme
     * @return true, ak je v retazci validny email
     */
    public boolean jeEmail(JTextField pole) {
        // skaredy regularny vyraz, akceptuje velke aj male pismena 
        // a tiez cislice, bodky a podciarkovniky v uzivatelskom mene
        return pole.getText().matches("[a-zA-Z0-9_.]+@[a-zA-z]+[.][a-zA-Z]+");
    }

    // TIETO (MOZNO ZATIAL) NIE SU POTREBNE
//    /**
//     * Hlaska pre nepovinne cele cislo
//     *
//     * @param parent rodic hlasky
//     * @param pole pole, ktore overujeme
//     */
//    public void hlaskaZleCeleCislo(JDialog parent, JTextField pole) {
//        String sprava = "Zadali ste nesprávny formát čísla v poli "
//                + pole.getName()
//                + ".";
//        JOptionPane.showMessageDialog(parent, sprava, NADPIS_CHYBOVEJ_HLASKY, ERROR_MESSAGE);
//    }
//
//    /**
//     * Hlaska pre povinny string
//     *
//     * @param parent rodic hlasky
//     * @param pole pole, ktore overujeme
//     * @param maxDlzka
//     */
//    public void hlaskaPrazdnyAleboDlhyString(JDialog parent, JTextField pole, int maxDlzka) {
//        String sprava = "Zadali ste nesprávny reťazec v poli "
//                + pole.getName()
//                + ". \nPole musí byť vyplnené a maximálna dĺžka reťazca je "
//                + maxDlzka
//                + ".";
//        JOptionPane.showMessageDialog(parent, sprava, NADPIS_CHYBOVEJ_HLASKY, ERROR_MESSAGE);
//    }
}
