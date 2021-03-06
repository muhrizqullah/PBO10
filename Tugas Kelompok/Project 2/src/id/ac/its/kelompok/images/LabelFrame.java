package id.ac.its.kelompok.images;

import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;


public class LabelFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private final JLabel label1;
	private final JLabel label2;
	private final JLabel label3;
	
	public LabelFrame() {
		super("Data Mahasiswa");
		setLayout(new FlowLayout());

		// Label 1
		ImageIcon christopherImg = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/christopher.jpg")).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
		
		label1 = new JLabel();
		label1.setText("<html> Nama : Christopher Baptista <br> NRP : 05111940000001</html>");
		label1.setIcon(christopherImg);
		label1.setHorizontalTextPosition(SwingConstants.CENTER);
		label1.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(label1);

		// Label 2
		ImageIcon  bungaImg = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/bunga.png")).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
		label2 = new JLabel("<html> Nama : Bunga Fairuz Wijdan <br> NRP : 05111940000030</html>");
		label2.setIcon(bungaImg);
		label2.setHorizontalTextPosition(SwingConstants.CENTER);
		label2.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(label2);
		

		// Label 3
		ImageIcon  akbarImg = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/akbar.jpg")).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
		label3 = new JLabel("<html> Nama : Muhammad Rizqullah A <br> NRP : 05111940000178</html>");
		label3.setIcon(akbarImg);
		label3.setHorizontalTextPosition(SwingConstants.CENTER);
		label3.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(label3);
	}
}
