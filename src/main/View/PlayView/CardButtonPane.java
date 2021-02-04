package View.PlayView;

import Model.Enum.Color;

import javax.swing.*;
import java.awt.*;

/**
 * classe qui représente un bouton avec un image de carte en fond
 */
public class CardButtonPane extends JButton {
    protected ImageIcon buffer; // l'image
    private int index; //l'indexe de la carte représenté dans son ensemble source
    private Color couleur; // la couleur de la carte

    public Color getCouleur() {
        return couleur;
    }

    public int getIndex() {
        return index;
    }

    public CardButtonPane(String name, int i, Color c){
        index = i;
        couleur = c;
        setPreferredSize(new Dimension(120,190));
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);

        //on récupère l'image du wagon de la bonne couleur
        buffer = new ImageIcon(getClass().getResource(name));

        //on modifie sa taille
        Image image = buffer.getImage();
        Image newimg = image.getScaledInstance(105, 175,  java.awt.Image.SCALE_SMOOTH);
        buffer = new ImageIcon(newimg);

        setIcon(buffer);
    }

}
