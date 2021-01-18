package Model.GameElements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import Model.Enum.Color;
import Model.Game;

public class Destinations {
    private HashMap<String, City> destinations;

    public Destinations(){
        destinations = new HashMap<>();
        genererDestination();
        genererRoutes();
    }

    public City getCity(String name){
        return destinations.get(name);
    }

    public void addDestination(City d) {
        this.destinations.put(d.getName(),d);
    }
    public void addRoute(Route r, City from, City to) {
        this.destinations.get(from.getName()).addRoute(r, to);
        this.destinations.get(to.getName()).addRoute(r, from);
    }

    public HashMap<String, City> getDestinations() {
        return destinations;
    }

    private void genererDestination(){
        addDestination(new City("Paris"));
        addDestination(new City("Madrid"));
        addDestination(new City("Berlin"));
        addDestination(new City("Barcelone"));
        addDestination(new City("Marseille"));
        addDestination(new City("Tokyo"));
        addDestination(new City("Kyoto"));
        addDestination(new City("Sydney"));
        addDestination(new City("Rio De Janeiro"));
        addDestination(new City("Buenos aires"));
        addDestination(new City("Dubai"));
        addDestination(new City("Le Caire"));
        addDestination(new City("Alger"));
        addDestination(new City("Marakech"));
        addDestination(new City("Tunis"));
        addDestination(new City("Istanbul"));
        addDestination(new City("Lyon"));
        addDestination(new City("Francfort"));
        addDestination(new City("Bruxelles"));
        addDestination(new City("Bruge"));
        addDestination(new City("Genève"));
        addDestination(new City("Hong Kong"));
        addDestination(new City("Londres"));
        addDestination(new City("Pekin"));
        addDestination(new City("Camberra"));
        addDestination(new City("Ougadougou"));
        addDestination(new City("Rennes"));
        addDestination(new City("Reims"));
        addDestination(new City("Montpellier"));
        addDestination(new City("Seville"));
        addDestination(new City("Vienne"));
        addDestination(new City("Moscou"));
        addDestination(new City("St Petersbourg"));
        addDestination(new City("Kiev"));
        addDestination(new City("Rabat"));
        addDestination(new City("New York"));
        addDestination(new City("Brooklin"));
        addDestination(new City("Cleveland"));
        addDestination(new City("Los Angeles"));
        addDestination(new City("Houston"));
        System.out.println("destinations generated...");
    }
    private void genererRoutes(){
        addRoute(new Route(3, Color.BLUE, false, 0),destinations.get("Paris"), destinations.get("Berlin"));
        addRoute(new Route(1, Color.ORANGE, false, 0),destinations.get("Paris"), destinations.get("Berlin"));
        addRoute(new Route(1, Color.GRAY, false, 0),destinations.get("Madrid"), destinations.get("Berlin"));
        addRoute(new Route(2, Color.RED, false, 0),destinations.get("Madrid"), destinations.get("Paris"));
        System.out.println("routes generated...");
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
            if(!saisie.equals("Cancel")){
                System.out.println("Bad syntax. Try again.");
            }
            return null;
        }
        String v1 = parts[0].trim();
        String v2 = parts[1].trim();

        //si les villes n'existent pas
        if(!destinations.containsKey(v1) || !destinations.containsKey(v2)){
            if(!v1.equals("Cancel") || !v2.equals("Cancel")){
                System.out.println("Bad city(ies). Try again.");
            }
            return null;
        }

        //s'il n'y a pas de route entre ces destinations
        if(!destinations.get(v1).getRoutesFrom().containsKey(destinations.get(v2))){
            System.out.println("Bad route. Try again.");
            return null;
        }

        //s'il n'y a plus d'une route qui relie les deux
        ArrayList<Route> tmp = destinations.get(v1).getRoutesFrom().get(destinations.get(v2));
        if(tmp.size() > 1){
            //on fait choisir la route
            System.out.println("There are several roads connecting these cities , which one do you choose ?");
            for(int i = 0; i < tmp.size(); i++){
                System.out.println("    "+(i+1)+" - "+tmp.get(i));
            }
            int c = Game.saisieValidIntBornes(1,tmp.size());
            return tmp.get(c-1);
        }
        //sinon on retourne la seule route
        return tmp.get(0);
    }

    /**
     * saisie d'une destination existante
     *
     * @return City une destination existante
     */
    public City saisieDestination(){
        String choix = "";
        Scanner entree =   new Scanner(System.in);

        try{
            choix = entree.next();
        }catch (InputMismatchException e){
            entree.next();
        }
        //verif de la saisie
        boolean exist = destinations.containsKey(choix);
        while(!exist) {
            System.out.println("Bad city. Try again.");
            try {
                choix = entree.next();
            } catch (InputMismatchException e) {
                entree.next();
            }
            exist = destinations.containsKey(choix);
        }
        return destinations.get(choix);
    }
}
