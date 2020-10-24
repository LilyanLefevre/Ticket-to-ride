package GameElements;
import Enum.*;

public class TrainCard {
    private Color color;

    public TrainCard(Color color) {
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
