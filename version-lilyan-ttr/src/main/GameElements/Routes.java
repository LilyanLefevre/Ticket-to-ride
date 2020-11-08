package GameElements;

import java.util.ArrayList;

public class Routes {
    private ArrayList<Route> routes;

    public Routes(){
        routes = new ArrayList<>();
    }

    public Route getRoute(int c){
        return routes.get(c);
    }

    public ArrayList<Route> getRoutes(){
        return routes;
    }

    public int howManyRoutes(){
        return routes.size();
    }

    public void addRoute(Route r){
        this.routes.add(r);
    }
}

