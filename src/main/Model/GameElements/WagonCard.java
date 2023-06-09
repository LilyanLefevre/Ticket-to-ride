package Model.GameElements;

/**
 * classe qui représente une carte Wagon (cartes de couleurs qui permettent de prendre les routes)
 */
public class WagonCard {
    private Color color;

    public WagonCard(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Answers a string containing a concise, human-readable
     * description of the receiver.
     *
     * @return String a printable representation for the receiver.
     */
    @Override
    public String toString() {
        return "    "+color.toString()+"\n";
    }
}
