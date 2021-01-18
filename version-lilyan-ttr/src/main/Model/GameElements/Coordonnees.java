package Model.GameElements;

import java.util.Objects;
import java.util.Random;

public class Coordonnees {
    final private int x;
    final private int y;

    public Coordonnees(int x, int y) {
        this.x = x;
        this.y = y;
    }
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

    public Coordonnees genererCoordonnees(int borneInf, int borneSup){
        int cx,cy;
        Random random = new Random();
        cx = borneInf+random.nextInt(borneSup-borneInf);
        cy = borneInf+random.nextInt(borneSup-borneInf);
        return new Coordonnees(cx,cy);
    }

}
