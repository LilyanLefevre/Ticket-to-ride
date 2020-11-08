package Enum;

import java.util.InputMismatchException;
import java.util.Scanner;

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
    GREY;

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
}
