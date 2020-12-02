package id.ac.its.kelompok.simplegui;

public class Rectangle extends Shape{
	private double length;
	private double width;
	
	public Rectangle(double length, double width) {
		this.length = length;
		this.width = width;
	}
	
	public double getLength() {
		return length;
	}

	
	public double getWidth() {
		return width;
	}

	@Override
	public double getArea() {
		return length * width;
	}
	
	@Override
	public double getCircumference() {
		return 2 * (length + width);
	}
}
