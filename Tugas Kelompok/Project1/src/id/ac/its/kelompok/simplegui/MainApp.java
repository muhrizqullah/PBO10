package id.ac.its.kelompok.simplegui;

import javax.swing.JOptionPane;

public class MainApp {
     public static void main(String[] args) {
          String shapeType = JOptionPane
                    .showInputDialog("Masukan Bentuk 2 dimensi yang ingin dihitung luas dan kelilingnya");
          
          if(shapeType.equals("Circle")||shapeType.equals("circle"))
          {
               String radiusString = JOptionPane.showInputDialog("Masukan Radius");
               double radius = Double.parseDouble(radiusString);
               Circle circ = new Circle(radius);
               JOptionPane.showMessageDialog(null, "Luas Lingkaran: " + circ.getArea() + "\nKeliling lingkaran: " + circ.getCircumference(), "Hasil Perhitungan" , JOptionPane.PLAIN_MESSAGE);
          }
          
          else if(shapeType.equals("Triangle")||shapeType.equals("triangle"))
          {
               String aString = JOptionPane.showInputDialog("Masukan sisi 1");
               String bString = JOptionPane.showInputDialog("Masukan sisi 2");
               String cString = JOptionPane.showInputDialog("Masukan sisi 3");

               double a = Double.parseDouble(aString);
               double b = Double.parseDouble(bString);
               double c = Double.parseDouble(cString);

               Triangle tri = new Triangle(a, b, c);
               JOptionPane.showMessageDialog(null, "Luas Segitiga: " + tri.getArea() + "\nKeliling Segitiga: " + tri.getCircumference(), "Hasil Perhitungan" , JOptionPane.PLAIN_MESSAGE);
         }
         else if(shapeType.equals("Rectangle")||shapeType.equals("rectangle"))
         {
               String lengthString = JOptionPane.showInputDialog("Masukan panjang");
               String widthString = JOptionPane.showInputDialog("Masukan lebar");

               double length = Double.parseDouble(lengthString);
               double width = Double.parseDouble(widthString);
               
               Rectangle rec = new Rectangle(length, width);
               JOptionPane.showMessageDialog(null, "Luas Segiempat: " + rec.getArea() + "\nKeliling Segiempat: " + rec.getCircumference(), "Hasil Perhitungan" , JOptionPane.PLAIN_MESSAGE);
         }
         else if(shapeType.equals("Parallelogram")||shapeType.equals("parallelogram"))
         {
        	 	String baseString = JOptionPane.showInputDialog("Masukan alas");
        	 	String heightString = JOptionPane.showInputDialog("Masukan tinggi");
        	 	String sideString = JOptionPane.showInputDialog("Masukan sisi");

        	 	double base = Double.parseDouble(baseString);
        	 	double height = Double.parseDouble(heightString);
        	 	double side = Double.parseDouble(sideString);
        	 	
        	 	Parallelogram par = new Parallelogram(base, height, side);
        	 	JOptionPane.showMessageDialog(null, "Luas Jajar Genjang: " + par.getArea() + "\nKeliling Jajar Genjang: " + par.getCircumference(), "Hasil Perhitungan" , JOptionPane.PLAIN_MESSAGE);
         }

         else
         {
               JOptionPane.showMessageDialog(null, "Masukan bentuk 2 dimensi!\nEx. Circle, Triangle, Rectangle, Parallelogram!",
               "Error!", JOptionPane.ERROR_MESSAGE);
         }
     }
}
