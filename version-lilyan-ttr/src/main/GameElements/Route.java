package GameElements;
import Enum.*;
import model.*;

import java.util.ArrayList;

public class Route {
    //représentent les deux destination réliées par cette route
    /*private final Destination dest1;
    private final Destination dest2;*/

    //représente le nombre de wagons que nécessite cette route pour poser ses wagons dessus
    private final int require;

    //représente la couleur des wagons nécessaire pour poser ses wagons dessus
    private final Color color;

    //booleen pour dire si cette route est un tunel
    private final boolean isTunel;

    //booleen pour dire si cette route necessite des locomotives dans le cas des routes ferries
    private final int locomotive;

    //enregistre le joueur qui a éventuellement posé ses wagons dessus
    private Player hasPlayerOn;

    public Route(/*Destination from, Destination to, */int require, Color color, boolean isTunel, int locomotive) {
        /*this.dest1 = from;
        this.dest2 = to;*/
        this.require = require;
        this.color = color;
        this.isTunel = isTunel;
        this.locomotive = locomotive;
        this.hasPlayerOn = null;
    }
    /*
    public Destination getFrom() {
        return dest1;
    }

    public Destination getTo() {
        return dest2;
    }*/

    public int getRequire() {
        return require;
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

    /**
     * tells if there is somebody on this route
     *
     * @return true if there is somebody on, false otherwise
     */
    public boolean isAlreadyTakenRoute(){
        return hasPlayerOn != null;
    }

    public void setPlayer(Player p) {
        this.hasPlayerOn = p;
    }

    /**
     * Answers a string containing a concise, human-readable
     * description of the receiver.
     *
     * @return String a printable representation for the receiver.
     */
    @Override
    public String toString() {
        return "with "+locomotive+" locomotive and isTunel = "+isTunel+". It requires "+require+" "+color.toString()+" wagons.\n";
    }

    public int getTunnel(Player p, Color c, Game g){
        //on tire trois cartes de la pioche
        ArrayList<TrainCard> tctmp = new ArrayList<>();
        int k = 0;
        for( int i = 0; i < 3; i++){
            TrainCard tmp = g.drawTrainCard();
            System.out.println(tmp);
            if(tmp.getColor() == c || tmp.getColor() == Color.RAINBOW){
                k++;
            }
            tctmp.add(tmp);
        }

        if( k > 0){
            //si le joueur n'a pas assez de cartes on s'arrete la
            if(p.countOccurencesOf(c) < this.getRequire()+k){
                System.out.println("Vous devez rajouter "+k+" carte(s) mais vous n'en avez pas assez. Raté !");
                return 0;
            }else{
                System.out.println("Vous devez rajouter "+k+" carte(s). Acceptez-vous ? (O/N)");
                boolean accept = g.saisieOuiNon();

                if(accept){
                    //on enregistre le joueur sur la route
                    this.setPlayer(p);

                    //on retire les cartes de couleur jouées ainsi que les éventuelles locos qui ont été jouées
                    int nbRemovedCard = p.removeTrainCards(c, this.getRequire()+k, g);
                    if(nbRemovedCard < this.getRequire()+k){
                        p.removeTrainCards(Color.RAINBOW, (this.getRequire()+k) - nbRemovedCard, g);
                    }
                    System.out.println("Vous possédez désormais la route "+this);
                    p.setWagons(p.getWagons()-this.getRequire());
                    p.setPoints(p.getPoints()+howManyPointsFor(this.getRequire()));
                    return 0;
                }
                return -1;
            }

        }else{
            //on enregistre le joueur sur la route
            this.setPlayer(p);

            //on retire les cartes de couleur jouées ainsi que les éventuelles locos qui ont été jouées
            int nbRemovedCard = p.removeTrainCards(c, this.getRequire()+k, g);
            if(nbRemovedCard < this.getRequire()+k){
                p.removeTrainCards(Color.RAINBOW, (this.getRequire()+k) - nbRemovedCard, g);
            }

            System.out.println("Vous n'avez pas besoin de rajouter de cartes, bravo!\nVous possédez désormais la route "+this);
            p.setWagons(p.getWagons()-this.getRequire());
            p.setPoints(p.getPoints()+howManyPointsFor(this.getRequire()));
            return 0;
        }
    }

    public int getRoute(Player p, Color c, Game g){
        //si le joueur n'a pas assez de cartes on s'arrete la
        if(p.countOccurencesOf(c) < this.getRequire()){
            System.out.println("Vous n'avez pas assez de cartes pour posséder cette route.");
            return -1;
        }else {
            //on enregistre le joueur sur la route
            this.setPlayer(p);

            //on retire les cartes de couleur jouées ainsi que les éventuelles locos qui ont été jouées
            int nbRemovedCard = p.removeTrainCards(c, this.getRequire(), g);
            if (nbRemovedCard < this.getRequire()) {
                p.removeTrainCards(Color.RAINBOW, (this.getRequire()) - nbRemovedCard, g);
            }
            System.out.println("Vous possédez désormais la route " + this);
            p.setWagons(p.getWagons()-this.getRequire());
            p.setPoints(p.getPoints()+howManyPointsFor(this.getRequire()));
            return 0;
        }
    }
    public int getFerrie(Player p, Color c, Game g){
        Player tmp = p;

        //on verifie qu'on a assez de locomotives
        int nbLocos = p.countOccurencesOf(Color.RAINBOW);
        if(getLocomotive() > 0) {
            if (nbLocos < this.getLocomotive()) {
                System.out.println("Vous n'avez pas assez de locomotive pour posséder ce ferrie.");
                return -1;
            }
        }
        //on verifie qu'on peut poser assez de cartes autres que les locos obligatoires
        if(p.countOccurencesOf(c) < getRequire()-getLocomotive()){
            System.out.println("Vous n'avez pas assez de cartes de couleur "+c+"  pour posséder ce ferrie.");
            return -1;
        }else {
            //on vérifie si les locos a poser obligatoirement ne font pas partie des cartes pour
            //poser le reste du chemin
            int nbCard = p.countWithoutRainbowOccurencesOf(c);
            nbCard +=nbLocos;

            //si on a assez de cartes loco plus d'autres cartes de couleur alors on peut prendre la route
            if (nbCard >= getRequire()) {
                this.setPlayer(p);

                //on retire les cartes de couleur c et les eventuelles cartes loco. qui ont completé le nombre de carte a avoir
                int nbRemovedCard = p.removeTrainCards(c, this.getRequire() - getLocomotive(), g);
                if (nbRemovedCard < this.getRequire() - getLocomotive()) {
                    p.removeTrainCards(Color.RAINBOW, (this.getRequire() - getLocomotive()) - nbRemovedCard, g);
                }

                //on retire le nombre de carte loco qu'on devait avoir pour prendre cette route
                p.removeTrainCards(Color.RAINBOW, getLocomotive(), g);
                System.out.println("Vous possédez désormais la route " + this);
                p.setWagons(p.getWagons()-this.getRequire());
                p.setPoints(p.getPoints()+howManyPointsFor(this.getRequire()));
                return 0;
            } else {
                System.out.println("Vous n'avez pas assez de Locomotives pour prendre cette route.");
                return -1;
            }
        }
    }

    /**
     * convertit un nombre de wagons en nombre de points
     * @param nb
     * @return la valeur en points de nb wagons
     */
    public int howManyPointsFor(int nb){
        switch(nb) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 7;
            case 6:
                return 15;
            case 8:
                return 21;
            default:
                throw new IllegalStateException("Unexpected value: " + nb);
        }
    }

}
