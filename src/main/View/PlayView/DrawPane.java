package View.PlayView;

import Controller.JButtonController;
import Model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * classe qui représente le panel qui affiche la pioche de carte wagon
 */
public class DrawPane extends JPanel {
    private Game g;
    private GridBagConstraints gbc = new GridBagConstraints();
    private CardButtonPane piocheWagon;
    private JPanel piocheVisibleWagon;
    private ArrayList<CardButtonPane> cartesVisibles;

    public JButton getPiocheWagon() {
        return piocheWagon;
    }

    public DrawPane(Game g) {
        this.g = g;
        this.cartesVisibles = new ArrayList<>();

        //setBackground(new Color(0.0f, 0.0f, 0.0f, 0.3f));

        setPreferredSize(new Dimension(800,215));
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        //pioche carte destination
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,0,30);

        //pioche carte wagon
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,0,30);

        piocheWagon = new CardButtonPane("/dos-wagon.jpg",-1,null);
        JPanel jp2 = new JPanel();
        jp2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jp2.setBackground(new Color(0,0,0,64));
        jp2.add(piocheWagon);
        piocheWagon.setEnabled(false);
        add(jp2,gbc);

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
                    CardButtonPane tmp = new CardButtonPane("/wagon-rouge.jpg", i, Model.Enum.Color.RED);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case BLACK:
                    tmp = new CardButtonPane("/wagon-noir.jpg", i, Model.Enum.Color.BLACK);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case BLUE:
                    tmp = new CardButtonPane("/wagon-bleu.jpg", i, Model.Enum.Color.BLUE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case GREEN:
                    tmp = new CardButtonPane("/wagon-vert.jpg", i, Model.Enum.Color.GREEN);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case WHITE:
                    tmp = new CardButtonPane("/wagon-blanc.jpg", i, Model.Enum.Color.WHITE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case ORANGE:
                    tmp = new CardButtonPane("/wagon-orange.jpg", i, Model.Enum.Color.ORANGE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case PURPLE:
                    tmp = new CardButtonPane("/wagon-violet.jpg", i, Model.Enum.Color.PURPLE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case YELLOW:
                    tmp = new CardButtonPane("/wagon-jaune.jpg", i, Model.Enum.Color.YELLOW);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case RAINBOW:
                    tmp = new CardButtonPane("/locomotive.jpg", i,  Model.Enum.Color.RAINBOW);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                default:
                    throw new IllegalStateException("Unexpected color: "+g.getDrawVisibleTrainCards().get(i).getColor()) ;
            }
        }
        add(piocheVisibleWagon,gbc);
    }

    /**
     * fonction qui ajoute un action listener aux cartes
     *
     * @param gc l'action listener
     */
    public void setActionListener(JButtonController gc){
        piocheWagon.addActionListener(gc);
        for(CardButtonPane c : cartesVisibles){
            c.addActionListener(gc);
        }
    }

    /**
     * fonction qui actualise l'affichage des cartes (en cas de prise d'une carte par ex)
     */
    public void updateCard(){
        piocheVisibleWagon.removeAll();
        cartesVisibles = new ArrayList<>();

        //afficher les images des cartes visibles de la pioche
        for(int i = 0; i < g.getDrawVisibleTrainCards().size(); i++){
            switch (g.getDrawVisibleTrainCards().get(i).getColor()){
                case RED:
                    CardButtonPane tmp = new CardButtonPane("/wagon-rouge.jpg", i, Model.Enum.Color.RED);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case BLACK:
                    tmp = new CardButtonPane("/wagon-noir.jpg", i, Model.Enum.Color.BLACK);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case BLUE:
                    tmp = new CardButtonPane("/wagon-bleu.jpg", i, Model.Enum.Color.BLUE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case GREEN:
                    tmp = new CardButtonPane("/wagon-vert.jpg", i, Model.Enum.Color.GREEN);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case WHITE:
                    tmp = new CardButtonPane("/wagon-blanc.jpg", i, Model.Enum.Color.WHITE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case ORANGE:
                    tmp = new CardButtonPane("/wagon-orange.jpg", i, Model.Enum.Color.ORANGE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case PURPLE:
                    tmp = new CardButtonPane("/wagon-violet.jpg", i, Model.Enum.Color.PURPLE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case YELLOW:
                    tmp = new CardButtonPane("/wagon-jaune.jpg", i, Model.Enum.Color.YELLOW);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case RAINBOW:
                    tmp = new CardButtonPane("/locomotive.jpg", i,  Model.Enum.Color.RAINBOW);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                default:
                    throw new IllegalStateException("Unexpected color: "+g.getDrawVisibleTrainCards().get(i).getColor()) ;
            }
        }
        piocheVisibleWagon.revalidate();
        piocheVisibleWagon.repaint();
    }
}
