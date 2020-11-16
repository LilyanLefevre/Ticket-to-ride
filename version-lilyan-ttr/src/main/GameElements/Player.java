package GameElements;
import Enum.*;
import model.*;

import java.util.ArrayList;

public class Player {
    private final String name;
    private ArrayList<TrainCard> tCards;
    private ArrayList<DestinationCard> dCards;
    private int points;
    private int wagons;
    private final Color color;

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getWagons() {
        return wagons;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setWagons(int wagons) {
        this.wagons = wagons;
    }


    public Color getColor() {
        return color;
    }


    public Player(String name, Color color, ArrayList<DestinationCard> dc, ArrayList<TrainCard> tc) {
        this.name = name;
        this.color = color;
        points = 0;
        wagons = 45;
        tCards = new ArrayList<>();
        dCards = new ArrayList<>();

        // tire 4 cartes destination au hasard
        for(int i = 0 ; i < 4; i++){
            drawTrainCard(tc);
        }

        // tire 4 cartes colorées au hasard
        for(int i = 0 ; i < 4; i++){
            drawDestinationCard(dc);
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
        String dc = new String();
        dc = "";
        for (int i =0; i < dCards.size(); i++) {
            dc += /*" "+(i+1) + " - "+ */dCards.get(i).toString();
        }
        String tc = new String();
        tc = "";
        for (int i =0; i < tCards.size(); i++) {
            tc += /*" "+(i+1) + " - "+ */tCards.get(i).toString();
        }
        return "\n"+name+":\nDestination cards :\n"+dc+"Train cards :\n"+tc+"Color : "+color+", points = "+points+", wagons = "+wagons+"\n";
    }

    public TrainCard drawTrainCard(ArrayList<TrainCard> tc){
        int nCard = (int)(Math.random() * (tc.size()));

        TrainCard tmp = tc.get(nCard);
        // on ajoute la carte tirée dans le jeu du joueur
        tCards.add(tc.get(nCard));

        // et on la retire de la pioche
        tc.remove(nCard);

        return tmp;
    }

    public DestinationCard drawDestinationCard(ArrayList<DestinationCard> dc){
        int nCard = (int)(Math.random() * (dc.size()));

        DestinationCard tmp = dc.get(nCard);

        // on ajoute la carte tirée dans le jeu du joueur
        dCards.add(dc.get(nCard));

        // et on la retire de la pioche
        dc.remove(nCard);
        return tmp;
    }

    public void addTrainCard(TrainCard tc){
        tCards.add(tc);
    }
    public void addDestinationCard(DestinationCard dc){
        dCards.add(dc);
    }

    /**
     * fonction qui permet de compter le nombre de carte d'une certaine couleur
     * en incluant les locos.
     * @param c la couleur voulue
     * @return un entier représentant le nombre de carte de la bonne couleur
     */
    public int countOccurencesOf(Color c){
        int i = 0;

        //compte le nombre de carte de la couleur donnée et locomotives
        for(TrainCard t : tCards){
            if(t.getColor() == c || t.getColor() == Color.RAINBOW){
                i++;
            }
        }
        return i;
    }
    /**
     * fonction qui permet de compter le nombre de carte d'une certaine couleur
     * en incluant les locos.
     * @param c la couleur voulue
     * @return un entier représentant le nombre de carte de la bonne couleur
     */
    public int countWithoutRainbowOccurencesOf(Color c ){
        int i = 0;

        //compte le nombre de carte de la couleur donnée et locomotives
        for(TrainCard t : tCards){
            if(t.getColor() == c){
                i++;
            }
        }
        return i;
    }

    /**
     * fonction qui supprime nb carte de couleur c dans les cartes Wagon du joueur
     * et les replace dans la pioche
     *
     * @param c la couleur des cartes a retirer
     * @param nb le nombre de carte a retirer
     * @return le nombre de carte retiré
     */
    public int removeTrainCards(Color c, int nb, Game g){
        int i = 0;
        ArrayList<TrainCard> toRemove = new ArrayList<>();
        for(TrainCard t : tCards){
            if(i < nb) {
                if (t.getColor() == c) {
                    toRemove.add(t);
                    i++;
                }
            }
        }

        int ret = toRemove.size();
        tCards.removeAll(toRemove);

        //on les replace dans la pioche
        for(TrainCard t : toRemove){
            int nCard = (int)(Math.random() * (g.getDrawTrainCards().size()));
            g.getDrawTrainCards().add(nCard,t);
        }

        return ret;
    }
}
