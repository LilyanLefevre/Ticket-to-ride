package Model.GameElements;
import Model.Game;

import javax.swing.*;
import java.util.ArrayList;

/**
 * classe qui représente une route dans le jeu
 */
public class Route implements Comparable{
    //représentent les deux destination réliées par cette route
    private final City city1;
    private final City city2;

    //représente le nombre de wagons que nécessite cette route pour poser ses wagons dessus
    private final int require;

    //représente la couleur des wagons nécessaire pour poser ses wagons dessus
    private final Color color;

    //booleen pour dire si cette route est un tunel
    private final boolean isTunel;

    //booleen pour dire si cette route necessite des locomotives dans le cas des routes ferries
    private int locomotive;

    //enregistre le joueur qui a éventuellement posé ses wagons dessus
    private Player playerOn;

    //enregistre le nombre de fois où on utilise la route pour rejoindre deux villes
    private int frequencyOfUse;

    public Route(City from, City to, int require, Color color, boolean isTunel, int locomotive) {
        this.city1 = from;
        this.city2 = to;
        this.require = require;
        this.color = color;
        this.isTunel = isTunel;
        this.locomotive = locomotive;
        this.playerOn = null;
        this.frequencyOfUse = 0;
    }

    public int getRequire() {
        return require;
    }
    public int getFrequencyOfUse() {
        return frequencyOfUse;
    }
    public City getCity1() {
        return city1;
    }
    public City getCity2() {
        return city2;
    }
    public Color getColor() {
        return color;
    }
    public boolean isTunel() {
        return isTunel;
    }
    public int getLocomotive() {
        return locomotive;
    }
    public Player getPlayer() {
        return playerOn;
    }
    public void setPlayer(Player p) {
        this.playerOn = p;
    }
    public void setFrequencyOfUse(int frequencyOfUse) {
        this.frequencyOfUse = frequencyOfUse;
    }

    public void setLocomotive(int i) {
        this.locomotive = i;
    }


    /**
     * tells if there is somebody on this route
     *
     * @return true if there is somebody on, false otherwise
     */
    public boolean isAlreadyTakenRoute(){
        return playerOn != null;
    }


    /**
     * Answers a string containing a concise, human-readable
     * description of the receiver.
     *
     * @return String a printable representation for the receiver.
     */
    @Override
    public String toString() {
        if(isTunel) {
            return "avec " + locomotive + " locomotive(s). C'est un tunnel. Elle nécessite " + require + " carte(s) wagon " + color.toString() + ".\n";
        }
        return "avec " + locomotive + " locomotive(s). Elle nécessite " + require + " carte(s) wagon " + color.toString() + ".\n";
    }


    @Override
    public int compareTo(Object o) {
        return ((Route)o).getFrequencyOfUse() - this.getFrequencyOfUse();
    }

