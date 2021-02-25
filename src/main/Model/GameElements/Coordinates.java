package Model.GameElements;

import java.util.Objects;
import java.util.Random;

/**
 * classe qui représente des coordonnées (x,y)
 */
public class Coordinates {
    final private int x;
    final private int y;
    private Random random;


    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * constructeur qui génère des coordonnées aléatoire entre 0 et 19
     */
    public Coordinates(Random r){
        Coordinates tmp = generateCoordinates(0,19, r);
        this.x = tmp.getX();
        this.y = tmp.getY();
        this.random = r;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Coordinates that = (Coordinates) o;
        return (x == that.x && y == that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * fonction qui génère des coordonnées dans [borneInf,borneSup]
     *
     * @param borneInf int la borne inférieure
     * @param borneSup int la borne supérieure
     *
     * @return Coordonnees les coordonnées générées
     */
    public Coordinates generateCoordinates(int borneInf, int borneSup, Random random){
        int cx,cy;
        cx = borneInf+random.nextInt(borneSup-borneInf);
        cy = borneInf+random.nextInt(borneSup-borneInf);
        return new Coordinates(cx,cy);
    }

    /**
     * fonction qui calcule la distance entre les coordonées courantes et données
     *
     * @param x Coordonees du deuxième point
     *
     * @return double la distance entre les deux
     */
    public double distance(Coordinates x) {
        return Math.sqrt(Math.pow((x.getY() - getY()),2) + Math.pow((x.getX() - getX()),2));
    }
}
