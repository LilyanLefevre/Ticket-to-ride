package View;

import Model.GameElements.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScorePane extends JPanel {
    HashMap<Player,JLabel> scoreLabels;
    JPanel container;

    public ScorePane(ArrayList<Player> pl) {
        scoreLabels = new HashMap<>();

        setBackground(new Color(0.0f, 0.0f, 0.0f, 0.3f));

        setLayout(new FlowLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel container = new JPanel();
        container.setBackground(new Color(0.0f, 0.0f, 0.0f, 0));
        container.setPreferredSize(new Dimension(500,300));
        container.setLayout(new GridLayout(5,0));

        //on affiche le label score
        JLabel sc = new JLabel("<html><u>Score</u></html>\"",SwingConstants.CENTER);
        Font font = new Font("Arial",Font.BOLD,20);
        sc.setFont(font);
        container.add(sc);

        //on affiche chaque joueur
        for(int i = 0; i < pl.size(); i++){
            JLabel tmp = new JLabel(pl.get(i).getName()+" "+pl.get(i).getPoints()+" points, "+pl.get(i).getWagons()+" wagons restants",SwingConstants.CENTER);
            font = new Font("Arial",Font.CENTER_BASELINE,16);
            tmp.setFont(font);
            container.add(tmp);
            scoreLabels.put(pl.get(i),tmp);
        }
        add(container);
    }

    public void updateScore(ArrayList<Player> players){
        //on affiche chaque joueur
        for(int i = 0; i < players.size(); i++){
            scoreLabels.get(players.get(i)).setText(players.get(i).getName()+" "+players.get(i).getPoints()+" points, "+players.get(i).getWagons()+" wagons restants");
            scoreLabels.put(players.get(i),scoreLabels.get(players.get(i)));

        }
    }

}
