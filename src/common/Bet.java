
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
}
