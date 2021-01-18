package View;

import Model.GameElements.City;
import Model.GameElements.Coordonnees;
import Model.GameElements.Destinations;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BoardPane extends JPanel {
    HashMap<String,Point> coordonneesVille;

    /* initialise l'ensemble des tuiles */
    public BoardPane(Destinations d) {
        coordonneesVille = new HashMap<>();
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1200,900));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;

        for (int y = 0; y < 20; y++) {
            gbc.gridy = y;
            for (int x = 0; x < 20; x++) {
                String nomVille = "";
                gbc.gridx = x;

                //on prepare l'affichage du nom (ou pas) de la ville (s'il y a)
                JLabel jl = new JLabel();
                gbc.gridwidth = 1;

                //on regarde s'il existe une ville qui a ces coordonnées là
                for (Map.Entry city : d.getDestinations().entrySet()) {
                    Coordonnees tmp = ((City) city.getValue()).getCoordonnees();

                    //s'il y a une ville avec ces coordonnées on entre le nom dans le label
                    if (tmp.getX() == x && tmp.getY() == y) {
                        jl.setText(((String) city.getKey()));
                        gbc.gridwidth = 2;
                        nomVille = ((City) city.getValue()).getName();
                    }
                }

                //si il n'y avait pas de ville alors on fait une tuile normale
                if (jl.getText() == "") {
                    Tile c = new Tile();
                    jl.setVisible(true);
                    c.add(jl);
                    c.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    c.setBackground(Color.RED);
                    add(c, gbc);
                }
                //sinon il y a une ville sur cette tuile
                else{
                    CityTile c = new CityTile();
                    jl.setVisible(true);
                    c.add(jl);
                    c.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    c.setBackground(Color.GREEN);
                    add(c, gbc);
                    coordonneesVille.put(jl.getText(),new Point(jl.getX(),jl.getY()));
                }
            }
        }
    }
    public void paintComponent (Graphics g) {
        super.paintComponent (g);

        Point p1 = coordonneesVille.get("Paris");
        Point p2 = coordonneesVille.get("Berlin");

        // Draw a line between paris and berlin
        g.drawLine (p1.x, p1.y,p2.x,p2.y);
        System.out.println("ptComponent"+p1.x +" "+ p1.y+" " + p2.x+" " + p2.y+" ");

    }

}