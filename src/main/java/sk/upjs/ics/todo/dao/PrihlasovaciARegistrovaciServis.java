package sk.upjs.ics.todo.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sk.upjs.ics.todo.entity.Pouzivatel;
import sk.upjs.ics.todo.exceptions.NeplatneRegistracneMenoException;
import sk.upjs.ics.todo.exceptions.ZleMenoAleboHesloException;

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
    public void prihlas(String meno, String heslo) throws ZleMenoAleboHesloException,
            NoSuchAlgorithmException {
        Pouzivatel novyPouzivatel = pouzivateliaDao.prihlas(meno, heslo);
        this.pouzivatel = novyPouzivatel;
    }

    // zaregistruje pouzivatela s danym menom a heslo (alebo vyhadze vynimky, ak su neplatne udaje)
    public void zaregistruj(Pouzivatel pouzivatel) throws NeplatneRegistracneMenoException,
            NoSuchAlgorithmException {
        pouzivateliaDao.registruj(pouzivatel);
    }

    /**
     * Zahashuje zadany string
     *
     * @param retazec retazec, ktoreho hash sa ma vypocitat
     * @return vrati String hashu v sestnastkovej sustave
     * @throws java.security.NoSuchAlgorithmException ak nenajde pozadovany
     * algoritmus
     */
    public String zahashuj(String retazec) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(retazec.getBytes());

        byte[] bajty = messageDigest.digest();

        StringBuilder sb = new StringBuilder();
        for (byte bajt : bajty) {
            // zdroj prevodu: 
            // http://howtodoinjava.com/2013/07/22/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
            sb.append(Integer.toString((bajt & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
