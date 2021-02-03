package Model.GameElements;

import java.util.Objects;
import java.util.Random;

/**
 * classe qui représente des coordonnées (x,y)
 */
public class Coordonnees {
    final private int x;
    final private int y;

    public Coordonnees(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * constructeur qui génère des coordonnées aléatoire entre 0 et 19
     */
    public Coordonnees(){
        Coordonnees tmp = genererCoordonnees(0,19);
        this.x = tmp.getX();
        this.y = tmp.getY();
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

        Coordonnees that = (Coordonnees) o;
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
    public Coordonnees genererCoordonnees(int borneInf, int borneSup){
        int cx,cy;
        Random random = new Random();
        cx = borneInf+random.nextInt(borneSup-borneInf);
        cy = borneInf+random.nextInt(borneSup-borneInf);
        return new Coordonnees(cx,cy);
    }

    /**
     * fonction qui calcule la distance entre les coordonées courantes et données
     *
     * @param x Coordonees du deuxième point
     *
     * @return double la distance entre les deux
     */
    public double distance(Coordonnees x) {
        return Math.sqrt(Math.pow((x.getY() - getY()),2) + Math.pow((x.getX() - getX()),2));
    }
}
