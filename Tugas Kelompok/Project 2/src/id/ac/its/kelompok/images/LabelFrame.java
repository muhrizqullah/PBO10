package id.ac.its.kelompok.images;

import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;


public class LabelFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	//private final JLabel label1;
	//private final JLabel label2;
	private final JLabel label3;
	
	public LabelFrame() {
		super("Data Mahasiswa");
		setLayout(new FlowLayout());

		// Tipe data label udah aku buat tinggal hapus comment aja
		// cara resize image ikut instansiasi akbarIMG aja 200x200 pixel
		// udah aku crop jadi kotak semua fotonya
		
		// Label 1
		// Chris buat disini

		// Label 2
		// Bunga yang punya kamu dibuat jadi 1 label aja gausah dipisah.

		// Label 3
		ImageIcon  akbarImg = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/akbar.jpg")).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));

		label3 = new JLabel();
		label3.setText("Muhammad Rizqullah A 05111940000178");
		label3.setIcon(akbarImg);
		label3.setHorizontalTextPosition(SwingConstants.CENTER);
		label3.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(label3);
	}
}
