package View;

import Controller.JButtonController;
import Controller.RouteController;
import Model.Game;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

public class GameView extends JFrame{
    private Game g;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JPanel container;
    private BoardPane board;
    private ScorePane score;
    private DrawPane draw;
    private ActualPlayerPane playerView;
    private ButtonPane buttons;
    public boolean fini = false;

    public GameView(final Game ga) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setTitle("Ticket to ride");
                g = ga;
                gbc.fill = GridBagConstraints.BOTH;

                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //on créer un conteneur global
                container = new JPanel();
                container.setLayout(new GridBagLayout());

                JScrollPane scrPane = new JScrollPane(container);
                add(scrPane);

                //plateau
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridheight = 2;
                gbc.insets = new Insets(0, 0, 0, 0);
                board = new BoardPane(g.getD(), g);
                container.add(board, gbc);

                //score
                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.gridheight = 1;
                gbc.insets = new Insets(0, 0, 0, 0);
                score = new ScorePane(g.getPlayers());
                container.add(score, gbc);

                //pioche
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridheight = 1;
                gbc.insets = new Insets(0, 0, 0, 0);
                draw = new DrawPane(g);
                container.add(draw, gbc);

                //carte du joueur actuel
                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.gridheight = 1;
                gbc.insets = new Insets(0, 0, 0, 0);
                playerView = new ActualPlayerPane(g.getPlayers().get(0));
                container.add(playerView, gbc);

                //boutons
                gbc.gridx = 1;
                gbc.gridy = 2;
                gbc.gridheight = 1;
                gbc.insets = new Insets(0, 0, 0, 0);
                buttons = new ButtonPane();
                container.add(buttons, gbc);

                setLocationRelativeTo(null);
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                pack();
                setVisible(true);
                fini = true;
            }
        });
    }



    public GridBagConstraints getGbc() {
        return gbc;
    }

    public void setGbc(GridBagConstraints gbc) {
        this.gbc = gbc;
    }

    public JPanel getContainer() {
        return container;
    }

    public void setContainer(JPanel container) {
        this.container = container;
    }

    public BoardPane getBoard() {
        return board;
    }

    public ScorePane getScore() {
        return score;
    }

    public void setScore(ScorePane score) {
        this.score = score;
    }

    public DrawPane getDraw() {
        return draw;
    }

    public void setDraw(DrawPane draw) {
        this.draw = draw;
    }

    public ActualPlayerPane getPlayerView() {
        return playerView;
    }

    public void setPlayerView(ActualPlayerPane playerView) {
        this.playerView = playerView;
    }

    public ButtonPane getButtons() {
        return buttons;
    }

    public void setActionListener(JButtonController gc){
        board.setActionListener(gc);
        buttons.setActionListener(gc);
        draw.setActionListener(gc);
        /*score.setActionListener(gc)*/;

        playerView.setActionListener(gc);
    }
    public void setRouteListener(RouteController mc){
        board.setMouseListener(mc);
    }

    public void updateView(Game g,JButtonController jc){
        score.updateScore(g.getPlayers());
        playerView.updateCard(g.getCurrentPlayer());
        draw.updateCard();
        draw.setActionListener(jc);

    }
}
