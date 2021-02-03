package View;

import javax.swing.*;
import java.awt.*;

/**
 * classe qui représente une image de carte non clickable
 */
public class CardImagePane extends JLabel {
    protected ImageIcon buffer;


    public CardImagePane(String name, final int nb){

        setPreferredSize(new Dimension(120,210));
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);

        //on récupère l'image du wagon de la bonne couleur
        buffer = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(name).getPath());

        //on modifie sa taille
        Image image = buffer.getImage();
        Image newimg = image.getScaledInstance(110, 190,  java.awt.Image.SCALE_SMOOTH);
        buffer = new ImageIcon(newimg);

        //on met l'image et son nb d'occurence
        setIcon(buffer);
        setText(""+nb);


    }

}
