package GameElements;
import Enum.*;

public class Route {
    private final Destination from;
    private final Destination to;
    private final int require;
    private final Color color;
    private final boolean isTunel;
    private final int locomotive;
    private int state;
    private Player hasPlayerOn;

    public Route(Destination from, Destination to, int require, Color color, boolean isTunel, int locomotive) {
        this.from = from;
        this.to = to;
        this.require = require;
        this.color = color;
        this.state = 0;
        this.isTunel = isTunel;
        this.locomotive = locomotive;
        hasPlayerOn = null;
    }

    public Destination getFrom() {
        return from;
    }

    public Destination getTo() {
        return to;
    }

    public int getRequire() {
        return require;
    }

    public Color getColor() {
        return color;
    }

    public int getState() {
        return state;
    }

    /**
     * tells if there is somebody on this route
     *
     * @return true if there is somebody on, false otherwise
     */
    public boolean isAlreadyTakenRoute(){
        if (hasPlayerOn != null){
            return true;
        }
        return false;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * Answers a string containing a concise, human-readable
     * description of the receiver.
     *
     * @return String a printable representation for the receiver.
     */
    @Override
    public String toString() {
        return "    From "+from+" to "+to+ " with "+locomotive+" locomotive and isTunel = "+isTunel+". It requires "+require+" "+color.toString()+" wagons.\n";
    }
}
