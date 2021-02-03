package View;

import Controller.JButtonController;
import Controller.RouteController;
import Model.Game;
import Model.GameElements.City;
import Model.GameElements.Coordonnees;
import Model.GameElements.Destinations;
import Model.GameElements.Route;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;

/**
 * classe qui représente le panel qui affiche les villes et les routes du jeu
 */
public class BoardPane extends JPanel {
    private static final int HIT_BOX_SIZE = 4; //hit box quand on clique sur les routes
    private HashMap<String,CityTile> cityTileHashMap; //ensemble qui contient les boutons représentants les villes
    private HashMap<Line2D,Route> routePath; //ensemble qui contient les Line2D qui représentent les routes
    private Game game;
    private Destinations d;
    private boolean fini;

    /* initialise l'ensemble des tuiles */
    public BoardPane(Destinations d, Game g) {
        fini = false;
        this.game = g;
        this.d = d;
        cityTileHashMap = new HashMap<>();
        routePath = new HashMap<>();

        setBackground(Color.lightGray);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1250,800));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;

        //on utilise une grille de 20*20 pour représenter les villes
        for (int y = 0; y < 20; y++) {
            gbc.gridy = y;
            for (int x = 0; x < 20; x++) {
                Coordonnees coordonnees = new Coordonnees(0,0);
                gbc.gridx = x;

                //on prepare l'affichage du nom (ou pas) de la ville (s'il y a)
                String name = "";
                gbc.gridwidth = 1;

                //on regarde s'il existe une ville qui a ces coordonnées là
                for (Map.Entry city : d.getDestinations().entrySet()) {
                    Coordonnees tmp = ((City) city.getValue()).getCoordonnees();

                    //s'il y a une ville avec ces coordonnées on entre le nom dans le label
                    if (tmp.getX() == x && tmp.getY() == y) {
                       name = ((String) city.getKey());
                        gbc.gridwidth = 1;
                    }
                }

                //si il y a une ville
                if (name != "") {
                    CityTile c = new CityTile(g.getD().getCity(name));
                    c.setMargin(new Insets(2,1,1,2));
                    add(c, gbc);
                    cityTileHashMap.put(name,c);
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
                Color c;
                if(((Route)route.getValue()).isAlreadyTakenRoute()){
                    c = ((Route)route.getValue()).getPlayer().getColor();
                }else{
                    c = Model.Enum.Color.getAwtColor(((Route)route.getValue()).getColor());
                }
                g.setColor(c);

                //on affiche une ligne entre ct1 et ct2
                ((Graphics2D)g).setStroke(new BasicStroke(5));

                //on initialise les points qui vont être reliés par une ligne
                Line2D line;
                int larg1 = c1.getWidth()/2;
                int larg2 = c2.getWidth()/2;
                int hau1 = c1.getHeight()/2;
                int hau2 = c2.getHeight()/2;

                //on espace un peu les routes qu'on detecte comme doubles
                if(ct2.getRoutesFrom().containsKey(ct1)){
                    boolean dejaFirstDouble = false;
                    for(Map.Entry r : routePath.entrySet()){
                        if(((Route) r.getValue()).getDest1() == ct2 && ((Route) r.getValue()).getDest2() == ct1){
                            dejaFirstDouble = true;
                        }
                    }
                    if(dejaFirstDouble){
                        larg1 = (c1.getWidth())/2+4;
                        larg2 = (c2.getWidth())/2+4;
                        hau1 = (c1.getHeight())/2+4;
                        hau2 = (c2.getHeight())/2+4;
                        if(ct1.getCoordonnees().getX() > ct2.getCoordonnees().getX()
                                && ct1.getCoordonnees().getY() > ct2.getCoordonnees().getY() ){
                            larg1 = (c1.getWidth())/2+10;
                            larg2 = (c2.getWidth())/2+10;
                            hau1 = (c1.getHeight())/2+10;
                            hau2 = (c2.getHeight())/2+10;
                        }

                    }else{
                        larg1 = (c1.getWidth())/2-4;
                        larg2 = (c2.getWidth())/2-4;
                        hau1 = (c1.getHeight())/2-4;
                        hau2 = (c2.getHeight())/2-4;

                        if(ct1.getCoordonnees().getX() < ct2.getCoordonnees().getX()
                                && ct1.getCoordonnees().getY() < ct2.getCoordonnees().getY() ){
                            larg1 = (c1.getWidth())/2-10;
                            larg2 = (c2.getWidth())/2-10;
                            hau1 = (c1.getHeight())/2-10;
                            hau2 = (c2.getHeight())/2-10;
                        }
                    }

                }

                line = new Line2D.Double(p1.x + larg1, p1.y + hau1, p2.x + larg2, p2.y + hau2);

                ((Graphics2D) g).draw(line);
                routePath.put(line, ((Route) route.getValue()));
            }
        }
    }

    /**
     * ajoute un action listener sur les boutons du panel
     *
     * @param gc le listener
     */
    public void setActionListener(JButtonController gc){
        for(Map.Entry city : cityTileHashMap.entrySet()){
            ((CityTile)city.getValue()).addActionListener(gc);
        }
    }

    /**
     * ajoute un mouselistener sur les routes du panel
     *
     * @param mc le listener
     */
    public void setMouseListener(RouteController mc){
        addMouseListener(mc);
    }

    public void updateRoute(){
        removeAll();
        revalidate();
        repaint();
    }

    /**
     * fonction qui retourne la route la plus proche de là où on clique
     *
     * @param e MouseEvent
     *
     * @return Route la route cliquée
     */
    public Route getRouteClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        int boxX = x - HIT_BOX_SIZE / 2;
        int boxY = y - HIT_BOX_SIZE / 2;

        //on met une hitbox pour ne pas avoir à cliquer au pixel près sur la route
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

