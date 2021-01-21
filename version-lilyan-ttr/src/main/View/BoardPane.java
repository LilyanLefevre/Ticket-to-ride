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
    private HashMap<String,CityTile> cityTileHashMap;
    private HashMap<Path2D,Route> routePath;
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
        List<Connection> connections = new ArrayList<Connection>();

        //on enregistre toutes les routes sous forme d'une connexion de JButton
        for (Map.Entry city : cityTileHashMap.entrySet()) {
            //on enregistre la position des villes
            CityTile c1 = cityTileHashMap.get(city.getKey());
            City ct1 = game.getD().getCity((String) city.getKey());
            //on parcoure les villes reliées à ct1
            for (Map.Entry route : ct1.getRoutesFrom().entrySet()) {
                City ct2 = game.getD().getCity(((City) route.getKey()).getName());
                CityTile c2 = cityTileHashMap.get(ct2.getName());
                Color c = Model.Enum.Color.getAwtColor(((ArrayList<Route>) route.getValue()).get(0).getColor());
                connections.add(new Connection(c1,c2,c, ((ArrayList<Route>) route.getValue()).get(0)));
            }
        }

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        //on trace les routes
        for (Connection connection : connections) {
            JButton source = connection.getSource();
            JButton dest = connection.getDestination();

            //on trace le fond en noir
            /*g2d.setStroke(new BasicStroke(8));
            g2d.setColor(Color.black);*/
            Path2D path = new Path2D.Double();
            /*path.moveTo(horizontalCenter(source), verticalCenter(source));
            path.curveTo(horizontalCenter(source), verticalCenter(dest),horizontalCenter(source), verticalCenter(dest),horizontalCenter(dest), verticalCenter(dest));
            g2d.draw(path);*/

            //on trace par dessus en plus petit la route de la bonne couleur
            g2d.setStroke(new BasicStroke(6));
            g2d.setColor(connection.getColor());
            path = new Path2D.Double();
            path.moveTo(horizontalCenter(source), verticalCenter(source));
            path.curveTo(horizontalCenter(source), verticalCenter(dest),horizontalCenter(source), verticalCenter(dest),horizontalCenter(dest), verticalCenter(dest));
            g2d.draw(path);
            routePath.put(path,connection.getRoute());
        }
        g2d.dispose();
    }

    protected double horizontalCenter(JComponent bounds) {

        return bounds.getX() + bounds.getWidth() / 2d;

    }

    protected double verticalCenter(JComponent bounds) {

        return bounds.getY() + bounds.getHeight() / 2d;

    }

    protected boolean hasIntersection(Line2D line, JComponent... exclude) {
        List<JComponent> toExclude = Arrays.asList(exclude);
        boolean intersects = false;
        for (Component comp : getComponents()) {
            if (!toExclude.contains(comp)) {
                if (line.intersects(comp.getBounds())) {
                    System.out.println(line.getP1() + "-" + line.getP2() + " intersets with " + ((JButton)comp).getText() + "; " + comp.getBounds());
                    intersects = true;
                    break;
                }
            }
        }
        return intersects;
    }

    protected Line2D lineDownTo(JComponent from, JComponent to) {
        return new Line2D.Double(horizontalCenter(from), from.getY(), horizontalCenter(from), verticalCenter(to));
    }

    protected Line2D lineAcrossTo(JComponent from, JComponent to) {
        return new Line2D.Double(from.getX(), verticalCenter(from), horizontalCenter(to), verticalCenter(from));
    }

    protected Point2D centerOf(Rectangle bounds) {
        return new Point2D.Double(bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2);
    }

    protected boolean canGoDownTo(Point2D startPoint, Point2D endPoint, JComponent to, JComponent from) {
        Point2D targetPoint = new Point2D.Double(startPoint.getX(), endPoint.getY());
        return !hasIntersection(new Line2D.Double(startPoint, targetPoint), to, from);
    }

    public HashMap<String, CityTile> getCityTileHashMap() {
        return cityTileHashMap;
    }

    public void setCityTileHashMap(HashMap<String, CityTile> cityTileHashMap) {
        this.cityTileHashMap = cityTileHashMap;
    }

    public HashMap<Path2D,Route> getRoutePath() {
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
        Point2D p = new Point2D.Double(x,y);

        for(Map.Entry path : routePath.entrySet()){
            if(((Path2D)path.getKey()).contains(p)){
                return ((Route)path.getValue());
            }
        }
        return null;
    }
}
