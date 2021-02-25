package Controller;

import Model.GameElements.Color;
import Model.Game;
import Model.GameElements.*;
import Model.GameElements.Player;
import View.PlayView.CardButtonPane;
import View.PlayView.CityTile;
import View.PlayView.GameView;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    private boolean endMessage;

    private CityTile choixCity1 = null;
    private CityTile choixCity2 = null;

    public JButtonController(Game g, GameView gv, boolean endMessage) {
        model = g;
        view = gv;
        this.endMessage = endMessage;
        currentPlayer = g.getPlayers().get(0);
        playIA();
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
                    if(currentPlayer.getLevel() == 0) {
                        int input = JOptionPane.showConfirmDialog(null, "Si vous prenez une carte " +
                                        "locomotive visible lors d'un tour, vous ne pouvez pas prendre d'autre carte !",
                                "Erreur", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    }
                    return;
                }

                //si il choisi une carte visible
                if(card.getIndex() >= 0){

                    WagonCard tmp = model.getDrawVisibleTrainCards().get(card.getIndex());
                    if(currentPlayer.getLevel() == 0) {
                        int input = JOptionPane.showConfirmDialog(null, "Vous avez pioché une carte "
                                + tmp.getColor(), "Prendre une carte", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        if (input == 2) {
                            return;
                        }
                    }
                    nbCardTaken++;
                    //on retire la carte de la pioche de cartes visibles
                    model.getDrawVisibleTrainCards().remove(tmp);

                    //on remet une nouvelle carte tiree au hasard dans la pioche
                    int nCard = (int) (Math.random() * (model.getWagonCardsDraw().size()));
                    model.getDrawVisibleTrainCards().add(model.getWagonCardsDraw().get(nCard));
                    model.getWagonCardsDraw().remove(nCard);

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

                    WagonCard newCard = currentPlayer.drawWagonCard(model.getWagonCardsDraw());
                    if(currentPlayer.getLevel() == 0) {
                        int input = JOptionPane.showConfirmDialog(null, "Vous avez pioché une carte "
                                + newCard.getColor(), "Prendre une carte", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                view.updateView(model,this);
                view.getPlayerView().updateCard(currentPlayer);
                view.repaint();
                if(nbCardTaken > 1){
                    if(currentPlayer.getLevel() == 0) {
                        int input = JOptionPane.showConfirmDialog(null, "Fin de votre tour",
                                "Fin du tour", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    }
                    currentPlayer = model.nextPlayer();
                    currentAction = 0;
                    nbCardTaken = 0;
                    checkFinPartie();
                    playIA();
                }
            }
        }



        // si on souhaite piocher des cartes destination
        if(src == view.getButtons().getPiocherD()){
            //s'il reste au moins une carte destination
            if(model.getDestinationCardsDraw().size() > 0) {
                System.out.println(currentPlayer.getName()+" a pioché les trois cartes Destination suivantes: ");
                ArrayList<DestinationCard> dctmp = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    if (model.getDestinationCardsDraw().size() > 0) {
                        DestinationCard tmpd = model.drawDestinationCard();
                        System.out.print("  " + (i + 1) + " - " + tmpd);
                        dctmp.add(tmpd);
                    }
                }


                String message = currentPlayer.getName()+" a pioché les cartes suivantes, sélectionner celle(s) que vous voulez garder.";
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
                if(currentPlayer.getLevel() == 0) {
                    int input = JOptionPane.showConfirmDialog(null, params,
                            "Piocher des cartes destinations", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }

                //on met les cartes dans le jeu du joueur
                System.out.println(currentPlayer.getName()+" a conservé les cartes suivantes :");
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
                if(currentPlayer.getLevel() == 0) {
                    int input = JOptionPane.showConfirmDialog(null, "Fin de votre tour",
                            "Fin du tour", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
                currentPlayer = model.nextPlayer();
                currentAction = 0;
                nbCardTaken = 0;
                checkFinPartie();
                playIA();

            }else{
                if(currentPlayer.getLevel() == 0) {
                    int input = JOptionPane.showConfirmDialog(null, "Il n'y a plus de carte detination disponible.",
                            "Piocher des cartes destinations", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
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

                        Route route1 = model.getDestinations().getRouteFromString(choixCity1.getCity().getName() +" - "+choixCity2.getCity().getName());
                        Route route2 = model.getDestinations().getRouteFromString(choixCity2.getCity().getName() +" - "+choixCity1.getCity().getName());
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

                                routeChoix.takeTunnel(choixColor, model,currentPlayer);
                            }
                            //si la route a une couleur
                            else {
                                routeChoix.takeTunnel(routeChoix.getColor(), model,currentPlayer);
                            }
                        }
                        //si c'est pas un tunnel
                        else {
                            //si c'est un ferrie
                            if (routeChoix.getColor() == Color.GRAY) {

                                Color choixColor = chooseColor();

                                //si le joueur a pas pu prendre le ferrie
                                if (routeChoix.takeFerrie( choixColor, model, currentPlayer) == -1) {
                                    currentAction = 0;
                                    choixCity1.setEnabled(true);
                                    choixCity2.setEnabled(true);
                                    choixCity1 = null;
                                    choixCity2 = null;
                                    return;
                                }
                            } else {
                                //si le joueur a pas pu prendre la route
                                if (routeChoix.takeRoute(routeChoix.getColor(), model, currentPlayer) == -1) {
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
                        checkFinPartie();
                        playIA();

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
            int taille = currentPlayer.getDestinationCards().size();

            Object[] params = new Object[taille];
            for(int i = 0; i < taille; i++){
                JLabel jb = new JLabel(currentPlayer.getDestinationCards().get(i).toString(),SwingConstants.CENTER);
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


    private void checkFinPartie(){
        if(model.gameIsOver()){
            //on calcule les points de chaque joueur avec les objectifs
            model.determineScore();

            //on récupère le vainqueur
            Player p = model.getWinner();
            FileWriter writer = null;
            try {
                writer = new FileWriter("./resultats.txt",true);
                writer.write(p.getName()+"\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String scoresWithNames = p.getName()+" a gagné\n";
            for(Player pl : model.getPlayers()){
                scoresWithNames += pl.getName()+" : "+pl.getPoints()+" points lvl = "+pl.getLevel()+"\n";
            }
            if(endMessage) {
                Object[] options1 = {"Jouer une autre partie", "Quitter"};
                int input = JOptionPane.showOptionDialog(null, scoresWithNames, "Fin de partie",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, options1[0]);
                if (input == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
            System.exit(0);
        }
    }

    private void playIA(){
        if(currentPlayer.getLevel() == 1){
            playIALevel2();
        }
        if(currentPlayer.getLevel() == 2){
            playIALevel3();
        }
        if(currentPlayer.getLevel() == 3){
            playIALevel4();
        }
        if(currentPlayer.getLevel() == 4){
            playIALevel5();
        }
        if(currentPlayer.getLevel() != 0){
            playIALevel1();
        }
    }


    /**
     * ia qui essaie de poser une route dès qu'elle peut
     */
    private void playIALevel1(){
        if(currentPlayer.getLevel() != 0) {
            ArrayList<Route> routes = model.getDestinations().getRoutes();
            Route desiredRoute = null;
            for (Route r : routes) {
                Color color = r.getColor();
                int nbCard = r.getRequire();
                if (currentPlayer.countOccurencesOf(color) >= nbCard && !r.isAlreadyTakenRoute() && currentPlayer.getWagons() >= nbCard) {
                    desiredRoute = r;
                }
            }

            //si on a trouvé une route à prendre
            if (desiredRoute != null) {
                tryGetRoute(desiredRoute);
            }
            //sinon on pioche des cartes
            else {
                if(model.getWagonCardsDraw().size() > 1) {
                    WagonCard wc = currentPlayer.drawWagonCard(model.getWagonCardsDraw());
                    System.out.print("Le joueur "+currentPlayer.getName()+" a pioché les cartes : " + wc.getColor());
                    wc = currentPlayer.drawWagonCard(model.getWagonCardsDraw());
                    System.out.println(" et " + wc.getColor());
                }else{
                    if(model.getDestinationCardsDraw().size() > 0) {
                        System.out.print("Le joueur "+currentPlayer.getName()+" a pioché les cartes destination : " );
                        for (int i = 0; i < 3; i++) {
                            if (model.getDestinationCardsDraw().size() > 0) {
                                DestinationCard tmpd = model.drawDestinationCard();
                                currentPlayer.addDestinationCard(tmpd);
                                System.out.print(tmpd+"  ");
                            }
                        }
                    }
                }
            }
            checkFinPartie();
            currentPlayer = model.nextPlayer();
            view.updateView(model, this);
            view.getPlayerView().updateCard(currentPlayer);
            view.repaint();
            playIA();
        }
    }

    /**
     * ia qui essaie de faire son objectif qui vaut le plus de points en priorité
     */
    private void playIALevel2() {
        if(currentPlayer.getLevel() != 0) {
            ArrayList<Route> routes = model.getDestinations().getRoutes();
            ArrayList<DestinationCard> objectifs = currentPlayer.getDestinationCards();
            Collections.sort(objectifs);
            Route desiredRoute = null;

            Graph<City, DefaultEdge> g = new DefaultUndirectedGraph<>(DefaultEdge.class);

            //on ajoute toutes les villes au graph
            for (Map.Entry city : model.getDestinations().getDestinations().entrySet()) {
                City c1 = (City)city.getValue();
                g.addVertex(c1);
            }

            //on ajoute tous les arcs entre les villes
            for (Route r : routes){
                g.addEdge(r.getCity1(), r.getCity2());
            }
            BellmanFordShortestPath<City, DefaultEdge> belmanFordAlg = new BellmanFordShortestPath<>(g);
            int count = 0;
            for(DestinationCard dc : objectifs) {
                count++;
                if (desiredRoute == null) {
                    //on cherche le chemin le plus court qui relie les deux villes
                    ShortestPathAlgorithm.SingleSourcePaths<City, DefaultEdge> iPaths = belmanFordAlg.getPaths(dc.getCity1());

                    //on récupère les villes parcourues par le chemin (il y en a forcément un)
                    GraphPath<City, DefaultEdge> path = iPaths.getPath(dc.getCity2());
                    if (path != null) {
                        List<City> listCities = path.getVertexList();
                        if (!listCities.isEmpty()) {
                            for (int i = 0; i < listCities.size() - 1; i++) {
                                City v1 = listCities.get(i);
                                City v2 = listCities.get(i + 1);
                                Route r = model.getDestinations().getRouteFromString(v1.getName() + " - " + v2.getName());
                                if (r == null) {
                                    r = model.getDestinations().getRouteFromString(v2.getName() + " - " + v1.getName());
                                }
                                Color color = r.getColor();
                                int nbCard = r.getRequire();
                                if (currentPlayer.countOccurencesOf(color) >= nbCard && !r.isAlreadyTakenRoute() && currentPlayer.getWagons() >= nbCard) {
                                    desiredRoute = r;
                                }
                            }
                        }
                    }
                }
            }
            if(desiredRoute == null && count==objectifs.size()){
                for (Route r : routes) {
                    Color color = r.getColor();
                    int nbCard = r.getRequire();
                    if (currentPlayer.countOccurencesOf(color) >= nbCard && !r.isAlreadyTakenRoute() && currentPlayer.getWagons() >= nbCard) {
                        desiredRoute = r;
                    }
                }
            }
            //si on a trouvé une route à prendre
            if (desiredRoute != null) {
                tryGetRoute(desiredRoute);
            }
            //sinon on pioche des cartes
            else {
                if(model.getDestinationCardsDraw().size() > 1) {
                    WagonCard wc = currentPlayer.drawWagonCard(model.getWagonCardsDraw());
                    System.out.print("Le joueur "+currentPlayer.getName()+"  pioché les cartes : " + wc.getColor());
                    wc = currentPlayer.drawWagonCard(model.getWagonCardsDraw());
                    System.out.println(" et " + wc.getColor());
                }else{
                    if(model.getDestinationCardsDraw().size() > 0) {
                        System.out.print("Le joueur "+currentPlayer.getName()+" a pioché les cartes destination : " );
                        for (int i = 0; i < 3; i++) {
                            if (model.getDestinationCardsDraw().size() > 0) {
                                DestinationCard tmpd = model.drawDestinationCard();
                                currentPlayer.addDestinationCard(tmpd);
                                System.out.print(tmpd+"  ");
                            }
                        }
                    }
                }
            }
            checkFinPartie();
            currentPlayer = model.nextPlayer();
            view.updateView(model, this);
            view.getPlayerView().updateCard(currentPlayer);
            view.repaint();
            playIA();
        }
    }


    /**
     * ia qui essaie de faire son objectif qui vaut le moins de points en priorité
     */
    private void playIALevel3() {
        if(currentPlayer.getLevel() != 0) {
            ArrayList<Route> routes = model.getDestinations().getRoutes();
            ArrayList<DestinationCard> objectifs = currentPlayer.getDestinationCards();
            Collections.sort(objectifs);
            Collections.reverse(objectifs);
            Route desiredRoute = null;

            Graph<City, DefaultEdge> g = new DefaultUndirectedGraph<>(DefaultEdge.class);

            //on ajoute toutes les villes au graph
            for (Map.Entry city : model.getDestinations().getDestinations().entrySet()) {
                City c1 = (City)city.getValue();
                g.addVertex(c1);
            }

            //on ajoute tous les arcs entre les villes
            for (Route r : routes){
                g.addEdge(r.getCity1(), r.getCity2());
            }
            BellmanFordShortestPath<City, DefaultEdge> belmanFordAlg = new BellmanFordShortestPath<>(g);
            int count = 0;
            for(DestinationCard dc : objectifs) {
                count++;
                if (desiredRoute == null) {
                    //on cherche le chemin le plus court qui relie les deux villes
                    ShortestPathAlgorithm.SingleSourcePaths<City, DefaultEdge> iPaths = belmanFordAlg.getPaths(dc.getCity1());

                    //on récupère les villes parcourues par le chemin (il y en a forcément un)
                    GraphPath<City, DefaultEdge> path = iPaths.getPath(dc.getCity2());
                    if (path != null) {
                        List<City> listCities = path.getVertexList();
                        if (!listCities.isEmpty()) {
                            for (int i = 0; i < listCities.size() - 1; i++) {
                                City v1 = listCities.get(i);
                                City v2 = listCities.get(i + 1);
                                Route r = model.getDestinations().getRouteFromString(v1.getName() + " - " + v2.getName());
                                if (r == null) {
                                    r = model.getDestinations().getRouteFromString(v2.getName() + " - " + v1.getName());
                                }
                                Color color = r.getColor();
                                int nbCard = r.getRequire();
                                if (currentPlayer.countOccurencesOf(color) >= nbCard && !r.isAlreadyTakenRoute() && currentPlayer.getWagons() >= nbCard) {
                                    desiredRoute = r;
                                }
                            }
                        }
                    }
                }
            }

            if(desiredRoute == null && count==objectifs.size()){
                for (Route r : routes) {
                    Color color = r.getColor();
                    int nbCard = r.getRequire();
                    if (currentPlayer.countOccurencesOf(color) >= nbCard && !r.isAlreadyTakenRoute() && currentPlayer.getWagons() >= nbCard) {
                        desiredRoute = r;
                    }
                }
            }
            //si on a trouvé une route à prendre
            if (desiredRoute != null) {
                tryGetRoute(desiredRoute);
            }

            //sinon on pioche des cartes
            else {
                if(model.getDestinationCardsDraw().size() > 1) {
                    WagonCard wc = currentPlayer.drawWagonCard(model.getWagonCardsDraw());
                    System.out.print("Le joueur "+currentPlayer.getName()+" a pioché les cartes : " + wc.getColor());
                    wc = currentPlayer.drawWagonCard(model.getWagonCardsDraw());
                    System.out.println(" et " + wc.getColor());
                }else{
                    if(model.getDestinationCardsDraw().size() > 0) {
                        System.out.print("Le joueur "+currentPlayer.getName()+" a pioché les cartes destination : " );
                        for (int i = 0; i < 3; i++) {
                            if (model.getDestinationCardsDraw().size() > 0) {
                                DestinationCard tmpd = model.drawDestinationCard();
                                currentPlayer.addDestinationCard(tmpd);
                                System.out.print(tmpd+"  ");
                            }
                        }
                    }
                }
            }
            checkFinPartie();
            currentPlayer = model.nextPlayer();
            view.updateView(model, this);
            view.getPlayerView().updateCard(currentPlayer);
            view.repaint();
            playIA();
        }
    }

    private void playIALevel4() {
        if(currentPlayer.getLevel() != 0) {
            ArrayList<Route> routes = model.getDestinations().getRoutes();
            ArrayList<DestinationCard> objectifs = currentPlayer.getDestinationCards();
            Collections.sort(objectifs);
            Route desiredRoute = null;

            Graph<City, DefaultEdge> g1 = new DefaultDirectedGraph<>(DefaultEdge.class);
            for (Map.Entry city : model.getDestinations().getDestinations().entrySet()) {
                City c1 = (City)city.getValue();
                g1.addVertex(c1);
            }
            for (Route r : routes){
                g1.addEdge(r.getCity1(), r.getCity2());
            }
            AllDirectedPaths<City, DefaultEdge> pathFindingAlg = new AllDirectedPaths(g1);
            int count = 0;
            for(DestinationCard dc : objectifs) {
                count++;
                if (desiredRoute == null) {
                    List<GraphPath<City, DefaultEdge>> graphPath = pathFindingAlg.getAllPaths(dc.getCity1(), dc.getCity2(), true, null);
                    graphPath = sortGraph(graphPath);
                    for (int k=0;k<graphPath.size();k++){
                        GraphPath<City,DefaultEdge> path = graphPath.get(k);
                        if (path != null) {
                            List<City> listCities = path.getVertexList();
                            if (!listCities.isEmpty()) {
                                boolean alreadytaken = false;
                                for (int i = 0; i < listCities.size() - 1; i++) {
                                    City v1 = listCities.get(i);
                                    City v2 = listCities.get(i + 1);
                                    Route r = model.getDestinations().getRouteFromString(v1.getName() + " - " + v2.getName());
                                    if (r == null) {
                                        r = model.getDestinations().getRouteFromString(v2.getName() + " - " + v1.getName());
                                    }
                                    Color color = r.getColor();
                                    int nbCard = r.getRequire();
                                    if (r.isAlreadyTakenRoute() && !r.getPlayer().getName().equals(currentPlayer.getName())){
                                        alreadytaken = true;
                                        desiredRoute=null;
                                    }
                                    if (alreadytaken == false && currentPlayer.countOccurencesOf(color) >= nbCard && !r.isAlreadyTakenRoute() && currentPlayer.getWagons() >= nbCard) {
                                        desiredRoute = r;
                                        k=graphPath.size();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (desiredRoute == null && count == objectifs.size()) {
                for (Route r : routes) {
                    Color color = r.getColor();
                    int nbCard = r.getRequire();
                    if (currentPlayer.countOccurencesOf(color) >= nbCard && !r.isAlreadyTakenRoute() && currentPlayer.getWagons() >= nbCard) {
                        desiredRoute = r;
                    }
                }
            }

            if(desiredRoute!=null){
                tryGetRoute(desiredRoute);
            }

            //sinon on pioche des cartes
            else {
                if(model.getDestinationCardsDraw().size() > 1) {
                    WagonCard wc = currentPlayer.drawWagonCard(model.getWagonCardsDraw());
                    System.out.print("Le joueur "+currentPlayer.getName()+" a pioché les cartes : " + wc.getColor());
                    wc = currentPlayer.drawWagonCard(model.getWagonCardsDraw());
                    System.out.println(" et " + wc.getColor());
                }else{
                    if(model.getDestinationCardsDraw().size() > 0) {
                        System.out.print("Le joueur "+currentPlayer.getName()+" a pioché les cartes destination : " );
                        for (int i = 0; i < 3; i++) {
                            if (model.getDestinationCardsDraw().size() > 0) {
                                DestinationCard tmpd = model.drawDestinationCard();
                                currentPlayer.addDestinationCard(tmpd);
                                System.out.print(tmpd+"  ");
                            }
                        }
                    }
                }
            }
            checkFinPartie();
            currentPlayer = model.nextPlayer();
            view.updateView(model, this);
            view.getPlayerView().updateCard(currentPlayer);
            view.repaint();
            playIA();
        }
    }

    private void playIALevel5() {
        if(currentPlayer.getLevel() != 0) {
            ArrayList<Route> routes = model.getDestinations().getRoutes();
            ArrayList<DestinationCard> objectifs = currentPlayer.getDestinationCards();
            Collections.sort(objectifs);
            Collections.reverse(objectifs);
            Route desiredRoute = null;

            Graph<City, DefaultEdge> g = new DefaultUndirectedGraph<>(DefaultEdge.class);

            //on ajoute toutes les villes au graph
            for (Map.Entry city : model.getDestinations().getDestinations().entrySet()) {
                City c1 = (City)city.getValue();
                g.addVertex(c1);
            }

            //on ajoute tous les arcs entre les villes
            for (Route r : routes){
                g.addEdge(r.getCity1(), r.getCity2());
            }
            BellmanFordShortestPath<City, DefaultEdge> belmanFordAlg = new BellmanFordShortestPath<>(g);
            int count = 0;
            for(DestinationCard dc : objectifs) {
                count++;
                if (desiredRoute == null) {
                    boolean alreadyt = false;
                    //on cherche le chemin le plus court qui relie les deux villes
                    ShortestPathAlgorithm.SingleSourcePaths<City, DefaultEdge> iPaths = belmanFordAlg.getPaths(dc.getCity1());

                    //on récupère les villes parcourues par le chemin (il y en a forcément un)
                    GraphPath<City, DefaultEdge> path = iPaths.getPath(dc.getCity2());
                    if (path != null) {
                        List<City> listCities = path.getVertexList();
                        if (!listCities.isEmpty()) {
                            for (int i = 0; i < listCities.size() - 1; i++) {
                                City v1 = listCities.get(i);
                                City v2 = listCities.get(i + 1);
                                Route r = model.getDestinations().getRouteFromString(v1.getName() + " - " + v2.getName());
                                if (r == null) {
                                    r = model.getDestinations().getRouteFromString(v2.getName() + " - " + v1.getName());
                                }
                                Color color = r.getColor();
                                int nbCard = r.getRequire();
                                if (r.isAlreadyTakenRoute() && !r.getPlayer().getName().equals(currentPlayer.getName())){
                                    alreadyt = true;
                                    desiredRoute=null;
                                }
                                if (alreadyt==false && currentPlayer.countOccurencesOf(color) >= nbCard && !r.isAlreadyTakenRoute() && currentPlayer.getWagons() >= nbCard) {
                                    desiredRoute = r;
                                }
                            }
                        }
                    }
                }
            }

            if (desiredRoute == null && count == objectifs.size()) {
                for (Route r : routes) {
                    Color color = r.getColor();
                    int nbCard = r.getRequire();
                    if (currentPlayer.countOccurencesOf(color) >= nbCard && !r.isAlreadyTakenRoute() && currentPlayer.getWagons() >= nbCard) {
                        desiredRoute = r;
                    }
                }
            }

            if(desiredRoute!=null){
                tryGetRoute(desiredRoute);
            }

            //sinon on pioche des cartes
            else {
                if(model.getDestinationCardsDraw().size() > 1) {
                    WagonCard wc = currentPlayer.drawWagonCard(model.getWagonCardsDraw());
                    System.out.print("Le joueur "+currentPlayer.getName()+" a pioché les cartes : " + wc.getColor());
                    wc = currentPlayer.drawWagonCard(model.getWagonCardsDraw());
                    System.out.println(" et " + wc.getColor());
                }else{
                    if(model.getDestinationCardsDraw().size() > 0) {
                        System.out.print("Le joueur "+currentPlayer.getName()+" a pioché les cartes destination : " );
                        for (int i = 0; i < 3; i++) {
                            if (model.getDestinationCardsDraw().size() > 0) {
                                DestinationCard tmpd = model.drawDestinationCard();
                                currentPlayer.addDestinationCard(tmpd);
                                System.out.print(tmpd+"  ");
                            }
                        }
                    }
                }
            }
            //si on a trouvé une route à prendre
            //sinon on pioche des cartes
            checkFinPartie();
            currentPlayer = model.nextPlayer();
            view.updateView(model, this);
            view.getPlayerView().updateCard(currentPlayer);
            view.repaint();
            playIA();
        }
    }

    public List<GraphPath<City, DefaultEdge>> sortGraph (List<GraphPath<City, DefaultEdge>> g){
        List<GraphPath<City, DefaultEdge>> nList = new ArrayList<>();
        int max = 10000;
        int indice = 0;
        int count = 0;
        int taille = g.size();
        while (count != taille) {
            for (int i = 0; i < g.size(); i++) {
                GraphPath<City, DefaultEdge> path = g.get(i);
                List<City> listCities = path.getVertexList();
                if (max > listCities.size()) {
                    max = listCities.size();
                    indice = i;
                }
            }
            max = 10000;
            nList.add(g.get(indice));
            g.remove(indice);
            indice = 0;
            count++;
        }
        return nList;
    }

    /**
     * try to get a given route
     *
     * @param desiredRoute
     */
    public void tryGetRoute(Route desiredRoute){
        if (desiredRoute.isTunel()) {
            //si la route n'a pas de couleur
            if (desiredRoute.getColor() == Color.GRAY) {

                Color choixColor = currentPlayer.getOccurenceMaxColor();

                desiredRoute.takeTunnel(choixColor, model, currentPlayer);
            }
            //si la route a une couleur
            else {
                desiredRoute.takeTunnel(desiredRoute.getColor(), model, currentPlayer);
            }
        }
        //si c'est pas un tunnel
        else {
            //si c'est un ferrie
            if (desiredRoute.getColor() == Color.GRAY) {

                Color choixColor = currentPlayer.getOccurenceMaxColor();

                //si le joueur a pas pu prendre le ferrie
                if (desiredRoute.takeFerrie(choixColor, model, currentPlayer) == -1) {
                    currentAction = 0;
                    choixCity1.setEnabled(true);
                    choixCity2.setEnabled(true);
                    choixCity1 = null;
                    choixCity2 = null;
                    System.out.println("Le joueur "+currentPlayer.getName()+" n'a pas pu prendre le ferrie");
                    return;
                }
            } else {
                //si le joueur a pas pu prendre la route
                if (desiredRoute.takeRoute(desiredRoute.getColor(), model, currentPlayer) == -1) {
                    currentAction = 0;
                    choixCity1.setEnabled(true);
                    choixCity2.setEnabled(true);
                    choixCity1 = null;
                    choixCity2 = null;
                    System.out.println("Le joueur "+currentPlayer.getName()+" n'a pas pu prendre la route");
                    return;
                }
            }
        }
    }

}
