package View;

import Controller.MouseController;
import Model.GameElements.Player;
import Model.GameElements.WagonCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActualPlayerPane extends JPanel {
    HashMap<Model.Enum.Color, Integer> occurencesCouleur;

    public ActualPlayerPane(ArrayList<Player> pl, Player p) {
        setBackground(new Color(0.0f, 0.0f, 0.0f, 0.3f));

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

        setPreferredSize(new Dimension(500,300));
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel name = new JLabel("Tour de "+p.getName());
        add(name);
        JLabel jb;
        Font font = new Font("Arial",Font.BOLD,16);

        for(Map.Entry couleur : occurencesCouleur.entrySet()){
            switch ((Model.Enum.Color)couleur.getKey()){
                case RED:
                    CardImagePane im = new CardImagePane("wagon-rouge.jpg",Model.Enum.Color.RED);
                    jb = new JLabel(""+((Integer)couleur.getValue()));
                    jb.setFont(font);
                    JPanel jp = new JPanel();
                    jp.setBorder(BorderFactory.createLineBorder(Color.black));
                    jp.setBackground(Color.white);
                    jp.add(jb);
                    im.add(jp);
                    add(im);
                    break;
                case BLACK:
                    im = new CardImagePane("wagon-noir.jpg",Model.Enum.Color.BLACK);
                    jb = new JLabel(""+((Integer)couleur.getValue()));
                    jb.setFont(font);
                    jp = new JPanel();
                    jp.setBorder(BorderFactory.createLineBorder(Color.black));
                    jp.setBackground(Color.white);
                    jp.add(jb);
                    im.add(jp);
                    add(im);
                    break;
                case BLUE:
                    im = new CardImagePane("wagon-bleu.jpg",Model.Enum.Color.BLUE);
                    jb = new JLabel(""+((Integer)couleur.getValue()));
                    jb.setFont(font);
                    jp = new JPanel();
                    jp.setBorder(BorderFactory.createLineBorder(Color.black));
                    jp.setBackground(Color.white);
                    jp.add(jb);
                    im.add(jp);
                    add(im);
                    break;
                case GREEN:
                    im = new CardImagePane("wagon-vert.jpg",Model.Enum.Color.GREEN);
                    jb = new JLabel(""+((Integer)couleur.getValue()));
                    jb.setFont(font);
                    jp = new JPanel();
                    jp.setBorder(BorderFactory.createLineBorder(Color.black));
                    jp.setBackground(Color.white);
                    jp.add(jb);
                    im.add(jp);
                    add(im);
                    break;
                case WHITE:
                    im = new CardImagePane("wagon-blanc.jpg",Model.Enum.Color.WHITE);
                    jb = new JLabel(""+((Integer)couleur.getValue()));
                    jb.setFont(font);
                    jp = new JPanel();
                    jp.setBorder(BorderFactory.createLineBorder(Color.black));
                    jp.setBackground(Color.white);
                    jp.add(jb);
                    im.add(jp);
                    add(im);
                    break;
                case ORANGE:
                    im = new CardImagePane("wagon-orange.jpg",Model.Enum.Color.ORANGE);
                    jb = new JLabel(""+((Integer)couleur.getValue()));
                    jb.setFont(font);
                    jp = new JPanel();
                    jp.setBorder(BorderFactory.createLineBorder(Color.black));
                    jp.setBackground(Color.white);
                    jp.add(jb);
                    im.add(jp);
                    add(im);
                    break;
                case PURPLE:
                    im = new CardImagePane("wagon-violet.jpg",Model.Enum.Color.PURPLE);
                    jb = new JLabel(""+((Integer)couleur.getValue()));
                    jb.setFont(font);
                    jp = new JPanel();
                    jp.setBorder(BorderFactory.createLineBorder(Color.black));
                    jp.setBackground(Color.white);
                    jp.add(jb);
                    im.add(jp);
                    add(im);
                    break;
                case YELLOW:
                    im = new CardImagePane("wagon-jaune.jpg",Model.Enum.Color.YELLOW);
                    jb = new JLabel(""+((Integer)couleur.getValue()));
                    jb.setFont(font);
                    jp = new JPanel();
                    jp.setBorder(BorderFactory.createLineBorder(Color.black));
                    jp.setBackground(Color.white);
                    jp.add(jb);
                    im.add(jp);
                    add(im);
                    break;
                case RAINBOW:
                    im = new CardImagePane("locomotive.jpg",Model.Enum.Color.RAINBOW);
                    jb = new JLabel(""+((Integer)couleur.getValue()));
                    jb.setFont(font);
                    jp = new JPanel();
                    jp.setBorder(BorderFactory.createLineBorder(Color.black));
                    jp.setBackground(Color.white);
                    jp.add(jb);
                    im.add(jp);
                    add(im);
                    break;
                default:
                    throw new IllegalStateException("Unexpected color: "+(Model.Enum.Color)couleur.getKey());
            }
        }
    }
    public void setMouseListener(MouseController mc){
        addMouseListener(mc);
    }
}
