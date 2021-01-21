package View;

import javax.swing.*;
import java.awt.*;

public class ButtonPane extends JPanel {

    public ButtonPane() {

        setLayout(new GridLayout(4,3));
        setBorder(BorderFactory.createLineBorder(Color.black));
        //setPreferredSize(new Dimension(700,100));


        JButton piocherW = new JButton("Piocher des wagons");
        JButton piocherD = new JButton("Piocher des destinations");
        JButton prendreR = new JButton("Prendre une route");
        JButton quitter = new JButton("Quitter");
        add(new JLabel());
        add(piocherW);
        add(new JLabel());
        add(new JLabel());
        add(piocherD);
        add(new JLabel());
        add(new JLabel());
        add(prendreR);
        add(new JLabel());
        add(new JLabel());
        add(quitter);
        add(new JLabel());

        setBackground(new Color(0.0f, 0.0f, 0.0f, 0.3f));
    }
}
