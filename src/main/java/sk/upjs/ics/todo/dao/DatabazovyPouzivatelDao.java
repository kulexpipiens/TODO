package sk.upjs.ics.todo.dao;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sk.upjs.ics.todo.entity.Pouzivatel;
import sk.upjs.ics.todo.exceptions.NeplatneRegistracneMenoException;
import sk.upjs.ics.todo.exceptions.ZleMenoAleboHesloException;
import sk.upjs.ics.todo.rowmappery.PouzivatelRowMapper;

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
     * @throws java.security.NoSuchAlgorithmException vyhodi ak nie je
     * podporovany hashovaci algoritmus
     */
    @Override
    public Pouzivatel prihlas(String meno, String heslo) throws ZleMenoAleboHesloException,
            NoSuchAlgorithmException {
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
     * @throws java.security.NoSuchAlgorithmException vyhodi ak nie je
     * podporovany hashovaci algoritmus
     */
    @Override
    public void registruj(Pouzivatel pouzivatel) throws NeplatneRegistracneMenoException,
            NoSuchAlgorithmException {
        if (existujePouzivatelSDanymMenom(pouzivatel.getMeno())) {
            throw new NeplatneRegistracneMenoException();
        }

        Map<String, Object> hodnoty = new HashMap<>();
        hodnoty.put("Meno", pouzivatel.getMeno());
        hodnoty.put("Heslo", PrihlasovaciARegistrovaciServis.INSTANCE.zahashuj(pouzivatel.getHeslo()));
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
     * @param pouzivatel pouzivatel ktoreho notifikacie sa upravuju
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
    private void skontrolujMenoAHeslo(String meno, String heslo) throws ZleMenoAleboHesloException,
            NoSuchAlgorithmException {
        String hashHeslaZTabulky;
        String hashHesla = PrihlasovaciARegistrovaciServis.INSTANCE.zahashuj(heslo);
        try {
            // nacita hash hesla pre dane meno
            String sql = "SELECT Heslo FROM " + nazovTabulky + " WHERE Meno = ?";
            hashHeslaZTabulky = (String) jdbcTemplate.queryForObject(sql, new Object[]{meno}, String.class);
        } catch (EmptyResultDataAccessException e) {
            // ak tam take meno nie je vratime výnimku
            throw new ZleMenoAleboHesloException();
        }

        boolean heslaSaZhoduju = hashHeslaZTabulky.equals(hashHesla);
        /* ak tam take meno je, tak vrati, ci sa heslo zhoduje alebo 
         ak sa nezhoduje (meno bolo dobre, ale heslo je nespravne), 
         tak opat vyhodime vynimku
         */
        if (!heslaSaZhoduju) {
            throw new ZleMenoAleboHesloException();
        }
    }

    /**
     * Vráti, či používateľ so zadaným menom existuje
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