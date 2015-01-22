package sk.ics.upjs.todo.exceptions;

public class ZleMenoAleboHesloException extends RuntimeException {

    private final String sprava = "Prihlásenie je neúspešné. Zadali ste nesprávne meno alebo heslo!";

    public String getSprava() {
        return sprava;
    }
}
