package GameElements;

import Enum.*;
import model.Game;
import sun.security.krb5.internal.crypto.Des;

import java.util.ArrayList;

public class TrainStations {
    ArrayList<TrainStation> tS;

    public TrainStations() {
        this.tS = new ArrayList<>();
    }

    public boolean isAlreadyUsed(Destination dest){
        for(TrainStation t : tS){
            if(t.getCity().toString() == dest.toString()){
                return true;
            }
        }
        return false;
    }

    public int addStation(Destination dest, Player p, Game g){
        System.out.println("Saisissez une couleur de carte Wagon pour poser une gare :");
        Color couleur = Color.saisieColor();
        int nbCard = howManyCardToTakeStation(p);
        if(p.countOccurencesOf(couleur) < nbCard){
            System.out.println("Erreur : vous n'avez pas assez de carte de couleur "+couleur+" pour poser une gare. Il vous en manque "+(nbCard-p.countOccurencesOf(couleur))+".");
            return -1;
        }else{
            System.out.println("Vous avez posé une gare à "+dest+", vous avez utilisé "+nbCard+" carte(s) "+couleur+".");
            p.removeTrainCards(couleur,nbCard,g);
            p.setNbTrainStation(p.getNbTrainStation()-1);
            this.tS.add(new TrainStation(dest,p));
            return 0;
        }
    }

    private int howManyCardToTakeStation(Player p){
        switch(p.getNbTrainStation()){
            case 3:
                return 1;
            case 2:
                return 2;
            case 1:
                return 3;
            default:
                throw new IllegalStateException("Unexpected value: " + p.getNbTrainStation());
        }
    }
}
