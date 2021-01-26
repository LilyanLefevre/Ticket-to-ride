package Model;

import java.util.*;

import Model.GameElements.*;
import Model.Enum.*;

import static java.lang.System.exit;

public class Game{
    // l'ensemble des joueurs
    private final ArrayList<Player> players;

    //joueur actuel
    private Player currentPlayer;
    private int indexCurrentPlayer;

    // pioche de cartes de couleurs
    private ArrayList<WagonCard> drawWagonCards;

    // 5 cartes de couleurs retournées
    private ArrayList<WagonCard> drawVisibleWagonCards;

    // pioche de cartes destination
    private ArrayList<DestinationCard> drawDestinationCards;

    //variable qui sert dans le cas où on annule une action
    private int alreadyCalled;

    //variable qui représente l'ensemble des destinations du jeu
    private Destinations d;

    private ArrayList<String> names;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<WagonCard> getDrawWagonCards() {
        return drawWagonCards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<WagonCard> getDrawVisibleTrainCards() {
        return drawVisibleWagonCards;
    }

    public ArrayList<DestinationCard> getDrawDestinationCards() {
        return drawDestinationCards;
    }

    public int getAlreadyCalled() {
        return alreadyCalled;
    }

    public void setAlreadyCalled(int alreadyCalled) {
        this.alreadyCalled = alreadyCalled;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Destinations getD() {
        return d;
    }

    public Game(ArrayList<String> names) {

        // initialisation des cartes wagons
        drawWagonCards = new ArrayList<>(Color.values().length*14);
        for(int i = 0; i < Color.values().length; i++){
            if(Color.values()[i] != Color.GRAY) {
                for (int j = 0; j < 14; j++) {
                    drawWagonCards.add(new WagonCard(Color.values()[i]));
                }
            }
        }

        //initialisation des cartes wagons visibles
        drawVisibleWagonCards = new ArrayList<>(5);
        for(int i = 0; i < 5; i++){
            int nCard = (int)(Math.random() * (drawWagonCards.size()));

            // on ajoute la carte tirée dans le jeu du joueur
            drawVisibleWagonCards.add(drawWagonCards.get(nCard));

            // et on la retire de la pioche
            drawWagonCards.remove(nCard);
        }

        //initialisation des destinations et des routes
        d = new Destinations();

        //initialisation des cartes destination
        drawDestinationCards = DestinationCard.genererCarteDestination(d);

        // initialisation des joueurs
        players = new ArrayList<>();
        HashMap<Integer, java.awt.Color> couleurJoueur = new HashMap<>();
        couleurJoueur.put(0, new java.awt.Color(248,160,141));
        couleurJoueur.put(1, new java.awt.Color(145,205,253));
        couleurJoueur.put(2, new java.awt.Color(165,102,12));
        couleurJoueur.put(3, new java.awt.Color(160,189,138));
        for(int i = 0; i < 4; i++){
            players.add(i,new Player(names.get(i),couleurJoueur.get(i), drawDestinationCards, drawWagonCards));
        }
        alreadyCalled = 0;

        currentPlayer = players.get(0);
        indexCurrentPlayer = 0;
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
        for (WagonCard drawVisibleTrainCard : drawVisibleWagonCards) {
            str.append(drawVisibleTrainCard.toString());
        }


        //on affiche les joueurs
        str.append("PLAYERS :\n");
        for (Player player : players) {
            str.append(player.toString());
        }
        return str.toString();
    }

    /**
     * fonction permet à un joueur de jouer un tour
     *
     * @param p le joueur qui joue
     */
    public void playTurn(Player p){
        int choix;

        //on récupère le choix du joueur
        System.out.println("Choisissez une option:\n  0 - Quitter\n  1 - Prendre des cartes Wagon\n  2 - Prendre possession des routes");
        //on affiche l'option s'il reste des cartes destination
        if (drawDestinationCards.size() > 0) {
            System.out.println("  3 - Prendre des cartes Destination");
        }

        choix = saisieValidIntBornes(0,3);

        switch (choix) {
            case 0:
                System.out.println("Are you sure ? (O/N)");
                boolean quit = saisieOuiNon();
                if (quit) {
                    determineScore();
                    System.out.println(scoreToString());
                    exit(0);
                }
                playTurn(p);
                break;
            //si on pioche des cartes wagons
            case 1:
                int c;
                System.out.println("/////////Vous souhaitez piocher des cartes Wagon./////////");
                //on itère deux fois car on peut tirer deux cartes sauf si on prend une locomotive délibérément
                for(int j = 0; j < 2; j++) {
                    //on présente les cartes dispos
                    System.out.println("Voici les cartes retournées actuellement : ");
                    for (int i = 0; i < drawVisibleWagonCards.size(); i++) {
                        System.out.print("  " + (i + 1) + " -" + drawVisibleWagonCards.get(i).toString());
                    }

                    //on fait choisir une carte au joueur
                    System.out.println("  6 -    Piocher une carte au hasard\nSaisissez un numéro : ");
                    c = saisieValidIntBornes(1, 6);

                    //s'il tente de prendre une carte loco et qu'il a déjà pris une autre carte -> erreur
                    while(c != 6 && drawVisibleWagonCards.get(c - 1).getColor() == Color.RAINBOW && j > 0 ){
                        System.out.println("Si vous prenez une carte locomotive visible lors d'un tour, vous ne pouvez pas piocher d'autre carte ! Veuillez saisir un autre nombre : ");
                        c = saisieValidIntBornes(1, 6);
                    }

                    //si il choisit une carte au hasard
                    if (c == 6) {
                        WagonCard tmp = p.drawTrainCard(drawWagonCards);
                        System.out.println("Vous avez pioché la carte " + tmp);
                    }

                    //si il prend une carte de la pioche visible
                    else {
                        //on retire la carte de la pioche de cartes visibles
                        WagonCard tmp = drawVisibleWagonCards.get(c - 1);
                        drawVisibleWagonCards.remove(tmp);

                        //on remet une nouvelle carte tiree au hasard dans la pioche
                        int nCard = (int) (Math.random() * (drawWagonCards.size()));
                        drawVisibleWagonCards.add(drawWagonCards.get(nCard));
                        drawWagonCards.remove(nCard);

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
                int nbVille = 0;
                //on affiche les routes possibles
                if(alreadyCalled == 0) {
                    System.out.println("Available routes :");

                    for (Map.Entry city : d.getDestinations().entrySet()) {
                        System.out.println("    "+nbVille+" - Routes from "+city.getKey()+" :\n"+((City)city.getValue()).routesFromToString());
                        nbVille++;
                    }
                    //affiche l'option annuler
                    System.out.println("    "+(nbVille + 1) + " - Cancel\n");
                }


                //on fait choisir une route au joueur
                System.out.println("Choose a route (\"from - to\") : ");
                Scanner sc = new Scanner(System.in);
                String str = sc.nextLine();
                Route routeChoix = d.getRouteFromString(str);

                //si le joueur veut annuler
                if(routeChoix == null){
                    alreadyCalled++;
                    playTurn(p);
                }
                //si le joueur ne veut pas annuler
                else{
                    //on vérifie que la route est libre et qu'il a assez de wagons pour prendre cette route
                    while (routeChoix.isAlreadyTakenRoute() || p.getWagons() < routeChoix.getRequire()) {
                        if (routeChoix.isAlreadyTakenRoute()) {
                            System.out.println("Erreur : la route est déjà prise ! ");
                        }
                        //on vérifie qu'il a le bon nombre de trains dispo
                        if (p.getWagons() < routeChoix.getRequire()) {
                            System.out.println("Erreur : vous n'avez pas assez de wagons pour prendre cette route ! ");
                        }

                        //il resaisit une route
                        str = sc.nextLine();
                        routeChoix = d.getRouteFromString(str);
                    }

                    //si la route est un tunnel
                    if (routeChoix.isTunel()) {
                        //si la route n'a pas de couleur
                        if (routeChoix.getColor() == Color.GRAY) {
                            System.out.println("Saisissez une couleur de carte Wagon pour tenter de prendre le tunnel :");
                            Color couleur = Color.saisieColor();
                            if (routeChoix.getTunnel(p, couleur, this) == -1) {
                                alreadyCalled++;
                                playTurn(p);
                            }
                            else{
                                p.addRoute(routeChoix);
                                Route inv = new Route(routeChoix.getDest2(),routeChoix.getDest1(), routeChoix.getRequire(), routeChoix.getColor(),routeChoix.isTunel(),routeChoix.getLocomotive());
                                p.addRoute(inv);
                            }
                        }
                        //si la route a une couleur
                        else {
                            if (routeChoix.getTunnel(p, routeChoix.getColor(), this) == -1) {
                                alreadyCalled++;
                                playTurn(p);
                            }
                            else{
                                p.addRoute(routeChoix);
                                Route inv = new Route(routeChoix.getDest2(),routeChoix.getDest1(), routeChoix.getRequire(), routeChoix.getColor(),routeChoix.isTunel(),routeChoix.getLocomotive());
                                p.addRoute(inv);
                            }
                        }
                    }
                    //si c'est pas un tunnel
                    else {
                        //si c'est un ferrie
                        if (routeChoix.getColor() == Color.GRAY) {
                            System.out.println("Saisissez une couleur de carte Wagon pour prendre le ferrie :");
                            Color couleur = Color.saisieColor();
                            if (routeChoix.getFerrie(p, couleur, this) == -1) {
                                alreadyCalled++;
                                playTurn(p);
                            }
                            else{
                                p.addRoute(routeChoix);
                                Route inv = new Route(routeChoix.getDest2(),routeChoix.getDest1(), routeChoix.getRequire(), routeChoix.getColor(),routeChoix.isTunel(),routeChoix.getLocomotive());
                                p.addRoute(inv);
                            }
                        } else {
                            if (routeChoix.getRoute(p, routeChoix.getColor(), this) == -1) {
                                alreadyCalled++;
                                playTurn(p);
                            }
                            else{
                                p.addRoute(routeChoix);
                                Route inv = new Route(routeChoix.getDest2(),routeChoix.getDest1(), routeChoix.getRequire(), routeChoix.getColor(),routeChoix.isTunel(),routeChoix.getLocomotive());
                                p.addRoute(inv);
                            }
                        }
                    }
                }
                break;

            //si on pioche des cartes destination
            case 3:
                if(drawDestinationCards.size() > 0) {
                    System.out.println("Vous avez pioché les trois cartes Destination suivantes: ");
                    ArrayList<DestinationCard> dctmp = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        if (drawDestinationCards.size() > 0) {
                            DestinationCard tmpd = drawDestinationCard();
                            System.out.print("  " + (i + 1) + " - " + tmpd);
                            dctmp.add(tmpd);
                        }
                    }
                    System.out.println("Souhaitez- vous en retirer une ou plusieurs ?(O/N)");
                    boolean removeDest = saisieOuiNon();
                    if (removeDest) {
                        int k = 0;
                        boolean removeOther = true;
                        while (k < 2 && removeOther) {
                            System.out.println("Saisissez le numéro d'une carte à enlever :");
                            if (k > 0) {
                                for (int i = 0; i < dctmp.size(); i++) {
                                    System.out.print("  " + (i + 1) + " - " + dctmp.get(i));
                                }
                            }
                            int nb = saisieValidIntBornes(1, dctmp.size());
                            //on remet dans la pioche la carte dont le joueur se débarasse
                            drawDestinationCards.add(dctmp.get(nb - 1));
                            dctmp.remove(nb - 1);
                            k++;

                            //demande si on en retire une autre
                            if (k < 2) {
                                System.out.println("Souhaitez-vous en retirer une autre ?(O/N)");
                                removeOther = saisieOuiNon();
                            }

                        }
                    }
                    //on met les cartes dans le jeu du joueur
                    System.out.println("Vous avez conservé les cartes suivantes :");
                    for (DestinationCard destinationCard : dctmp) {
                        System.out.print(destinationCard);
                        p.addDestinationCard(destinationCard);
                    }
                }else{
                    System.out.println("Désolé, il n'y a plus de cartes Destination disponibles.");
                    alreadyCalled++;
                    playTurn(p);
                }
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
        //on fait tourner le jeu tant qu'il reste plus de 3 wagons à chaque joueurs
        while(!gameIsOver()){
            for(Player p : players) {
                alreadyCalled = 0;
                currentPlayer = p;
                System.out.println("\n\n////////////////////C'est au tour de " + p.getName()+" de jouer////////////////////");
                System.out.println(p);
                playTurn(p);
            }
        }

        //quand on détecte la fin d'une partie on calcule les points des joueurs suivant leur objectif
        determineScore();
        scoreToString();
    }

    public static int saisieValidIntBornes(int borneInf,int borneSup){
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

    public boolean saisieOuiNon(){
        String choix = "";
        Scanner entree = new Scanner(System.in);

        try{
            choix = entree.next();
        }catch (InputMismatchException e){
            entree.next();
        }

        //verif de la saisie
        while(!choix.equals("O") && !choix.equals("o") && !choix.equals("N") && !choix.equals("n")) {
            System.out.println("Erreur : veuilez entrer soit oui (O), soit non (N).");
            try {
                choix = entree.next();
            } catch (InputMismatchException e) {
                entree.next();
            }
        }
        return choix.equals("o") || choix.equals("O");
    }

    /**
     * fonction permettant de piocher une carte wagon et de la retirer de la pioche
     *
     * @return TrainCard la carte pioché
     */
    public WagonCard drawTrainCard(){
        int nCard = (int)(Math.random() * (drawWagonCards.size()));

        WagonCard tmp = drawWagonCards.get(nCard);

        // et on la retire de la pioche
        drawWagonCards.remove(nCard);

        return tmp;
    }

    /**
     * fonction permettant de piocher une carte destination et de la retirer de la pioche
     *
     * @return DestinationCard la carte pioché
     */
    public DestinationCard drawDestinationCard(){
        int nCard = (int)(Math.random() * (drawDestinationCards.size()));

        DestinationCard tmp = drawDestinationCards.get(nCard);

        // et on la retire de la pioche
        drawDestinationCards.remove(nCard);

        return tmp;
    }

    /**
     * fonction qui retourne une chaine affichant l'identité de chaque joueurs
     *
     * @return String
     */
    public String scoreToString(){
        String res = "";
        for(Player p : players){
            res += p.toString();
        }
        return res;
    }

    public Player getWinner(){
        Player max = players.get(0);
        for(int i = 1; i < players.size(); i++){
            if(players.get(i).getPoints() > max.getPoints()){
                max = players.get(i);
            }
        }
        return max;
    }


    public void determineScore(){
        for (Player p : players){
            names = new ArrayList<>();
            for(DestinationCard d : p.getdCards()){
                City from = d.getFrom();
                names.add(from.getName());
                City to = d.getTo();
                boolean result = false;
                for(Route r : p.getRoutesEmpruntes()){
                    if(r.getDest1()==from){
                        result = findDest(r.getDest2(),to,p);
                        if(result) {
                            p.setPoints(p.getPoints() + d.getPoints());
                        }
                    }
                }
                if(!result) {
                    p.setPoints(p.getPoints() - d.getPoints());
                }
            }
        }
    }

    public boolean findDest(City dest, City destFinal, Player p){
        if(!names.contains(dest.getName())){
            names.add(dest.getName());
        }
        boolean result = false;
        for (Route r : p.getRoutesEmpruntes()){
            if(r.getDest1() == dest) {
                if(r.getDest2() == destFinal) {
                    result = true;
                }else {
                    if(!names.contains(r.getDest2().toString())) {
                        result = findDest(r.getDest2(), destFinal, p);
                    }
                }
            }
        }
        return result;
    }

    public Player nextPlayer(){
        if(indexCurrentPlayer == players.size()-1){
            currentPlayer = players.get(0);
            indexCurrentPlayer = 0;
        }else{
            indexCurrentPlayer += 1;
            currentPlayer = players.get(indexCurrentPlayer);
        }
        return currentPlayer;
    }
}
