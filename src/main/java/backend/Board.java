package backend;

import java.util.*;
import java.lang.*;

public class Board implements IPositionChangeObserver {
    private final Map<Vector2d, Piece> pieces = new LinkedHashMap<>();
    private Color turn;

    public Board() {
        this.turn = Color.WHITE;
        this.initialize();
    }

    public boolean isWithinBoundaries(Vector2d position) {
        return (1 <= position.x) && (position.x <= 8) && (1 <= position.y) && (position.y <= 8);
    }

    public boolean canWalkMove(Vector2d fromPosition, Vector2d toPosition) {
        int xDifference = Math.abs(fromPosition.x - toPosition.x);
        int yDifference = Math.abs(fromPosition.y - toPosition.y);
        int sum = xDifference + yDifference;
        int product = xDifference * yDifference;
        return (!(isOccupied(toPosition))) && (sum == 1) && (product == 0);
    }

    public boolean canJumpMove(Vector2d fromPosition, Vector2d toPosition) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Vector2d unitVector2d = new Vector2d(j, i);
                Vector2d toJumpOver = fromPosition.add(unitVector2d);

                if ((this.isWithinBoundaries(toJumpOver)) && (this.isOccupied(toJumpOver)) &&
                        (this.isWithinBoundaries(toJumpOver.add(unitVector2d))) &&
                        ((toJumpOver.add(unitVector2d)).equals(toPosition))) {
                    return true;
                }
            }
        }
        return false;
    }

    public Move getMove(LinkedList<Vector2d> moves, LinkedList<Move> moveTypes, Vector2d fromPosition, Vector2d toPosition) {

        if (isOccupied(toPosition)) return Move.NONE;

        boolean firstMove = moves.size() < 2;
        boolean checkWalk = canWalkMove(fromPosition, toPosition);
        boolean checkJump = canJumpMove(fromPosition, toPosition);


        if (firstMove) {
            if (checkWalk) return Move.WALK;
            else if (checkJump) return Move.JUMP;
        } else {
            if ((checkJump) && (!(moveTypes.get(moveTypes.size() - 1).equals(Move.WALK)))) {
                return Move.JUMP;
            }
        }
        return Move.NONE;
    }

    public Map<Vector2d, Piece> getPieces() {
        return pieces;
    }

    public Piece objectAt(Vector2d position) {
        return pieces.get(position);
    }

    public boolean isOccupied(Vector2d position) {
        Piece element = pieces.get(position);
        return element != null;
    }

    public void initializePieces(Color color) {
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 8; j++) {
                Vector2d position;
                if (color.equals(Color.WHITE)) {
                    position = new Vector2d(j, i);
                } else {
                    position = new Vector2d(9 - j, 9 - i);
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

    public void initializeToShowGameOverUtility(Color color, int y, int x) {
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 8; j++) {
                Vector2d position;
                if (color.equals(Color.BLACK)) {
                    if ((i == y) && (j == x)) {
                        position = new Vector2d(x, y + 1);
                    } else {
                        position = new Vector2d(j, i);
                    }
                } else {
                    if ((9 - i == y) && (9 - j == x)) {
                        position = new Vector2d(x, y - 1);
                    } else {
                        position = new Vector2d(9 - j, 9 - i);
                    }
                }

                Piece piece = new Piece(this, position, color);
                pieces.put(position, piece);
                piece.addObserver(this);
            }
        }
    }

    public void initializeToShowGameOver() { // for testing purposes
        initializeToShowGameOverUtility(Color.BLACK, 2, 4);
        initializeToShowGameOverUtility(Color.WHITE, 7, 4);
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

    public boolean gameOverCheckUtility(Color color) {
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 8; j++) {
                Vector2d position;
                if (color.equals(Color.BLACK)) {
                    position = new Vector2d(j, i);
                } else {
                    position = new Vector2d(9 - j, 9 - i);
                }
                if (this.pieces.get(position) != null) {
                    if (!(this.pieces.get(position).getColor().equals(color))) return false;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean gameOverCheck() {
        return (gameOverCheckUtility(Color.WHITE)) || (gameOverCheckUtility(Color.BLACK));
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
