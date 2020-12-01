package GameElements;

import java.util.HashMap;
import Enum.Color;
public class Destinations {
    private HashMap<String,Destination> destinations;

    public Destinations(){
        destinations = new HashMap<>();
        genererDestination();
        genererRoutes();
    }

    public void addDestination(Destination d) {
        this.destinations.put(d.getName(),d);
    }
    public void addRoute(Route r, Destination from, Destination to) {
        this.destinations.get(from.getName()).addRoute(r, to);
    }

    public HashMap<String, Destination> getDestinations() {
        return destinations;
    }

    private void genererDestination(){
        addDestination(new Destination("Paris"));
        addDestination(new Destination("Berlin"));
        System.out.println("destinations générées.");
    }
    private void genererRoutes(){
        destinations.get("Paris").addRoute(new Route(3, Color.BLUE, false, 0), destinations.get("Berlin"));
        System.out.println("routes générées.");
    }
}
