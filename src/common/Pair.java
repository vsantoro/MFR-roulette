package common;

public class Pair extends Bet {

    public Pair() {
        super("Pair");
    }

    public String toString() {
        return code + " / " + amount;
    }

    public String codeMessage() {
        return code.toUpperCase() + " " + amount;
    }

    public Pair clone() {
        return (Pair) super.clone();
    }
}
