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
