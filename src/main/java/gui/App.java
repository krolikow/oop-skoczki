package gui;
import backend.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.util.LinkedList;


public class App extends Application implements IPositionChangeObserver {
    private Board board;
    private final GridPane grid = new GridPane();
    private final LinkedList<Vector2d> moves = new LinkedList<>();
    private Piece initialPiece;
    private final GuiElementBox elementCreator = new GuiElementBox();

    @Override
    public void init() {
        try{
            this.board = new Board();
        }

        catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            System.exit(-1);
        }
    }

    public void initializeGrid(){

        this.grid.setGridLinesVisible(false);
        this.grid.setGridLinesVisible(true);
        this.grid.getRowConstraints().clear();
        this.grid.getColumnConstraints().clear();


        for (int i = 1; i <= 8; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(70));

            Label xAxis = new Label( String.format("%c", (char)(96+i)));
            GridPane.setHalignment(xAxis, HPos.CENTER);
            grid.add(xAxis, i, 0, 1, 1);
        }

        for (int i = 1; i <= 8; i++) {
            grid.getRowConstraints().add(new RowConstraints(70));

            Label yAxis = new Label(String.format("%d",i));
            GridPane.setHalignment(yAxis, HPos.CENTER);
            grid.add(yAxis, 0, i, 1, 1);
        }

        for (int i=1; i<= 8; i++) {
            for (int j=1; j<= 8; j++) {
                Vector2d currentPosition = new Vector2d(j, i);
                Pane pane = new Pane();
                grid.add(pane,j,i,1,1);
                setPaneColor(pane,j,i);
                VBox element;
                element = elementCreator.setImages(board,currentPosition);
                GridPane.setHalignment(element, HPos.CENTER);
                grid.add(element, j, i,1,1);

                element.setOnMouseClicked(click -> {
                    Vector2d elementsPosition = new Vector2d(GridPane.getColumnIndex(pane),GridPane.getRowIndex(pane));

                    if (moves.isEmpty()){
                        if ((this.board.isOccupied(elementsPosition))&&
                                (this.board.getPieces().get(elementsPosition).getColor().equals(this.board.getTurn()))){
                            pane.setBackground(new Background(new BackgroundFill(Color.rgb(0, 255, 100), CornerRadii.EMPTY, Insets.EMPTY)));
                        }

                        Piece potentialInitialPiece = this.board.getPieces().get(elementsPosition);
                        if ((potentialInitialPiece!=null)&&(potentialInitialPiece.getColor().equals(this.board.getTurn()))){
                            initialPiece = this.board.getPieces().get(elementsPosition);
                            moves.add(elementsPosition);
                        }
                    }

                    else{
                        if (initialPiece!=null){
                            Vector2d lastMove = moves.getLast();
                            if(this.board.canMoveTo(moves,lastMove,elementsPosition)){
                                moves.add(elementsPosition);
                                pane.setBackground(new Background(new BackgroundFill(Color.rgb(0, 102, 0), CornerRadii.EMPTY, Insets.EMPTY)));
                            }
                            else{
                                pane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("You can't make move to " + elementsPosition);
                                alert.showAndWait();
                                setPaneColor(pane, GridPane.getColumnIndex(pane),GridPane.getRowIndex(pane));
                            }
                        }
                    }
                });
            }
        }

        Label label = new Label("y\\x");
        grid.add(label, 0, 0, 1, 1);
        grid.getColumnConstraints().add(new ColumnConstraints(70));
        grid.getRowConstraints().add(new RowConstraints(70));
        GridPane.setHalignment(label, HPos.CENTER);
    }

    public void setPaneColor(Pane pane,int i, int j){
        if((i+j)%2==0){
            pane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 153), CornerRadii.EMPTY, Insets.EMPTY)));
        }
        else{
            pane.setBackground(new Background(new BackgroundFill(Color.rgb(178, 255, 102), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {}

    public void updateTurn(VBox box){
        Label whoseTurn = new Label("Turn: " + board.getTurn());
        box.getChildren().add(whoseTurn);
    }
    public VBox createVBoxInterface() {
        Label whoseTurn = new Label("Turn: " + board.getTurn());
        Button startButton = new Button("Start");
        VBox hBoxInterface = new VBox( startButton,whoseTurn);
        hBoxInterface.setAlignment(Pos.CENTER);
        hBoxInterface.setSpacing(15);

        startButton.setOnAction(click -> {
            board.setOpposite();
            initialPiece.makeMoves(moves);
            moves.clear();
            initializeGrid();
            hBoxInterface.getChildren().clear();
            hBoxInterface.getChildren().addAll(startButton);

            if (this.board.gameOverCheck()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(this.board.getTurn() + " won the game! Congratulations!");
                alert.showAndWait();
            }
            this.updateTurn(hBoxInterface);
        });

        return hBoxInterface;
    }

    @Override
    public void positionChanged() {
        Platform.runLater(() -> {
            this.grid.getChildren().clear();
            initializeGrid();
        });
    }


    @Override
    public void start(Stage primaryStage) {
        this.grid.setGridLinesVisible(true);
        VBox vBoxContent = createVBoxInterface();
        initializeGrid();
        HBox mainContent = new HBox(this.grid, vBoxContent);
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setSpacing(30);
        HBox wrapper = new HBox(mainContent);
        wrapper.setPadding(new Insets(10));
        primaryStage.setScene(new Scene(wrapper));
        primaryStage.show();
    }
}
