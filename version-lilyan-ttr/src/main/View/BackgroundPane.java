package View;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class BackgroundPane extends JPanel {

    private BufferedImage backgroundImage;

    public BackgroundPane(final String name){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    backgroundImage = ImageIO.read(new File(Thread.currentThread().getContextClassLoader().getResource(name).getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setLayout(new BorderLayout());
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}