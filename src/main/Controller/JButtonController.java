package Controller;

import Model.Enum.Color;
import Model.Game;
import Model.GameElements.*;
import View.PlayView.CardButtonPane;
import View.PlayView.CityTile;
import View.PlayView.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.System.exit;

/**
 * classe du controller des boutons du jeu
 */
public class JButtonController implements ActionListener {
    private Game model;
    private GameView view;

    private Player currentPlayer;
    private int currentAction = 0;
    private int nbCardTaken = 0;
    private int lastIndexTaken = -3;

    private CityTile choixCity1 = null;
    private CityTile choixCity2 = null;


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



        //si on appuie sur le bouton piocher une carte wagon
        if(src == view.getButtons().getPiocherW()){
            System.out.println("Vous avez cliqué sur piocher un wagon");
            view.getButtons().getPiocherW().setEnabled(false);
            view.getButtons().getPiocherD().setEnabled(false);
            view.getButtons().getPrendreR().setEnabled(false);
            view.getDraw().getPiocheWagon().setEnabled(true);
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
                            "Erreur",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
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
                            +newCard.getColor(),"Prendre une carte",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
                view.updateView(model,this);
                view.getPlayerView().updateCard(currentPlayer);
                view.repaint();
                if(nbCardTaken > 1){
                    int input = JOptionPane.showConfirmDialog(null ,"Fin de votre tour",
                            "Fin du tour",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    currentPlayer = model.nextPlayer();
                    currentAction = 0;
                    nbCardTaken = 0;
                }
            }
        }



