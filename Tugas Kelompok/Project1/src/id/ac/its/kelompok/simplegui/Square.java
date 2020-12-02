package id.ac.its.kelompok.simplegui;

public class Square extends Rectangle {
	private double side;
	
	public Square(double length, double width, double side) {
		super(length, width);
		this.side = side;
	}

	public double getSide() {
		return side;
	}

	@Override
	public double getArea() {
		return side * side;
	}
	
	@Override
	public double getCircumference() {
		return 4 * side;
	}
}
