package backend;

import java.util.ArrayList;
import java.util.LinkedList;

public class Piece {
    private final ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    private Vector position;
    private final Board board;
    private final Color color;

    public Piece(Board board, Vector position, Color color){
        this.position = position;
        this.board = board;
        this.color = color;
    }

    public Vector getPosition(){
        return this.position;
    }

    public Color getColor(){
        return this.color;
    }
    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    public void positionChanged(Vector oldPosition, Vector newPosition){
        for (IPositionChangeObserver observer : this.observers)
            observer.positionChanged(oldPosition, newPosition);
    }
    public void positionChanged(){};
    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    public void makeMoves(LinkedList<Vector> moves){
        for(Vector move: moves){
            positionChanged(this.position, move);
            this.position = move;
        }
    }
}
