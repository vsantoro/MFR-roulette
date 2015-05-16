/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package common;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*
* @author POOP
*/

public class Bets
{
    private static ArrayList<Bet> bets = new ArrayList<>();

	private Bets() { }

    static {
//        bets.add( new Manque() );
//        bets.add( new Passe() );
//        bets.add( new Rouge() );
        bets.add(new Single());
    }

    public static Bet decodeBet(String str)
    {
        String []parts = str.split("\\s|_");
        for(Bet b : bets)
        {
            if(b.getClass().getName().toUpperCase().equals(parts[0]))
            {
                try
                {
					Bet bb = null;
					// Slede�a linija je namerno zakomentarisana zato �to
					// klasa Bet nema javnu metodu clone().
                    // bb = b.clone();						// Na mestu gde se tr�i stvaranje ovakvog objekta
															// treba postaviti iznos uloga

					// Ovako mo�e da funkcioni�e samo za one uloge koji
					// nemaju dodatne parametre.
					// Za one uloge koji imaju dodatne parametre, poput Single uloga, potrebno
					// je obezbediti postavljanje tih parametara pre return.

                    bb=b.clone();
                    bb.setAmount(parts.length - 1);
                    if(parts[0]=="SINGLE")
                        ((Single)bb).setNumber(Integer.parseInt(parts[1]));
                    else
                    if(parts[0]=="COLUMN")
                    {
                        //****Dodati odgovarajuca polja
                    }
                    else
                    {
                        //****Dodati odgovarajuca polja
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
