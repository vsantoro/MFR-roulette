
package common;

/**
 *
 * @author POOP
 */

public abstract class Bet implements Cloneable
{
    protected String code;
    protected double amount;
    public abstract  String toString();
    public abstract  String codeMessage();

    public void setAmount(double _amount)
    {
        amount=_amount;
    }
    public Bet(String _code)
    {
        code=_code;
        amount=0;
    }

    public Bet clone()
    {
        try
        {
            Bet b=(Bet)super.clone();
            return b;
        }
        catch(CloneNotSupportedException er){return null;}
    }

    public boolean isRouge(int number) {
        if(number == 0) return false;
        return (number == 1 || number == 3 ||
                number == 5 ||
                number == 7 || number == 9 ||
                number == 12 ||
                number == 14 ||
                number == 16 || number == 18 ||
                number == 19 || number == 21 ||
                number == 23 ||
                number == 25 || number == 27 ||
                number == 30 ||
                number == 32 ||
                number == 34 || number == 36);
    }

    public abstract double winning(int number);
}
