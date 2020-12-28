package id.ac.its.kelompok.snakegame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class Game extends JPanel {
 
    private static final long serialVersionUID = 1L;
    private Timer timer;
    private Snake snake;
    private Point cherry;
    private int points = 0;
    private int best;
    private BufferedImage image;
    private GameStatus status;
    private boolean didLoadCherryImage = true;

    private static Font FONT_M = new Font("ArcadeClassic", Font.PLAIN, 24);
    private static Font FONT_M_ITALIC = new Font("ArcadeClassic", Font.ITALIC, 24);
    private static Font FONT_L_ITALIC = new Font("ArcadeClassic", Font.ITALIC, 60);
    private static Font FONT_L = new Font("ArcadeClassic", Font.PLAIN, 84);
    private static Font FONT_XL = new Font("ArcadeClassic", Font.PLAIN, 150);
    private static int WIDTH = 760;
    private static int HEIGHT = 520;
    private static int DELAY = 50;
    private static HighScore[] h=HighScore.getHighScores();
    
    public Game() {
        try {
            image = ImageIO.read(new File("cherry.png"));
        } catch (IOException e) {
            didLoadCherryImage = false;
        }

        addKeyListener(new KeyListener());
        setFocusable(true);
        setBackground(new Color(13, 24, 33));
        setDoubleBuffered(true);

        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        status = GameStatus.NOT_STARTED;

        repaint();
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void update() {
        snake.move();

        if (cherry != null && snake.getHead().intersects(cherry, 20)) {
            snake.addTail();
            cherry = null;
            points += 1 * snake.getLevel();
        }

        if (cherry == null) {
            spawnCherry();
        }

        checkForGameOver();
    }

    private void reset() {
        points = 0;
        cherry = null;
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        setStatus(GameStatus.RUNNING);
    }
    
    private void showHighscore() {
    	setStatus(GameStatus.SHOW_HIGHSCORE);
    	repaint();
    }
    
    private void setStatus(GameStatus newStatus) {
        switch(newStatus) {
            case RUNNING:
                timer = new Timer();
                timer.schedule(new GameLoop(), 0, DELAY);
                break;
            case PAUSED:
                timer.cancel();
            case GAME_OVER:
                timer.cancel();
                best = points > best ? points : best;
                break;
        }

        status = newStatus;
        if(status == GameStatus.SHOW_HIGHSCORE)
        	System.out.println("Next Game");
    }

    private void togglePause() {
        setStatus(status == GameStatus.PAUSED ? GameStatus.RUNNING : GameStatus.PAUSED);
        repaint();
    }

    private void toggleSettings() {
        setStatus(status == GameStatus.SETTINGS ? GameStatus.NOT_STARTED : GameStatus.SETTINGS);
        repaint();
    }

    private void checkForGameOver() {
        Point head = snake.getHead();
        boolean hitBoundary = head.getX() <= 20
            || head.getX() >= WIDTH + 10
            || head.getY() <= 40
            || head.getY() >= HEIGHT + 30;

        boolean ateItself = false;

        for(Point t : snake.getTail()) {
            ateItself = ateItself || head.equals(t);
        }

        if (hitBoundary || ateItself) {
            setStatus(GameStatus.GAME_OVER);
        }
    }

    public void drawCenteredString(Graphics g, String text, Font font, int y) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (WIDTH - metrics.stringWidth(text)) / 2;

        g.setFont(font);
        g.drawString(text, x, y);
    }

    private void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Warna Tulisan, Ubah juga line 186
        g2d.setColor(new Color(179, 240, 219));
        g2d.setFont(FONT_M);

        if (status == GameStatus.NOT_STARTED) {
          drawCenteredString(g2d, "SNAKE", FONT_XL, 200);
          g2d.setColor(new Color(185, 49, 79));
          drawCenteredString(g2d, "GAME", FONT_XL, 300);
          g2d.setColor(new Color(97, 168, 156));
          drawCenteredString(g2d, "Tekan keyboard untuk mulai main!", FONT_M_ITALIC, 360);
          drawCenteredString(g2d, "Tekan Escape untuk pengaturan!", FONT_M_ITALIC, 390);
          return;
        }

        if (status == GameStatus.SETTINGS) {
            System.out.println("Settings");
            drawCenteredString(g2d, "SETTINGS", FONT_L_ITALIC, 75);
            g2d.setColor(new Color(97, 168, 156));
            drawCenteredString(g2d, "Tekan enter untuk mulai main!", FONT_M_ITALIC, 560);
            return;
        }

        Point p = snake.getHead();
        best = h[0].getScore();

        g2d.setColor(new Color(90, 182, 193)); //Wana tulisan SCORE
        g2d.drawString("SCORE: " + String.format ("%04d", points), 20, 30);
        if(best >= points)
            g2d.drawString("BEST: " + String.format ("%04d", best), 660, 30);
        if(best < points)
            g2d.drawString("BEST: " + String.format ("%04d", points), 660, 30);
        if (status == GameStatus.SHOW_HIGHSCORE) {
        	
        	System.out.println("Show HighScores");

            g2d.setColor(new Color(185, 49, 79));
        	g2d.drawString("Name", 100,80);
			g2d.drawString("Score", 320, 80);
			g2d.drawLine(60, 90, 400, 90);
            g2d.setFont(new Font("Arial", Font.BOLD, 22));
            
            
			for (int i=0; i<h.length; i++)
				if (HighScore.getHighScores()[i].getScore()>0)
				{
                    g2d.setColor(new Color(239, 199, 95)); // Warna Nama
					g2d.drawString(new Integer(i+1).toString()+".", 65, 115+i*40);
					g2d.drawString(h[i].getName(), 100, 115+i*40);
					g2d.drawString(new Integer(h[i].getScore()).toString(), 320, 115+i*40);
				}
            
            g2d.setColor(new Color(185, 49, 79)); // Warna Tulisan
            g2d.drawString("Tekan keyboard untuk main lagi!", 250, 535);
			return;
        }
        
        if (cherry != null) {
          if (didLoadCherryImage) {
            g2d.drawImage(image, cherry.getX(), cherry.getY(), 60, 60, null);
          } else {
            g2d.setColor(new Color(209, 52, 91)); // Warna Cherry
            g2d.fillOval(cherry.getX(), cherry.getY(), 15, 15);
            g2d.setColor(new Color(185, 49, 79)); // Warna Tulisan
          }
        }

        if (status == GameStatus.GAME_OVER) {
        	System.out.println("Gameover");
            drawCenteredString(g2d, "Yah, Nabrak!", FONT_L, 300);
            g2d.setColor(new Color(239, 199, 95));
            drawCenteredString(g2d, "Tekan ENTER untuk melihat Top Score", FONT_M_ITALIC, 350);
            g2d.setColor(new Color(185, 49, 79));
           
			return;
        }

        if (status == GameStatus.PAUSED) {
            System.out.println("Paused");
            drawCenteredString(g2d, "Loh, kok berhenti?", FONT_L_ITALIC, 300);
            return;
        }
        
        //Snake
        g2d.setColor(new Color(snake.getR(), snake.getG(), snake.getB()));
        g2d.fillRect(p.getX(), p.getY(), 15, 15);

        for(int i = 0, size = snake.getTail().size(); i < size; i++) {
            Point t = snake.getTail().get(i);

            g2d.fillRect(t.getX(), t.getY(), 15, 15);
        }

        //OutLine
        g2d.setColor(new Color(39, 66, 64));
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(20, 40, WIDTH, HEIGHT);
    }

    public void spawnCherry() {
        cherry = new Point((new Random()).nextInt(WIDTH - 60) + 20,
            (new Random()).nextInt(HEIGHT - 60) + 40);
    }

    private class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if (status == GameStatus.SHOW_HIGHSCORE)
            {
            	reset();
            }

            if (status == GameStatus.RUNNING) {
                switch(key) {
                    case KeyEvent.VK_LEFT: snake.turn(Direction.LEFT); break;
                    case KeyEvent.VK_RIGHT: snake.turn(Direction.RIGHT); break;
                    case KeyEvent.VK_UP: snake.turn(Direction.UP); break;
                    case KeyEvent.VK_DOWN: snake.turn(Direction.DOWN); break;
                }
            }

            if(status == GameStatus.SETTINGS)
            {
                if(key == KeyEvent.VK_ENTER)
                    setStatus(GameStatus.RUNNING);
            }

            if (status == GameStatus.NOT_STARTED) {
                if(key == KeyEvent.VK_ESCAPE)
                    toggleSettings();
                else
                    setStatus(GameStatus.RUNNING);
            }

            if (status == GameStatus.GAME_OVER && key == KeyEvent.VK_ENTER) {
            	if (points>HighScore.getHighScores()[9].getScore())
            		{
            			String name=JOptionPane.showInputDialog(null, "Wah masuk Top Score, Hebat!\nTulis namamu dong.\nNote: Oiya, maksimal 10 karakter ya!",
            					"Yeay!", JOptionPane.INFORMATION_MESSAGE);
                        if (name!=null)
                        {
            				HighScore.addHighScore(new HighScore(points,(name.length()>10)?name.substring(0, 10):name));
                            h = HighScore.getHighScores();
                        }
            		}
            	
            	showHighscore();
            	System.out.println("NewGame");
            }

            if (key == KeyEvent.VK_ESCAPE) {
                togglePause();
            }
        }
    }

    private class GameLoop extends java.util.TimerTask {
        public void run() {
            update();
            repaint();
        }
    }
}
