package sk.ics.upjs.todo.entity;

public class Pouzivatel {

    String meno;

    String heslo;

    String mail;

    boolean chceNotifikacie;

    // Ako dlho pred splnenim ulohy chce byt uzivatel informovany (v hodinach).
    // Pouzivame typ Integer, aby sme mohli nastavit aj hodnotu null, 
    // pretoze to nie je povinne pole
    Integer dobaNotifikacie;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getHeslo() {
        return heslo;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }

    public boolean isChceNotifikacie() {
        return chceNotifikacie;
    }

    public void setChceNotifikacie(boolean chceNotifikacie) {
        this.chceNotifikacie = chceNotifikacie;
    }

    public Integer getDobaNotifikacie() {
        return dobaNotifikacie;
    }

    public void setDobaNotifikacie(Integer dobaNotifikacie) {
        this.dobaNotifikacie = dobaNotifikacie;
    }
}
