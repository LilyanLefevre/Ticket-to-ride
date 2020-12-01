package org.testDeroulementJeu;

import model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static void main(String [] args){
        ArrayList<String> names = new ArrayList<>(Arrays.asList("lilyan", "eros", "loic", "test"));
        Game g = new Game(names);
        System.out.println(g.toString());
        g.runGame();
        g.displayScore();
    }
}
