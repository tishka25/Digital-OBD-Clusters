/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusters;

import clusters.Cluster;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.*;
import processing.data.Table;
import processing.data.TableRow;

/**
 *
 * @author teodorstanishev
 */
public class DataLogger extends Thread {

    private PApplet parent;
    private Cluster cluster;
    public Table table;

    public DataLogger() {

    }

    public DataLogger(PApplet p, Cluster c) {
        this.parent = p;
        this.cluster = c;
    }

    @Override
    public void run() {
        try {
            setup();
        } catch (InterruptedException ex) {
            Logger.getLogger(DataLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setup() throws InterruptedException {
        table = new Table();
        table.addColumn("id");
        table.addColumn("value");
        table.addColumn("name");
        table.addColumn("time");
        while (true) {
            if (cluster.value >= 0) {
                TableRow newRow = table.addRow();
                newRow.setInt("id", table.getRowCount() - 1);
                newRow.setInt("value", (int)cluster.value);

                newRow.setString("name", cluster.name);
                newRow.setString("time", LocalDateTime.now().toString());

                parent.saveTable(table, "/Users/teodorstanishev/Desktop/logs/" + cluster.name + ".csv");

            }
            Thread.sleep(1000);
        }
    }

    public Object getData() {
        return cluster;
    }
}
