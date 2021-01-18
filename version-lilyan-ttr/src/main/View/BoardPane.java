package View;

import Model.Game;
import Model.GameElements.City;
import Model.GameElements.Coordonnees;
import Model.GameElements.Destinations;
import Model.GameElements.Route;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardPane extends JPanel {
    HashMap<String,CityTile> coordonneesVille;
    Game game;
    boolean fini;

    /* initialise l'ensemble des tuiles */
    public BoardPane(Destinations d, Game g) {
        fini = false;
        this.game = g;
        coordonneesVille = new HashMap<>();
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1200,800));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;

        for (int y = 0; y < 20; y++) {
            gbc.gridy = y;
            for (int x = 0; x < 20; x++) {
                Coordonnees coordonnees = new Coordonnees(0,0);
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
                        coordonnees = tmp;
                    }
                }

                //si il y a une ville
                if (jl.getText() != "") {
                    CityTile c = new CityTile();
                    jl.setVisible(true);
                    c.add(jl);
                    c.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    c.setBackground(Color.GREEN);
                    add(c, gbc);
                    coordonneesVille.put(jl.getText(),c);
                }
            }
        }
        fini = true;
    }
    public void paintComponent (Graphics g) {
        super.paintComponent (g);
        //on parcoure toutes les villes affichées
        for (Map.Entry city : coordonneesVille.entrySet()) {
            //on enregistre la position des villes
            CityTile c1 = coordonneesVille.get(city.getKey());
            Point p1 = c1.getLocation();
            City ct1 = game.getD().getCity((String) city.getKey());

            //on parcoure les villes reliées à ct1
            for (Map.Entry route : ct1.getRoutesFrom().entrySet()) {
                City ct2 = game.getD().getCity(((City) route.getKey()).getName());
                //!\debug/!\ verifie que la ville  bien été affichée car certaines ne s'affichent pas parfois
                if(coordonneesVille.containsKey(ct2.getName())) {
                    CityTile c2 = coordonneesVille.get(ct2.getName());
                    Point p2 = c2.getLocation();

                    //on met la bonne couleur
                    switch (((ArrayList<Route>)route.getValue()).get(0).getColor()){
                        case RED:
                            g.setColor(Color.red);
                            break;
                        case BLACK:
                            g.setColor(Color.black);
                            break;
                        case BLUE:
                            g.setColor(Color.blue);
                            break;
                        case GREEN:
                            g.setColor(Color.green);
                            break;
                        case WHITE:
                            g.setColor(Color.white);
                            break;
                        case ORANGE:
                            g.setColor(Color.orange);
                            break;
                        case PURPLE:
                            g.setColor(Color.MAGENTA);
                            break;
                        case YELLOW:
                            g.setColor(Color.yellow);
                            break;
                        case GRAY:
                            g.setColor(Color.lightGray);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected color") ;
                    }

                    //on affiche une ligne entre ct1 et ct2
                    ((Graphics2D)g).setStroke(new BasicStroke(5));
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }


    }

}