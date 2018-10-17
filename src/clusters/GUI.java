/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusters;

import g4p_controls.GButton;
import g4p_controls.GEvent;
import g4p_controls.GImageToggleButton;
import g4p_controls.GPanel;
import processing.core.PApplet;

/**
 *
 * @author teodorstanishev
 */
public class GUI {
    public PApplet parent;
    public GPanel optionsPanel;
    public GImageToggleButton toggleDragBtn;
    public GButton menuButton;

    public GUI(PApplet p) {
        this.parent = p;

        menuButton = new GButton(parent, 0, parent.height - 20, 60, 20, "Options");
        menuButton.addEventHandler(parent, "menuButtonHandler");
        toggleDragBtn = new GImageToggleButton(parent, 0, 0);
        optionsPanel = new GPanel(parent, 0, 100, 400, 400, "Options");
        optionsPanel.addEventHandler(parent, "optionsHandler");
        optionsPanel.addControl(toggleDragBtn, 0, 30);
        optionsPanel.setVisible(false);
    }
    
}
