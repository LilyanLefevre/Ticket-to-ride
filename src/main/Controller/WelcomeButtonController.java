package Controller;

import View.MenuView.WelcomeFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * classe qui controle la vue de la fenetre d'accueil
 */
public class WelcomeButtonController implements ActionListener {
    private int status = 0;
    private WelcomeFrame view;
    private int nbJoueurs;
    private ArrayList<String> nomJoueurs;

    public int getStatus() {
        return status;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public ArrayList<String> getNomJoueurs() {
        return nomJoueurs;
    }

    public WelcomeButtonController(WelcomeFrame view) {
        this.view = view;
        nomJoueurs = new ArrayList<>();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if(src == view.getCjp().getConfirm()) {
            nbJoueurs = (int) view.getCjp().getChoixJoueurs().getSelectedItem();
            if(nbJoueurs != 0) {
                view.getCjp().getConsigne1().setVisible(false);
                view.getCjp().getConsigne2().setVisible(true);
                view.getCjp().getChoixJoueurs().setVisible(false);
                view.getCjp().getNom().setVisible(true);
                view.getCjp().getConfirm().setVisible(false);
                view.getCjp().getConfirm2().setVisible(true);
                view.getCjp().getQuit().setVisible(true);
                view.getRootPane().setDefaultButton(view.getCjp().getConfirm2());
                view.getCjp().getNom().requestFocus();
            }else{
                status = 1;
            }

        }
        if(src == view.getCjp().getQuit()){
            nbJoueurs = 0;
            nomJoueurs = new ArrayList<>();
            view.getCjp().getConsigne1().setVisible(true);
            view.getCjp().getConsigne2().setVisible(false);
            view.getCjp().getChoixJoueurs().setVisible(true);
            view.getCjp().getNom().setVisible(false);
            view.getCjp().getConfirm().setVisible(true);
            view.getCjp().getConfirm2().setVisible(false);
            view.getCjp().getQuit().setVisible(false);
            view.getRootPane().setDefaultButton(view.getCjp().getConfirm());
            view.getCjp().getChoixJoueurs().requestFocus();
        }
        if(src == view.getCjp().getConfirm2()){
            String n = view.getCjp().getNom().getText();
            if(n.isEmpty()){
                JOptionPane.showOptionDialog(null, "Vous ne pouvez pas saisir un nom vide",
                        "Erreur", JOptionPane.OK_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
            }else{
                nomJoueurs.add(n);
            }
            view.getCjp().getNom().setText("");
            if(nomJoueurs.size() == nbJoueurs){
                status = 1;
            }
        }
    }
}
