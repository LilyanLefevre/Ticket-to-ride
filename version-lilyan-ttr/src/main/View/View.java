package View;

import Model.Game;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class View {
    Game g = new Game(new ArrayList<>(Arrays.asList("lilyan", "eros", "loic", "View")));
    GridBagConstraints gbc = new GridBagConstraints();

    public View() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                gbc.fill = GridBagConstraints.BOTH;

                //on creer une fenetre
                JFrame frame = new JFrame("TTR");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //on créer un conteneur global
                JPanel container = new JPanel();
                container.setLayout(new GridBagLayout());

                JScrollPane scrPane = new JScrollPane(container);
                frame.add(scrPane);

                //plateau
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridheight = 2;
                gbc.insets = new Insets(0,0,0,0);
                container.add(new BoardPane(g.getD(),g), gbc);

                //score
                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.gridheight = 1;
                gbc.insets = new Insets(0,0,0,0);
                container.add(new ScorePane(g.getPlayers()), gbc);

                //pioche
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridheight = 1;
                gbc.insets = new Insets(0,0,0,0);
                container.add(new DrawPane(g), gbc);

                //carte du joueur actuel
                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.gridheight = 2;
                gbc.insets = new Insets(0,0,0,0);
                container.add(new ActualPlayerPane(g.getPlayers(), g.getPlayers().get(0)), gbc);

                frame.setLocationRelativeTo(null);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.pack();
                frame.setVisible(true); }
        });
    }
    public static void main(String[] args) {
        new View();
    }


}
