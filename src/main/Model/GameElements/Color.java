package Model.GameElements;

/**
 * classe qui représente un nombre limité de couleurs
 * qui composent le jeu
 */
public enum Color {
    RED,
    BLACK,
    BLUE,
    GREEN,
    RAINBOW,
    ORANGE,
    PURPLE,
    WHITE,
    YELLOW,
    GRAY;

    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return this.name();
    }


    /**
     * fonction qui retourne une couleur utilisable par awt
     * depuis une couleur du jeu
     *
     * @param co Color la couleur à convertir en couleur awt
     *
     * @return une couleur java.awt.Color correspondante
     */
    public static java.awt.Color getAwtColor(Color co){
        java.awt.Color c;
        //on met la bonne couleur
        switch (co) {
            case RED:
                c = java.awt.Color.red;
                break;
            case BLACK:
                c = java.awt.Color.black;
                break;
            case BLUE:
                c = java.awt.Color.blue;
                break;
            case GREEN:
                c = java.awt.Color.green;
                break;
            case WHITE:
                c = java.awt.Color.white;
                break;
            case ORANGE:
                c = java.awt.Color.orange;
                break;
            case PURPLE:
                c = java.awt.Color.MAGENTA;
                break;
            case YELLOW:
                c = java.awt.Color.yellow;
                break;
            case GRAY:
                c = java.awt.Color.gray;
                break;
            default:
                throw new IllegalStateException("Unexpected color");
        }
        return c;
    }
}
