package clusters;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import g4p_controls.*;
import processing.core.*;

/**
 *
 * @author teodorstanishev
 */
@SuppressWarnings("serial")
public class Main extends PApplet {

    @Override
    public void settings() {
        size(800, 600, JAVA2D);
    }

    Cluster rpm;
    Cluster speedo;
    GUI gui;

    @Override
    public void setup() {
        imageMode(CENTER);
        rpm = new Cluster(this, 0.0f, 90.0f, 180.0f, 450.0f, "RPM", loadImage("assets/lancia_delta_rpm.png"), loadImage("assets/dab.jpg"));
        speedo = new Cluster(this, 0.0f, 220.0f, -45.0f, 220.0f, "Speedo");

        rpm.position = new PVector(width / 2, height / 2);
        speedo.position = new PVector(width / 3, height / 2);

        gui = new GUI(this);
        
            }

    public void menuButtonHandler(GButton btn, GEvent event) {
        if (event == GEvent.CLICKED) {
            gui.optionsPanel.setVisible(true);
            gui.optionsPanel.setCollapsed(false);
        }
    }

    public void handleToggleButtonEvents(GImageToggleButton button, GEvent event) {
        if (button == gui.toggleDragBtn && event == GEvent.CLICKED)
            Cluster.enableDrag = button.getState() !=0 ;
    }

    public void optionsHandler(GPanel p, GEvent e) {
        if (e == GEvent.COLLAPSED) {
            gui.optionsPanel.setVisible(false);
        }
    }

    @Override
    public void draw() {
        background(0);
        debugValues(rpm);
        debugValues(speedo);
        speedo.update();
        rpm.update();

    }

    void debugValues(Cluster c) {
        if (keyPressed && key == 'w') {
            c.setValue(c.value + 1);
        } else if (keyPressed && key == 's') {
            c.setValue(c.value - 1);
        }
    }

    /**
     * @param passedArgs the command line arguments
     */
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{Main.class.getName()};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
