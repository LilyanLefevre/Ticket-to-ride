package Controller;

import Model.Game;
import Model.GameElements.City;
import View.CityTile;
import View.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GameController implements ActionListener {
    private Game model;
    private GameView view;
    private HashMap<String, CityTile> cityHashMap;

    public GameController(Game g, GameView gv) {
        model = g;
        view = gv;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src instanceof CityTile){
            CityTile city = (CityTile) src;
            System.out.println("Vous avez cliqué sur la ville "+city.getCity().getName());
        }
    }
}
