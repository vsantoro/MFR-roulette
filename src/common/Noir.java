package common;

public class Noir extends Bet {

    public Noir() {
        super("Noir");
    }

    public String toString() {
        return code + " / " + amount;
    }

    public String codeMessage() {
        return code.toUpperCase() + " " + amount;
    }

    public Noir clone() {
        return (Noir) super.clone();
    }
}
