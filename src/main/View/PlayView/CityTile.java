package View.PlayView;

import Model.GameElements.City;

import javax.swing.*;
import java.awt.*;

/**
 *  classe qui représente une ville du jeu sous forme d'un bouton
 */
public class CityTile extends JButton {
    private final City c; //enregistre la ville qu'elle représente

    public CityTile(City city) {
        super(city.getName());
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setFont(new Font("Arial", Font.BOLD, 11));
        c = city;
    }

    public City getCity() {
        return c;
    }

}
