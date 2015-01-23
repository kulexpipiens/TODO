package sk.ics.upjs.todo.entity;

//entita úloha + getre + setre + metóda toString
import java.util.Date;

public class Uloha {

    private long id;
    private String nazov;
    private String popis;
    private String priorita;
    private Date datum;
    private Kategoria kategoria;
    private boolean stav;
    private int trvanie; // v minutach

    public int getTrvanie() {
        return trvanie;
    }

    public void setTrvanie(int trvanie) {
        this.trvanie = trvanie;
    }

    /* public String getCas() {
     return Integer.toString(this.datum.getHours()+':'+this.getDatum().getMinutes());
     }

     public void setCas(String cas) {
     this.datum.setHours(Integer.parseInt(cas.substring(0, 1)));
     this.datum.setMinutes(Integer.parseInt(cas.substring(3,4)));
     }*/
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Kategoria getKategoria() {
        return kategoria;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

    public boolean getStav() {
        return stav;
    }

    public void setStav(boolean stav) {
        this.stav = stav;
    }

    @Override
    public String toString() {
        return "Uloha: " + nazov + " " + datum.getDate() + "." + datum.getMonth()
                + "." + datum.getYear() + " " + datum.getHours() + ":" + datum.getMinutes();
    }

}
