package GameElements;
import Enum.*;


public class TrainStation {
    private Destination city;
    private Player p;

    public TrainStation(Destination city, Player p) {
        this.city = city;
        this.p = p;
    }

    public Destination getCity() {
        return city;
    }

    public Player getPlayer() {
        return p;
    }


}