        // si on souhaite piocher des cartes destination
        if(src == view.getButtons().getPiocherD() /*|| src == view.getDraw().getPiocheDestination()*/){
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
                        "Piocher des cartes destinations",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);

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
                        "Fin du tour",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                currentPlayer = model.nextPlayer();
                currentAction = 0;
                nbCardTaken = 0;
            }else{
                int input = JOptionPane.showConfirmDialog(null ,"Il n'y a plus de carte detination disponible.",
                        "Piocher des cartes destinations",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
            }
        }



        //bouton pour prendre une route
        if(src == view.getButtons().getPrendreR()) {
            //on désactive les boutons et on attend que le joueur appuie sur une ville
            view.getButtons().getPiocherW().setEnabled(false);
            view.getButtons().getPiocherD().setEnabled(false);
            view.getButtons().getPrendreR().setEnabled(false);
            view.getDraw().getPiocheWagon().setEnabled(false);
            if (currentAction == 0) {
                choixCity1 = null;
                choixCity2 = null;
                currentAction = 2;
            }
        }

        //si on appuie sur une ville
        if(src instanceof CityTile) {
            //si auparavent on a appuyer sur prendre une route
             if(currentAction == 2){
                if(src instanceof CityTile) {
                    if(choixCity1 == null){
                        choixCity1 = (CityTile)src;
                        choixCity1.setEnabled(false);
                    }else if(choixCity2 == null){
                        choixCity2 = (CityTile)src;
                        choixCity2.setEnabled(false);

                        Route route1 = model.getD().getRouteFromString(choixCity1.getCity().getName() +" - "+choixCity2.getCity().getName());
                        Route route2 = model.getD().getRouteFromString(choixCity2.getCity().getName() +" - "+choixCity1.getCity().getName());
                        Route routeChoix = null;
                        if(route1 != null && route2 != null){
                            routeChoix = chooseDoubleRoute(route1,route2);
                        }
                        else{
                            if(route1 != null){
                                routeChoix = route1;
                            }else{
                                routeChoix = route2;
                            }
                        }

                        //si elle n'existe toujours pas c'est qu'il n'y a pas de route entre ces villes
                        if(routeChoix == null){
                            int input = JOptionPane.showConfirmDialog(null ,"Il n'y a pas de route entre ces deux villes.",
                                    "Erreur",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                            choixCity1.setEnabled(true);
                            choixCity2.setEnabled(true);
                            choixCity1 = null;
                            choixCity2 = null;
                            return;
                        }
                        int input;
                        if(routeChoix.isAlreadyTakenRoute()){
                            input = JOptionPane.showConfirmDialog(null ,"La route est déjà prise.",
                                    "Erreur",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                            choixCity1.setEnabled(true);
                            choixCity2.setEnabled(true);
                            choixCity1 = null;
                            choixCity2 = null;
                            return;
                        }

                        if(currentPlayer.getWagons() < routeChoix.getRequire()){
                            input = JOptionPane.showConfirmDialog(null ,"Vous n'avez pas assez de wagon.",
                                    "Erreur",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                            choixCity1.setEnabled(true);
                            choixCity2.setEnabled(true);
                            choixCity1 = null;
                            choixCity2 = null;
                            return;
                        }

                        if (routeChoix.isTunel()) {
                            //si la route n'a pas de couleur
                            if (routeChoix.getColor() == Color.GRAY) {

                                Color choixColor = chooseColor();

                                routeChoix.getTunnel(choixColor, model,currentPlayer);
                            }
                            //si la route a une couleur
                            else {
                                routeChoix.getTunnel(routeChoix.getColor(), model,currentPlayer);
                            }
                        }
                        //si c'est pas un tunnel
                        else {
                            //si c'est un ferrie
                            if (routeChoix.getColor() == Color.GRAY) {

                                Color choixColor = chooseColor();

                                //si le joueur a pas pu prendre le ferrie
                                if (routeChoix.getFerrie( choixColor, model, currentPlayer) == -1) {
                                    currentAction = 0;
                                    choixCity1.setEnabled(true);
                                    choixCity2.setEnabled(true);
                                    choixCity1 = null;
                                    choixCity2 = null;
                                    return;
                                }
                            } else {
                                //si le joueur a pas pu prendre la route
                                if (routeChoix.getRoute(routeChoix.getColor(), model, currentPlayer) == -1) {
                                    currentAction = 0;
                                    choixCity1.setEnabled(true);
                                    choixCity2.setEnabled(true);
                                    choixCity1 = null;
                                    choixCity2 = null;
                                    return;
                                }
                            }
                        }
                        currentPlayer = model.nextPlayer();
                        currentAction = 0;
                        nbCardTaken = 0;
                        choixCity1.setEnabled(true);
                        choixCity2.setEnabled(true);
                        choixCity1 = null;
                        choixCity2 = null;
                    }
                }
            }
        }



        //bouton pour quitter la partie
        if(src == view.getButtons().getQuitter()){
            int input = JOptionPane.showConfirmDialog(null, "Voulez-vous quitter la partie ?");
            if(input == 0){
                exit(0);
            }
        }



        //bouton pour voir ses objectifs
        if(src == view.getPlayerView().getObjButton()){
            int taille = currentPlayer.getdCards().size();

            Object[] params = new Object[taille];
            for(int i = 0; i < taille; i++){
                JLabel jb = new JLabel(currentPlayer.getdCards().get(i).toString(),SwingConstants.CENTER);
                params[i] = jb;
            }
            int input = JOptionPane.showConfirmDialog(null ,params,
                    "Mes cartes destination",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }

        //fin d'un tour
        if(currentAction == 0) {
            view.getButtons().getPiocherW().setEnabled(true);
            view.getButtons().getPiocherD().setEnabled(true);
            view.getButtons().getPrendreR().setEnabled(true);
            view.getDraw().getPiocheWagon().setEnabled(false);
            view.updateView(model,this);
            view.repaint();
        }
    }

    /**
     * fonction qui permet de choisir une couleur graphiquement
     * @return Color la couleur choisie
     */
    public Color chooseColor(){
        //on fait choisir une couleur pour prendre le tunnel
        Object[] params = new Object[2];
        params[0] = "Choisissez une couleur pour tenter le tunnel :";
        JComboBox colorList = new JComboBox<>(Color.values());
        colorList.setSelectedIndex(0);
        params[1] = colorList;
        int input = JOptionPane.showConfirmDialog(null ,params,
                "Choix d'une couleur",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
        return (Color) colorList.getSelectedItem();
    }

    /**
     * fonction qui permet de choisir une couleur graphiquement
     * @return Color la couleur choisie
     */
    public Route chooseDoubleRoute(Route r1, Route r2){
        //on fait choisir une couleur pour prendre le tunnel
        Object[] params = new Object[2];
        params[0] = "Choisissez une route à prendre pour relier ces deux villes :";
        Route[] r = new Route[2];
        r[0] = r1;
        r[1] = r2;
        JComboBox routeList = new JComboBox<>(r);
        routeList.setSelectedIndex(0);
        params[1] = routeList;
        int input = JOptionPane.showConfirmDialog(null ,params,
                "Choix d'une route",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
        return (Route) routeList.getSelectedItem();
    }

}