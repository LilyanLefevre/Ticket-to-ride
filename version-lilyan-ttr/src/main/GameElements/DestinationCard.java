package GameElements;

import java.util.ArrayList;
import java.util.HashMap;

public class DestinationCard {
    private final Destination from;
    private final Destination to;
    private final int points;

    public DestinationCard(Destination from, Destination to, int points) {
        this.from = from;
        this.to = to;
        this.points = points;
    }

    public static ArrayList genererCarteDestination(Destinations d){
        ArrayList<DestinationCard> ret = new ArrayList<>();
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        ret.add(new DestinationCard(d.getDestinations().get("Paris"), d.getDestinations().get("Berlin"), 2));
        return ret;
    }

    public Destination getFrom() {
        return from;
    }

    public Destination getTo() {
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
