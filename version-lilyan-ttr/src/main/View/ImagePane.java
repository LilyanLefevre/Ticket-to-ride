package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImagePane extends JPanel {
    private static final long   serialVersionUID    = 1L;
    protected Image buffer;

    public ImagePane(String name){
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
        g.drawImage(buffer,0,0,null);
    }
}
