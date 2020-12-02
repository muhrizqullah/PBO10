package id.ac.its.kelompok.simplegui;

public class Diamond extends Shape {
	private double side;
	private double diagonal1;
	private double diagonal2;

	public Diamond(double side, double diagonal1, double diagonal2) {
		super();
		this.side = side;
		this.diagonal1 = diagonal1;
		this.diagonal2 = diagonal2;
	}

	public double getSide() {
		return side;
	}
	
	public double getDiagonal1() {
		return diagonal1;
	}


	public double getDiagonal2() {
		return diagonal2;
	}

	@Override
	public double getArea() {
		return 0.5 * (diagonal1 * diagonal2);
	}
	
	@Override
	public double getCircumference() {
		return 4 * side;
	}
}
