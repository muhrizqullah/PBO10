package id.ac.its.kelompok.snakegame;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

public class BigCherry extends Point {
	
	Timer timer;
	TimerTask task;
	public boolean show;
	
	public BigCherry(int x, int y) {
		super(x,y);
		this.show = true;
	}
	
	public BigCherry(Point p) {
		super(p);
	}
	
//	public void Show() {
//		timer = new Timer();
//		task = new TimerTask() {
//			@Override
//			public void run() {
//				System.out.println("show");
//			}
//			
//		};
//		
//		timer.schedule(task, 5*1000);
//		
//		this.show = false;
//	}
//
//	public boolean isShow() {
//		return show;
//	}
//
//	public void setShow(boolean show) {
//		this.show = show;
//	}
//	

}
