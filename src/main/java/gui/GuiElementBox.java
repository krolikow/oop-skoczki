package gui;

import backend.Board;
import backend.Piece;
import backend.Vector2d;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private Image blackPiece, whitePiece;

    private void loadImages() {
        try {
            this.blackPiece = new Image(new FileInputStream("src/main/resources/black-piece.png"));
            this.whitePiece = new Image(new FileInputStream("src/main/resources/white-piece.png"));
        } catch (FileNotFoundException ex) {
            System.out.println("File doesn't exist.");
        }
    }

    public GuiElementBox() {
        loadImages();
    }

    public VBox setImages(Board board, Vector2d position) {

        ImageView elementView;
        VBox elementVBox = new VBox();
        if (board.objectAt(position) != null) {
            Piece piece = board.objectAt(position);
            elementView = switch ((piece).getColor()) {
                case WHITE -> new ImageView(whitePiece);
                case BLACK -> new ImageView(blackPiece);
            };
            elementView.setFitWidth(40);
            elementView.setFitHeight(40);
            elementVBox.getChildren().addAll(elementView);
        }

        elementVBox.setAlignment(Pos.CENTER);

        return elementVBox;

    }
}
