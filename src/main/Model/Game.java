package Model;

import java.util.*;

import Model.GameElements.*;

/**
 * classe qui représente le moteur du jeu
 */
public class Game{
    private Random random;
    // l'ensemble des joueurs
    private final ArrayList<Player> players;

    //joueur actuel
    private Player currentPlayer;
    private int indexCurrentPlayer;

    // pioche de cartes de couleurs
    private ArrayList<WagonCard> wagonCardsDraw;

    // 5 cartes de couleurs retournées
    private ArrayList<WagonCard> visibleWagonCardsDraw;

    // pioche de cartes destination
    private ArrayList<DestinationCard> destinationCardsDraw;

    //variable qui représente l'ensemble des destinations du jeu
    private Destinations destinations;

    //variable qui sert à enregistrer les villes déjà visitées pour la fonction finDest
    private ArrayList<String> names;

    //variable qui enregistre si la partie contient uniquement des ias ou pas
    private boolean onlyIA;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<WagonCard> getWagonCardsDraw() {
        return wagonCardsDraw;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<WagonCard> getDrawVisibleTrainCards() {
        return visibleWagonCardsDraw;
    }

    public ArrayList<DestinationCard> getDestinationCardsDraw() {
        return destinationCardsDraw;
    }

    public Destinations getDestinations() {
        return destinations;
    }

    public boolean isOnlyIA() {
        return onlyIA;
    }

    public Game(ArrayList<String> names, Random r) {
        this.random = r;

        // initialisation des cartes wagons
        wagonCardsDraw = new ArrayList<>(Color.values().length*14);
        for(int i = 0; i < Color.values().length; i++){
            if(Color.values()[i] != Color.GRAY) {
                for (int j = 0; j < 14; j++) {
                    wagonCardsDraw.add(new WagonCard(Color.values()[i]));
                }
            }
        }

        //initialisation des cartes wagons visibles
        visibleWagonCardsDraw = new ArrayList<>(5);
        for(int i = 0; i < 5; i++){
            int nCard = (int)(Math.random() * (wagonCardsDraw.size()));

            // on ajoute la carte tirée dans le jeu du joueur
            visibleWagonCardsDraw.add(wagonCardsDraw.get(nCard));

            // et on la retire de la pioche
            wagonCardsDraw.remove(nCard);
        }

        //initialisation des destinations et des routes
        destinations = new Destinations(random);

        //initialisation des cartes destination
        destinationCardsDraw = DestinationCard.generateDestinationCard(destinations);

        // initialisation des joueurs
        players = new ArrayList<>();
        if(names.size() == 0){
            onlyIA = true;
        }else{
            onlyIA = false;
        }
        HashMap<Integer, java.awt.Color> couleurJoueur = new HashMap<>();
        couleurJoueur.put(0, new java.awt.Color(248,160,141));
        couleurJoueur.put(1, new java.awt.Color(145,205,253));
        couleurJoueur.put(2, new java.awt.Color(165,102,12));
        couleurJoueur.put(3, new java.awt.Color(160,189,138));
        for(int i = 0; i < names.size(); i++){
            players.add(i,new Player(names.get(i),couleurJoueur.get(i), destinationCardsDraw, wagonCardsDraw, 0));
        }

        for(int i = names.size(); i < 4; i++){
            players.add(i,new Player("bot "+i, couleurJoueur.get(i), destinationCardsDraw, wagonCardsDraw, i+2));
        }

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
        for (WagonCard drawVisibleTrainCard : visibleWagonCardsDraw) {
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


    /**
     * fonction permettant de piocher une carte wagon et de la retirer de la pioche
     *
     * @return TrainCard la carte pioché
     */
    public WagonCard drawTrainCard(){
        int nCard = (int)(Math.random() * (wagonCardsDraw.size()));

        WagonCard tmp = wagonCardsDraw.get(nCard);

        // et on la retire de la pioche
        wagonCardsDraw.remove(nCard);

        return tmp;
    }

    /**
     * fonction permettant de piocher une carte destination et de la retirer de la pioche
     *
     * @return DestinationCard la carte pioché
     */
    public DestinationCard drawDestinationCard(){
        int nCard = (int)(Math.random() * (destinationCardsDraw.size()));

        DestinationCard tmp = destinationCardsDraw.get(nCard);

        // et on la retire de la pioche
        destinationCardsDraw.remove(nCard);

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


    /**
     * fonction qui va calculer le score de chaque joueur en vérifiant s'il a fait ses objectifs ou non
     */
    public void determineScore(){
        //on parcoure les joueurs
        for (Player p : players){
            //tableau qui va servir à enregistrer les noms des villes déjà visitées
            names = new ArrayList<>();

            //on parcoure les objectifs du joueur courant
            for(DestinationCard d : p.getDestinationCards()){
                //on enregistre le couple de villes à relier
                City from = d.getCity1();
                names.add(from.getName());
                City to = d.getCity2();

                boolean result = false;
                //pour chaque route empruntée par le joueur
                for(Route r : p.getOwnedRoute()){
                    //on regarde si elle part de la premiere ville de l'objectif
                    if(r.getCity1()==from){
                        //et on essaie de construire le chemin vers la deuxieme ville
                        result = findDest(r.getCity2(),to,p);

                        //si il existe un chemin on ajoute les points
                        if(result) {
                            p.setPoints(p.getPoints() + d.getPoints());
                        }
                    }
                }
                //si on a pas atteint la ville alors on retire des points au joueur
                if(!result) {
                    p.setPoints(p.getPoints() - d.getPoints());
                }
            }
        }
    }

    /**
     * fonction qui va construire un chemin entre deux villes données parmis les routes prises par le joueur
     *
     * @param dest City la ville de départ
     * @param destFinal City la ville d'arrivée
     * @param p Player le joueur concerné
     *
     * @return boolean true si le joueur a relié les deux villes, false sinon
     */
    public boolean findDest(City dest, City destFinal, Player p){
        if(!names.contains(dest.getName())){
            names.add(dest.getName());
        }
        boolean result = false;
        for (Route r : p.getOwnedRoute()){
            if(r.getCity1() == dest) {
                if(r.getCity2() == destFinal) {
                    result = true;
                }else {
                    if(!names.contains(r.getCity2().toString())) {
                        result = findDest(r.getCity2(), destFinal, p);
                    }
                }
            }
        }
        return result;
    }

    /**
     * fonction qui retourne le prochain joueur à jouer
     *
     * @return Player le prochain joueur à jouer
     */
    public Player nextPlayer(){
        //si on est actuellement sur le dernier joueur
        if(indexCurrentPlayer == players.size()-1){
            //on retourne le premier
            currentPlayer = players.get(0);
            indexCurrentPlayer = 0;
        }
        //sinon on retourne juste le suivant
        else{
            indexCurrentPlayer += 1;
            currentPlayer = players.get(indexCurrentPlayer);
        }
        return currentPlayer;
    }
}
