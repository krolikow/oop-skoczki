package backend;

import gui.App;
import javafx.application.Application;

public class Game {
    public static void main(String[] args) {
        try {
            Application.launch(App.class, args);
        } catch (IllegalArgumentException ex) {
            System.out.println("Error has occured: " + ex);
    }
}}
