package Model.GameElements;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

    //A MODIFIER
    public void addRoute(Route r) {
        this.destinations.get(r.getDest1().getName()).addRoute(r, r.getDest2());
    }

    public HashMap<String, City> getDestinations() {
        return destinations;
    }

    /**
     * fonction qui genere entre 35 et 45 villes
     * et qui construit leur nom en piochant dans un dictionnaire de prefixes et
     * de suffixes
     */
    private void genererDestination() {
        String prefixe = "./txt/prefixes.txt";
        String suffixe = "./txt/suffixes.txt";
        List<String> lines = new ArrayList<>();
        List<String> linessuff = new ArrayList<>();
        int count = 0;

        //on lit toutes les lignes dans le fichier de prefixes
        try {
            lines = Files.readAllLines(Paths.get(Thread.currentThread().getContextClassLoader().getResource(prefixe).getPath()), Charset.defaultCharset());
            for (String line : lines) {
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //on lit toutes les lignes dans le fichier de suffixes
        try {
            linessuff = Files.readAllLines(Paths.get(Thread.currentThread().getContextClassLoader().getResource(suffixe).getPath()),Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //on tire un nb de villes au hasard
        Random random = new Random();
        count--;
        int nbvilles = 35+random.nextInt(45-35);

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
            System.out.println(name);
            City c1 = new City(name);

            //on récupère toutes les positions générées
            ArrayList<Coordonnees> cvilles = new ArrayList<>();
            for (Map.Entry ville : destinations.entrySet()) {
                cvilles.add(((City) ville.getValue()).getCoordonnees());
            }

            //on change les positions si deux villes se collent/superposent
            //il faut mini une case libre entre chaque ville
            for(int y=0;y<cvilles.size();y++){
                if((cvilles.get(y).getX()+1 == c1.getCoordonnees().getX()|| cvilles.get(y).getX()-1==c1.getCoordonnees().getX() || cvilles.get(y).getX() == c1.getCoordonnees().getX()) &&(cvilles.get(y).getY()+1 == c1.getCoordonnees().getY()|| cvilles.get(y).getY()-1==c1.getCoordonnees().getY() || c1.getCoordonnees().getY() == cvilles.get(y).getY())){
                    c1 = new City(name);
                    y=0;
                }
            }
            addDestination(c1);
        }
        System.out.println(nbvilles+" destinations generated...");
    }

    /**
     * fonction qui génère en moyenne 2 fois plus de routes qu'il n'y a de villes
     * en évitant les croisements de villes/routes
     */
    private void genererRoutes(){
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
        }

        for (Map.Entry from : destinations.entrySet()){
            boolean enter = false;
            double distance = 10000;
            City destination=(City)from.getValue();
            int x1 = ((City)from.getValue()).getCoordonnees().getX();
            int y1 = ((City)from.getValue()).getCoordonnees().getY();
            for (Map.Entry to : destinations.entrySet()){
                int x2 = ((City)to.getValue()).getCoordonnees().getX();
                int y2 = ((City)to.getValue()).getCoordonnees().getY();
                double distance1 = Math.sqrt(Math.pow((y2 - y1),2) + Math.pow((x2 - x1),2));
                if(distance1<distance && ((City)to.getValue()).getRoutesFrom().size()==0 && ((City) from.getValue()).getName()!=((City) to.getValue()).getName() && !((City)to.getValue()).getRoutesFrom().containsKey(((City)from.getValue()).getName())){
                    enter = true;
                    distance = distance1;
                    destination = (City)to.getValue();
                }
            }
            if(enter) {
                Random random = new Random();
                int r = 0+random.nextInt(9-0);
                while (CountColor.get(r)>6){
                    r = 0+random.nextInt(9-0);
                }
                Color c = randomColor.get(r);
                CountColor.set(r,CountColor.get(r)+1);
                //A MODIFIER
                addRoute(new Route((City)from.getValue(),destination,1, c, false, 0));
            }
        }

        for (Map.Entry from : destinations.entrySet()){
            boolean enter = false;
            double distance = 10000;
            City destination=(City)from.getValue();
            int x1 = ((City)from.getValue()).getCoordonnees().getX();
            int y1 = ((City)from.getValue()).getCoordonnees().getY();
            for (Map.Entry to : destinations.entrySet()){
                int x2 = ((City)to.getValue()).getCoordonnees().getX();
                int y2 = ((City)to.getValue()).getCoordonnees().getY();
                double distance1 = Math.sqrt(Math.pow((y2 - y1),2) + Math.pow((x2 - x1),2));
                if(distance1<distance && ((City) from.getValue()).getName()!=((City) to.getValue()).getName()  && !((City)from.getValue()).getRoutesFrom().containsKey((to.getValue()))){
                    enter = true;
                    distance = distance1;
                    destination = (City)to.getValue();
                }
            }
            if(enter) {
                Random random = new Random();
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
                addRoute(new Route((City)from.getValue(),destination,1, c, false, 0));
            }
        }
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
