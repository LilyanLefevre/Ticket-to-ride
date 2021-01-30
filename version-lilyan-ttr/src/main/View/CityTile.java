package View;

import Model.GameElements.City;

import javax.swing.*;
import java.awt.*;

/* classe qui représente une ville du tableau */
public class CityTile extends JButton {
    private final City c;

    public CityTile(City city) {
        super(city.getName());
        this.setFont(new Font("Arial", Font.BOLD, 11));
        c = city;
    }

    public City getCity() {
        //setMinimumSize(new Dimension(200,20));
        return c;
    }

}
