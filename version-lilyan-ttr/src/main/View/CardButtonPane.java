package View;

import Model.Enum.Color;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CardButtonPane extends JButton {
    private static final long   serialVersionUID    = 1L;
    protected ImageIcon buffer;
    private int index;
    private Color couleur;

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
        buffer = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(name).getPath());

        //on modifie sa taille
        Image image = buffer.getImage();
        Image newimg = image.getScaledInstance(105, 175,  java.awt.Image.SCALE_SMOOTH);
        buffer = new ImageIcon(newimg);

        setIcon(buffer);
    }

}
