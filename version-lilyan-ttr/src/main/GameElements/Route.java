package GameElements;
import Enum.*;

public class Route {
    //représentent les deux destination réliées par cette route
    private final Destination dest1;
    private final Destination dest2;

    //représente le nombre de wagons que nécessite cette route pour poser ses wagons dessus
    private final int require;

    //représente la couleur des wagons nécessaire pour poser ses wagons dessus
    private final Color color;

    //booleen pour dire si cette route est un tunel
    private final boolean isTunel;

    //booleen pour dire si cette route necessite des locomotives dans le cas des routes ferries
    private final int locomotive;

    //enregistre le joueur qui a éventuellement posé ses wagons dessus
    private Player hasPlayerOn;

    public Route(Destination from, Destination to, int require, Color color, boolean isTunel, int locomotive) {
        this.dest1 = from;
        this.dest2 = to;
        this.require = require;
        this.color = color;
        this.isTunel = isTunel;
        this.locomotive = locomotive;
        hasPlayerOn = null;
    }

    public Destination getFrom() {
        return dest1;
    }

    public Destination getTo() {
        return dest2;
    }

    public int getRequire() {
        return require;
    }

    public Color getColor() {
        return color;
    }

    /**
     * tells if there is somebody on this route
     *
     * @return true if there is somebody on, false otherwise
     */
    public boolean isAlreadyTakenRoute(){
        return hasPlayerOn != null;
    }

    public void setPlayer(Player p) {
        this.hasPlayerOn = p;
    }

    /**
     * Answers a string containing a concise, human-readable
     * description of the receiver.
     *
     * @return String a printable representation for the receiver.
     */
    @Override
    public String toString() {
        return "    From "+dest1+" to "+dest2+ " with "+locomotive+" locomotive and isTunel = "+isTunel+". It requires "+require+" "+color.toString()+" wagons.\n";
    }
}
