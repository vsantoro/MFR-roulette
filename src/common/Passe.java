package common;


public class Passe extends Bet {

    public Passe() {
        this(0);
    }

    public Passe(double amount) {
        super("Passe", amount);
    }

    public double winning(int number) {
        if(19 <= number && number <=36){
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

    public Passe clone() {
        return (Passe) super.clone();
    }
}
