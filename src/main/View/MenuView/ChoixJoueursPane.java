package View.MenuView;

import Controller.WelcomeButtonController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * classe qui représente le panel dans la fenetre d'avant jeu ou on choisit les options (nb joueurs, bots...)
 */
public class ChoixJoueursPane extends JPanel {
    private JButton confirm;
    private JButton confirm2;
    private JButton quit;
    private JComboBox choixJoueurs;
    private boolean fini = false;
    private JTextField nom;
    private JLabel consigne1;
    private JLabel consigne2;

    public JButton getConfirm() {
        return confirm;
    }
    public JButton getConfirm2() {
        return confirm2;
    }

    public JButton getQuit() {
        return quit;
    }

    public JComboBox getChoixJoueurs() {
        return choixJoueurs;
    }

    public boolean isFini() {
        return fini;
    }

    public JTextField getNom() {
        return nom;
    }


    public JLabel getConsigne1() {
        return consigne1;
    }

    public JLabel getConsigne2() {
        return consigne2;
    }

    public ChoixJoueursPane() {
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        /**
         * saisie du nombre de joueurs
         */
        consigne1 = new JLabel("Choisissez le nombre de joueur(s) :");
        consigne2 = new JLabel("Saisissez le nom des joueurs :");
        consigne2.setVisible(false);

        //on ajoute le choix du nb de joueurs
        Integer[] r = new Integer[4];
        r[0] = 1;
        r[1] = 2;
        r[2] = 3;
        r[3] = 4;
        choixJoueurs = new JComboBox<>(r);
        choixJoueurs.setPreferredSize(new Dimension(300,50));
        choixJoueurs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        choixJoueurs.setSelectedIndex(0);

        nom = new JTextField();
        nom.setPreferredSize(new Dimension(100,50));
        nom.setVisible(false);

        confirm = new JButton("Confirmer");
        confirm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        confirm2 = new JButton("Confirmer");
        confirm2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        confirm2.setVisible(false);
        quit = new JButton("Annuler");
        quit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        quit.setVisible(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        add(consigne1,gbc);
        add(consigne2,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        add(confirm,gbc);
        add(confirm2,gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        add(quit,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        add(choixJoueurs,gbc);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(nom,gbc);

        fini = true;
    }

    public void setActionListener(WelcomeButtonController wc) {
        confirm.addActionListener(wc);
        confirm2.addActionListener(wc);
        quit.addActionListener(wc);
    }
}
