package Model.GameElements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * classe qui représente une carte destination
 */
public class DestinationCard {
    private final City dest1;
    private final City dest2;
    private final int points;

    public DestinationCard(City from, City to, int points) {
        this.dest1 = from;
        this.dest2 = to;
        this.points = points;
    }

    public City getDest1() {
        return dest1;
    }

    public City getDest2() {
        return dest2;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Answers a string containing a concise, human-readable
     * description of the receiver.
     *
     * @return String a printable representation for the receiver.
     */
    @Override
    public String toString() {
        return "    From "+ dest1 +" to "+ dest2 +". It worth "+points+" points.\n";
    }

    /**
     * fonction qui génère des cartes destination
     *
     * @param d l'ensemble de destinations existantes
     *
     * @return un tableau de DestinationCard
     */
    public static ArrayList genererCarteDestination(Destinations d){
        ArrayList<DestinationCard> ret = new ArrayList<>();

        //on créer une carte objectif pour chaque ville
        for (Map.Entry from : d.getDestinations().entrySet()){

            //on récupère les noms de toutes les villes
            List<String> keysAsArray = new ArrayList<String>(d.getDestinations().keySet());

            //on tire un entier au hasard et on prend la ville correspondante
            Random r = new Random();
            City to = d.getDestinations().get(keysAsArray.get(r.nextInt(keysAsArray.size())));

            //on initialise une carte destination
            DestinationCard d1 = new DestinationCard ((City)from.getValue(),to,2);

            //on vérifie qu'on créé pas un objectif reliant deux fois la meme ville
            //ou qu'on crée pas deux fois la meme carte
            while (ret.contains(d1) || from.getValue() == to){
                to = d.getDestinations().get(keysAsArray.get(r.nextInt(keysAsArray.size())));
                d1 = new DestinationCard ((City)from.getValue(),to,2);
            }

            //on va calculer la distance entre les deux villes trouvées
            Coordonnees v1 = ((City) from.getValue()).getCoordonnees();
            Coordonnees v2 = to.getCoordonnees();
            double distance1 = v1.distance(v2);

            //on met un nb de point selon la distance entre les deux villes
            int points = 0;
            if(distance1<6) {
                points = 5 + r.nextInt(9 - 5);
            }
            if(distance1>=6 && distance1<10) {
                points = 9 + r.nextInt(13 - 9);
            }
            if(distance1>=10 && distance1<15) {
                points = 13 + r.nextInt(18 - 13);
            }
            if(distance1>=15) {
                points = 17 + r.nextInt(22 - 17);
            }
            //on ajoute au tableau la carte créée
            ret.add(new DestinationCard((City)from.getValue(),to,points));
        }

        return ret;
    }
}
