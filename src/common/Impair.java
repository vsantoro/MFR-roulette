package common;

public class Impair extends Bet {

    public Impair() {
        this(0);
    }

    public Impair(double amount) {
        super("Impair", amount);
    }

    public double winning(int number) {
        if(number != 0 && number%2 != 0){
            return (amount * 36) / 18;
        }
        return 0;
    }

    public String toString() {
        return code + " / " + amount;
    }

    public String getCode() {
        return code.toUpperCase() + " " + amount;
    }

    public Impair clone() {
        return (Impair) super.clone();
    }
}
