package View;

import Model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawPane extends JPanel {
    private Game g;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JPanel piocheDestination;
    private JPanel piocheWagon;
    private JPanel piocheVisibleWagon;
    private ArrayList<CardImagePane> cartesVisibles;

    public DrawPane(Game g) {
        this.g = g;
        this.cartesVisibles = new ArrayList<>();

        setBackground(new Color(0.0f, 0.0f, 0.0f, 0.3f));

        setPreferredSize(new Dimension(800,215));
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        //pioche carte destination
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,0,30);
        piocheDestination = new JPanel();
        piocheDestination.add(new CardImagePane("dos-destination.jpg",-1));
        piocheDestination.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        piocheDestination.setBackground(new Color(0,0,0,64));
        add(piocheDestination,gbc);

        //pioche carte wagon
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,0,30);
        piocheWagon = new JPanel();
        piocheWagon.add(new CardImagePane("dos-wagon.jpg",-1));
        piocheWagon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        piocheWagon.setBackground(new Color(0,0,0,64));
        add(piocheWagon,gbc);

        //pioche visible carte wagon
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,0,5);
        piocheVisibleWagon = new JPanel();
        piocheVisibleWagon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        piocheVisibleWagon.setBackground(new Color(0,0,0,64));


        //afficher les images des cartes visibles de la pioche
        for(int i = 0; i < g.getDrawVisibleTrainCards().size(); i++){
            switch (g.getDrawVisibleTrainCards().get(i).getColor()){
                case RED:
                    CardImagePane tmp = new CardImagePane("wagon-rouge.jpg", i);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case BLACK:
                    tmp = new CardImagePane("wagon-noir.jpg", i);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case BLUE:
                    tmp = new CardImagePane("wagon-bleu.jpg", i);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case GREEN:
                    tmp = new CardImagePane("wagon-vert.jpg", i);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case WHITE:
                    tmp = new CardImagePane("wagon-blanc.jpg", i);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case ORANGE:
                    tmp = new CardImagePane("wagon-orange.jpg", i);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case PURPLE:
                    tmp = new CardImagePane("wagon-violet.jpg", i);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case YELLOW:
                    tmp = new CardImagePane("wagon-jaune.jpg", i);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case RAINBOW:
                    tmp = new CardImagePane("locomotive.jpg", i);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                default:
                    throw new IllegalStateException("Unexpected color: "+g.getDrawVisibleTrainCards().get(i).getColor()) ;
            }
        }
        add(piocheVisibleWagon,gbc);
    }
}
