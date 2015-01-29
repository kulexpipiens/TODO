package sk.upjs.ics.todo.testy;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.todo.dao.DatabazovyUlohaDao;
import sk.upjs.ics.todo.dao.Factory;
import sk.upjs.ics.todo.dao.PrihlasovaciARegistrovaciServis;
import sk.upjs.ics.todo.dao.UlohaDao;
import sk.upjs.ics.todo.entity.Kategoria;
import sk.upjs.ics.todo.entity.Uloha;
import sk.upjs.ics.todo.exceptions.ZleMenoAleboHesloException;

public class DatabazovyUlohaDaoTest {

    private static JdbcTemplate jdbcTemplate;

    private static UlohaDao ulohaDao;

    private static final int POCET_ULOH_V_DATABAZE = 2;
    private static final int POCET_DNESNYCH_ULOH_V_DATABAZE = 0;
    private static final int POCET_TYZDNOVYCH_ULOH_V_DATABAZE = 0;
    private static final int POCET_MESACNYCH_ULOH_V_DATABAZE = 0;
    private static final int POCET_ULOH_Z_INTERVALU = 2;

    @BeforeClass
    public static void setUp() throws ZleMenoAleboHesloException, NoSuchAlgorithmException {
        System.setProperty("testovaciRezim", "true");
        jdbcTemplate = new JdbcTemplate(Factory.INSTANCE.dataSource());
        ulohaDao = new DatabazovyUlohaDao(jdbcTemplate);

        PrihlasovaciARegistrovaciServis.INSTANCE.prihlas("Admin", "qwerty123456");
    }

    @Test
    public void testDajVsetky() {
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(POCET_ULOH_V_DATABAZE, zoznamUloh.size());
    }

    @Test
    public void testPridajUlohu() {
        Uloha ulohaPridaj = new Uloha();
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        int pocetUlohPovodne = zoznamUloh.size();

        Kategoria kategoria = new Kategoria();
        kategoria.setId(5);
        kategoria.setNazov("Jedlo");
        kategoria.setPopis("o jedle");

        ulohaPridaj.setNazov("Nova Uloha");
        ulohaPridaj.setKategoria(kategoria);
        ulohaPridaj.setPriorita("Vysoká");
        ulohaPridaj.setStav(false);
        ulohaPridaj.setDatum(new Date(2015, 01, 02, 12, 00, 00));
        ulohaPridaj.setPopis("popis o novej ulohe");

        ulohaDao.pridajUlohu(ulohaPridaj);
        zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(pocetUlohPovodne + 1, zoznamUloh.size());

        ulohaPridaj = zoznamUloh.get(zoznamUloh.size() - 1);
        ulohaDao.vymazUlohu(ulohaPridaj);
    }

    @Test
    public void testUpravUlohu() {
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        Uloha ulohaZmena
                = zoznamUloh.get(zoznamUloh.size() - 1);
        String povodnyPopis = ulohaZmena.getPopis();
        ulohaZmena.setPopis("Zmeneny popis");

        ulohaDao.upravUlohu(ulohaZmena);

        zoznamUloh = ulohaDao.dajVsetky();
        assertEquals("Zmeneny popis",
                zoznamUloh.get(zoznamUloh.size() - 1).getPopis());

        ulohaZmena.setPopis(povodnyPopis);
        ulohaDao.upravUlohu(ulohaZmena);
    }

    @Test
    public void testVymazUlohu() {
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        int pocetUlohPovodne = zoznamUloh.size();

        Uloha ulohaNaVymazanie = zoznamUloh.get(pocetUlohPovodne - 1);
        ulohaDao.vymazUlohu(ulohaNaVymazanie);

        zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(pocetUlohPovodne - 1, zoznamUloh.size());

        ulohaDao.pridajUlohu(ulohaNaVymazanie);
    }

    @Test
    public void testOznacZaSplnenu() {
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        Uloha ulohaZmena = zoznamUloh.get(0);
        ulohaDao.oznacZaSplnenu(ulohaZmena);

        zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(true, zoznamUloh.get(0).getStav());
    }

    @Test
    public void testOznacZaNesplnenu() {
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        Uloha ulohaZmena = zoznamUloh.get(0);
        ulohaDao.oznacZaNesplnenu(ulohaZmena);

        zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(false, zoznamUloh.get(0).getStav());
    }

    /*
     * POZOR pri nasledujucich testoch, ako plynie cas, tak pocty dnesnych, 
     * tyzdnovych a mesacnych uloh sa menia !!!
     * Teda treba pred testovanim specificky popridavat ulohy...
     */
    @Test
    public void testDajDnesne() {
        List<Uloha> zoznamUloh = ulohaDao.dajDnesne();
        assertEquals(POCET_DNESNYCH_ULOH_V_DATABAZE, zoznamUloh.size());

    }

    @Test
    public void testDajTyzdnove() {
        List<Uloha> zoznamUloh = ulohaDao.dajTyzdnove();
        assertEquals(POCET_TYZDNOVYCH_ULOH_V_DATABAZE, zoznamUloh.size());
    }

    @Test
    public void testDajMesacne() {
        List<Uloha> zoznamUloh = ulohaDao.dajMesacne();
        assertEquals(POCET_MESACNYCH_ULOH_V_DATABAZE, zoznamUloh.size());
    }

    @Test
    public void testDajZCasovehoIntervalu() {
        Calendar datumOd = Calendar.getInstance();
        datumOd.set(2015, 1, 1);

        Calendar datumDo = Calendar.getInstance();
        datumDo.set(2015, 1, 4);

        List<Uloha> zoznamUloh = ulohaDao.dajZCasovehoIntervalu(datumOd, datumDo);

        assertEquals(POCET_ULOH_Z_INTERVALU, zoznamUloh.size());
    }
}
