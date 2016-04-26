package common;


public abstract class Bet implements Cloneable
{
    protected String code;
    protected double amount;
    public abstract  String toString();
    public abstract  String getCode();

    public void setAmount(double _amount)
    {
        amount=_amount;
    }

    public Bet(String _code, double _amount)
    {
        code = _code;
        amount = _amount;
    }

    //makes a bet duplicate
    public Bet clone()
    {
        try {
            return (Bet)super.clone();
        }
        catch(CloneNotSupportedException er){return null;}
    }

    //calculates winning amount, depending on the bet
    public abstract double winning(int number);
}
