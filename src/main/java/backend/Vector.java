package backend;

import java.util.Objects;

public class Vector {
    public final int x, y;


    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }


    public boolean precedes(Vector that) {
        return this.x <= that.x && this.y <= that.y ;
    }


    public boolean follows(Vector that) {
        return this.x >= that.x && this.y >= that.y;
    }


    public Vector upperRight(Vector that) {
        return new Vector(Math.max(this.x, that.x), Math.max(this.y, that.y));
    }


    public Vector lowerLeft(Vector that) {
        return new Vector(Math.min(this.x, that.x), Math.min(this.y, that.y));
    }


    public Vector add(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }


    public Vector subtract(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y);
    }

    @Override
    public boolean equals(Object that){
        if (this == that)
            return true;
        if (!(that instanceof Vector))
            return false;
        Vector other = (Vector) that;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public Vector opposite() {
        return new Vector(-this.x, -this.y);
    }

    public Vector moveUp(){
        return new Vector(this.x,this.y+1);
    }

//    public Vector moveDown(){
//        return new Vector(position.x,position.y-1);
//    }
//
//    public Vector moveRight(){
//        return new Vector(position.x+1,position.y);
//    }
//
//    public Vector moveLeft(){
//        return new Vector(this..x-1,position.y);
//    }
}

