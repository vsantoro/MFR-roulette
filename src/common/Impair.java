package common;

public class Impair extends Bet {

    public Impair() {
        super("Impair");
    }

    public double winning(int number) {
        if(number != 0 && number%2 != 0){
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

    public Impair clone() {
        return (Impair) super.clone();
    }
}
