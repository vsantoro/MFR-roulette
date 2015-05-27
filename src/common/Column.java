package common;

public class Column extends Bet{
    private int column;

    public Column() {
        this(0, 0);
    }

    public Column(double amount, int _column) {
        super("Column", amount);
        column = _column;
    }
    public void setColumn(int _column) {
        column = _column;
    }

    public double winning(int number) {
        if(number != 0 && number % 3 == column){
            return (amount * 36) / 12;
        }
        return 0;
    }

    @Override
    public String toString()
    {
        return code + " " + column + " / " + amount;
    }

    @Override
    public String getCode()
    {
        return code.toUpperCase() + "_" + column + " " + amount;
    }

    public Column clone()
    {
        return (Column)super.clone();
    }
}
