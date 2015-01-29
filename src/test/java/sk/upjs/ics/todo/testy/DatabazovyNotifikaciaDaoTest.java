package sk.upjs.ics.todo.testy;

import java.util.List;
import org.junit.AfterClass;
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
    
    private static final int POCET_NOTIFIKACII = 2;
    
    // v databaze su notifikacie k uloham s id 4 a 19
    private static final int ID_ULOHY_PRE_NOVU_NOTIFIKACIU = 6;
    private static final int ID_ULOHY_PRE_EXISTUJUCU_NOTIFIKACIU = 4;

    @BeforeClass
    public static void setUp() {
        System.setProperty("testovaciRezim", "true");
        jdbcTemplate = new JdbcTemplate(Factory.INSTANCE.dataSource());
        notifikaciaDao = new DatabazovyNotifikaciaDao(jdbcTemplate);
    }
    
    @Test
    public void testDajVsetky()
    {
        List<Notifikacia> notifikacie = notifikaciaDao.dajVsetkyNotifikacie();
        assertEquals(POCET_NOTIFIKACII, notifikacie.size());
    }
    
    public void testPridajNotifikaciuNovejUlohe()
    {
        notifikaciaDao.pridajNotifikaciu(ID_ULOHY_PRE_NOVU_NOTIFIKACIU);
        List<Notifikacia> notifikacie = notifikaciaDao.dajVsetkyNotifikacie();
        assertEquals(POCET_NOTIFIKACII+1, notifikacie.size());
    }

    // stara notifikacia by sa mala nahradit novou
    public void testPridajNotifikaciuExistujucejUlohe()
    {
        // vymazeme notifikaciu, ktoru sme tam pridali
        notifikaciaDao.vymazNotifikaciu(ID_ULOHY_PRE_NOVU_NOTIFIKACIU);
        
        notifikaciaDao.pridajNotifikaciu(ID_ULOHY_PRE_EXISTUJUCU_NOTIFIKACIU);
        List<Notifikacia> notifikacie = notifikaciaDao.dajVsetkyNotifikacie();
        assertEquals(POCET_NOTIFIKACII, notifikacie.size());
    }
    
    public void testPridajAVymaz()
    {
        notifikaciaDao.pridajNotifikaciu(ID_ULOHY_PRE_NOVU_NOTIFIKACIU);
        notifikaciaDao.vymazNotifikaciu(ID_ULOHY_PRE_NOVU_NOTIFIKACIU);
        List<Notifikacia> notifikacie = notifikaciaDao.dajVsetkyNotifikacie();
        assertEquals(POCET_NOTIFIKACII, notifikacie.size());    
    }
}
