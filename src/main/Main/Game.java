package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import GameElements.*;
import Enum.*;

public class Game {
    private ArrayList<Player> players;
    private ArrayList<TrainCard> drawTrainCards;
    private ArrayList<DestinationCard> drawDestinationCards;
    private ArrayList<Route> routes;
    private String destination_file_path;
    private String route_file_path;

    public Game(ArrayList<String> names, String destination_file_path, String route_file_path) {

        // initialisation des cartes colorées
        drawTrainCards = new ArrayList<>();
        for(int i = 0; i < Color.values().length; i++){
            for( int j = 0; j < 14; j++){
                drawTrainCards.add(new TrainCard( Color.values()[i]));
            }
        }

        // initialisation des cartes destinations
        this.destination_file_path = destination_file_path;
        drawDestinationCards = new ArrayList<>();
        readDestinationFromFile("destination");

        // initialisation des routes
        this.route_file_path = route_file_path;
        routes = new ArrayList<>();
        readDestinationFromFile("route");

        // initialisation des joueurs
        players = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            players.add(i,new Player(names.get(i), Color.values()[i], drawDestinationCards, drawTrainCards));
        }

    }

    /**
     * fonction qui lit les cartes destinations dans le fichier au chemin
     * destination_file_path et les ajoute dans la pioche
     */
    private void readDestinationFromFile(String typeOfFile){
        // ouvre le fichier et le lit
        if(typeOfFile.equals("destination")){
            try(BufferedReader br = new BufferedReader(new FileReader(destination_file_path))) {
                String line = br.readLine();
                while (line != null && !line.equals("%")) {
                    processLine(line, typeOfFile);
                    line = br.readLine();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }else if(typeOfFile.equals("route")) {
            try (BufferedReader br = new BufferedReader(new FileReader(route_file_path))) {
                String line = br.readLine();
                while (line != null && !line.equals("%")) {
                    processLine(line, typeOfFile);
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * fonction qui va découper une ligne du fichier et creer la carte destination correspondante
     * et l'ajoute à la pioche
     * @param line ligne du fichier à découper
     */
    private void processLine(String line, String typeOfFile){
        StringTokenizer tokenizer = new StringTokenizer(line, " ");

        Destination dest1 = Destination.valueOf(tokenizer.nextToken());
        Destination dest2 = Destination.valueOf(tokenizer.nextToken());

        if( typeOfFile.equals("destination")) {
            int points = Integer.parseInt(tokenizer.nextToken());
            this.drawDestinationCards.add(new DestinationCard(dest1, dest2, points));
        }else if (typeOfFile.equals("route")){
            Color color = Color.valueOf(tokenizer.nextToken());
            int points = Integer.parseInt(tokenizer.nextToken());
            Boolean isTunel = Boolean.parseBoolean(tokenizer.nextToken());
            int locomotive = Integer.parseInt(tokenizer.nextToken());
            this.routes.add(new Route(dest1, dest2, points, color, isTunel, locomotive));
        }
    }

    /**
     * Answers a string containing a concise, human-readable
     * description of the receiver.
     *
     * @return String a printable representation for the receiver.
     */
    @Override
    public String toString() {
        String str = "PLAYERS :\n";
        for(int i = 0; i < players.size(); i++){
            str += players.get(i).toString();
        }
        str += "\nROUTES : \n";
        for(int i = 0; i < routes.size(); i++){
            str += routes.get(i).toString();
        }
        return str;
    }
}
