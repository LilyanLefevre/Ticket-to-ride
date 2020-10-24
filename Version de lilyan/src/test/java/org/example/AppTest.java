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
        Game g = new Game(names,"/home/lilyan/Bureau/Ticket-to-Ride/destinations.txt","/home/lilyan/Bureau/Ticket-to-Ride/routes.txt");
        System.out.println(g.toString());
    }
}
