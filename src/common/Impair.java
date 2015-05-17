package common;

public class Impair extends Bet {

    public Impair() {
        super("Impair");
    }

    public String toString() {
        return code + " / " + amount;
    }

    public String codeMessage() {
        return code.toUpperCase() + " " + amount;
    }

    public Impair clone() {
        return (Impair) super.clone();
    }
}
