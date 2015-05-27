package common;


public class Manque extends Bet {

    public Manque() {
        this(0);
    }

    public Manque(double amount) {
        super("Manque", amount);
    }

    public double winning(int number) {
        if(1 <= number && number <= 18){
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

    public Manque clone() {
        return (Manque)super.clone();
    }
}
