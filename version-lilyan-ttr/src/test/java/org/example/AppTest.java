package org.example;

import Main.*;



import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void mainTest(){
        ArrayList<String> names = new ArrayList<>(Arrays.asList("lilyan", "eros", "loic", "test"));
        Game g = new Game(names,"./destinations.txt","./routes.txt");
        System.out.println(g.toString());
    }
}
