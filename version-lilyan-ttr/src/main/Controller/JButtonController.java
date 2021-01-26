package Controller;

import Model.Enum.Color;
import Model.Game;
import Model.GameElements.City;
import Model.GameElements.DestinationCard;
import Model.GameElements.Player;
import Model.GameElements.WagonCard;
import View.CardButtonPane;
import View.CityTile;
import View.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;

import static java.lang.System.exit;

public class JButtonController implements ActionListener {
    private Game model;
    private GameView view;
    private HashMap<String, CityTile> cityHashMap;
    private Player currentPlayer;

    private int currentAction = 0;
    private int nbCardTaken = 0;
    private int lastIndexTaken = -3;


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
        System.out.println("classe de l'element touché : "+src.getClass() );
        if(src instanceof CityTile){
            CityTile city = (CityTile) src;
            System.out.println("Vous avez cliqué sur la ville "+city.getCity().getName());
        }

        //si on appuie sur le bouton piocher une carte wagon
        if(src == view.getButtons().getPiocherW()){
            System.out.println("Vous avez cliqué sur piocher un wagon");
            view.getButtons().getPiocherW().setEnabled(false);
            view.getButtons().getPiocherD().setEnabled(false);
            view.getButtons().getPrendreR().setEnabled(false);
            view.getDraw().getPiocheDestination().setEnabled(false);
            currentAction = 1;
        }

        //si on appuie sur une carte wagon
        if(src instanceof CardButtonPane){
            CardButtonPane card = (CardButtonPane)src;
            if(currentAction == 1){
                //s'il tente de prendre une carte loco et qu'il a déjà pris une autre carte -> erreur
                if(lastIndexTaken != -1 && card.getCouleur() == Color.RAINBOW && nbCardTaken > 0){
                    int input = JOptionPane.showConfirmDialog(null ,"Si vous prenez une carte " +
                            "locomotive visible lors d'un tour, vous ne pouvez pas prendre d'autre carte !",
                            "Erreur",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                //si il choisi une carte visible
                if(card.getIndex() >= 0){

                    WagonCard tmp = model.getDrawVisibleTrainCards().get(card.getIndex());
                    int input = JOptionPane.showConfirmDialog(null ,"Vous avez pioché une carte "
                            +tmp.getColor(),"Prendre une carte",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if(input == 2){
                        return;
                    }
                    nbCardTaken++;
                    //on retire la carte de la pioche de cartes visibles
                    model.getDrawVisibleTrainCards().remove(tmp);

                    //on remet une nouvelle carte tiree au hasard dans la pioche
                    int nCard = (int) (Math.random() * (model.getDrawVisibleTrainCards().size()));
                    model.getDrawVisibleTrainCards().add(model.getDrawWagonCards().get(nCard));
                    model.getDrawWagonCards().remove(nCard);

                    //on l'ajoute dans les cartes du joueur
                    currentPlayer.addTrainCard(tmp);

                    //si il a pris une carte locomotive on arrête
                    if (tmp.getColor() == Color.RAINBOW) {
                        nbCardTaken = 2;
                    }
                }
                //S'il pioche une carte au hasard
                else{
                    nbCardTaken++;

                    WagonCard newCard = currentPlayer.drawTrainCard(model.getDrawWagonCards());
                    int input = JOptionPane.showConfirmDialog(null ,"Vous avez pioché une carte "
                            +newCard.getColor(),"Prendre une carte",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
                view.updateView(model,this);
                view.getPlayerView().updateCard(currentPlayer);
                view.repaint();
                if(nbCardTaken > 1){
                    int input = JOptionPane.showConfirmDialog(null ,"Fin de votre tour",
                            "Fin du tour",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    currentPlayer = model.nextPlayer();
                    currentAction = 0;
                    nbCardTaken = 0;
                }
            }
        }

        // si on souhaite piocher des cartes destination
        if(src == view.getButtons().getPiocherD() || src == view.getDraw().getPiocheDestination()){
            //s'il reste au moins une carte destination
            if(model.getDrawDestinationCards().size() > 0) {
                System.out.println("Vous avez pioché les trois cartes Destination suivantes: ");
                ArrayList<DestinationCard> dctmp = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    if (model.getDrawDestinationCards().size() > 0) {
                        DestinationCard tmpd = model.drawDestinationCard();
                        System.out.print("  " + (i + 1) + " - " + tmpd);
                        dctmp.add(tmpd);
                    }
                }


                String message = "Vous avez pioché les cartes suivantes, sélectionner celle(s) que vous voulez garder.";
                Object[] params = new Object[4];
                params[0] = message;
                JCheckBox cb1 = new JCheckBox(dctmp.get(0).toString());
                params[1] = cb1;
                JCheckBox cb2 = null;
                JCheckBox cb3 = null;
                if(dctmp.size() > 1){
                    cb2 = new JCheckBox(dctmp.get(1).toString());
                    params[2] = cb2;
                }
                if(dctmp.size() > 2){
                    cb3 = new JCheckBox(dctmp.get(2).toString());
                    params[3] = cb3;

                }

                int input = JOptionPane.showConfirmDialog(null ,params,
                        "Piocher des cartes destinations",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

                //on met les cartes dans le jeu du joueur
                System.out.println("Vous avez conservé les cartes suivantes :");
                if(cb1.isSelected()){
                    System.out.print(dctmp.get(0).toString());
                    currentPlayer.addDestinationCard(dctmp.get(0));
                }
                if(dctmp.size() > 1 && cb2.isSelected()){
                    System.out.print(dctmp.get(1).toString());
                    currentPlayer.addDestinationCard(dctmp.get(1));
                }
                if(dctmp.size() > 2 && cb3.isSelected()){
                    System.out.print(dctmp.get(2).toString());
                    currentPlayer.addDestinationCard(dctmp.get(2));
                }
                input = JOptionPane.showConfirmDialog(null ,"Fin de votre tour",
                        "Fin du tour",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                currentPlayer = model.nextPlayer();
                currentAction = 0;
                nbCardTaken = 0;
            }else{
                int input = JOptionPane.showConfirmDialog(null ,"Il n'y a plus de carte detination disponible.",
                        "Piocher des cartes destinations",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            }

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

        if(src == view.getPlayerView().getObjButton()){
            int taille = currentPlayer.getdCards().size();
            
            Object[] params = new Object[taille];
            for(int i = 0; i < taille; i++){
                JLabel jb = new JLabel(currentPlayer.getdCards().get(i).toString(),SwingConstants.CENTER);
                params[i] = jb;
            }
            int input = JOptionPane.showConfirmDialog(null ,params,
                    "Mes cartes destination",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }

        if(currentAction == 0) {
            view.getButtons().getPiocherW().setEnabled(true);
            view.getButtons().getPiocherD().setEnabled(true);
            view.getButtons().getPrendreR().setEnabled(true);
            view.getDraw().getPiocheDestination().setEnabled(true);
            view.updateView(model,this);
            view.repaint();
        }
    }
}
