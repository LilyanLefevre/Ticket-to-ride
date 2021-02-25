package Controller;

import Model.GameElements.Route;
import View.PlayView.GameView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * classe de l'écouteur de la souris qui va permettre de cliquer sur une route
 */
public class RouteController implements MouseListener {
    private GameView view;

    public RouteController(GameView gv) {
        view = gv;
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
            System.out.println("vous avez cliqué sur la route entre "+r.getCity1()+" et "+r.getCity2());
            int input = JOptionPane.showConfirmDialog(null ,"Cette route relie "+r.getCity1()+
                    " à "+r.getCity2()+". Elle est de couleur "+r.getColor()+" " +"et il faut "+r.getRequire()+
                    " wagons pour la prendre.", r.getCity1()+" à "+r.getCity2()
                    ,JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
