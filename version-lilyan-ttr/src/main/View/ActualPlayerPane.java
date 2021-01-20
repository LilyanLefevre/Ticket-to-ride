package View;

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
        setBackground(new Color(0,0,0,64));

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
                    ImagePane im = new ImagePane("wagon-rouge.png");
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
                    im = new ImagePane("wagon-noir.png");
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
                    im = new ImagePane("wagon-bleu.png");
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
                    im = new ImagePane("wagon-vert.png");
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
                    im = new ImagePane("wagon-blanc.png");
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
                    im = new ImagePane("wagon-orange.png");
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
                    im = new ImagePane("wagon-violet.png");
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
                    im = new ImagePane("wagon-jaune.png");
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
                    im = new ImagePane("locomotive.png");
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

}
