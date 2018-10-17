/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusters;

/**
 *
 * @author teodorstanishev
 */
import g4p_controls.GPanel;
import java.io.File;
import java.io.FilenameFilter;
import static java.lang.Math.*;
import processing.core.*;

import processing.core.PVector;

public class Cluster {

    PApplet parent;

    //Only for development purposes
    public static String folder = "/Users/teodorstanishev/NetBeansProjects/Digital Gauge/src/assets/";

    public float startValue;
    public float endValue;
    public float startDegree;
    public float endDegree;
    public float value = 0;
    public String name = "cluster";
    public float step = 1;
    public float angle = 0;
    public PVector position = new PVector(0, 0);
    public int size = 200;
    public PGraphics gauge, arrow;
    public static boolean enableDrag = false;

    public Cluster(PApplet p, float startValue, float endValue, float startDegree, float endDegree, String name) {
        this.parent = p;
        this.startValue = startValue;
        this.endValue = endValue;
        this.startDegree = startDegree;
        this.endDegree = endDegree;
        this.name = name;

        //Create gauge
        this.gauge = createSkin("DEFAULT_GAUGE", size, size);
        //Create arrow
        this.arrow = createSkin("DEFAULT_ARROW", (int) (size * 0.5f), (int) (size * 0.01f));
    }

    public Cluster(PApplet p, float startValue, float endValue, float startDegree, float endDegree, String name, PImage g, PImage a) {
        this(p, startValue, endValue, startDegree, endDegree, name);

        //Create gauge
        this.gauge = createSkin(g, "DEFAULT", size, size);

        //Create arrow 
        this.arrow = createSkin(a, "DEFAULT", (int) (size * 0.5), (int) (size * 0.1));

    }

    public void setup() {
        parent.imageMode(parent.CENTER);
    }

    public void update() {
        angle = parent.radians(parent.map(value, startValue, endValue, startDegree, endDegree));
        float _width = size;
        float _height = size * 0.1f;

        //Draw the gauge skin
        parent.image(gauge, position.x, position.y, size * 2, size * 2);
        //
        //Arrow skin
        parent.pushMatrix();
        parent.translate(position.x, position.y);
        parent.rotate((angle));
        parent.image(arrow, -_width * 0.5f, 0);
//        parent.image((PImage) arrow, -_width * 0.5f, 0, _width, _height);
        parent.popMatrix();

        this.drag();
    }

    public void setDrag(boolean e) {
        this.enableDrag = e;
    }

    public boolean getDrag() {
        return enableDrag;
    }

    private void drag() {
        if (enableDrag && parent.mousePressed && parent.dist(position.x, position.y, parent.mouseX, parent.mouseY) <= size) {
            float x = position.x;
            float y = position.y;
            position = new PVector((x + parent.mouseX - parent.pmouseX), (y + parent.mouseY - parent.pmouseY));
        }
    }

    public void setValue(float value) {
        this.value = value;
    }

    public PVector angleToCoord(float angle, PVector position, float r) {
        // circle.getRadius() * cosf(angle*(3.14f/180)+135)+circle.getPosition().x
        // circle.getRadius() * sinf(angle*(3.14f/180)+135) + circle.getPosition().y
        PVector pos = new PVector(0, 0);
        angle = PApplet.radians(angle);
        pos.x = (float) ((r - (r * 0.11f)) * sin((angle - 80) * -1) + (position.x - (r * 0.05f)) + r);
        pos.y = (float) ((r - (r * 0.11f)) * cos((angle - 80) * -1) + (position.y + (r * 0.05f)) + r);

        return pos;
    }

    //Create skin without images
    private PGraphics createSkin(String type, int w, int h) {
        File dir = new File(folder);

        File[] matches = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(Cluster.this.name);
            }
        });
        System.out.println(matches);
        PGraphics g = parent.createGraphics(w * 2, h * 2);
        switch (type) {
            case "DEFAULT_GAUGE":
                g.beginDraw();
                //Put the backgroudn of the gauge
                g.ellipseMode(parent.CORNER);
                g.fill(100);
                g.ellipse(0, 0, g.width, g.height);
                //
                //Put the arc in the frontend
                g.fill(255);
                g.arc(0, 0, g.width, g.height, parent.radians(startDegree + 180), parent.radians(endDegree + 180));
                //

                //Text 
                g.textSize(22);
                g.fill(0);
                g.textAlign(parent.CENTER);
                g.text(name, (int) (g.width * 0.5f), (int) (g.height * 0.8f));

                for (int i = (int) startValue; i <= endValue; i = 20 + i) {
                    int _value = (int) i;
                    _value = (int) parent.map(_value, startValue, endValue, startDegree, endDegree);
                    int x = (int) angleToCoord(_value, position, size).x;
                    int y = (int) angleToCoord(_value, position, size).y;
                    g.fill(0);
                    g.textSize(20);
                    g.text(i, x, y);
                }
                //
                g.endDraw();
                break;
            case "DEFAULT_ARROW":
                g.beginDraw();
                g.fill(255, 0, 0);
                g.rect(0, 0, g.width, g.height);
                g.endDraw();
                break;
        }
        g.save(folder+type);
        return g;
    }

    //Create skin with images
    private PGraphics createSkin(PImage i, String type, int w, int h) {
        PGraphics g = parent.createGraphics(w * 2, h * 2);
        switch (type) {
            case "DEFAULT":
                g.beginDraw();
                g.image(i, 0, 0, g.width, g.height);
                g.endDraw();
                break;
        }
        return g;
    }
}
