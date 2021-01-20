package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/* classe qui représente une ville du tableau */
public class CityTile extends JPanel {

    public CityTile() {
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
        return new Dimension(80, 20);
    }

    /* change la couleur des tuiles quand on appuie dessus */
    protected void colorTile() {
        if (getBackground() != Color.RED) {
            setBackground(Color.RED);
        } else {
            setBackground(Color.WHITE);
        }
    }
}
