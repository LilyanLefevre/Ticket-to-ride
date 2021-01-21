package View;

import Model.GameElements.Route;

import javax.swing.*;
import java.awt.*;

public class Connection {

    private final JButton source;
    private final JButton destination;
    private Color c;
    private Route r;

    public Connection(JButton source, JButton destination, Color c, Route r) {
        this.source = source;
        this.destination = destination;
        this.c = c;
        this.r = r;
    }

    public Color getColor(){
        return c;
    }
    public JButton getSource() {
        return source;
    }
    public Route getRoute() {
        return r;
    }
    public JButton getDestination() {
        return destination;
    }


}

