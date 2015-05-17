package common;


public class Rouge extends Bet {
    public Rouge()
    {
        super("Rouge");
    }

    public double winning(int number) {
        if(isRouge(number)){
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

    public Rouge clone() {
        return (Rouge)super.clone();
    }
}
