package Controller;

import Model.Game;
import Model.GameElements.Player;
import Model.GameElements.Route;
import View.GameView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RouteController implements MouseListener {
    private Game model;
    private GameView view;
    private Player currentPlayer;

    public RouteController(Game g, GameView gv) {
        model = g;
        view = gv;
        currentPlayer = g.getPlayers().get(0);
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Route r = view.getBoard().getRouteClicked(e);
        if(r != null){
            System.out.println("vous avez cliqué sur la route entre "+r.getDest1()+" et "+r.getDest2());
            int input = JOptionPane.showConfirmDialog(null ,"Cette route relie "+r.getDest1()+" à "+r.getDest2()+". Elle est de couleur "+r.getColor()+" " +
                            "et il faut "+r.getRequire()+" wagons pour la prendre.", r.getDest1()+" à "+r.getDest2(),JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("souris pressée");
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println("souris relachée");
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("souris entrée");
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("souris sortie");
    }
}
