package common;

public class Noir extends Bet {

    public Noir() {
        this(0);
    }

    public Noir(double amount) {
        super("Noir", amount);
    }

    public double winning(int number) {

        if (number == 0) return 0;
        int color = number % 18;
        if(color == 0) return 0;
        if (color <= 10 && color % 2 == 0) return (amount * 36) / 18;
        if (color > 10 && color % 2 == 1) return (amount * 36) / 18;
        return 0;
    }

    public String toString() {
        return code + " / " + amount;
    }

    public String getCode() {
        return code.toUpperCase() + " " + amount;
    }

    public Noir clone() {
        return (Noir) super.clone();
    }
}
