package Model.GameElements;

import java.util.*;

public class City {
    private String name;
    private Coordonnees c;
    private HashMap<City, Route> routesFrom;

    public City(String name){
        this.name = name;
        this.routesFrom = new HashMap<>();
        c = new Coordonnees();
    }

    public String getName() {
        return name;
    }

    public Coordonnees getCoordonnees() {
        return c;
    }

    public HashMap<City, Route> getRoutesFrom() {
        return routesFrom;
    }

    public void addRoute(Route r, City to){
        //s'il n'y aucune route associée à cette destination
        //on initialise l'ensemble de routes
        if(routesFrom.get(to) == null){
            //puis on enregistre la route pour la ville concernée
            routesFrom.put(to,r);
        }
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
