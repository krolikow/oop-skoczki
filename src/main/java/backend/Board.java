package backend;

import java.util.*;
import java.lang.*;

public class Board implements IPositionChangeObserver{
    private final Map<Vector2d, Piece> pieces = new LinkedHashMap<>();
    private Color turn;

    public Board() {
        this.turn = Color.WHITE;
        this.initialize();
    }

    public boolean isWithinBoundaries(Vector2d position) {
        return (1 <= position.x) && (position.x <= 8) && (1 <= position.y) && (position.y <= 8);
    }

    public boolean canWalkMove(Vector2d fromPosition, Vector2d toPosition){
        int xDifference = Math.abs(fromPosition.x - toPosition.x);
        int yDifference = Math.abs(fromPosition.y - toPosition.y);
        int sum = xDifference + yDifference;
        int product = xDifference * yDifference;
        return (!(isOccupied(toPosition))) && (sum == 1) && (product == 0);
    }

    public boolean canMoveTo(LinkedList<Vector2d> moves, Vector2d fromPosition, Vector2d toPosition) {

        boolean firstMove = moves.size() < 2;
        boolean check = canWalkMove(fromPosition, toPosition);

        if (!isOccupied(toPosition)){
                for (int i=-1;i<2;i++){
                    for (int j=-1;j<2;j++){
                        Vector2d unitVector2d = new Vector2d(j,i);
                        Vector2d toJumpOver = fromPosition.add(unitVector2d);

                        if((this.isWithinBoundaries(toJumpOver))&&(this.isOccupied(toJumpOver))&&
                                (this.isWithinBoundaries(toJumpOver.add(unitVector2d)))&&
                                (toJumpOver.add(unitVector2d).equals(toPosition))){
                            check = true;
                        }
                    }
                }
            }
        if(firstMove) return check;
        else{
            //second part of statement checks if previous move wasn't walk move
            return ((check)&&(!canWalkMove(fromPosition, moves.get(moves.size() - 2))));
            }
    }

    public Map<Vector2d, Piece> getPieces(){
        return pieces;
    }

    public Piece objectAt(Vector2d position) {
        return pieces.get(position);
    }

    public boolean isOccupied(Vector2d position) {
        Piece element = pieces.get(position);
        return element != null;
    }

    public void initializePieces(Color color){
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 8; j++) {
                Vector2d position;
                if (color.equals(Color.WHITE)) {
                    position = new Vector2d(j, i);
                }
                else{
                    position = new Vector2d(9-j, 9-i);
                }
                Piece piece = new Piece(this, position, color);
                pieces.put(position, piece);
                piece.addObserver(this);
            }
        }
    }

    public void initialize() {
        initializePieces(Color.WHITE);
        initializePieces(Color.BLACK);
    }

    public void setOpposite() {
        switch (turn) {
            case WHITE -> this.turn = Color.BLACK;
            case BLACK -> this.turn = Color.WHITE;
        }
    }

    public Color getTurn() {
        return this.turn;
    }

    public boolean gameOverCheckUtility(Color color){
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 8; j++) {
                Vector2d position;
                if (color.equals(Color.BLACK)) {
                    position = new Vector2d(j, i);
                }
                else{
                    position = new Vector2d(9-j, 9-i);
                }
                if (!(this.pieces.get(position).getColor().equals(color))) return false;
            }
        }
        return true;
    }
    public boolean gameOverCheck(){
        return gameOverCheckUtility(Color.WHITE)&&gameOverCheckUtility(Color.BLACK);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Piece piece = this.pieces.get(oldPosition);
        this.pieces.remove(oldPosition);
        this.pieces.put(newPosition, piece);
    }

    @Override
    public void positionChanged() {
    }

}
