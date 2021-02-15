package Model.GameElements;

import Model.Enum.Color;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * classe qui représente un ensemble de villes composant le jeu
 */
public class Destinations {
    private HashMap<String, City> destinations;
    private ArrayList<Route> routes;
    private Random random;

    /* variables servant aux différents appels récursifs pour la fonction construireChemin */
    private ArrayList<String> names;
    private ArrayList<Route> rEmpruntees;
    private ArrayList<Integer>NbRails = new ArrayList<>();

    public Destinations(Random random){
        destinations = new HashMap<>();
        routes = new ArrayList<>();
        rEmpruntees = new ArrayList<>();
        this.random = random;
        genererDestination();
        genererRoutes();
    }

    public City getCity(String name){
        return destinations.get(name);
    }

    public HashMap<String, City> getDestinations() {
        return destinations;
    }

    /**
     * fonction ajoutant une ville à l'ensemble
     *
     * @param d City la ville à ajouter
     */
    public void addDestination(City d) {
        this.destinations.put(d.getName(),d);
    }

    /**
     * fonction ajoutant une route à une ville de l'ensemble
     *
     * @param r Route la route à ajouter
     */
    public void addRoute(Route r) {
        this.destinations.get(r.getDest1().getName()).addRoute(r, r.getDest2());
        routes.add(r);
    }


