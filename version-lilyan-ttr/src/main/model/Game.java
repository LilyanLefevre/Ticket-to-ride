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

    public ArrayList<TrainCard> getDrawTrainCards() {
        return drawTrainCards;
    }

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
            drawTrainCards.remove(nCard);
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
     * fonction qui va découper une ligne du fichier et creer la carte destination ou la route correspondante
     * et l'ajoute à la pioche ou au jeu
     *
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
            boolean isTunel = Boolean.parseBoolean(tokenizer.nextToken());
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
        StringBuilder str = new StringBuilder("DRAW VISIBLE TRAIN CARDS : \n");
        for (TrainCard drawVisibleTrainCard : drawVisibleTrainCards) {
            str.append(drawVisibleTrainCard.toString());
        }


        //on affiche les joueurs
        str.append("PLAYERS :\n");
        for (Player player : players) {
            str.append(player.toString());
        }

        //on affiche l'ensemble des routes qui relient les villes
        str.append("\nROUTES : \n");
        for (Route route : routes) {
            str.append(route.toString());
        }
        return str.toString();
    }

    public void playTurn(Player p){
        int choix;

        //on récupère le choix du joueur
        System.out.println("Choisissez une option:\n  1 - Prendre des cartes Wagon\n  2 - Prendre possession des routes\n  3 - Prendre des cartes Destination\n  4 - Bâtir une gare");
        choix = saisieValidIntBornes(1,4);

        switch (choix) {
            //si on pioche des cartes wagons
            case 1:
                int c;
                System.out.println("/////////Vous souhaitez piocher des cartes Wagon./////////");
                //on itère deux fois car on peut tirer deux cartes sauf si on prend une locomotive délibérément
                for(int j = 0; j < 2; j++) {
                    //on présente les cartes dispos
                    System.out.println("Voici les cartes retournées actuellement : ");
                    for (int i = 0; i < drawVisibleTrainCards.size(); i++) {
                        System.out.print("  " + (i + 1) + " -" + drawVisibleTrainCards.get(i).toString());
                    }

                    //on fait choisir une carte au joueur
                    System.out.println("  6 -    Piocher une carte au hasard\nSaisissez un numéro : ");
                    c = saisieValidIntBornes(1, 6);

                    //s'il tente de prendre une carte loco et qu'il a déjà pris une autre carte -> erreur
                    while(c != 6 && drawVisibleTrainCards.get(c - 1).getColor() == Color.RAINBOW && j > 0 ){
                        System.out.println("Si vous prenez une carte locomotive visible lors d'un tour, vous ne pouvez pas piocher d'autre carte ! Veuillez saisir un autre nombre : ");
                        c = saisieValidIntBornes(1, 6);
                    }

                    //si il choisit une carte au hasard
                    if (c == 6) {
                        TrainCard tmp = p.drawTrainCard(drawTrainCards);
                        System.out.println("Vous avez pioché la carte " + tmp);
                    }

                    //si il prend une carte de la pioche visible
                    else {
                        //on retire la carte de la pioche de cartes visibles
                        TrainCard tmp = drawVisibleTrainCards.get(c - 1);
                        drawVisibleTrainCards.remove(tmp);

                        //on remet une nouvelle carte tiree au hasard dans la pioche
                        int nCard = (int) (Math.random() * (drawTrainCards.size()));
                        drawVisibleTrainCards.add(drawTrainCards.get(nCard));
                        drawTrainCards.remove(nCard);

                        //on l'ajoute dans les cartes du joueur
                        p.addTrainCard(tmp);
                        System.out.println("Vous avez pioché la carte " + tmp);

                        //si il a pris une carte locomotive on arrête
                        if (tmp.getColor() == Color.RAINBOW) {
                            j = 2;
                        }
                    }
                }
                break;

            //si on pose des wagons sur une route
            case 2:

                //on affiche les routes possibles
                System.out.println("Voici les routes disponibles:");
                for(int i = 0; i < routes.size(); i++){
                    if(routes.get(i).isAlreadyTakenRoute()){
                        System.out.println("DÉJÀ PRISE "+(i+1)+" - "+routes.get(i));
                    }else {
                        System.out.println((i + 1) + " - " + routes.get(i));
                    }
                }

                //on fait choisir une route au joueur
                System.out.println("Saisissez un numéro : ");
                c = saisieValidIntBornes(1, routes.size());

                //on vérifie que la route est libre et qu'il a assez de wagons pour prendre cette route
                while(routes.get(c-1).isAlreadyTakenRoute() || p.getWagons() < routes.get(c-1).getRequire()) {
                    if(routes.get(c - 1).isAlreadyTakenRoute()){
                        System.out.println("Erreur : la route est déjà prise ! ");
                    }
                    //on vérifie qu'il a le bon nombre de trains dispo
                    if (p.getWagons() < routes.get(c - 1).getRequire()) {
                        System.out.println("Erreur : vous n'avez pas assez de wagons pour prendre cette route ! ");
                    }

                    //il resaisit un numero de route
                    c = saisieValidIntBornes(1, routes.size());
                }

                //si la route est un tunnel
                if(routes.get(c - 1).isTunel()){
                    //si la route n'a pas de couleur
                    if(routes.get(c - 1).getColor() == Color.GREY){
                        System.out.println("Saisissez une couleur de carte Wagon pour tenter de prendre le tunnel :");
                        Color couleur = Color.valueOf(p.saisieOwnedColor());
                        routes.get(c - 1).getTunnel(p,couleur, this);
                    }
                    //si la route a une couleur
                    else{
                        routes.get(c - 1).getTunnel(p,routes.get(c - 1).getColor(),this);
                    }
                }
                //si c'est pas un tunnel
                else{
                    //si c'est un ferrie
                    if(routes.get(c - 1).getColor() == Color.GREY){
                        System.out.println("Saisissez une couleur de carte Wagon pour prendre le ferrie :");
                        Color couleur = Color.valueOf(p.saisieOwnedColor());
                        routes.get(c - 1).getFerrie(p,couleur,this);
                    }else{
                        routes.get(c - 1).getRoute(p,routes.get(c - 1).getColor(), this);
                    }
                }

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

    public int saisieValidIntBornes(int borneInf,int borneSup){
        int choix = -1;
        Scanner entree =   new Scanner(System.in);

        try{
            choix = entree.nextInt();
        }catch (InputMismatchException e){
            entree.next();
        }
        //verif de la saisie
        while(choix < borneInf || choix > borneSup){
            System.out.println("Erreur : le numéro que vous avez entré est incorrect. Veuillez réessayer : ");
            try{
                choix = entree.nextInt();
            }catch (InputMismatchException e){
                entree.next();
            }
        }
        return choix;
    }

    public TrainCard drawTrainCard(){
        int nCard = (int)(Math.random() * (drawTrainCards.size()));

        TrainCard tmp = drawTrainCards.get(nCard);

        // et on la retire de la pioche
        drawTrainCards.remove(nCard);

        return tmp;
    }
}
