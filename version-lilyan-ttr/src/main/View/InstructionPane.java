package View;

import Model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InstructionPane extends JPanel {
    Game g;
    JButton jouerButton;
    JButton jouer;

    public InstructionPane(Game g) {
        this.g = g;

        setBackground(new Color(0, 0, 0, 64));
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));


    }
}
