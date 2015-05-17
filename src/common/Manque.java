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

public class Manque extends Bet {

    public Manque() {
        super("Manque");
    }

    public double winning(int number) {
        if(1 <= number && number <= 18){
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

    public Manque clone() {
        return (Manque)super.clone();
    }
}
