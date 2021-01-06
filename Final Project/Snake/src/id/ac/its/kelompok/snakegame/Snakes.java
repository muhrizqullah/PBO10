package id.ac.its.kelompok.snakegame;

import java.util.ArrayList;
import java.awt.*;

public class Snakes extends Point {
    private Direction direction;
    private Point head;
    private ArrayList<Point> tail;
    private Color snakeColor;

    public Snakes(int x, int y) {
        super(x, y);
        // Menginisiasi Snake dengan panjang 4
        this.head = new Point(getX(), getY());
        this.tail = new ArrayList<Point>();
        this.direction = Direction.RIGHT;

        this.tail.add(new Point(0, 0));
        this.tail.add(new Point(0, 0));
        this.tail.add(new Point(0, 0));
    }

    public Point getHead() {
        return head;
    }

    public ArrayList<Point> getTail() {
        return tail;
    }

    public Color getColor() {
        return snakeColor;
    }

    public void setColor(Color color) {
        this.snakeColor = color;
    }

    public void turn(Direction d) {
        if (d.isX() && direction.isY() || d.isY() && direction.isX()) {
            direction = d;
        }
    }

    public void move(int level) {
        ArrayList<Point> newTail = new ArrayList<Point>();

        for (int i = 0, size = tail.size(); i < size; i++) {
            Point previous = i == 0 ? head : tail.get(i - 1);

            newTail.add(new Point(previous.getX(), previous.getY()));
        }

        this.tail = newTail;
        this.head.move(this.direction, 4 * level);
    }

    public void addTail() {
        this.tail.add(new Point(-10, -10));
    }

    public void drawSnake(Graphics2D g){
        int pixel = 17;
        g.setColor(snakeColor);
        g.fillRect(head.getX(), head.getY(), pixel, pixel);

        for(int i = 0, size = getTail().size(); i < size; i++){
            Point t = getTail().get(i);
            g.fillRect(t.getX(), t.getY(), pixel, pixel);
        }

    }

}
