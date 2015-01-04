package sk.ics.upjs.todo.home;

//entita úloha + getre + setre + metóda toString
public class Uloha {

    private long id;
    private String nazov;
    private String popis;
    private String priorita;
    private String datum;
    private String cas;
    private Kategoria kategoria;
    private boolean stav;

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
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

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
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
    public String toString(){
        return "Uloha: "+nazov+" "+datum;
    }  

  
    
}
