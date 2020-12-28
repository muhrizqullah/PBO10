package id.ac.its.kelompok.snakegame;

import java.util.*;

public class Snake {
    private Direction direction;
    private Point head;
    private int level = 2;
    private int r = 255, g = 212, b = 0;
    private ArrayList<Point> tail;
    
    public Snake(int x, int y) {
        this.head = new Point(x, y);
        this.direction = Direction.RIGHT;
        this.tail = new ArrayList<Point>();
        
        this.tail.add(new Point(0, 0));
        this.tail.add(new Point(0, 0));
        this.tail.add(new Point(0, 0));
    }

    public void move() {
        ArrayList<Point> newTail = new ArrayList<Point>();
        
        for (int i = 0, size = tail.size(); i < size; i++) {
            Point previous = i == 0 ? head : tail.get(i - 1);

            newTail.add(new Point(previous.getX(), previous.getY()));
        }
        
        this.tail = newTail;
        this.head.move(this.direction, 4 * getLevel());
    }
    
    public void addTail() {
        this.tail.add(new Point(-10, -10));
    }
    
    public void turn(Direction d) {       
        if (d.isX() && direction.isY() || d.isY() && direction.isX()) {
           direction = d; 
        }       
    }
    
    public ArrayList<Point> getTail() {
        return this.tail;
    }
    
    public Point getHead() {
        return this.head;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    
}
