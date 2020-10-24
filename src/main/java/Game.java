import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private ArrayList<TrainCard> drawTrainCards;
    private ArrayList<DestinationCard> drawDestinationCards;
    private ArrayList<Route> routes;

    public Game(ArrayList<String> names) {

        // initialisation des joueurs
        for(int i = 0; i < 4; i++){
            players.add(i,new Player(names.get(i),Color.values()[i]));
        }


    }
}
