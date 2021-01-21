package View;

import Controller.GameController;
import Model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameView {
    private Game g;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JFrame frame;
    private JPanel container;
    private BoardPane board;
    private ScorePane score;
    private DrawPane draw;
    private ActualPlayerPane playerView;

    public GameView(final Game g) throws IOException {
        this.g = g;
        gbc.fill = GridBagConstraints.BOTH;

        //on creer une fenetre
        frame = new JFrame("TTR");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //on créer un conteneur global
        container = new JPanel();
        container.setLayout(new GridBagLayout());

        BackgroundPane bgPanel = new BackgroundPane("back-ttr-2.png");
        JScrollPane scrPane = new JScrollPane(container);
        container.setOpaque(false);
        scrPane.setOpaque(false);
        frame.setContentPane(bgPanel);
        frame.add(container);

        //plateau
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.insets = new Insets(0,0,0,0);
        board = new BoardPane(g.getD(),g);
        container.add(board, gbc);

        //score
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0,0,0,0);
        score = new ScorePane(g.getPlayers());
        container.add(score, gbc);

        //pioche
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0,0,0,0);
        draw = new DrawPane(g);
        container.add(draw, gbc);

        //carte du joueur actuel
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.insets = new Insets(0,0,0,0);
        playerView = new ActualPlayerPane(g.getPlayers(), g.getPlayers().get(0));
        container.add(playerView, gbc);

        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
    }


    public GridBagConstraints getGbc() {
        return gbc;
    }

    public void setGbc(GridBagConstraints gbc) {
        this.gbc = gbc;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
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

    public void setActionListener(GameController gc){
        board.setActionListener(gc);
        /*score.setActionListener(gc);
        draw.setActionListener(gc);
        playerView.setActionListener(gc);*/
    }
}
