package common;


public class Manque extends Bet {

    public Manque() {
        super("Manque");
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

    public String codeMessage() {
        return code.toUpperCase() + " " + amount;
    }

    public Manque clone() {
        return (Manque)super.clone();
    }
}
