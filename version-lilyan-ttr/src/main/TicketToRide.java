import Controller.GameController;
import Model.Game;
import View.GameView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TicketToRide {
    public static void main (String [] args) throws IOException {
        Game g = new Game(new ArrayList<>(Arrays.asList("lilyan", "eros", "loic", "View")));
        GameView gv = new GameView(g);
        GameController gc = new GameController(g,gv);
        gv.setActionListener(gc);
        System.out.println(g.toString());
        g.runGame();
        System.out.println(g.scoreToString());
    }
}
