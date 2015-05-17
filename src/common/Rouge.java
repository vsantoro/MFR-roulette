/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author POOP
 */
public class Rouge extends Bet {
    public Rouge()
    {
        super("Rouge");
    }

    public double winning(int number) {
        if(isRouge(number)){
            return (amount * 36) / 18;
        }
        return -amount;
    }

    public String toString() {
        return code + " / " + amount;
    }

    public String codeMessage() {
        return code.toUpperCase() + " " + amount;
    }

    public Rouge clone() {
        return (Rouge)super.clone();
    }
}
