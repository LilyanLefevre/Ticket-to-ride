package Model.GameElements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DestinationCard {
    private final City from;
    private final City to;
    private final int points;

    public DestinationCard(City from, City to, int points) {
        this.from = from;
        this.to = to;
        this.points = points;
    }
    // A MODIFIER
    public static ArrayList genererCarteDestination(Destinations d){
        ArrayList<DestinationCard> ret = new ArrayList<>();
        for (Map.Entry from : d.getDestinations().entrySet()){
            List<String> keysAsArray = new ArrayList<String>(d.getDestinations().keySet());
            Random r = new Random();
            City to = d.getDestinations().get(keysAsArray.get(r.nextInt(keysAsArray.size())));
            DestinationCard d1 = new DestinationCard ((City)from.getValue(),to,2);
            while (ret.contains(d1) || from.getValue() == to){
                to = d.getDestinations().get(keysAsArray.get(r.nextInt(keysAsArray.size())));
                d1 = new DestinationCard ((City)from.getValue(),to,2);
            }
            int x1 = ((City) from.getValue()).getCoordonnees().getX();
            int y1 = ((City) from.getValue()).getCoordonnees().getY();
            int x2 = to.getCoordonnees().getX();
            int y2 = to.getCoordonnees().getY();
            double distance1 = Math.sqrt(Math.pow((y2 - y1),2) + Math.pow((x2 - x1),2));
            int points = 0;
            if(distance1<6)
                points = 5+r.nextInt(9-5);
            if(distance1>=6 && distance1<10)
                points = 9+r.nextInt(13-9);
            if(distance1>=10 && distance1<15)
                points = 13+r.nextInt(18-13);
            if(distance1>=15)
                points = 17+r.nextInt(22-17);
            ret.add(new DestinationCard((City)from.getValue(),to,points));
        }

        return ret;
    }

    public City getFrom() {
        return from;
    }

    public City getTo() {
        return to;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Answers a string containing a concise, human-readable
     * description of the receiver.
     *
     * @return String a printable representation for the receiver.
     */
    @Override
    public String toString() {
        return "    From "+from+" to "+to+". It worth "+points+" points.\n";
    }
}
