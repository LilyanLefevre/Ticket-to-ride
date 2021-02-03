package View.MenuView;

import Controller.WelcomeButtonController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * classe qui représente la fenetre d'accueil qui demande le nb de joueur et leur nom
 */
public class WelcomeFrame extends JFrame {
    private int status = 0;
    private ChoixJoueursPane cjp;
    public boolean fini = false;


    public int getStatus() {
        return status;
    }

    public ChoixJoueursPane getCjp() {
        return cjp;
    }

    public boolean isFini() {
        return fini;
    }

    public WelcomeFrame(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                //on cree un ecouteur sur la croix pour prevenir le programme que le joueur ne veut pas jouer
                WindowListener exitListener = new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                            status = -1;
                    }
                };
                addWindowListener(exitListener);

                // Modifier l'icône  et titre de JFrame
                setTitle("Les aventuriers du rail !");
                Toolkit kit = Toolkit.getDefaultToolkit();
                Image img = kit.getImage(Thread.currentThread().getContextClassLoader().getResource("icon.jpg").getPath());
                setIconImage(img);
                setPreferredSize(new Dimension(400, 200));

                cjp = new ChoixJoueursPane();
                while(!cjp.isFini()){
                    System.out.println("");
                }
                add(cjp);

                getRootPane().setDefaultButton(getCjp().getConfirm());
                getCjp().getChoixJoueurs().requestFocus();

                //place la fenetre au centre
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                Point centerPoint = ge.getCenterPoint();
                int dx = centerPoint.x - 200;
                int dy = centerPoint.y -100;
                setLocation(dx, dy);

                pack();
                setVisible(true);
                fini = true;
            }
        });
    }

    public void setActionListener(WelcomeButtonController wc){
        cjp.setActionListener(wc);
    }

}
