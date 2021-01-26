package View;

import Controller.RouteController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonPane extends JPanel {
    private JButton piocherW;
    private JButton piocherD;
    private JButton prendreR;
    private JButton quitter;

    public JButton getPiocherW() {
        return piocherW;
    }

    public JButton getPiocherD() {
        return piocherD;
    }

    public JButton getPrendreR() {
        return prendreR;
    }

    public JButton getQuitter() {
        return quitter;
    }

    public ButtonPane() {
        GridLayout gl = new GridLayout(4,3);
        gl.setVgap(15);
        setLayout(gl);
        setBorder(BorderFactory.createLineBorder(Color.black));
        //setBackground(new Color(0,0,0,0.3f));


        piocherW = new JButton("Piocher des wagons");
        piocherD = new JButton("Piocher des destinations");
        prendreR = new JButton("Prendre une route");
        quitter = new JButton("Quitter");
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
    }

    public void setActionListener(ActionListener ac){
        piocherD.addActionListener(ac);
        piocherW.addActionListener(ac);
        prendreR.addActionListener(ac);
        quitter.addActionListener(ac);
    }

    public void setMouseListener(RouteController mc) {
        piocherD.addMouseListener(mc);
        piocherW.addMouseListener(mc);
        prendreR.addMouseListener(mc);
        quitter.addMouseListener(mc);
    }
}
