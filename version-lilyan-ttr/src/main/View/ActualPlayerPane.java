package View;

import Controller.RouteController;
import Model.GameElements.Player;
import Model.GameElements.WagonCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActualPlayerPane extends JPanel {
    private HashMap<Model.Enum.Color, Integer> occurencesCouleur;
    private JLabel name;
    private JPanel cardWagonPanel;
    private JPanel cardDestinationPanel;

    public HashMap<Model.Enum.Color, Integer> getOccurencesCouleur() {
        return occurencesCouleur;
    }

    public void setOccurencesCouleur(HashMap<Model.Enum.Color, Integer> occurencesCouleur) {
        this.occurencesCouleur = occurencesCouleur;
    }

    public JLabel getNamePlayer() {
        return name;
    }

    public void setName(JLabel name) {
        this.name = name;
    }

    public JPanel getCardWagonPanel() {
        return cardWagonPanel;
    }

    public void setCardWagonPanel(JPanel cardWagonPanel) {
        this.cardWagonPanel = cardWagonPanel;
    }

    public JPanel getCardDestinationPanel() {
        return cardDestinationPanel;
    }

    public void setCardDestinationPanel(JPanel cardDestinationPanel) {
        this.cardDestinationPanel = cardDestinationPanel;
    }


    public ActualPlayerPane(Player p) {
        setBackground(new Color(0.0f, 0.0f, 0.0f, 0.3f));

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500,300));
        setBorder(BorderFactory.createLineBorder(Color.black));

        occurencesCouleur = new HashMap<>();
        ArrayList<WagonCard> wCards = p.getwCards();

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
        cardWagonPanel.setLayout(new FlowLayout());
        cardWagonPanel.setBackground(new Color(0,0,0,0));

        for(Map.Entry couleur : occurencesCouleur.entrySet()){
            switch ((Model.Enum.Color)couleur.getKey()){
                case RED:
                    CardImagePane im = new CardImagePane("wagon-rouge.jpg",Model.Enum.Color.RED,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case BLACK:
                    im = new CardImagePane("wagon-noir.jpg",Model.Enum.Color.BLACK,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case BLUE:
                    im = new CardImagePane("wagon-bleu.jpg",Model.Enum.Color.BLUE,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case GREEN:
                    im = new CardImagePane("wagon-vert.jpg",Model.Enum.Color.GREEN,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case WHITE:
                    im = new CardImagePane("wagon-blanc.jpg",Model.Enum.Color.WHITE,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case ORANGE:
                    im = new CardImagePane("wagon-orange.jpg",Model.Enum.Color.ORANGE,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case PURPLE:
                    im = new CardImagePane("wagon-violet.jpg",Model.Enum.Color.PURPLE,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case YELLOW:
                    im = new CardImagePane("wagon-jaune.jpg",Model.Enum.Color.YELLOW,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case RAINBOW:
                    im = new CardImagePane("locomotive.jpg",Model.Enum.Color.RAINBOW,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                default:
                    throw new IllegalStateException("Unexpected color: "+(Model.Enum.Color)couleur.getKey());
            }
            add(cardWagonPanel,BorderLayout.CENTER);
        }

        //on affiche les cartes objectifs
        cardDestinationPanel = new JPanel();
        cardDestinationPanel.setBackground(new Color(0.0f, 0.0f, 0.0f, 0));
        JButton objButton = new JButton("Voir les objectifs");
        cardDestinationPanel.add(objButton);
        add(cardDestinationPanel,BorderLayout.SOUTH);
    }
    public void setMouseListener(RouteController mc){
        addMouseListener(mc);
    }

    public void updateCard(Player p){
        ActualPlayerPane newPane = new ActualPlayerPane(p);

        name.setText(p.getName());

        occurencesCouleur = new HashMap<>();
        ArrayList<WagonCard> wCards = p.getwCards();

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
            switch ((Model.Enum.Color)couleur.getKey()){
                case RED:
                    CardImagePane im = new CardImagePane("wagon-rouge.jpg",Model.Enum.Color.RED,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case BLACK:
                    im = new CardImagePane("wagon-noir.jpg",Model.Enum.Color.BLACK,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case BLUE:
                    im = new CardImagePane("wagon-bleu.jpg",Model.Enum.Color.BLUE,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case GREEN:
                    im = new CardImagePane("wagon-vert.jpg",Model.Enum.Color.GREEN,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case WHITE:
                    im = new CardImagePane("wagon-blanc.jpg",Model.Enum.Color.WHITE,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case ORANGE:
                    im = new CardImagePane("wagon-orange.jpg",Model.Enum.Color.ORANGE,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case PURPLE:
                    im = new CardImagePane("wagon-violet.jpg",Model.Enum.Color.PURPLE,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case YELLOW:
                    im = new CardImagePane("wagon-jaune.jpg",Model.Enum.Color.YELLOW,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                case RAINBOW:
                    im = new CardImagePane("locomotive.jpg",Model.Enum.Color.RAINBOW,((Integer)couleur.getValue()));
                    cardWagonPanel.add(im);
                    break;
                default:
                    throw new IllegalStateException("Unexpected color: "+(Model.Enum.Color)couleur.getKey());
            }
            add(cardWagonPanel,BorderLayout.CENTER);
        }
        cardWagonPanel.revalidate();
        cardWagonPanel.repaint();
    }
}
