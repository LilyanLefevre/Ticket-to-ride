package Model.GameElements;

import java.util.*;

/**
 * classe qui représente une ville
 */
public class City {
    private String name;
    private Coordinates coordinates;
    private Random random;

    /**
     * cet attibrut contient toutes les villes reliées par la ville courante
     * et stocke une route pour chacun des couples composés de la ville courante
     * et de chaque ville de l'ensemble
     */
    private HashMap<City, Route> routesFrom;

    public City(String name, Random r){
        this.name = name;
        this.routesFrom = new HashMap<>();
        this.random = r;
        coordinates = new Coordinates(r);
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public HashMap<City, Route> getRoutesFrom() {
        return routesFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City that = (City) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * fonction qui permet d'ajouter une route qui relie la ville
     * courant à la ville donnée en paramètre
     *
     * @param r la route à ajouter
     * @param to la ville reliée
     */
    public void addRoute(Route r, City to){
        //s'il n'y aucune route associée à cette destination
        //on initialise l'ensemble de routes
        if(routesFrom.get(to) == null){
            //puis on enregistre la route pour la ville concernée
            routesFrom.put(to,r);
        }
    }

    /**
     * fonction qui retourne un string représentant toutes les routes qui partent de la ville
     * courante
     *
     * @return un String avec toutes les routes
     */
    public String routesFromToString(){
        String ret = "";
        Iterator it = routesFrom.entrySet().iterator();

        //on parcoure toutes les villes qui sont reliées à la ville courante
        while(it.hasNext()){
            Map.Entry city = (Map.Entry) it.next();
            ret += "        to "+city.getKey()+" : \n";
            ret += "            "+((Route)city.getValue()).toString();

        }
        return ret;
    }
}
