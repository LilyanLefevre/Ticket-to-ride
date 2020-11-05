package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import GameElements.*;
import Enum.*;

public class Game {
    // l'ensemble des joueurs
    private ArrayList<Player> players;

    // pioche de cartes de couleurs
    private ArrayList<TrainCard> drawTrainCards;

    // 5 cartes de couleurs retournées
    private ArrayList<TrainCard> drawVisibleTrainCards;

    // pioche de cartes destination
    private ArrayList<DestinationCard> drawDestinationCards;

    // ensemble de routes du plateau
    private ArrayList<Route> routes;

    // répertorie les gares qui ont été posées sur le plateau
    private ArrayList<TrainStation> trainStations;

    // fichier contenant les cartes destination
    private String destination_file_path;

    //fichier contenant les routes
    private String route_file_path;

    public Game(ArrayList<String> names, String destination_file_path, String route_file_path) {

        // initialisation des cartes colorées
        drawTrainCards = new ArrayList<>(Color.values().length*14);
        for(int i = 0; i < Color.values().length; i++){
            if(Color.values()[i] != Color.GREY) {
                for (int j = 0; j < 14; j++) {
                    drawTrainCards.add(new TrainCard(Color.values()[i]));
                }
            }
        }

        //initialisation des cartes visibles
        drawVisibleTrainCards = new ArrayList<>(5);
        for(int i = 0; i < 5; i++){
            int nCard = (int)(Math.random() * (drawTrainCards.size()));

            // on ajoute la carte tirée dans le jeu du joueur
            drawVisibleTrainCards.add(drawTrainCards.get(nCard));

            // et on la retire de la pioche
            drawTrainCards.remove(i);
        }

        // initialisation des cartes destinations
        this.destination_file_path = destination_file_path;
        drawDestinationCards = new ArrayList<>();
        readDestinationFromFile("destination");

        // initialisation des routes
        this.route_file_path = route_file_path;
        routes = new ArrayList<>();
        readDestinationFromFile("route");

        // initialisation des joueurs
        players = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            players.add(i,new Player(names.get(i), Color.values()[i], drawDestinationCards, drawTrainCards));
        }

        // initialise le tableau de gares posées
        trainStations = new ArrayList<>();
    }

    /**
     * fonction qui lit les cartes destinations dans le fichier au chemin
     * destination_file_path et les ajoute dans la pioche
     */
    private void readDestinationFromFile(String typeOfFile){
        // ouvre le fichier et le lit
        if(typeOfFile.equals("destination")){
            try(BufferedReader br = new BufferedReader(new FileReader(destination_file_path))) {
                String line = br.readLine();
                while (line != null && !line.equals("%")) {
                    processLine(line, typeOfFile);
                    line = br.readLine();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }else if(typeOfFile.equals("route")) {
            try (BufferedReader br = new BufferedReader(new FileReader(route_file_path))) {
                String line = br.readLine();
                while (line != null && !line.equals("%")) {
                    processLine(line, typeOfFile);
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * fonction qui va découper une ligne du fichier et creer la carte destination correspondante
     * et l'ajoute à la pioche
     * @param line ligne du fichier à découper
     */
    private void processLine(String line, String typeOfFile){
        StringTokenizer tokenizer = new StringTokenizer(line, " ");

        Destination dest1 = Destination.valueOf(tokenizer.nextToken());
        Destination dest2 = Destination.valueOf(tokenizer.nextToken());

        if( typeOfFile.equals("destination")) {
            int points = Integer.parseInt(tokenizer.nextToken());
            this.drawDestinationCards.add(new DestinationCard(dest1, dest2, points));
        }else if (typeOfFile.equals("route")){
            Color color = Color.valueOf(tokenizer.nextToken());
            int points = Integer.parseInt(tokenizer.nextToken());
            Boolean isTunel = Boolean.parseBoolean(tokenizer.nextToken());
            int locomotive = Integer.parseInt(tokenizer.nextToken());
            this.routes.add(new Route(dest1, dest2, points, color, isTunel, locomotive));
        }
    }

    /**
     * Answers a string containing a concise, human-readable
     * description of the receiver.
     *
     * @return String a printable representation for the receiver.
     */
    @Override
    public String toString() {
        //on affiche les cartes retournées
        String str = "DRAW VISIBLE TRAIN CARDS : \n";
        for(int i = 0; i < drawVisibleTrainCards.size(); i++){
            str += drawVisibleTrainCards.get(i).toString();
        }


        //on affiche les joueurs
        str += "PLAYERS :\n";
        for(int i = 0; i < players.size(); i++){
            str += players.get(i).toString();
        }

        //on affiche l'ensemble des routes qui relient les villes
        str += "\nROUTES : \n";
        for(int i = 0; i < routes.size(); i++){
            str += routes.get(i).toString();
        }
        return str;
    }

    public void playTurn(Player p){
        int choix = -1;
        Scanner entree =   new Scanner(System.in);
        boolean err = false;

        //on récupère le choix du joueur
        System.out.println("Choisissez une option:\n  1 - Prendre des cartes Wagon\n  2 - Prendre possession des routes\n  3 - Prendre des cartes Destination\n  4 - Bâtir une gare");
        try{
            choix = entree.nextInt();
        }catch (InputMismatchException e){
            entree.next();
        }
        //verif de la saisie
        while(choix < 1 || choix > 4){
            System.out.println("Erreur : le numéro que vous avez entré est incorrect. Veuillez réessayer : ");
            try{
                choix = entree.nextInt();
            }catch (InputMismatchException e){
                entree.next();
            }
        }

        switch (choix) {
            //si on pioche des cartes wagons
            case 1:
                System.out.println("Vous avez pioché deux cartes Wagon");
                break;

            //si on pose des wagons sur une route
            case 2:
                System.out.println("Vous avez posé des wagons");
                break;

            //si on pioche des cartes destination
            case 3:
                System.out.println("Vous avez pioché trois cartes Destination");
                break;

            //si on construit une gare
            case 4:
                System.out.println("Vous avez posé une gare");
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + choix);
        }
    }

    /**
     * tells if a player has less than 3 wagons
     * that involves the end of the game
     *
     * @return true if a player has less than 3 wagons, false otherwise
     */
    public boolean gameIsOver(){
        for (Player p : players) {
            if( p.getWagons() < 3 ) {
                return true;
            }
        }
        return false;
    }

    public void runGame(){
        while(!gameIsOver()){
            for(Player p : players) {
                System.out.println("\n\n////////////////////C'est au tour de " + p.getName()+" de jouer////////////////////");
                System.out.println(p);
                playTurn(p);
            }
        }
    }
}
