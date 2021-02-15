package Model.Player;
import Model.Game;
import Model.GameElements.DestinationCard;
import Model.GameElements.Route;
import Model.GameElements.WagonCard;
import View.PlayView.GameView;

import java.awt.Color;
import java.util.ArrayList;

/**
 * classe représentant un joueur du jeu
 */
public class HumanPlayer extends EveryPlayer {

    public HumanPlayer(String name, Color color, ArrayList<DestinationCard> dc, ArrayList<WagonCard> tc) {
        super(name,color,dc,tc);
    }

    @Override
    public void playTurn(GameView gv) {

    }

}
