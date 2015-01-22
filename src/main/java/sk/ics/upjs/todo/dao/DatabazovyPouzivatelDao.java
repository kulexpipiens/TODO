package sk.ics.upjs.todo.dao;

import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sk.ics.upjs.todo.entity.Pouzivatel;
import sk.ics.upjs.todo.exceptions.NeplatneRegistracneMenoException;
import sk.ics.upjs.todo.exceptions.ZleMenoAleboHesloException;

public class DatabazovyPouzivatelDao implements PouzivatelDao {

    JdbcTemplate jdbcTemplate;

    public DatabazovyPouzivatelDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Prihlasenie uzivatela
     *
     * @param pouzivatel pouzivatel, ktoreho sa snazime prihlasit
     * @return vrati referenciu na novo prihlaseneho pouzivatela alebo null ak
     * prihlasenie neprebehlo uspesne
     */
    @Override
    public Pouzivatel prihlas(Pouzivatel pouzivatel) {
        skontrolujMenoAHeslo(pouzivatel);
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

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.setTableName("UZIVATELIA");
        insert.execute(hodnoty);
    }

    /**
     * Overi, ci je zadane spravne meno a heslo pouzivatela
     *
     * @param meno prihlasovacie meno
     * @param heslo prihasovacie heslo
     * @return true ak je zadane korektne meno a heslo
     */
    private void skontrolujMenoAHeslo(Pouzivatel pouzivatel) throws ZleMenoAleboHesloException {
        String hesloZTabulky;
        try {
            // nacita heslo pre dane meno
            String sql = "SELECT Heslo FROM UZIVATELIA WHERE Meno = ?";
            hesloZTabulky = (String) jdbcTemplate.queryForObject(
                    sql, new Object[]{pouzivatel.getMeno()}, String.class);
        } catch (EmptyResultDataAccessException e) {
            // ak tam take meno nie je vratime výnimku
            throw new ZleMenoAleboHesloException();
        }

        boolean heslaSaZhoduju = hesloZTabulky.equals(pouzivatel.getHeslo());
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
            String sql = "SELECT Heslo FROM UZIVATELIA WHERE Meno = ?";
            heslo = jdbcTemplate.queryForObject(sql, new Object[]{meno}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

        return true;
    }

}
