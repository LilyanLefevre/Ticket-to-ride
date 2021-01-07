package GameElements;

import java.util.*;

public class City {
    private String name;
    private HashMap<City, ArrayList<Route>> routesFrom;

    public City(String name){
        this.name = name;
        this.routesFrom = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public HashMap<City, ArrayList<Route>> getRoutesFrom() {
        return routesFrom;
    }

    public void addRoute(Route r, City to){
        //s'il n'y aucune route associée à cette destination
        //on initialise l'ensemble de routes
        if(routesFrom.get(to) == null){
            ArrayList<Route> tmp = new ArrayList<>();
            tmp.add(r);
            //puis on enregistre la route pour la ville concernée
            routesFrom.put(to,tmp);
        }

        //s'il y a deja des routes pour cette ville
        //on ajoute simplement au tableau
        else{
            routesFrom.get(to).add(r);
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
            //on affiche toutes les routes pour rejoindre les villes depuis la ville courante
            for(int i = 0; i < ((ArrayList<Route>)city.getValue()).size(); i++){
                ret += "            "+((ArrayList<Route>)city.getValue()).get(i).toString();
            }
        }
        return ret;
    }
}
