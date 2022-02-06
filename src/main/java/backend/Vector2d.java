package backend;

import java.util.Objects;

public class Vector2d {
    protected final int x, y;


    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }


    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    @Override
    public boolean equals(Object that){
        if (this == that)
            return true;
        if (!(that instanceof Vector2d))
            return false;
        Vector2d other = (Vector2d) that;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

}

