package sk.ics.upjs.todo.exceptions;

public class NeplatneRegistracneMenoException extends RuntimeException {

    private final String sprava = "Vami zadané meno už používa iný užívateľ. Zvoľte iné meno!";

    public String getSprava() {
        return sprava;
    }
}
