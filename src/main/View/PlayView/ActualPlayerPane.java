package View.PlayView;

import Controller.JButtonController;
import Model.GameElements.Player;
import Model.GameElements.WagonCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * classe qui représente le panel qui affiche les cartes du joueur actuel
 */
public class ActualPlayerPane extends JPanel {
    private HashMap<Model.GameElements.Color, Integer> occurencesCouleur; //map ou ono enregistre les occurences des cartes du joueur
    private JLabel name;
    private JPanel cardWagonPanel;
    private JPanel cardDestinationPanel;
    private JButton objButton;

    public JButton getObjButton() {
        return objButton;
    }

    public ActualPlayerPane(Player p) {
        setLayout(new BorderLayout(0,40));
        setBorder(BorderFactory.createLineBorder(Color.black));

        occurencesCouleur = new HashMap<>();
        ArrayList<WagonCard> wCards = p.getWagonCards();

        //on compte les occurences des couleurs
        for(int i = 0; i < wCards.size(); i++){
            if(occurencesCouleur.containsKey(wCards.get(i).getColor())){
                occurencesCouleur.put(wCards.get(i).getColor(),occurencesCouleur.get(wCards.get(i).getColor()) + 1);
            }else{
                occurencesCouleur.put(wCards.get(i).getColor(),1);
            }
        }


        //on affiche le joueur courant
        name = new JLabel("<html><u>Tour de "+p.getName()+"</u></html>",SwingConstants.CENTER);
        Font font = new Font("Arial",Font.BOLD,20);
        name.setFont(font);
        add(name,BorderLayout.NORTH);

        //on affiche les cartes du joueur
        cardWagonPanel = new JPanel();
        cardWagonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        cardWagonPanel.setBackground(new Color(0,0,0,0));
        cardWagonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        for(Map.Entry couleur : occurencesCouleur.entrySet()){
            switch ((Model.GameElements.Color)couleur.getKey()){
                case ROUGE:
                    CardImagePane im = new CardImagePane("/wagon-rouge.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case NOIR:
                    im = new CardImagePane("/wagon-noir.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case BLEU:
                    im = new CardImagePane("/wagon-bleu.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case VERT:
                    im = new CardImagePane("/wagon-vert.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case BLANC:
                    im = new CardImagePane("/wagon-blanc.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case ORANGE:
                    im = new CardImagePane("/wagon-orange.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case VIOLET:
                    im = new CardImagePane("/wagon-violet.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case JAUNE:
                    im = new CardImagePane("/wagon-jaune.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case LOCOMOTIVE:
                    im = new CardImagePane("/locomotive.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                default:
                    throw new IllegalStateException("Unexpected color: "+(Model.GameElements.Color)couleur.getKey());
            }
            add(cardWagonPanel,BorderLayout.CENTER);
        }

        //on affiche les cartes objectifs
        cardDestinationPanel = new JPanel();
        cardDestinationPanel.setBackground(new Color(0.0f, 0.0f, 0.0f, 0));
        objButton = new JButton("Voir les objectifs");
        objButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cardDestinationPanel.add(objButton);
        add(cardDestinationPanel,BorderLayout.SOUTH);
    }

    /**
     * fonction qui ajoute un actionListener aux boutons du panel
     *
     * @param mc le controleur à ajouter
     */
    public void setActionListener(JButtonController mc){
        objButton.addActionListener(mc);
    }

    /**
     * fonction qui recharge l'affichage des cartes du joueur (quand il prend une carte par ex)
     *
     * @param p le joueur à actualiser
     */
    public void updateCard(Player p){
        name.setText("<html><u>Tour de "+p.getName()+"</u></html>");

        occurencesCouleur = new HashMap<>();
        ArrayList<WagonCard> wCards = p.getWagonCards();

        //on compte les occurences des couleurs
        for(int i = 0; i < wCards.size(); i++){
            if(occurencesCouleur.containsKey(wCards.get(i).getColor())){
                occurencesCouleur.put(wCards.get(i).getColor(),occurencesCouleur.get(wCards.get(i).getColor()) + 1);
            }else{
                occurencesCouleur.put(wCards.get(i).getColor(),1);
            }
        }
        cardWagonPanel.removeAll();
        for(Map.Entry couleur : occurencesCouleur.entrySet()){
            switch ((Model.GameElements.Color)couleur.getKey()){
                case ROUGE:
                    CardImagePane im = new CardImagePane("/wagon-rouge.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case NOIR:
                    im = new CardImagePane("/wagon-noir.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case BLEU:
                    im = new CardImagePane("/wagon-bleu.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case VERT:
                    im = new CardImagePane("/wagon-vert.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case BLANC:
                    im = new CardImagePane("/wagon-blanc.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case ORANGE:
                    im = new CardImagePane("/wagon-orange.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case VIOLET:
                    im = new CardImagePane("/wagon-violet.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case JAUNE:
                    im = new CardImagePane("/wagon-jaune.jpg", ((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case LOCOMOTIVE:
                    im = new CardImagePane("/locomotive.jpg",((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                default:
                    throw new IllegalStateException("Unexpected color: "+(Model.GameElements.Color)couleur.getKey());
            }
            add(cardWagonPanel,BorderLayout.CENTER);
        }
        cardWagonPanel.revalidate();
        cardWagonPanel.repaint();
    }
}
