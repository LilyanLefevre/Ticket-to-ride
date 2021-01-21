package Controller;

import Model.Game;
import Model.GameElements.City;
import Model.GameElements.Player;
import View.CityTile;
import View.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.HashMap;

import static java.lang.System.exit;

public class JButtonController implements ActionListener {
    private Game model;
    private GameView view;
    private HashMap<String, CityTile> cityHashMap;
    private Player currentPlayer;


    public JButtonController(Game g, GameView gv) {
        model = g;
        view = gv;
        currentPlayer = g.getPlayers().get(0);
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
        if(src == view.getButtons().getPiocherW()){
            System.out.println("Vous avez cliqué sur piocher un wagon");
        }
        if(src == view.getButtons().getPiocherD()){
            System.out.println("Vous avez cliqué sur piocher des destinations");
        }
        if(src == view.getButtons().getPrendreR()){
            System.out.println("Vous avez cliqué sur prendre une route");
        }
        if(src == view.getButtons().getQuitter()){
            System.out.println("Vous avez cliqué sur quitter");
            int input = JOptionPane.showConfirmDialog(null, "Voulez-vous quitter la partie ?");
            if(input == 0){
                exit(0);
            }
        }

    }
}
