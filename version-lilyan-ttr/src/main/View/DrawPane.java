package View;

import Model.GameElements.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawPane extends JPanel {

    public DrawPane(ArrayList<Player> pl, Player p) {
        setPreferredSize(new Dimension(300,100));
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        //pioche carte destination
        JPanel cdp = new JPanel();
        cdp.setBackground(Color.pink);
        JLabel cdn = new JLabel("Destination");
        cdp.add(cdn);
        add(cdp);

        //pioche carte wagon
        JPanel cwp = new JPanel();
        cwp.setBackground(Color.gray);
        JLabel cwn = new JLabel("Wagon");
        cwp.add(cwn);
        add(cwp);
    }
}
