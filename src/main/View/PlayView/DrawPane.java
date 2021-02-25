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
                case ROUGE:
                    CardButtonPane tmp = new CardButtonPane("/wagon-rouge.jpg", i, Model.GameElements.Color.ROUGE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case NOIR:
                    tmp = new CardButtonPane("/wagon-noir.jpg", i, Model.GameElements.Color.NOIR);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case BLEU:
                    tmp = new CardButtonPane("/wagon-bleu.jpg", i, Model.GameElements.Color.BLEU);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case VERT:
                    tmp = new CardButtonPane("/wagon-vert.jpg", i, Model.GameElements.Color.VERT);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case BLANC:
                    tmp = new CardButtonPane("/wagon-blanc.jpg", i, Model.GameElements.Color.BLANC);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case ORANGE:
                    tmp = new CardButtonPane("/wagon-orange.jpg", i, Model.GameElements.Color.ORANGE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case VIOLET:
                    tmp = new CardButtonPane("/wagon-violet.jpg", i, Model.GameElements.Color.VIOLET);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case JAUNE:
                    tmp = new CardButtonPane("/wagon-jaune.jpg", i, Model.GameElements.Color.JAUNE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case LOCOMOTIVE:
                    tmp = new CardButtonPane("/locomotive.jpg", i,  Model.GameElements.Color.LOCOMOTIVE);
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
        if(piocheWagon.getActionListeners().length == 0) {
            piocheWagon.addActionListener(gc);
        }
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
                case ROUGE:
                    CardButtonPane tmp = new CardButtonPane("/wagon-rouge.jpg", i, Model.GameElements.Color.ROUGE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case NOIR:
                    tmp = new CardButtonPane("/wagon-noir.jpg", i, Model.GameElements.Color.NOIR);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case BLEU:
                    tmp = new CardButtonPane("/wagon-bleu.jpg", i, Model.GameElements.Color.BLEU);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case VERT:
                    tmp = new CardButtonPane("/wagon-vert.jpg", i, Model.GameElements.Color.VERT);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case BLANC:
                    tmp = new CardButtonPane("/wagon-blanc.jpg", i, Model.GameElements.Color.BLANC);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case ORANGE:
                    tmp = new CardButtonPane("/wagon-orange.jpg", i, Model.GameElements.Color.ORANGE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case VIOLET:
                    tmp = new CardButtonPane("/wagon-violet.jpg", i, Model.GameElements.Color.VIOLET);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case JAUNE:
                    tmp = new CardButtonPane("/wagon-jaune.jpg", i, Model.GameElements.Color.JAUNE);
                    cartesVisibles.add(tmp);
                    piocheVisibleWagon.add(tmp);
                    break;
                case LOCOMOTIVE:
                    tmp = new CardButtonPane("/locomotive.jpg", i,  Model.GameElements.Color.LOCOMOTIVE);
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
