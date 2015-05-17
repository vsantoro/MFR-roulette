package common;

public class Noir extends Bet {

    public Noir() {
        super("Noir");
    }

    public double winning(int number) {
        if(!isRouge(number)){
            return (amount * 36) / 18;
        }
        return -amount;
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
