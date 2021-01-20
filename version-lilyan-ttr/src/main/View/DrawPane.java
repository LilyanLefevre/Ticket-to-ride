package View;

import Model.Game;

import javax.swing.*;
import java.awt.*;

public class DrawPane extends JPanel {
    private Game g;
    private GridBagConstraints gbc = new GridBagConstraints();

    public DrawPane(Game g) {
        setBackground(new Color(0,0,0,64));

        this.g = g;
        setPreferredSize(new Dimension(800,215));
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        //pioche carte destination
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,0,30);
        JPanel cdp = new JPanel();
        cdp.add(new ImagePane("dos-destination.png"));
        cdp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cdp.setBackground(new Color(0,0,0,64));
        add(cdp,gbc);

        //pioche carte wagon
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,0,30);
        JPanel jp = new JPanel();
        jp.add(new ImagePane("dos-wagon.png"));
        jp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jp.setBackground(new Color(0,0,0,64));
        add(jp,gbc);

        //pioche visible carte wagon
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,0,5);
        JPanel cwp = new JPanel();
        cwp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cwp.setBackground(new Color(0,0,0,64));


        for(int i = 0; i < g.getDrawVisibleTrainCards().size(); i++){
            switch (g.getDrawVisibleTrainCards().get(i).getColor()){
                case RED:
                    cwp.add(new ImagePane("wagon-rouge.png"));
                    break;
                case BLACK:
                    cwp.add(new ImagePane("wagon-noir.png"));
                    break;
                case BLUE:
                    cwp.add(new ImagePane("wagon-bleu.png"));
                    break;
                case GREEN:
                    cwp.add(new ImagePane("wagon-vert.png"));
                    break;
                case WHITE:
                    cwp.add(new ImagePane("wagon-blanc.png"));
                    break;
                case ORANGE:
                    cwp.add(new ImagePane("wagon-orange.png"));
                    break;
                case PURPLE:
                    cwp.add(new ImagePane("wagon-violet.png"));
                    break;
                case YELLOW:
                    cwp.add(new ImagePane("wagon-jaune.png"));
                    break;
                case RAINBOW:
                    cwp.add(new ImagePane("locomotive.png"));
                    break;
                default:
                    throw new IllegalStateException("Unexpected color: "+g.getDrawVisibleTrainCards().get(i).getColor()) ;
            }
        }
        add(cwp,gbc);
    }
}
