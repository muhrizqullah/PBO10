package id.ac.its.kelompok.images;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class LabelFrame extends JFrame{
	private final JLabel nameLabel;
	private final JLabel nrpLabel;
	private final JLabel imageLabel;
	
	public LabelFrame() {
		super("Data Mahasiswa");
		setLayout(new FlowLayout());
		
		Icon img = new ImageIcon(getClass().getResource("/bunga.png"));
		imageLabel = new JLabel(img, SwingConstants.CENTER);
		imageLabel.setToolTipText("Foto Mahasiswa");
		add(imageLabel);
		
		nameLabel = new JLabel("Nama");
		nameLabel.setToolTipText("Nama Mahasiswa");
		add(nameLabel);
		
		nrpLabel = new JLabel("NRP");
		nrpLabel.setToolTipText("NRP Mahasiswa");
		add(nrpLabel);		
	}
}