    /**
     * fonction qui permet de prendre un tunnel graphiquement
     * @param c Color la couleur désirée
     *
     */
    public int takeTunnel(Color c, Game model, Player currentPlayer){
        //on tire trois cartes de la pioche
        ArrayList<WagonCard> tctmp = new ArrayList<>();

        //si le joueur n'a pas assez de carte de couleur sans rajout on s'arrete la
        if(currentPlayer.countOccurencesOf(c) < this.getRequire()){
            int input = JOptionPane.showOptionDialog(null, "Vous n'avez pas assez de carte "+c+".", "Prendre une route", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, new String[]{"OK"}, "OK");
            return -1;
        }
        int k = 0;
        System.out.println("Le joueur "+currentPlayer.getName()+" veut prendre un tunnel et tire les cartes suivantes : " );
        for( int i = 0; i < 3; i++){
            if(model.getWagonCardsDraw().size() > 0) {
                WagonCard tmp = model.drawTrainCard();
                System.out.print(tmp);
                if (tmp.getColor() == c || tmp.getColor() == Color.LOCOMOTIVE) {
                    k++;
                }
                tctmp.add(tmp);
            }
        }

        if( k > 0){
            //si le joueur n'a pas assez de cartes on s'arrete la
            if(currentPlayer.countOccurencesOf(c) < this.getRequire()+k){
                if(currentPlayer.getLevel() == 0) {
                    int input = JOptionPane.showOptionDialog(null, "Vous n'avez pas assez de carte "
                                    + c + ", il en fallait " + (this.getRequire() + k), "Prendre une route",JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, new String[]{"OK"}, "OK");
                }
                System.out.println("Le joueur "+currentPlayer.getName()+" n'a pas pu prendre le tunnel de "+this.getCity1().getName()+" à "+this.getCity2().getName());
            }else{

                int accept = JOptionPane.YES_OPTION;
                if(currentPlayer.getLevel() == 0) {
                    accept = JOptionPane.showConfirmDialog(null, "Vous devez rajouter " + k + " carte(s)." +
                                    " Acceptez-vous ?", "Prendre une route", JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE);
                }

                if(accept == JOptionPane.YES_OPTION){
                    //on enregistre le joueur sur la route
                    this.setPlayer(currentPlayer);

                    //on retire les cartes de couleur jouées ainsi que les éventuelles locos qui ont été jouées
                    int nbRemovedCard = currentPlayer.removeWagonCards(c, this.getRequire()+k, model);
                    if(nbRemovedCard < this.getRequire()+k){
                        currentPlayer.removeWagonCards(Color.LOCOMOTIVE, (this.getRequire()+k) - nbRemovedCard, model);
                    }

                    currentPlayer.setWagons(currentPlayer.getWagons()-this.getRequire());
                    currentPlayer.setPoints(currentPlayer.getPoints()+this.howManyPointsFor(this.getRequire()));
                    currentPlayer.addRoute(this);
                    Route inv = new Route(this.getCity2(),this.getCity1(), this.getRequire(), this.getColor(),this.isTunel(),this.getLocomotive());
                    currentPlayer.addRoute(inv);
                    if(currentPlayer.getLevel() == 0) {
                        JOptionPane.showOptionDialog(null, "Vous possédez désormais la route " + this,
                                "Prendre une route", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, new String[]{"OK"}, "OK");
                    }
                    System.out.println("Le joueur "+currentPlayer.getName()+" a rajouté des cartes pour prendre le tunnel de "+this.getCity1().getName()+" à "+this.getCity2().getName());
                }else{
                    System.out.println("Le joueur "+currentPlayer.getName()+" n'a pas voulu rajouter des cartes pour prendre le tunnel de "+this.getCity1().getName()+" à "+this.getCity2().getName());
                }
            }

        }else{
            //on enregistre le joueur sur la route
            this.setPlayer(currentPlayer);

            //on retire les cartes de couleur jouées ainsi que les éventuelles locos qui ont été jouées
            int nbRemovedCard = currentPlayer.removeWagonCards(c, this.getRequire()+k, model);
            if(nbRemovedCard < this.getRequire()+k){
                currentPlayer.removeWagonCards(Color.LOCOMOTIVE, (this.getRequire()+k) - nbRemovedCard, model);
            }

            //on change les stats du joueur
            currentPlayer.setWagons(currentPlayer.getWagons()-this.getRequire());
            currentPlayer.setPoints(currentPlayer.getPoints()+this.howManyPointsFor(this.getRequire()));
            currentPlayer.addRoute(this);
            Route inv = new Route(this.getCity2(),this.getCity1(), this.getRequire(), this.getColor(),this.isTunel(),this.getLocomotive());
            currentPlayer.addRoute(inv);
            if(currentPlayer.getLevel() == 0) {
                JOptionPane.showOptionDialog(null, "Vous n'avez pas besoin de rajouter de cartes. " +
                                "Vous possédez désormais la route " + this, "Prendre une route", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, new String[]{"OK"}, "OK");
            }
            System.out.println("Le joueur "+currentPlayer.getName()+" n'a pas eu besoin de rajouter des cartes pour prendre le tunnel de "+this.getCity1().getName()+" à "+this.getCity2().getName());
        }
        return 0;
    }
    /**
     * fonction qui permet de prendre un ferrie graphiquement
     * @param c Color la couleur désirée
     *
     * @return int -1 si le joueur ne pouvait prendre la route, 0 s'il l'a prise
     */
    public int takeFerrie(Color c, Game model, Player currentPlayer){

        //on verifie qu'on a assez de locomotives
        int nbLocos = currentPlayer.countOccurencesOf(Color.LOCOMOTIVE);
        if(this.getLocomotive() > 0) {
            if (nbLocos < this.getLocomotive()) {
                if(currentPlayer.getLevel() == 0) {
                    JOptionPane.showOptionDialog(null, "Vous n'avez pas assez de locomotive pour " +
                                    "posséder ce ferrie." + this, "Prendre une route", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, new String[]{"OK"}, "OK");
                }
                System.out.println("Le joueur "+currentPlayer.getName()+" n'a pas assez de carte pour prendre le ferrie de "+this.getCity1().getName()+" à "+this.getCity2().getName());
                return -1;
            }
        }
        //on verifie qu'on peut poser assez de cartes autres que les locos obligatoires
        if(currentPlayer.countOccurencesOf(c) < this.getRequire()-this.getLocomotive()){
            if(currentPlayer.getLevel() == 0) {
                JOptionPane.showOptionDialog(null, "Vous n'avez pas assez de cartes de couleur " + c +
                                "  pour posséder ce ferrie.", "Prendre une route", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, new String[]{"OK"}, "OK");
            }
            System.out.println("Le joueur "+currentPlayer.getName()+" n'a pas assez de carte de couleur pour prendre le ferrie de "+this.getCity1().getName()+" à "+this.getCity2().getName());
            return -1;
        }else {
            //on vérifie si les locos a poser obligatoirement ne font pas partie des cartes pour
            //poser le reste du chemin
            int nbCard = currentPlayer.countWithoutRainbowOccurencesOf(c);
            nbCard +=nbLocos;

            //si on a assez de cartes loco plus d'autres cartes de couleur alors on peut prendre la route
            if (nbCard >= this.getRequire()) {
                this.setPlayer(currentPlayer);

                //on retire les cartes de couleur c et les eventuelles cartes loco. qui ont completé le nombre de carte a avoir
                int nbRemovedCard = currentPlayer.removeWagonCards(c, this.getRequire() - this.getLocomotive(), model);
                if (nbRemovedCard < this.getRequire() - this.getLocomotive()) {
                    currentPlayer.removeWagonCards(Color.LOCOMOTIVE, (this.getRequire() - this.getLocomotive()) - nbRemovedCard, model);
                }

                //on retire le nombre de carte loco qu'on devait avoir pour prendre cette route
                currentPlayer.removeWagonCards(Color.LOCOMOTIVE, this.getLocomotive(), model);
                if(currentPlayer.getLevel() == 0) {
                    JOptionPane.showOptionDialog(null, "Vous possédez désormais la route " + this,
                            "Prendre une route", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, new String[]{"OK"}, "OK");
                }
                System.out.println("Le joueur "+currentPlayer.getName()+" a pris le ferrie de "+this.getCity1().getName()+" à "+this.getCity2().getName());
                currentPlayer.setWagons(currentPlayer.getWagons()-this.getRequire());
                currentPlayer.setPoints(currentPlayer.getPoints()+this.howManyPointsFor(this.getRequire()));
                currentPlayer.addRoute(this);
                Route inv = new Route(this.getCity2(),this.getCity1(), this.getRequire(), this.getColor(),this.isTunel(),this.getLocomotive());
                currentPlayer.addRoute(inv);
                return 0;
            } else {
                if(currentPlayer.getLevel() == 0) {
                    JOptionPane.showOptionDialog(null, "Vous n'avez pas assez de Locomotives pour " +
                                    "prendre cette route.", "Prendre une route", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, new String[]{"OK"}, "OK");
                }
                System.out.println("Le joueur "+currentPlayer.getName()+" n'a pas assez de Locomotives pour prendre le ferrie de "+this.getCity1().getName()+" à "+this.getCity2().getName());
                return -1;
            }
        }
    }

