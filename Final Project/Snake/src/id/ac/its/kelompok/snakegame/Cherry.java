package id.ac.its.kelompok.snakegame;

import java.awt.Color;
import java.awt.Graphics;

public class Cherry extends Point {
	
	public Cherry(int x, int y) {
		super(x,y);
	}
	
	public Cherry(Point p) {
		super(p);
	}
	
	public void Draw(Graphics g) {
		g.setColor(new Color(209, 52, 91)); // Warna Cherry
        g.fillOval(getX(), getY(), 15, 15);
	}

	
	
	
	
}
