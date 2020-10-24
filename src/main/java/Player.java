import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<TrainCard> tCards;
    private ArrayList<DestinationCard> dCards;
    private int points;
    private int wagons;
    private Color color;
    private int trainStation;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        points = 0;
        wagons = 45;
        trainStation = 3;
    }
}
