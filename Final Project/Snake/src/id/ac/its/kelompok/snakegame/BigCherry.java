package id.ac.its.kelompok.snakegame;

import java.awt.Color;
import java.awt.Graphics;

public class BigCherry extends Point {
	private int spawn_time;
	private boolean big;
	
	public BigCherry(int x, int y) {
		super(x,y);
		this.big = false;
	}
	
	public BigCherry(Point p) {
		super(p);
	}
	
	public void Draw(Graphics g, int time) {
		g.setColor(new Color(212, 175, 55)); // Warna Cherry
		if(isBig())	{
			g.fillOval(getX(), getY(), 25, 25);
			if(time % 10 == 0)
				setBig(false);
		}
		else {
			g.fillOval(getX(), getY(), 20, 20);
			if(time % 10 == 0)
				setBig(true);
			//setBig(true);
		}
        
	}

	public int getSpawn_time() {
		return spawn_time;
	}

	public void setSpawn_time(int spawn_time) {
		this.spawn_time = spawn_time;
	}

	public boolean isBig() {
		return big;
	}

	public void setBig(boolean big) {
		this.big = big;
	}
	
	

}
