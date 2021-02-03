package View;

import Model.GameElements.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * classe qui représente le panel qui affiche les scores des joueurs
 */
public class ScorePane extends JPanel {
    HashMap<Player,JLabel> scoreLabels; //ensemble de labels qui contiennet les infos des joueurs
    JPanel container;

    public ScorePane(ArrayList<Player> pl) {
        scoreLabels = new HashMap<>();

        //setBackground(new Color(0.0f, 0.0f, 0.0f, 0.3f));

        setLayout(new FlowLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        container = new JPanel();
        container.setBackground(new Color(0.0f, 0.0f, 0.0f, 0));
        container.setPreferredSize(new Dimension(400,200));
        container.setLayout(new GridLayout(5,0));

        //on affiche le label score
        JLabel sc = new JLabel("<html><u>Score</u></html>\"",SwingConstants.CENTER);
        Font font = new Font("Arial",Font.BOLD,20);
        sc.setFont(font);
        container.add(sc);

        //on affiche chaque joueur
        for(int i = 0; i < pl.size(); i++){
            JLabel tmp = new JLabel("<html> <body style='text-align: center'>"+pl.get(i).getName()+" "+pl.get(i).getPoints()+" points, "
                    +pl.get(i).getWagons()+" wagons restants </body></html>",SwingConstants.CENTER);
            font = new Font("Arial",Font.BOLD,19);
            tmp.setFont(font);
            tmp.setForeground(pl.get(i).getColor());
            container.add(tmp);
            scoreLabels.put(pl.get(i),tmp);
        }
        add(container);
    }

    /**
     * fonction qui rafaîchit l'affichage des scores des joueurs
     *
     * @param players ensemble des joueurs du jeu
     */
    public void updateScore(ArrayList<Player> players){
        //on affiche chaque joueur
        for(int i = 0; i < players.size(); i++){
            scoreLabels.get(players.get(i)).setText("<html> <body style='text-align: center'>"+players.get(i).getName()+" "+players.get(i).getPoints()+" points, "
                    +players.get(i).getWagons()+" wagons restants </body></html>");
            scoreLabels.put(players.get(i),scoreLabels.get(players.get(i)));
        }

        revalidate();
        repaint();
    }

}
