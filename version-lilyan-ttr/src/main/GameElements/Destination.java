package GameElements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Destination {
    private String name;
    private HashMap<Destination, ArrayList<Route>> routesFrom;

    public Destination(String name){
        this.name = name;
        this.routesFrom = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public HashMap<Destination, ArrayList<Route>> getRoutesFrom() {
        return routesFrom;
    }

    public void addRoute(Route r, Destination to){
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
        Destination that = (Destination) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * saisie d'une destination existante
     *
     * @return String une destination existante
     */
    /*public static Destination saisieDestination(){
        String choix = "";
        Scanner entree =   new Scanner(System.in);

        try{
            choix = entree.next();
        }catch (InputMismatchException e){
            entree.next();
        }
        //verif de la saisie
        int exist = 0;
        for(Destination c : Destination.values()) {
            try {
                if (c.toString().equals(choix)) {
                    exist = 1;
                }
            }catch(IllegalArgumentException e){};
        }
        while(exist != 1) {
            System.out.println("Erreur : cette destination n'existe pas. Veuillez réessayer : ");
            try {
                choix = entree.next();
            } catch (InputMismatchException e) {
                entree.next();
            }
            for(Destination c : Destination.values()) {
                try {
                    if (c.toString().equals(choix)) {
                        exist = 1;
                    }
                }catch(IllegalArgumentException e){};
            }
        }
        return Destination.valueOf(choix);
    }
    */
    @Override
    public String toString() {
        return this.name;
    }
}
