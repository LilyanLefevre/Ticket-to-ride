package View;

import Model.GameElements.Player;
import Model.GameElements.WagonCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ActualPlayerPane extends JPanel {

    public ActualPlayerPane(ArrayList<Player> pl, Player p) {
        setPreferredSize(new Dimension(500,300));
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel name = new JLabel("Tour de "+p.getName());
        add(name);
        ArrayList<WagonCard> wCards = p.getwCards();
        for(int i = 0; i < wCards.size(); i++){
            JPanel jp = new JPanel();
            JLabel tmp = new JLabel("CARTE");
            switch (wCards.get(i).getColor()){
                case RED:
                    tmp.setText("RED");
                    jp.setBackground(Color.red);
                break;
                case BLACK:
                    tmp.setText("BLACK");
                    tmp.setForeground(Color.WHITE);
                    jp.setBackground(Color.black);
                break;
                case BLUE:
                    tmp.setText("BLUE");
                    tmp.setForeground(Color.WHITE);
                    jp.setBackground(Color.blue);
                break;
                case GREEN:
                    tmp.setText("GREEN");
                    jp.setBackground(Color.green);
                break;
                case WHITE:
                    tmp.setText("WHITE");
                    jp.setBackground(Color.white);
                break;
                case ORANGE:
                    tmp.setText("ORANGE");
                    jp.setBackground(Color.orange);
                break;
                case PURPLE:
                    tmp.setText("PURPLE");
                    jp.setBackground(Color.MAGENTA);
                break;
                case YELLOW:
                    tmp.setText("YELLOW");
                    jp.setBackground(Color.yellow);
                break;
                case RAINBOW:
                    tmp.setText("RAINBOW");
                    jp.setBackground(Color.PINK);
                break;
                default:
                    throw new IllegalStateException("Unexpected color: "+wCards.get(i).getColor()) ;
            }
            jp.add(tmp);
            add(jp);
        }

    }

}
