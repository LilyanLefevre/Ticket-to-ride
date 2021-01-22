package View;

import Controller.JButtonController;
import Controller.MouseController;
import Model.Game;
import Model.GameElements.City;
import Model.GameElements.Coordonnees;
import Model.GameElements.Destinations;
import Model.GameElements.Route;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

public class BoardPane extends JPanel {
    private static final int HIT_BOX_SIZE = 4;
    private HashMap<String,CityTile> cityTileHashMap;
    private HashMap<Line2D,Route> routePath;
    private Game game;
    private boolean fini;

    /* initialise l'ensemble des tuiles */
    public BoardPane(Destinations d, Game g) {
        fini = false;
        this.game = g;
        cityTileHashMap = new HashMap<>();
        routePath = new HashMap<>();

        setBackground(new Color(0.0f, 0.0f, 0.0f, 0.6f));
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
                    CityTile c = new CityTile(g.getD().getCity(jl.getText()));
                    c.add(jl);
                    c.setMargin(new Insets(2,1,1,2));
                    add(c, gbc);
                    cityTileHashMap.put(jl.getText(),c);
                }
            }
        }
        fini = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        routePath = new HashMap<>();
        super.paintComponent (g);
        //on parcoure toutes les villes affichées
        for (Map.Entry city : cityTileHashMap.entrySet()) {
            //on enregistre la position des villes
            CityTile c1 = cityTileHashMap.get(city.getKey());
            Point p1 = c1.getLocation();
            City ct1 = game.getD().getCity((String) city.getKey());

            //on parcoure les villes reliées à ct1
            for (Map.Entry route : ct1.getRoutesFrom().entrySet()) {
                City ct2 = game.getD().getCity(((City) route.getKey()).getName());
                CityTile c2 = cityTileHashMap.get(ct2.getName());
                Point p2 = c2.getLocation();

                //on met la bonne couleur
                Color c = Model.Enum.Color.getAwtColor(((Route)route.getValue()).getColor());
                g.setColor(c);

                //on affiche une ligne entre ct1 et ct2
                ((Graphics2D)g).setStroke(new BasicStroke(5));
                int larg = c2.getWidth()/2;
                int hau = c2.getHeight()/2;
                int distx = abs(ct1.getCoordonnees().getX() - ct2.getCoordonnees().getX());
                int disty = abs(ct1.getCoordonnees().getY() - ct2.getCoordonnees().getY());

                Line2D line;
                if(ct2.getRoutesFrom().containsKey(ct1)){
                    if(distx>disty) {
                        line = new Line2D.Double(p1.x + larg, p1.y + hau + 2, p2.x + larg, p2.y + hau + 2);
                    }else {
                        line = new Line2D.Double(p1.x + larg + 2, p1.y + hau, p2.x + larg + 2, p2.y + hau);
                    }
                }else {
                    line = new Line2D.Double(p1.x + larg, p1.y + hau, p2.x + larg, p2.y + hau);
                }

                ((Graphics2D) g).draw(line);
                routePath.put(line, ((Route) route.getValue()));
            }
        }
    }



    public HashMap<String, CityTile> getCityTileHashMap() {
        return cityTileHashMap;
    }

    public void setCityTileHashMap(HashMap<String, CityTile> cityTileHashMap) {
        this.cityTileHashMap = cityTileHashMap;
    }

    public HashMap<Line2D,Route> getRoutePath() {
        return routePath;
    }

    public void setActionListener(JButtonController gc){
        for(Map.Entry city : cityTileHashMap.entrySet()){
            ((CityTile)city.getValue()).addActionListener(gc);
        }
    }
    public void setMouseListener(MouseController mc){
        addMouseListener(mc);
    }

    public Route getRouteClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        int boxX = x - HIT_BOX_SIZE / 2;
        int boxY = y - HIT_BOX_SIZE / 2;

        int width = HIT_BOX_SIZE;
        int height = HIT_BOX_SIZE;

        for (Map.Entry line : routePath.entrySet()) {
            if (((Line2D)line.getKey()).intersects(boxX, boxY, width, height)){
                return ((Route)line.getValue());
            }
        }
        return null;
    }
}
