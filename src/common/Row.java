package common;


public class Row extends Bet {
    private int row;

    public Row() {
        this(0, 0);
    }

    public Row(double amount, int _row) {
        super("Row", amount);
        row = _row;
    }

    public void setRow(int _row) {
        row=_row;
    }

    public double winning(int number) {
        if(number != 0 && Math.ceil(number / 3) == row){
            return (amount * 36) / 3;
        }
        return 0;
    }

    @Override
    public String toString() {
        return code + " " + row + " / " + amount;
    }

    @Override
    public String getCode() {
        return code.toUpperCase() + "_" + row + " " + amount;
    }

    public Row clone() {
        return (Row)super.clone();
    }
}
