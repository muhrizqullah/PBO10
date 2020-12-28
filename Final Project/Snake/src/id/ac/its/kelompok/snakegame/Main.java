package id.ac.its.kelompok.snakegame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    public Main() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        setTitle("Snake Game");
        setSize(800, 600);

        Game game = new Game();
        add(game);
        game.requestFocusInWindow();
        getContentPane().validate();
        getContentPane().repaint();

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
