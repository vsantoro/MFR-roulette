package common;

/**
 * Created by Jovan on 5/16/2015.
 */
public class Single extends Bet implements Cloneable
{
    private int number;
    public Single()
    {
        super("Single");
    }
    public void setNumber(int _number)
    {
        number=_number;
    }

    @Override
    public String toString()
    {
        return code +" "+number +" / "+amount;
    }

    @Override
    public String codeMessage()
    {
        return code.toUpperCase()+"_"+number+" " + amount;
    }

    public Single clone()
    {
        Single s=(Single)super.clone();
        return s;
    }
}
