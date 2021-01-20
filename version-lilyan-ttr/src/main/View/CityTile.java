package View;

import Model.GameElements.City;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/* classe qui représente une ville du tableau */
public class CityTile extends JButton {
    private final City c;

    public CityTile(City city) {
        c = city;
    }

    /* modifie la taille par défaut de la grille utilisée */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(80, 20);
    }

    /* change la couleur des tuiles quand on appuie dessus */
    protected void colorTile() {
        if (getBackground() != Color.RED) {
            setBackground(Color.RED);
        } else {
            setBackground(Color.WHITE);
        }
    }

    public City getCity() {
        return c;
    }
}
