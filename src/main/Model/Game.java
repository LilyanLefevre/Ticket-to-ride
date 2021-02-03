package Model;

import java.util.*;

import Model.GameElements.*;
import Model.Enum.*;

/**
 * classe qui représente le moteur du jeu
 */
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


    /**
     * fonction qui va calculer le score de chaque joueur en vérifiant s'il a fait ses objectifs ou non
     */
    public void determineScore(){
        //on parcoure les joueurs
        for (Player p : players){
            //tableau qui va servir à enregistrer les noms des villes déjà visitées
            names = new ArrayList<>();

            //on parcoure les objectifs du joueur courant
            for(DestinationCard d : p.getdCards()){
                //on enregistre le couple de villes à relier
                City from = d.getDest1();
                names.add(from.getName());
                City to = d.getDest2();

                boolean result = false;
                //pour chaque route empruntée par le joueur
                for(Route r : p.getRoutesEmpruntes()){
                    //on regarde si elle part de la premiere ville de l'objectif
                    if(r.getDest1()==from){
                        //et on essaie de construire le chemin vers la deuxieme ville
                        result = findDest(r.getDest2(),to,p);

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
