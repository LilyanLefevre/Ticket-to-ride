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

        // tire 4 cartes destination au hasard
        for(int i = 0 ; i < 4; i++){
            Random rand = new Random();
            int nCard;
            nCard = 0 + rand.nextInt((dc.size()-1)-0);

            // on ajoute la carte tirée dans le jeu du joueur
            dCards.add(dc.get(i));
            // et on la retire de la pioche
            dc.remove(i);
        }
        // tire 4 cartes colorées au hasard
        for(int i = 0 ; i < 4; i++){
            Random rand = new Random();
            int nCard;
            nCard = 0 + rand.nextInt((tc.size()-1)-0);

            // on ajoute la carte tirée dans le jeu du joueur
            tCards.add(tc.get(i));
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
        return "Player "+name+".\nDestination cards :"+dc+"\nTrain cards :"+tc+"\n Points = "+points+", wagons = "+wagons+" and train stations = "+trainStation+"\n";
    }
}
