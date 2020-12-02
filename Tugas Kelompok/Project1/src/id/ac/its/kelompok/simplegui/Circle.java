package id.ac.its.kelompok.simplegui;

public class Circle extends Shape{
     
     private double radius;
     

     public Circle(double radius) {
          this.radius = radius;
     }

     public double getRadius() {
          return radius;
     }

     @Override
     public double getArea(){
          return PHI * radius * radius;
     }

	@Override
	public double getCircumference() {
		return 2 * PHI * radius;
	}
}
