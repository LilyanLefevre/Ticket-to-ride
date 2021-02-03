package Model.Enum;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

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
     * fonction qui permet de saisir une couleur existante
     *
     * @return une Color existante
     */
    public static Color saisieColor(){
        String choix = "";
        Scanner entree =   new Scanner(System.in);

        try{
            choix = entree.next();
        }catch (InputMismatchException e){
            entree.next();
        }
        //verif de la saisie
        int exist = 0;
        for(Color c : Color.values()) {
            try {
                if (c.toString().equals(choix)) {
                    exist = 1;
                }
            }catch(IllegalArgumentException e){};
        }
        while(exist != 1) {
            System.out.println("Erreur : cette couleur n'existe pas. Veuillez réessayer : ");
            try {
                choix = entree.next();
            } catch (InputMismatchException e) {
                entree.next();
            }
            for(Color c : Color.values()) {
                try {
                    if (c.toString().equals(choix)) {
                        exist = 1;
                    }
                }catch(IllegalArgumentException e){};
            }
        }
        return Color.valueOf(choix);
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

    /**
     * fonction retournant une couleur au hasard
     *
     * @return Color la couleur tirée au hasard
     */
    public Color aleaColor(){
        HashMap<Integer, Color> randomColor = new HashMap<>();
        randomColor.put(0,Color.WHITE);
        randomColor.put(1,Color.RED);
        randomColor.put(2,Color.BLACK);
        randomColor.put(3,Color.BLUE);
        randomColor.put(4,Color.GREEN);
        randomColor.put(5,Color.ORANGE);
        randomColor.put(6,Color.PURPLE);
        randomColor.put(7,Color.YELLOW);
        randomColor.put(8,Color.GRAY);

        Random random = new Random();
        int r = 0+random.nextInt(9-0);

        return randomColor.get(r);
    }
}
