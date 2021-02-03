package Model.GameElements;
import Model.Game;

import java.awt.Color;
import java.util.ArrayList;

/**
 * classe représentant un joueur du jeu
 */
public class Player {
    private final String name; //son nom
    private ArrayList<Route> routesEmpruntes; //les routes qu'il possède
    private ArrayList<WagonCard> wCards; //ses cartes wagons
    private ArrayList<DestinationCard> dCards; //ses cartes destination
    private int points; //ses points
    private int wagons; //son nombre de wagons restants
    private final Color color; //sa couleur

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getWagons() {
        return wagons;
    }

    public ArrayList<Route> getRoutesEmpruntes(){return routesEmpruntes;}

    public ArrayList<WagonCard> getwCards() {
        return wCards;
    }

    public ArrayList<DestinationCard> getdCards() {
        return dCards;
    }

    public Color getColor() {
        return color;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setWagons(int wagons) {
        this.wagons = wagons;
    }

    public Player(String name, Color color, ArrayList<DestinationCard> dc, ArrayList<WagonCard> tc) {
        this.name = name;
        this.color = color;
        points = 0;
        wagons = 45;
        wCards = new ArrayList<>();
        dCards = new ArrayList<>();
        routesEmpruntes = new ArrayList<>();

        // tire 4 cartes destination au hasard
        for(int i = 0 ; i < 4; i++){
            drawTrainCard(tc);
        }

        // tire 4 cartes colorées au hasard
        for(int i = 0 ; i < 4; i++){
            drawDestinationCard(dc);
        }
    }

    /**
     * Answers a string containing a concise, human-readable
     * description of the receiver.
     *
     * @return String a printable representation for the receiver.
     */
    @Override
    public String toString() {
        String dc = new String();
        dc = "";
        for (int i =0; i < dCards.size(); i++) {
            dc += dCards.get(i).toString();
        }
        String tc = new String();
        tc = "";
        for (int i =0; i < wCards.size(); i++) {
            tc += wCards.get(i).toString();
        }
        return "\n"+name+":\nDestination cards :\n"+dc+"Train cards :\n"+tc+"Color : "+color+", points = "+points+", wagons = "+wagons+"\n";
    }


    /**
     * fonction qui ajoute une route au tableau de routes empruntées par le joueur
     *
     * @param r1 Route la route à ajouter
     */
    public void addRoute(Route r1){
        this.routesEmpruntes.add(r1);
    }

    /**
     * fonction qui ajoute une carte wagon à l'ensemble de carte wagon du joueur
     *
     * @param tc WagonCard la carte à ajouter
     */
    public void addTrainCard(WagonCard tc){
        wCards.add(tc);
    }

    /**
     * fonction qui ajoute une carte destination à l'ensemble de carte destination du joueur
     *
     * @param dc DestinationCard la carte à ajouter
     */
    public void addDestinationCard(DestinationCard dc){
        dCards.add(dc);
    }

    /**
     * fonction qui permet au joueur de piocher une carte wagon dans la pioche et la retourne
     *
     * @param tc l'ensemble de cartes wagon qui composent la pioche
     *
     * @return une carte tirée au hasard dedans
     */
    public WagonCard drawTrainCard(ArrayList<WagonCard> tc){
        int nCard = (int)(Math.random() * (tc.size()));

        WagonCard tmp = tc.get(nCard);
        // on ajoute la carte tirée dans le jeu du joueur
        wCards.add(tc.get(nCard));

        // et on la retire de la pioche
        tc.remove(nCard);

        return tmp;
    }

    /**
     * fonction qui permet au joueur de piocher une carte destination dans la pioche et la retourne
     *
     * @param dc l'ensemble de cartes destination qui composent la pioche
     *
     * @return une carte tirée au hasard dedans
     */
    public DestinationCard drawDestinationCard(ArrayList<DestinationCard> dc){
        int nCard = (int)(Math.random() * (dc.size()));

        DestinationCard tmp = dc.get(nCard);

        // on ajoute la carte tirée dans le jeu du joueur
        dCards.add(dc.get(nCard));

        // et on la retire de la pioche
        dc.remove(nCard);
        return tmp;
    }


    /**
     * fonction qui permet de compter le nombre de carte d'une certaine couleur
     * en incluant les locos.
     * @param c la couleur voulue
     * @return un entier représentant le nombre de carte de la bonne couleur
     */
    public int countOccurencesOf(Model.Enum.Color c){
        int i = 0;

        //compte le nombre de carte de la couleur donnée et locomotives
        for(WagonCard t : wCards){
            if(t.getColor() == c || t.getColor() == Model.Enum.Color.RAINBOW){
                i++;
            }
        }
        return i;
    }
    /**
     * fonction qui permet de compter le nombre de carte d'une certaine couleur
     * en incluant les locos.
     * @param c la couleur voulue
     * @return un entier représentant le nombre de carte de la bonne couleur
     */
    public int countWithoutRainbowOccurencesOf(Model.Enum.Color c ){
        int i = 0;

        //compte le nombre de carte de la couleur donnée et locomotives
        for(WagonCard t : wCards){
            if(t.getColor() == c){
                i++;
            }
        }
        return i;
    }

    /**
     * fonction qui supprime nb carte de couleur c dans les cartes Wagon du joueur
     * et les replace dans la pioche
     *
     * @param c la couleur des cartes a retirer
     * @param nb le nombre de carte a retirer
     * @return le nombre de carte retiré
     */
    public int removeTrainCards(Model.Enum.Color c, int nb, Game g){
        int i = 0;
        ArrayList<WagonCard> toRemove = new ArrayList<>();
        for(WagonCard t : wCards){
            if(i < nb) {
                if (t.getColor() == c) {
                    toRemove.add(t);
                    i++;
                }
            }
        }

        int ret = toRemove.size();
        wCards.removeAll(toRemove);

        //on les replace dans la pioche
        for(WagonCard t : toRemove){
            int nCard = (int)(Math.random() * (g.getDrawWagonCards().size()));
            g.getDrawWagonCards().add(nCard,t);
        }

        return ret;
    }
}
