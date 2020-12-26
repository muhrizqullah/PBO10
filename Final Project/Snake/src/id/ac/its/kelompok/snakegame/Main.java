package id.ac.its.kelompok.snakegame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
	
	private JPanel menu;
	private JButton start;
	
    public Main() {
        initUI();
    }

    private void initUI() {
    	setLayout(new BorderLayout());
    	menu = new JPanel();
    	menu.setPreferredSize(new Dimension(800,600));
    	
    	start = new JButton("Start");
    	start.setBackground(Color.GREEN);
    	start.addMouseListener(new MouseListener() {
    		public void mousePressed(MouseEvent e) {
    			remove(start);
    			getContentPane().remove(menu);
    	        
    			Game game = new Game();
    			add(game);
    			game.requestFocusInWindow();
    			getContentPane().validate();
    			getContentPane().repaint();
    	        
    		}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
    	});
    	
    	JPanel buttonsPanel = new JPanel();
    	buttonsPanel.setLocation(100, 500);
    	buttonsPanel.setBackground(Color.BLACK);
    	buttonsPanel.add(start); 
    	buttonsPanel.repaint();
    	
    	menu.add(buttonsPanel);
    	 
    	//  add(new Game());
    	
    	add(menu);
        setTitle("Snake Game");
        setSize(800, 600);

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
        });
    }
}
