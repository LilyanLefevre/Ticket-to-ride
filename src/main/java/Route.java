public class Route {
    private final Destination from;
    private final Destination to;
    private final int require;
    private final Color color;
    private int state;

    public Route(Destination from, Destination to, int require, Color color) {
        this.from = from;
        this.to = to;
        this.require = require;
        this.color = color;
        this.state = 0;
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

    public void setState(int state) {
        this.state = state;
    }
}
