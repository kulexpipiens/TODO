package sk.upjs.ics.todo.testy;

import javax.swing.JTextField;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import sk.upjs.ics.todo.gui.VerifikatorVstupov;

public class VerifikatorVstupovTest {

    private static JTextField pole;
    private static VerifikatorVstupov verifikatorVStupov;

    @BeforeClass
    public static void setUp() {
        verifikatorVStupov = new VerifikatorVstupov();
        pole = new JTextField();
    }

    @Test
    public void jeNeprazdnyTestNaozajJe() {
        pole.setText("Som nejaky text!");
        Assert.assertTrue(verifikatorVStupov.jeNeprazdny(pole));
    }

    @Test
    public void jeNeprazdnyTestNieJe() {
        pole.setText("");
        Assert.assertFalse(verifikatorVStupov.jeNeprazdny(pole));
    }

    @Test
    public void jeCeleKladneCisloTestNieJeJeDesatinne() {
        pole.setText("-3.14");
        Assert.assertFalse(verifikatorVStupov.jeCeleKladneCislo(pole));
    }

    @Test
    public void jeCeleKladneCisloTestNieJeJeString() {
        pole.setText("1a1hoj12");
        Assert.assertFalse(verifikatorVStupov.jeCeleKladneCislo(pole));
    }

    @Test
    public void jeCeleKladneCisloTestNieJeJeZaporne() {
        pole.setText("-1000");
        Assert.assertFalse(verifikatorVStupov.jeCeleKladneCislo(pole));
    }

    @Test
    public void jeCeleKladneCisloTestJeJeNula() {
        pole.setText("00");
        Assert.assertTrue(verifikatorVStupov.jeCeleKladneCislo(pole));
    }

    @Test
    public void jeCeleKladneCisloTestJe() {
        pole.setText("100");
        Assert.assertTrue(verifikatorVStupov.jeCeleKladneCislo(pole));
    }

    @Test
    public void jeMaxDlzkyTestJeDlhsie() {
        pole.setText("123456");
        Assert.assertFalse(verifikatorVStupov.jeMaxDlzky(pole, 5));
    }

    @Test
    public void jeMaxDlzkyTestJePresne() {
        pole.setText("12345");
        Assert.assertTrue(verifikatorVStupov.jeMaxDlzky(pole, 5));
    }

    @Test
    public void jeMaxDlzkyTestJe() {
        pole.setText("ahoj");
        Assert.assertTrue(verifikatorVStupov.jeMaxDlzky(pole, 5));
    }

    @Test
    public void jeMaxDlzkyTestJePrazdnyRetazec() {
        pole.setText("");
        Assert.assertTrue(verifikatorVStupov.jeMaxDlzky(pole, 5));
    }

    @Test
    public void jeEmailTestNieJeBlud() {
        pole.setText("asasdasd");
        Assert.assertFalse(verifikatorVStupov.jeEmail(pole));
    }

    @Test
    public void jeEmailTestNieJeBlud2() {
        pole.setText("asasdasd@");
        Assert.assertFalse(verifikatorVStupov.jeEmail(pole));
    }

    @Test
    public void jeEmailTestNieJeBlud3() {
        pole.setText("asasdasd@.");
        Assert.assertFalse(verifikatorVStupov.jeEmail(pole));
    }

    @Test
    public void jeEmailTestJe() {
        pole.setText("asasdasd@asd.asd");
        Assert.assertTrue(verifikatorVStupov.jeEmail(pole));
    }

    @Test
    public void jeEmailTestJeChuckNorrisov() {
        pole.setText("gmail@chucknorris.com");
        Assert.assertTrue(verifikatorVStupov.jeEmail(pole));
    }
}
