import Controller.JButtonController;
import Controller.RouteController;
import Model.Game;
import View.PlayView.GameView;

import java.util.ArrayList;
import java.util.Random;


public class TicketToRideBOT {
    private static long SEED = System.currentTimeMillis();

    public static void main (String [] args){
        System.out.println("Seed = "+SEED);

        /**
         * deuxième fenetre qui lance le jeu
         */
        //on créé le jeu
        Random r = new Random(SEED);
        Game g = new Game(new ArrayList<>(), r);

        //on créé la vue
        GameView gv = new GameView(g);
        while(!gv.fini){
            System.out.print("");
        }


        //on ajoute les controlleurs
        RouteController mc = new RouteController(gv);
        gv.setRouteListener(mc);
        JButtonController gc = new JButtonController(g,gv,false);
        gv.setActionListener(gc);
    }
}

