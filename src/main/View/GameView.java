package View;

import Controller.JButtonController;
import Controller.RouteController;
import Model.Game;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

/**
 * classe qui représente la vue du jeu
 */
public class GameView extends JFrame{
    private Game g;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JPanel container;
    private BoardPane board; //le plateau avec les villes et les routes
    private ScorePane score; //les scores
    private DrawPane draw; //la pioche
    private ActualPlayerPane playerView; //la vue du joueur actuel
    private ButtonPane buttons; //les boutons
    public boolean fini = false;

    public BoardPane getBoard() {
        return board;
    }

    public DrawPane getDraw() {
        return draw;
    }

    public ActualPlayerPane getPlayerView() {
        return playerView;
    }

    public ButtonPane getButtons() {
        return buttons;
    }

    public void setGbc(GridBagConstraints gbc) {
        this.gbc = gbc;
    }


    public GameView(final Game ga){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Modifier l'icône de JFrame
                Toolkit kit = Toolkit.getDefaultToolkit();
                Image img = kit.getImage(Thread.currentThread().getContextClassLoader().getResource("icon.jpg").getPath());
                setIconImage(img);
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
                playerView.setPreferredSize(new Dimension(board.getWidth()+score.getWidth(), board.getHeight()+draw.getHeight()));
                container.add(playerView, gbc);

                //boutons
                gbc.gridx = 1;
                gbc.gridy = 2;
                gbc.gridheight = 1;
                gbc.insets = new Insets(0, 0, 0, 0);
                buttons = new ButtonPane();
                container.add(buttons, gbc);

                playerView.getObjButton().setPreferredSize(buttons.getPiocherD().getPreferredSize());

                setLocationRelativeTo(null);
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                pack();
                setVisible(true);
                fini = true;
            }
        });
    }

    /**
     * fonction qui ajoute un controller aux panels qui composent la vue
     *
     * @param gc le controller
     */
    public void setActionListener(JButtonController gc){
        board.setActionListener(gc);
        buttons.setActionListener(gc);
        draw.setActionListener(gc);
        playerView.setActionListener(gc);
    }

    /**
     * fonction qui ajoute un listener de souris aux panels qui en ont besoin
     *
     * @param mc le listener
     */
    public void setRouteListener(RouteController mc){
        board.setMouseListener(mc);
    }

    /**
     * fonction qui rafraichit la vue
     *
     * @param g Game le jeu
     * @param jc le controller de boutons
     */
    public void updateView(Game g,JButtonController jc){
        score.updateScore(g.getPlayers());
        playerView.updateCard(g.getCurrentPlayer());
        draw.updateCard();
        draw.setActionListener(jc);
    }
}
