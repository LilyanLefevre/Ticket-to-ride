package View;

import Model.GameElements.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScorePane extends JPanel {

    public ScorePane(ArrayList<Player> pl) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setPreferredSize(new Dimension(300,400));

        JLabel sc = new JLabel("Scores :");
        add(sc);
        for(int i = 0; i < pl.size(); i++){
            JLabel tmp = new JLabel("   - "+pl.get(i).getName()+" "+pl.get(i).getPoints()+" points, "+pl.get(i).getWagons()+" wagons restants");
            add(tmp);
        }

    }

}
