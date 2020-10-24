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
    private int trainStation;

    public Player(String name, Color color, ArrayList<DestinationCard> dc, ArrayList<TrainCard> tc) {
        this.name = name;
        this.color = color;
        points = 0;
        wagons = 45;
        trainStation = 3;
        tCards = new ArrayList<>();
        dCards = new ArrayList<>();

        // tire 4 cartes destination au hasard
        for(int i = 0 ; i < 4; i++){
            int nCard = (int)(Math.random() * (dc.size()));

            // on ajoute la carte tirée dans le jeu du joueur
            dCards.add(dc.get(nCard));

            // et on la retire de la pioche
            dc.remove(i);
        }
        // tire 4 cartes colorées au hasard
        for(int i = 0 ; i < 4; i++){
            int nCard = (int)(Math.random() * (tc.size()));

            // on ajoute la carte tirée dans le jeu du joueur
            tCards.add(tc.get(nCard));

            // et on la retire de la pioche
            tc.remove(i);
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
        String dc = "";
        for(int i = 0; i < dCards.size(); i++){
            dc += dCards.get(i).toString();
        }
        String tc = "";
        for(int i = 0; i < tCards.size(); i++){
            tc += tCards.get(i).toString();
        }
        return "\n"+name+":\nDestination cards :\n"+dc+"Train cards :\n"+tc+"Color : "+color+", points = "+points+", wagons = "+wagons+" and train stations = "+trainStation+"\n";
    }
}
