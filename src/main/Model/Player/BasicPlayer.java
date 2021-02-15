package Model.Player;

import Model.GameElements.DestinationCard;
import Model.GameElements.WagonCard;
import View.PlayView.GameView;

import java.awt.*;
import java.util.ArrayList;

public class BasicPlayer extends EveryPlayer{

    public BasicPlayer(String name, Color color, ArrayList<DestinationCard> dc, ArrayList<WagonCard> tc) {
        super(name, color, dc, tc);
    }

    @Override
    public void playTurn(GameView gv) {

    }
}
