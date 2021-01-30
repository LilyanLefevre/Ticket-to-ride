import Controller.JButtonController;
import Controller.RouteController;
import Model.Game;
import Model.GameElements.Player;
import View.GameView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TicketToRide {
    public static void main (String [] args) throws IOException {
        Game g = new Game(new ArrayList<>(Arrays.asList("lilyan", "eros", "loic", "View")));
        GameView gv = new GameView(g);
        int i=0;
        while(!gv.fini){
            System.out.print("");
        }
        RouteController mc = new RouteController(g,gv);
        gv.setRouteListener(mc);
        JButtonController gc = new JButtonController(g,gv);
        gv.setActionListener(gc);

        //on fait tourner le jeu tant qu'il reste plus de 3 wagons à chaque joueurs
        while(!g.gameIsOver()){
            for(Player p : g.getPlayers()) {
                g.setAlreadyCalled(0);
                g.setCurrentPlayer(p);
                System.out.println("\n\n////////////////////C'est au tour de " + p.getName()+" de jouer////////////////////");
                System.out.println(p);
                g.playTurn(p);
            }
        }

        //quand on détecte la fin d'une partie on calcule les points des joueurs suivant leur objectif
        g.determineScore();
        System.out.println(g.scoreToString());
    }
}
