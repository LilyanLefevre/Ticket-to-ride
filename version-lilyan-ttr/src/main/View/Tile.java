package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/* classe qui représente une tuile du tableau */
public class Tile extends JPanel {

    public Tile() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                colorTile();
            }
        });
    }

    /* modifie la taille par défaut de la grille utilisée */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(10, 10);
    }

    /* change la couleur des tuiles quand on appuie dessus */
    protected void colorTile() {
        if (getBackground() != Color.RED) {
            setBackground(Color.RED);
        } else {
            setBackground(null);
        }
    }
}