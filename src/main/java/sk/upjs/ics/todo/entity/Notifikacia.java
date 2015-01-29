package sk.upjs.ics.todo.entity;

import java.util.Date;

public class Notifikacia {

    private long id;
    private long idUlohy;
    private String nazov;
    private String popis;
    private String priorita;
    private Date datum;
    private boolean stav;
    private int trvanie;
    private String vlastnik;
    private String mail;
    private boolean chceNotifikacie;
    // ako dlho pred casom splnenia ulohy ma prist upozornenie
    private int dobaNotifikacie; // v hodinach

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUlohy() {
        return idUlohy;
    }

    public void setIdUlohy(long idUlohy) {
        this.idUlohy = idUlohy;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public String getPriorita() {
        return priorita;
    }

    public void setPriorita(String priorita) {
        this.priorita = priorita;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public boolean isStav() {
        return stav;
    }

    public void setStav(boolean stav) {
        this.stav = stav;
    }

    public int getTrvanie() {
        return trvanie;
    }

    public void setTrvanie(int trvanie) {
        this.trvanie = trvanie;
    }

    public String getVlastnik() {
        return vlastnik;
    }

    public void setVlastnik(String vlastnik) {
        this.vlastnik = vlastnik;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isChceNotifikacie() {
        return chceNotifikacie;
    }

    public void setChceNotifikacie(boolean chceNotifikacie) {
        this.chceNotifikacie = chceNotifikacie;
    }

    public int getDobaNotifikacie() {
        return dobaNotifikacie;
    }

    public void setDobaNotifikacie(int dobaNotifikacie) {
        this.dobaNotifikacie = dobaNotifikacie;
    }
}
