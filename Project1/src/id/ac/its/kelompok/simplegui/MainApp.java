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
               JOptionPane.showMessageDialog(null, "Luas Segitiga: " + tri.getArea() + "Keliling Segitiga: " + tri.getCircumference(), "Hasil Perhitungan" , JOptionPane.PLAIN_MESSAGE);
         }
     }
}
