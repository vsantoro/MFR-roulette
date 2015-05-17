package common;


public class Passe extends Bet {
    public Passe() {
        super("Passe");
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

    public String codeMessage() {
        return code.toUpperCase() + " " + amount;
    }

    public Passe clone() {
        return (Passe) super.clone();
    }
}
