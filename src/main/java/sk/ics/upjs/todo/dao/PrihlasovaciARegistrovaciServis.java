package sk.ics.upjs.todo.dao;

import sk.ics.upjs.todo.entity.Pouzivatel;

public enum PrihlasovaciARegistrovaciServis {

    INSTANCE;

    private final PouzivatelDao pouzivateliaDao = Factory.INSTANCE.pouzivatelDao();
    private Pouzivatel pouzivatel;

    // vrati pouzivatela
    public Pouzivatel getPouzivatel() {
        return pouzivatel;
    }

    // odhlasi pouzivatela
    public void odhlas() {
        pouzivatel = null;
    }

    // prihlasi pouzivatela s danym menom a heslom (alebo vyhadze vynimky, ak su neplatne udaje)
    public void prihlas(Pouzivatel pouzivatel) {
        pouzivatel = pouzivateliaDao.prihlas(pouzivatel.getMeno(), pouzivatel.getHeslo());
        this.pouzivatel = pouzivatel;
    }

    // zaregistruje pouzivatela s danym menom a heslo (alebo vyhadze vynimky, ak su neplatne udaje)
    public void zaregistruj(Pouzivatel pouzivatel) {
        pouzivateliaDao.registruj(pouzivatel);
    }
}
