package org.testDeroulementJeu;

import Model.Game;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static void main(String [] args){

        ArrayList<String> names = new ArrayList<>(Arrays.asList("lilyan", "eros", "loic", "View"));
        Game g = new Game(names);
        System.out.println(g.toString());
        g.runGame();
        System.out.println(g.scoreToString());
    }
}
