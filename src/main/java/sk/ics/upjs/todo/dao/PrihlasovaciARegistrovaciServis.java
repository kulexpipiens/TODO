package sk.ics.upjs.todo.dao;

import sk.ics.upjs.todo.entity.Pouzivatel;

public enum PrihlasovaciARegistrovaciServis {

    INSTANCE;

    private final PouzivatelDao pouzivateliaDao = new DatabazovyPouzivatelDao(Factory.INSTANCE.jdbcTemplate());
    private Pouzivatel pouzivatel;

    // vrati pouzivatela
    public Pouzivatel getPouzivatel() {
        return pouzivatel;
    }

    // odhlasi pouzivatela
    public void odhlas() {
        pouzivatel = null;
    }

    // prihlasi pouzivatela s danym menom a heslo (alebo vyhadze vynimky ak su neplatne udaje)
    public void prihlas(Pouzivatel pouzivatel) {
        pouzivatel = pouzivateliaDao.prihlas(pouzivatel);
        this.pouzivatel = pouzivatel;
    }

    // zaregistruje pouzivatela s danym menom a heslo (alebo vyhadze vynimky ak su neplatne udaje)
    public void zaregistruj(Pouzivatel pouzivatel) {
        pouzivateliaDao.registruj(pouzivatel);
    }
}
