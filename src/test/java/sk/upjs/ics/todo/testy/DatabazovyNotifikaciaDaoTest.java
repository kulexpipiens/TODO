package sk.upjs.ics.todo.testy;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.todo.dao.DatabazovyNotifikaciaDao;
import sk.upjs.ics.todo.dao.Factory;
import sk.upjs.ics.todo.dao.NotifikaciaDao;
import sk.upjs.ics.todo.entity.Notifikacia;

public class DatabazovyNotifikaciaDaoTest {

    private static JdbcTemplate jdbcTemplate;
    private static NotifikaciaDao notifikaciaDao;

    private static final int POCET_NOTIFIKACII = 1;

    private static final int ID_ULOHY_PRE_NOVU_NOTIFIKACIU = 27;

    @BeforeClass
    public static void setUp() {
        System.setProperty("testovaciRezim", "true");
        jdbcTemplate = new JdbcTemplate(Factory.INSTANCE.dataSource());
        notifikaciaDao = new DatabazovyNotifikaciaDao(jdbcTemplate);
    }

    @Test
    public void testDajVsetky() {
        List<Notifikacia> notifikacie = notifikaciaDao.dajVsetkyNotifikacie();
        assertEquals(POCET_NOTIFIKACII, notifikacie.size());
    }

    public void testPridajAVymaz() {
        otestujPridanie();
        otestujVymazanie();
    }

    private void otestujVymazanie() {
        notifikaciaDao.vymazNotifikaciu(ID_ULOHY_PRE_NOVU_NOTIFIKACIU);
        List<Notifikacia> notifikacie = notifikaciaDao.dajVsetkyNotifikacie();
        assertEquals(POCET_NOTIFIKACII, notifikacie.size());
    }

    private void otestujPridanie() {
        notifikaciaDao.pridajNotifikaciu(ID_ULOHY_PRE_NOVU_NOTIFIKACIU);
        List<Notifikacia> notifikacie = notifikaciaDao.dajVsetkyNotifikacie();
        assertEquals(POCET_NOTIFIKACII + 1, notifikacie.size());
    }
}
