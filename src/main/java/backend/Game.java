package backend;

import gui.App;
import javafx.application.Application;

public class Game {
    public static void main(String[] args) {
        try {
            Application.launch(App.class, args);
            Board board = new Board();
//            System.out.println(board.canMoveTo(new Vector(2,7),new Vector(2,6)));
        } catch (IllegalArgumentException ex) {
            System.out.println("Error has occured: " + ex);
    }
}}
