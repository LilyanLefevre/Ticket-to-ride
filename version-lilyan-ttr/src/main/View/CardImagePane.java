package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CardImagePane extends JPanel {
    private static final long   serialVersionUID    = 1L;
    protected Image buffer;
    private int index;
    private Model.Enum.Color couleur;

    public CardImagePane(String name, int i){
        couleur = null;
        index = i;
        setPreferredSize(new Dimension(100,190));
        setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

        try {
            buffer = ImageIO.read(new File(Thread.currentThread().getContextClassLoader().getResource(name).getPath()));
        }
        catch(IOException exc) {
            exc.printStackTrace();
        }

    }
    public CardImagePane(String name, Model.Enum.Color c){
        couleur = c;
        index = -1;
        setBackground(new Color(0,0,0,64));
        setPreferredSize(new Dimension(100,190));
        setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

        try {
            buffer = ImageIO.read(new File(Thread.currentThread().getContextClassLoader().getResource(name).getPath()));
        }
        catch(IOException exc) {
            exc.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer,0,0,getWidth(),getHeight(),this);
    }
}
