package common;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Bets
{
    private static ArrayList<Bet> bets = new ArrayList<>();

	private Bets() { }

    static {
        bets.add( new Manque() );
        bets.add( new Passe() );
        bets.add( new Rouge() );
        bets.add(new Noir());
        bets.add(new Pair());
        bets.add(new Impair());
        bets.add(new Single());
        bets.add(new Column());
        bets.add(new Row());
    }

    public static Bet decodeBet(String str)
    {
        String []parts = str.split("\\s|_");
        for(Bet b : bets)
        {
            String[] temp=(b.getClass().getName().toUpperCase()).split("\\.");
            if(temp[1].equals(parts[0]))
            {
                try
                {
					Bet bb = null;

                    bb=b.clone();
                    bb.setAmount(Double.parseDouble(parts[parts.length - 1]));
                    if(parts[0].equals("SINGLE"))
                        ((Single)bb).setNumber(Integer.parseInt(parts[1]));
                    else
                    if(parts[0].equals("COLUMN"))
                    {
                        ((Column)bb).setColumn(Integer.parseInt(parts[1]));
                    }
                    else
                    if(parts[0].equals("ROW"))
                    {
                        ((Row)bb).setRow(Integer.parseInt(parts[1]));
                    }
                    return bb;
                }
                catch (Exception ex)
                {
                }
            }
        }
        return null;
    }
}
