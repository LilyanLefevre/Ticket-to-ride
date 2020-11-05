package GameElements;
import Enum.*;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private String name;
    private ArrayList<TrainCard> tCards;
    private ArrayList<DestinationCard> dCards;
    private int points;
    private int wagons;
    private Color color;
    private int nbTrainStation;

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getWagons() {
        return wagons;
    }

    public Color getColor() {
        return color;
    }

    public int getNbTrainStation() {
        return nbTrainStation;
    }

    public Player(String name, Color color, ArrayList<DestinationCard> dc, ArrayList<TrainCard> tc) {
        this.name = name;
        this.color = color;
        points = 0;
        wagons = 45;
        nbTrainStation = 3;
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
        StringBuilder dc = new StringBuilder();
        for (DestinationCard dCard : dCards) {
            dc.append(dCard.toString());
        }
        StringBuilder tc = new StringBuilder();
        for (TrainCard tCard : tCards) {
            tc.append(tCard.toString());
        }
        return "\n"+name+":\nDestination cards :\n"+dc+"Train cards :\n"+tc+"Color : "+color+", points = "+points+", wagons = "+wagons+" and train stations = "+nbTrainStation+"\n";
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

    public int countOccurencesOf(Color c){
        int i = 0;
        for(TrainCard t : tCards){
            if(t.getColor() == c){
                i++;
            }
        }
        return i;
    }

    public void removeColoredCards(Color c, int nb){
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
        tCards.removeAll(toRemove);
    }
}
