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
    private static final int POCET_ULOH_Z_INTERVALU = 0;

    @BeforeClass
    public static void setUp() throws ZleMenoAleboHesloException, NoSuchAlgorithmException {
        System.setProperty("testovaciRezim", "true");
        jdbcTemplate = new JdbcTemplate(Factory.INSTANCE.dataSource());
        ulohaDao = new DatabazovyUlohaDao(jdbcTemplate);

        PrihlasovaciARegistrovaciServis.INSTANCE.prihlas("Admin", "qwerty123456");

        /* aby testy platili stale, musime upravit datumy momentalne ulozenych 
         uloh v databaze - upravime datum uloh o aktualny cas + 35 dni, 
         aby sme si pri ostatnych testoch (ci mame mesacne, tyzdnove...) boli
         isty, ze tam take ulohy nie su */
        jdbcTemplate.execute("UPDATE ULOHY SET datum = DATE_ADD(CURDATE(), INTERVAL 35 DAY)");
    }

    @Test
    public void testDajVsetky() {
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(POCET_ULOH_V_DATABAZE, zoznamUloh.size());
    }

    @Test
    public void testPridajAVymazUlohu() {
        // pripravym ulohu na pridanie a nasledne vymazanie
        Uloha uloha = new Uloha();

        Kategoria kategoria = new Kategoria();
        kategoria.setId(5);
        kategoria.setNazov("Jedlo");
        kategoria.setPopis("o jedle");

        uloha.setNazov("Nova Uloha");
        uloha.setKategoria(kategoria);
        uloha.setPriorita("Vysoká");
        uloha.setStav(false);
        uloha.setDatum(new Date(2015, 01, 02, 12, 00, 00));
        uloha.setPopis("popis o novej ulohe");

        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        int pocetUlohPovodne = zoznamUloh.size();
        
        otestujPridanie(uloha, pocetUlohPovodne);
        otestujVymazanie(uloha, pocetUlohPovodne);
    }

    private void otestujPridanie(Uloha ulohaPridaj, int pocetUlohPovodne) {
        ulohaDao.pridajUlohu(ulohaPridaj);
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(pocetUlohPovodne + 1, zoznamUloh.size());
    }

    private void otestujVymazanie(Uloha ulohaVymaz, int pocetUlohPovodne) {
        // zistim maximalne id, teda id, ktore bolo pridane, aby som takuto
        // ulohu mohol vymazat
        String sql = "SELECT MAX(uloha_id) FROM ULOHY";
        int maxId = jdbcTemplate.queryForObject(sql, Integer.class);

        ulohaVymaz.setId(maxId);
        ulohaDao.vymazUlohu(ulohaVymaz);

        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(pocetUlohPovodne, zoznamUloh.size());
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
    public void testOznacZaSplnenu() {
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        Uloha ulohaZmena = zoznamUloh.get(0);

        // zapamatame si povodny stav
        boolean povodnyStav = ulohaZmena.getStav();

        ulohaDao.oznacZaSplnenu(ulohaZmena);

        zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(true, zoznamUloh.get(0).getStav());

        // do povodneho stavu
        if (!povodnyStav) {
            ulohaDao.oznacZaNesplnenu(ulohaZmena);
        }
    }

    @Test
    public void testOznacZaNesplnenu() {
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        Uloha ulohaZmena = zoznamUloh.get(0);

        // zapamatame si povodny stav
        boolean povodnyStav = ulohaZmena.getStav();

        ulohaDao.oznacZaNesplnenu(ulohaZmena);

        zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(false, zoznamUloh.get(0).getStav());

        // do povodneho stavu
        if (povodnyStav) {
            ulohaDao.oznacZaSplnenu(ulohaZmena);
        }
    }

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
