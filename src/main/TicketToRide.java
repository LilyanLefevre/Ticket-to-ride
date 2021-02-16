import Controller.JButtonController;
import Controller.RouteController;
import Controller.WelcomeButtonController;
import Model.Game;
import View.MenuView.WelcomeFrame;
import View.PlayView.GameView;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.exit;

public class TicketToRide {
    private static long SEED = System.currentTimeMillis();

    public static void main (String [] args){
        System.out.println("Seed = "+SEED);

        /**
         * première fenetre qui demande de saisir les paramètres
         */
        WelcomeFrame view1 = new WelcomeFrame();
        while(!view1.fini){
            System.out.print("");
        }
        WelcomeButtonController welcomeController = new WelcomeButtonController(view1);
        view1.setActionListener(welcomeController);

        while(welcomeController.getStatus() == 0 && view1.getStatus() == 0){
            System.out.print("");
        }
        if(view1.getStatus() == -1){
            exit(0);
        }else{
            view1.dispose();
            System.out.println("il y a "+welcomeController.getNbJoueurs()+" joueurs");
        }
        /**
         * deuxième fenetre qui lance le jeu
         */
        //on créé le jeu
        Random r = new Random(SEED);
        Game g = new Game(new ArrayList<>(welcomeController.getNomJoueurs()), r);

        //on créé la vue
        GameView gv = new GameView(g);
        while(!gv.fini){
            System.out.print("");
        }

        //on ajoute les controlleurs
        RouteController mc = new RouteController(gv);
        gv.setRouteListener(mc);
        JButtonController gc = new JButtonController(g,gv);
        gv.setActionListener(gc);

        //on fait tourner le jeu tant qu'il reste plus de 3 wagons à chaque joueurs
        /*while(!g.gameIsOver()){
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
        System.out.println(g.scoreToString());*/
    }
}