    /**
     * fonction qui permet de prendre une route normale graphiquement
     * @param c Color la couleur désirée
     *
     * @return int -1 si le joueur ne pouvait prendre la route, 0 s'il l'a prise
     */
    public int takeRoute(Color c, Game model, Player currentPlayer) {
        //si le joueur n'a pas assez de cartes on s'arrete la
        if(currentPlayer.countOccurencesOf(c) < this.getRequire()){
            if(currentPlayer.getLevel() == 0) {
                JOptionPane.showOptionDialog(null, "Vous n'avez pas assez de cartes pour posséder " +
                        "cette route.", "Prendre une route", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, new String[]{"OK"}, "OK");
            }
            System.out.println("Le joueur "+currentPlayer.getName()+" n'a pas assez de carte pour prendre la route de "+this.getCity1().getName()+" à "+this.getCity2().getName());
            return -1;
        }else {
            //on enregistre le joueur sur la route
            this.setPlayer(currentPlayer);

            //on retire les cartes de couleur jouées ainsi que les éventuelles locos qui ont été jouées
            int nbRemovedCard = currentPlayer.removeWagonCards(c, this.getRequire(), model);
            if (nbRemovedCard < this.getRequire()) {
                currentPlayer.removeWagonCards(Color.LOCOMOTIVE, (this.getRequire()) - nbRemovedCard, model);
            }
            if(currentPlayer.getLevel() == 0) {
                JOptionPane.showOptionDialog(null, "Vous possédez désormais la route " + this,
                        "Prendre une route", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, new String[]{"OK"}, "OK");
            }
            System.out.println("Le joueur "+currentPlayer.getName()+" a pris la route de "+this.getCity1().getName()+" à "+this.getCity2().getName());
            currentPlayer.setWagons(currentPlayer.getWagons()-this.getRequire());
            currentPlayer.setPoints(currentPlayer.getPoints()+this.howManyPointsFor(this.getRequire()));
            currentPlayer.addRoute(this);
            Route inv = new Route(this.getCity2(),this.getCity1(), this.getRequire(), this.getColor(),this.isTunel(),this.getLocomotive());
            currentPlayer.addRoute(inv);
            return 0;
        }
    }
    /**
     * convertit un nombre de wagons en nombre de points
     * @param nb
     * @return la valeur en points de nb wagons
     */
    public int howManyPointsFor(int nb) {
        switch (nb) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 7;
            case 5:
                return 10;
            case 6:
                return 15;
            case 8:
                return 21;
            default:
                throw new IllegalStateException("Unexpected value: " + nb);
        }
    }

}
