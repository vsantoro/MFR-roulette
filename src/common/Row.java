package common;

/**
 * Created by Dragan Obradovic on 17-May-15.
 */
public class Row extends Bet {
    private int row;
    public Row()
    {
        super("Row");
    }
    public void setRow(int _row)
    {
        row=_row;
    }

    @Override
    public String toString()
    {
        return code + " " + row + " / " + amount;
    }

    @Override
    public String codeMessage()
    {
        return code.toUpperCase() + "_" + row + " " + amount;
    }

    public Row clone()
    {
        return (Row)super.clone();
    }
}
