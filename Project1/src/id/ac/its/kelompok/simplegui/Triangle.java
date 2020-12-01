package id.ac.its.kelompok.simplegui;

public class Triangle extends Shape{

     private double a;
     private double b;
     private double c;

     public Triangle(double a, double b, double c) {
          this.a = a;
          this.b = b;
          this.c = c;
     }
     
     @Override
     public double getArea() {
          return (a * b)/2;
     }

     @Override
     public double getCircumference() {
          return a+b+c;
     }

     public double getA() {
          return a;
     }

     public double getB() {
          return b;
     }

     public double getC() {
          return c;
     }
     
}