    /**
     * fonction qui genere entre 35 et 40 villes
     * et qui construit leur nom en piochant dans un dictionnaire de prefixes et
     * de suffixes
     */
    private void genererDestination() {
        /* fichier où se trouvent les prefixes et les suffixes */
        String prefixe = "/prefixes.txt";
        String suffixe = "/suffixes.txt";

        List<String> lines = new ArrayList<>();
        List<String> linessuff = new ArrayList<>();
        int count = 0;

        //on lit toutes les lignes dans le fichier de prefixes
        try {
            lines = new BufferedReader(new InputStreamReader( getClass().getResourceAsStream(prefixe), StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
            for (String line : lines) {
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //on lit toutes les lignes dans le fichier de suffixes
        try {
            linessuff = new BufferedReader(new InputStreamReader( getClass().getResourceAsStream(suffixe), StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //on tire un nb de villes au hasard
        count--;
        int nbvilles = 35+random.nextInt(40-35);

        for (int i=0;i<nbvilles;i++){
            //pour chaque ville crée on pioche un préfixe et un suffixe et on les assemble
            int nbpref = 0+random.nextInt(count-0);
            int nbsuff = 0+random.nextInt(count-0);
            String name = lines.get(nbpref)+linessuff.get(nbsuff);

            //on verifie que la ville n'existe pas déjà
            while(destinations.containsKey(name)){
                nbpref = 0+random.nextInt(count-0);
                nbsuff = 0+random.nextInt(count-0);
                name = lines.get(nbpref)+linessuff.get(nbsuff);
            }
            City c1 = new City(name, random);

            //on récupère toutes les positions générées
            ArrayList<Coordonnees> cvilles = new ArrayList<>();
            for (Map.Entry ville : destinations.entrySet()) {
                cvilles.add(((City) ville.getValue()).getCoordonnees());
            }

            //on change les positions si deux villes se collent/superposent
            //il faut mini une case libre entre chaque ville
            for(int y=0;y<cvilles.size();y++){
                if(cvilles.get(y).distance(c1.getCoordonnees()) < 2){
                    c1 = new City(name, random);
                    y=0;
                }
            }
            addDestination(c1);
        }
    }

    /**
     * fonction qui génère en moyenne 2 fois plus de routes qu'il n'y a de villes
     * en évitant les croisements de villes/routes
     */
    private void genererRoutes(){
        //on enregistre les couleurs dans un tableau pour pouvoir compter
        //leur nombre d'utilisation
        ArrayList<Line2D>TabRoutes = new ArrayList<>();
        HashMap<Integer, Color> randomColor = new HashMap<>();
        randomColor.put(0,Color.WHITE);
        randomColor.put(1,Color.RED);
        randomColor.put(2,Color.BLACK);
        randomColor.put(3,Color.BLUE);
        randomColor.put(4,Color.GREEN);
        randomColor.put(5,Color.ORANGE);
        randomColor.put(6,Color.PURPLE);
        randomColor.put(7,Color.YELLOW);
        randomColor.put(8,Color.GRAY);

        ArrayList<Integer>CountColor = new ArrayList<>();
        for (int i=0;i<9;i++){
            CountColor.add(0);
            NbRails.add(0);
        }

        int compteur = 0;
        for (Map.Entry from : destinations.entrySet()){
            GenererFirstRoute((City)from.getValue(),TabRoutes,CountColor,randomColor,1, compteur);
        }

        int count = 0;

        for (Map.Entry from : destinations.entrySet()){
            if(count<(destinations.size()*2)/3&&GenererLastRoute((City)from.getValue(),TabRoutes,CountColor,randomColor)==true)
                count++;
        }
        genererDoubleRoute();
    }

    /**
     * double 10 routes suivant leur fréquence d'utilisation
     */
    public void genererDoubleRoute(){
        Graph<City, DefaultEdge> g = new DefaultUndirectedGraph<>(DefaultEdge.class);

        //on ajoute toutes les villes au graph
        for (Map.Entry city : destinations.entrySet()) {
            City c1 = (City)city.getValue();
            g.addVertex(c1);
        }

        //on ajoute tous les arcs entre les villes
        for (Route r : routes){
            g.addEdge(r.getDest1(), r.getDest2());
        }
        BellmanFordShortestPath<City, DefaultEdge> belmanFordAlg = new BellmanFordShortestPath<>(g);


        for(City c1 : g.vertexSet()){
            for(City c2 : g.vertexSet()) {
                if(c1 != c2) {
                    //on cherche le chemin le plus court qui relie les deux villes
                    ShortestPathAlgorithm.SingleSourcePaths<City, DefaultEdge> iPaths = belmanFordAlg.getPaths(c1);

                    //on récupère les villes parcourues par le chemin (il y en a forcément un)
                    GraphPath<City, DefaultEdge> path = iPaths.getPath(c2);
                    if(path != null) {
                        List<City> listCities = path.getVertexList();
                        if (!listCities.isEmpty()) {
                            for(int i = 0; i < listCities.size() - 1 ; i++){
                                City v1 = listCities.get(i);
                                City v2 = listCities.get(i+1);
                                Route r = getRouteFromString(v1.getName()+" - "+v2.getName());
                                if(r == null){
                                    r = getRouteFromString(v2.getName()+" - "+v1.getName());
                                }
                                r.setFreqUtilisation(r.getFreqUtilisation()+1);

                            }
                        }
                    }
                }
            }
        }
        //on trie les routes de la plus utilisée à la moins utilisée
        Collections.sort(routes);

        HashMap<Integer, Color> randomColor = new HashMap<>();
        randomColor.put(0,Color.WHITE);
        randomColor.put(1,Color.RED);
        randomColor.put(2,Color.BLACK);
        randomColor.put(3,Color.BLUE);
        randomColor.put(4,Color.GREEN);
        randomColor.put(5,Color.ORANGE);
        randomColor.put(6,Color.PURPLE);
        randomColor.put(7,Color.YELLOW);
        randomColor.put(8,Color.GRAY);

        int k = 0;
        //on complete les routes pour avoir deux fois plus de routes que de villes
        while(routes.size() != destinations.size() * 2){
            Route cur = routes.get(k);

            //on prend une couleur au hasard différente de celle de la premiere
            int r = 0+random.nextInt(9-0);
            while(randomColor.get(r) == cur.getColor()){
                r = 0+random.nextInt(9-0);
            }
            //on ajoute la double route
            Route tmp = new Route(cur.getDest2(), cur.getDest1(), cur.getRequire(), randomColor.get(r), cur.isTunel(), cur.getLocomotive());
            addRoute(tmp);
            k++;
        }
    }

    /**
     * fonction servant à générer des routes pour relier toutes les villes ensemble
     * @param from
     * @param TabRoutes
     * @param CountColor
     * @param randomColor
     * @param nbtour
     * @param compteur
     */
    public void GenererFirstRoute(City from, ArrayList<Line2D>TabRoutes,ArrayList<Integer>CountColor,HashMap<Integer, Color> randomColor, int nbtour, int compteur){
        int x1 = from.getCoordonnees().getX();
        int y1 = from.getCoordonnees().getY();
        boolean enter = false;
        Line2D lenreg = null;
        City destination = from;
        double distance = 10000;

        for (Map.Entry to : destinations.entrySet()){
            boolean intersect = false;
            int x2 = ((City)to.getValue()).getCoordonnees().getX();
            int y2 = ((City)to.getValue()).getCoordonnees().getY();
            double distance1 = Math.sqrt(Math.pow((y2 - y1),2) + Math.pow((x2 - x1),2));
            Line2D l = new Line2D.Double(x1,y1,x2,y2);

            if(!(from.getCoordonnees().equals(((City)to.getValue()).getCoordonnees()))
                    && distance1<distance
                    && !((City)to.getValue()).getRoutesFrom().containsKey(from.getName())) {
                if ((nbtour == 1 && ((City) to.getValue()).getRoutesFrom().size() == 0) || nbtour == 2) {
                    for (int i = 0; i < TabRoutes.size(); i++) {
                        if (l.intersectsLine(TabRoutes.get(i)) && l != TabRoutes.get(i)) {
                            System.out.println(from);
                            Point2D p = new Point2D.Double(x2, y2);
                            Point2D p1 = new Point2D.Double(x1, y1);
                            Point2D inter = intersection(TabRoutes.get(i), l);

                            if (!p.equals(inter) && !p1.equals(inter)) {
                                intersect = true;
                            }
                        }
                    }
                    if (!intersect) {
                        distance = distance1;
                        enter = true;
                        destination = (City) to.getValue();
                        lenreg = l;
                    }
                }
            }
        }
        if(enter) {
            int r = 0+random.nextInt(9-0);
            while (CountColor.get(r)>6){
                r = 0+random.nextInt(9-0);
            }
            Color c = randomColor.get(r);
            CountColor.set(r,CountColor.get(r)+1);
            //A MODIFIER
            if(lenreg!=null)
                TabRoutes.add(lenreg);
            System.out.println("Distance : "+distance);
            int taille = CheckTaille(distance);
            System.out.println("Taille : "+taille);
            if(c == Color.GRAY){
                addRoute(new Route((City)from,destination,taille, c, true, 0));
            }else{
                addRoute(new Route((City)from,destination,taille, c, false, 0));
            }
        }
        else{
            compteur++;
            if(compteur!=1000)
                GenererFirstRoute(from, TabRoutes, CountColor, randomColor, 2, compteur);
        }
    }

    /**
     * fonction servant à générer des routes pour ...
     * @param from
     * @param TabRoutes
     * @param CountColor
     * @param randomColor
     */
    public boolean GenererLastRoute(City from, ArrayList<Line2D>TabRoutes,ArrayList<Integer>CountColor,HashMap<Integer, Color> randomColor){
        boolean enter = false;
        Line2D lenreg = null;
        Line2D l = null;
        double distance = 10000;
        City destination=from;
        int x1 = from.getCoordonnees().getX();
        int y1 = from.getCoordonnees().getY();
        for (Map.Entry to : destinations.entrySet()) {
            boolean intersect = false;
            int x2 = ((City) to.getValue()).getCoordonnees().getX();
            int y2 = ((City) to.getValue()).getCoordonnees().getY();
            l = new Line2D.Double(x1, y1, x2, y2);
            double distance1 = Math.sqrt(Math.pow((y2 - y1),2) + Math.pow((x2 - x1),2));
            if (distance1<distance
                    &&!(from.getCoordonnees().equals(((City)to.getValue()).getCoordonnees()))
                    && !(from.getRoutesFrom().containsKey((to.getValue())))
                    && !(((City) to.getValue()).getRoutesFrom().containsKey(from))){
                for (int i = 0; i < TabRoutes.size(); i++) {
                    if (l.intersectsLine(TabRoutes.get(i))&& l!=TabRoutes.get(i)) {
                        Point2D p = new Point2D.Double(x2,y2);
                        Point2D p1 = new Point2D.Double(x1,y1);
                        Point2D inter = intersection(TabRoutes.get(i),l);
                        if(!p.equals(inter) && !p1.equals(inter)){
                            intersect = true;}
                    }
                }
                if (!intersect) {
                    distance = distance1;
                    enter = true;
                    destination = (City) to.getValue();
                    lenreg = l;
                }
            }
        }
        if(enter) {
            int r = 0+random.nextInt(9-0);
            int cc = 0;
            boolean b = false;
            while (CountColor.get(r)>6 && !b){
                r = 0+random.nextInt(9-0);
                cc++;
                if(cc==9)
                    b=true;
            }
            Color c = randomColor.get(r);
            CountColor.set(r,CountColor.get(r)+1);
            //A MODIFIER
            if(lenreg!=null)
                TabRoutes.add(lenreg);
            System.out.println("Distance : "+distance);
            int taille = CheckTaille(distance);
            System.out.println("Taille : "+taille);
            if(c == Color.GRAY){
                addRoute(new Route((City)from,destination,taille, c, true, 0));
            }else{
                addRoute(new Route((City)from,destination,taille, c, false, 0));
            }
            return true;
        }
        return false;
    }


    /**
     * fonction qui créer un chemin entre deux villes
     *
     //* @param v1 City une ville
     //* @param v2 City l'autre ville
     */
    private void construireChemin(City v1, City v2) {
        if(!names.contains(v1.getName())){
            names.add(v1.getName());
        }
        for (Route r : routes){
            if(r.getDest1() == v1) {
                if(r.getDest2() == v2) {
                    rEmpruntees.add(r);
                }
                else{
                    if(!names.contains(r.getDest2().toString())) {
                        construireChemin(r.getDest2(), v2);
                    }
                }
            }
        }
    }


    public int CheckTaille(double distance){
        int nbroutes = destinations.size()*2;
        if(distance<2 || (distance>=2 && distance<=3)) {
            if(NbRails.get(1)+1<nbroutes*0.1) {
                NbRails.set(1, NbRails.get(1) + 1);
                return 1;
            }
        }
        if(distance>=3 && distance<=4 || (NbRails.get(1)+1>nbroutes*0.1 && distance<=4)){
            if(NbRails.get(2)+1<nbroutes*0.3){
                NbRails.set(2, NbRails.get(2)+1);
                return 2;
            }
        }
        if(distance>=4 && distance<=5 || (NbRails.get(2)+1>nbroutes*0.3 && distance<=5)){
            if(NbRails.get(3)+1<nbroutes*0.3){
                NbRails.set(3, NbRails.get(3)+1);
                return 3;
            }
        }
        if(distance>=5 && distance<=6 || (NbRails.get(3)+1>nbroutes*0.3 && distance<=6)){
            if(NbRails.get(4)+1<nbroutes*0.2){
                NbRails.set(4, NbRails.get(4)+1);
                return 4;
            }
        }
        if(distance>=6 && distance <=7 || (NbRails.get(4)+1>nbroutes*0.2 && distance<=7)) {
            if(NbRails.get(5)+1<nbroutes*0.04){
                NbRails.set(5, NbRails.get(5)+1);
                return 5;
            }
        }
        if(distance>=7 && distance<=8 || NbRails.get(5)+1>nbroutes*0.04 && distance<=8){
            if(NbRails.get(6)+1<nbroutes*0.07){
                NbRails.set(6, NbRails.get(6)+1);
                return 6;
            }
        }
        if(distance>=8 || NbRails.get(6)+1>nbroutes*0.07){
            NbRails.set(8, NbRails.get(8)+1);
            return 8;
        }
        return 0;
    }


    /**
     * retourne une route saisie sous la forme v1 - v2
     *
     * @return City une destination existante ou null si le joueur veut annuler
     */
    public Route getRouteFromString(String saisie){
        String [] parts = saisie.split("-");
        //si la saisie est mauvaise
        if(parts.length != 2){
            return null;
        }
        String v1 = parts[0].trim();
        String v2 = parts[1].trim();

        //si les villes n'existent pas
        if(!destinations.containsKey(v1) || !destinations.containsKey(v2)){
            return null;
        }

        //s'il n'y a pas de route entre ces destinations
        if(!destinations.get(v1).getRoutesFrom().containsKey(destinations.get(v2))){
            return null;
        }

        Route tmp = destinations.get(v1).getRoutesFrom().get(destinations.get(v2));
        return tmp;
    }

    /**
     * fonction retourne le Point2D où se croisent les deux lignes
     *
     * @param a Line2D la première ligne
     * @param b Line2D la deuxième ligne
     *
     * @return Point2D le point d'intersection
     */
    public static Point2D intersection(Line2D a, Line2D b) {
        double x1 = a.getX1(), y1 = a.getY1(), x2 = a.getX2(), y2 = a.getY2(), x3 = b.getX1(), y3 = b.getY1(),
                x4 = b.getX2(), y4 = b.getY2();
        double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (d == 0) {
            return null;
        }

        double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
        double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

        return new Point2D.Double(xi, yi);
    }
}