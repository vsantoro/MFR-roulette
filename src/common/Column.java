package common;

public class Column extends Bet{
    private int column;
    public Column()
    {
        super("Column");
    }
    public void setColumn(int _column)
    {
        column=_column;
    }

    public double winning(int number) {
        if(number % 3 == column){
            return (amount * 36) / 12;
        }
        return -amount;
    }

    @Override
    public String toString()
    {
        return code + " " + column + " / " + amount;
    }

    @Override
    public String codeMessage()
    {
        return code.toUpperCase() + "_" + column + " " + amount;
    }

    public Column clone()
    {
        return (Column)super.clone();
    }
}
