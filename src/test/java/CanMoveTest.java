import backend.*;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class CanMoveTest {

    @Test
    public void canMoveTest(){
        Board board = new Board();
        LinkedList<Vector2d> moves = new LinkedList();
        moves.add(new Vector2d(2,2));
        moves.add(new Vector2d(2,4));

        assertFalse(board.canMoveTo(moves,moves.getLast(),new Vector2d(2,6)));


    }
}
