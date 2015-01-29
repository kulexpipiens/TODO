package sk.ics.upjs.todo.dao;

import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sk.ics.upjs.todo.entity.Pouzivatel;
import sk.ics.upjs.todo.exceptions.NeplatneRegistracneMenoException;
import sk.ics.upjs.todo.exceptions.ZleMenoAleboHesloException;
import sk.ics.upjs.todo.rowmappery.PouzivatelRowMapper;

public class DatabazovyPouzivatelDao implements PouzivatelDao {

    private final JdbcTemplate jdbcTemplate;
    private static final PouzivatelRowMapper mapovacPouzivatelov = new PouzivatelRowMapper();
    private static final String nazovTabulky = "UZIVATELIA";

    public DatabazovyPouzivatelDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Prihlasenie uzivatela
     *
     * @param meno prihlasovacie meno
     * @param heslo prihlasovacie heslo
     * @return vrati referenciu na novoprihlaseneho pouzivatela alebo null, ak
     * prihlasenie neprebehlo uspesne
     */
    @Override
    public Pouzivatel prihlas(String meno, String heslo) {
        skontrolujMenoAHeslo(meno, heslo);
        // po kontrole mena a hesla nacitame z databazy zvysne udaje o pouzivatelovi
        String sql = "SELECT * FROM " + nazovTabulky + " WHERE Meno = ?";
        Pouzivatel pouzivatel = jdbcTemplate.queryForObject(sql, mapovacPouzivatelov, meno);

        return pouzivatel;
    }

    /**
     * Registracia noveho uzivatela
     *
     * @param pouzivatel pouzivatel, ktoreho sa snazime prihlasit
     */
    @Override
    public void registruj(Pouzivatel pouzivatel) throws NeplatneRegistracneMenoException {
        if (existujePouzivatelSDanymMenom(pouzivatel.getMeno())) {
            throw new NeplatneRegistracneMenoException();
        }
        Map<String, Object> hodnoty = new HashMap<>();
        hodnoty.put("Meno", pouzivatel.getMeno());
        hodnoty.put("Heslo", pouzivatel.getHeslo());
        hodnoty.put("Mail", pouzivatel.getMail());
        hodnoty.put("chce_notifikacie", pouzivatel.isChceNotifikacie());
        hodnoty.put("doba_notifikacie", pouzivatel.getDobaNotifikacie());

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.setTableName(nazovTabulky);
        insert.execute(hodnoty);
    }

    /**
     * Upravi v databze nastavenia notifikacii pre daneho uzivatela
     *
     * @param pouzivatel
     */
    @Override
    public void upravNotifikacie(Pouzivatel pouzivatel) {
        String dopyt = "UPDATE " + nazovTabulky + " \n"
                + "SET chce_notifikacie = ?, \n"
                + "doba_notifikacie = ? \n"
                + "WHERE Meno = ?\n";

        jdbcTemplate.update(dopyt,
                pouzivatel.isChceNotifikacie(),
                pouzivatel.getDobaNotifikacie(),
                pouzivatel.getMeno());
    }

    /**
     * Overi, ci je zadane spravne meno a heslo pouzivatela
     *
     * @param meno prihlasovacie meno
     * @param heslo prihasovacie heslo
     * @return true ak je zadane korektne meno a heslo
     */
    private void skontrolujMenoAHeslo(String meno, String heslo) throws ZleMenoAleboHesloException {
        String hesloZTabulky;
        try {
            // nacita heslo pre dane meno
            String sql = "SELECT Heslo FROM " + nazovTabulky + " WHERE Meno = ?";
            hesloZTabulky = (String) jdbcTemplate.queryForObject(sql, new Object[]{meno}, String.class);
        } catch (EmptyResultDataAccessException e) {
            // ak tam take meno nie je vratime výnimku
            throw new ZleMenoAleboHesloException();
        }

        boolean heslaSaZhoduju = hesloZTabulky.equals(heslo);
        /* ak tam take meno je, tak vrati, ci sa heslo zhoduje alebo 
         ak sa nezhoduje (meno bolo dobre, ale heslo je nespravne), 
         tak opat vyhodime vynimku*/
        if (!heslaSaZhoduju) {
            throw new ZleMenoAleboHesloException();
        }
    }

    /**
     * Vráti, ci používateľ so zadaným menom existuje
     *
     * @param meno meno pouzivatela
     * @return true, ak užívateľ existuje
     */
    private boolean existujePouzivatelSDanymMenom(String meno) {
        String heslo = null;
        try {
            String sql = "SELECT Heslo FROM " + nazovTabulky + " WHERE Meno = ?";
            heslo = jdbcTemplate.queryForObject(sql, new Object[]{meno}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

}
