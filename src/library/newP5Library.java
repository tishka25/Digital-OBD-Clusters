/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package library;

/**
 *
 * @author teodorstanishev
 * @modified Oct 9, 2018
 * @version Expression version is undefined on line 14, column 15 in Templates/Classes/P5Library.java.
 */

import processing.core.PApplet;

public class newP5Library {

    private static PApplet parent;
    public final String VERSION = "0.01";
    /**
    * newP5Library constructor
    * @param parent PApplet
    */
    public newP5Library(PApplet parent) {
        this.parent = parent;
        this.parent.registerMethod("dispose", this);
    }

    
    public void dispose() {
         //this is a required method
    }

}
