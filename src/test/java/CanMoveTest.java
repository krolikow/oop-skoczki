import backend.Board;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class CanMoveTest {

    @Test
    public void canMoveTest(){
        Board board = new Board();
        LinkedList<backend.Vector> moves = new LinkedList();
        moves.add(new backend.Vector(2,2));
//        moves.add(new backend.Vector(2,4));

        assertFalse(board.canMoveTo(moves,moves.getLast(),new backend.Vector(2,6)));


    }
}
