package common;


public class Single extends Bet implements Cloneable
{
    private int number;

    public Single() {
        this(0, 0);
    }

    public Single(double amount, int _number) {
        super("Single", amount);
        number = _number;
    }



    public void setNumber(int _number)
    {
        number=_number;
    }

    public double winning(int _number) {
        if(_number == number){
            return (amount * 36);
        }
        return 0;
    }

    @Override
    public String toString()
    {
        return code +" "+number +" / "+amount;
    }

    @Override
    public String getCode()
    {
        return code.toUpperCase()+"_"+number+" " + amount;
    }

    public Single clone()
    {
        return (Single)super.clone();
    }
}
