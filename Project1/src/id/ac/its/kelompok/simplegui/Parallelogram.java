package id.ac.its.kelompok.simplegui;

public class Parallelogram extends Shape {
	private double height;
	private double base;
	private double side;
	
	public Parallelogram(double base, double height, double side) {
		this.height = height;
		this.base = base;
		this.side = side;
	}

	public double getHeight() {
		return height;
	}

	public double getBase() {
		return base;
	}

	public double getSide() {
		return side;
	}
	
	@Override
	public double getArea() {
		return height * base;
	}
	
	@Override
	public double getCircumference() {
		return 2 * (base + side);
	}
}
