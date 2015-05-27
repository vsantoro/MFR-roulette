package common;


public class Rouge extends Bet {

    public Rouge() {
        this(0);
    }

    public Rouge(double amount) {
        super("Rouge", amount);
    }

    public double winning(int number) {
        if (number == 0) return 0;
        int color = number % 18;
        if(color == 0) return (amount * 36) / 18;
        if (color <= 10 && color % 2 == 1) return (amount * 36) / 18;
        if (color > 10 && color % 2 == 0) return (amount * 36) / 18;
        return 0;
    }

    public String toString() {
        return code + " / " + amount;
    }

    public String getCode() {
        return code.toUpperCase() + " " + amount;
    }

    public Rouge clone() {
        return (Rouge)super.clone();
    }
}
