package backend;

import java.util.*;
import java.lang.*;

public class Board implements IPositionChangeObserver{
    private Map<Vector, Piece> pieces = new LinkedHashMap<>();
    private Color turn;

    public Board() {
        this.turn = Color.WHITE;
        this.initialize();
    }

    public boolean isWithinBoundaries(Vector position) {
        return (1 <= position.x) && (position.x <= 8) && (1 <= position.y) && (position.y <= 8);
    }

    public boolean canWalkMove(Vector fromPosition, Vector toPosition){
        int xDifference = Math.abs(fromPosition.x - toPosition.x);
        int yDifference = Math.abs(fromPosition.y - toPosition.y);
        int sum = xDifference + yDifference;
        int product = xDifference * yDifference;
        System.out.println(sum);
        System.out.println(product);
        return (!(isOccupied(toPosition))) && (sum == 1) && (product == 0);
    }

    public boolean canMoveTo(LinkedList<Vector> moves, Vector fromPosition, Vector toPosition) {

            boolean flag = moves.size() < 2;
            if(canWalkMove(fromPosition,toPosition)){
                // first part of statement checks if previous move wasn't walk move
                if (flag) return true;
                else{
                    return (canWalkMove(fromPosition,moves.get(moves.size()-2)));
                }
            }

            if (!isOccupied(toPosition)){
                for (int i=-1;i<2;i++){
                    for (int j=-1;j<2;j++){
                        Vector unitVector = new Vector(j,i);
                        Vector toJumpOver = fromPosition.add(unitVector);

                        if((this.isWithinBoundaries(toJumpOver))&&(this.isOccupied(toJumpOver))&&
                                (this.isWithinBoundaries(toJumpOver.add(unitVector)))&&(toJumpOver.add(unitVector).equals(toPosition))){
                            if (flag) return true;
                            else{
                                return !canWalkMove(fromPosition,moves.get(moves.size()-2));
                            }
                        }
                    }
                }
            }
        return false;
    }

    public Map<Vector, Piece> getPieces(){
        return pieces;
    }
    public Piece objectAt(Vector position) {
        return pieces.get(position);
    }

    public boolean isOccupied(Vector position) {
        Piece element = pieces.get(position);
        return element != null;
    }

    public void initialize() {

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                Vector position = new Vector(j + 1, i + 1);
                Piece piece = new Piece(this, position, Color.WHITE);
                pieces.put(position, piece);
                piece.addObserver(this);
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                Vector position = new Vector(j + 1, 8 - i);
                Piece piece = new Piece(this, position, Color.BLACK);
                pieces.put(position, piece);
                piece.addObserver(this);
            }
        }
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

    public boolean gameOverCheck(){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(this.pieces.get(new Vector(j + 1, i + 1)).getColor().equals(Color.BLACK))) return false;
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(this.pieces.get(new Vector(j + 1, 8 - i)).getColor().equals(Color.WHITE))) return false;
            }
        }

        return true;
    }

    @Override
    public void positionChanged(Vector oldPosition, Vector newPosition) {
        Piece piece = this.pieces.get(oldPosition);
        this.pieces.remove(oldPosition);
        this.pieces.put(newPosition, piece);
    }

    @Override
    public void positionChanged() {
    }

}
