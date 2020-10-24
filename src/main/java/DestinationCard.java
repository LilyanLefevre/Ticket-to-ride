public class DestinationCard {
    private final Destination from;
    private final Destination to;
    private final int points;

    public DestinationCard(Destination from, Destination to, int points) {
        this.from = from;
        this.to = to;
        this.points = points;
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

}
