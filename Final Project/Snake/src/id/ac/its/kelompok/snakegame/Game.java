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
        setBackground(Color.black);
        setDoubleBuffered(true);

        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        status = GameStatus.NOT_STARTED;
        repaint();
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        
//        if(status == GameStatus.SHOW_HIGHSCORE)
//        	System.out.println("show hc");
        render(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void update() {
        snake.move();

        if (cherry != null && snake.getHead().intersects(cherry, 20)) {
            snake.addTail();
            cherry = null;
            points++;
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

        g2d.setColor(new Color(53, 220, 8));
        g2d.setFont(FONT_M);

        if (status == GameStatus.NOT_STARTED) {
          drawCenteredString(g2d, "SNAKE", FONT_XL, 200);
          drawCenteredString(g2d, "GAME", FONT_XL, 300);
          drawCenteredString(g2d, "Tekan keyboard untuk mulai main!", FONT_M_ITALIC, 330);

          return;
        }

        Point p = snake.getHead();
        best = h[0].getScore();
        g2d.drawString("SCORE: " + String.format ("%04d", points), 20, 30);
        g2d.drawString("BEST: " + String.format ("%04d", best), 660, 30);
        
        if (status == GameStatus.SHOW_HIGHSCORE) {
        	
        	System.out.println("Show HighScores");

        	g2d.drawString("Name", 100,80);
			g2d.drawString("Score", 320, 80);
			g2d.drawLine(60, 90, 400, 90);
            g2d.setFont(new Font("Arial", Font.BOLD, 22));
            
            
			for (int i=0; i<h.length; i++)
				if (HighScore.getHighScores()[i].getScore()>0)
				{
					g2d.drawString(new Integer(i+1).toString()+".", 65, 115+i*40);
					g2d.drawString(h[i].getName(), 100, 115+i*40);
					g2d.drawString(new Integer(h[i].getScore()).toString(), 320, 115+i*40);
				}
            
            g2d.drawString("Tekan keyboard untuk main lagi!", 250, 535);
			return;
        }
        
        if (cherry != null) {
          if (didLoadCherryImage) {
            g2d.drawImage(image, cherry.getX(), cherry.getY(), 60, 60, null);
          } else {
            g2d.setColor(Color.RED);
            g2d.fillOval(cherry.getX(), cherry.getY(), 10, 10);
            g2d.setColor(new Color(53, 220, 8));
          }
        }

        if (status == GameStatus.GAME_OVER) {
        	System.out.println("Gameover");
            drawCenteredString(g2d, "Tekan ENTER untuk melihat Top Score", FONT_M_ITALIC, 330);
            drawCenteredString(g2d, "Yah, Nabrak!", FONT_L, 300);
           
			return;
        }

        if (status == GameStatus.PAUSED) {
            System.out.println("Paused");
            drawCenteredString(g2d, "Loh, kok berhenti?", FONT_M_ITALIC, 300);
            return;
        }
        
        
        
        g2d.setColor(new Color(74, 245, 14));
        g2d.fillRect(p.getX(), p.getY(), 10, 10);

        for(int i = 0, size = snake.getTail().size(); i < size; i++) {
            Point t = snake.getTail().get(i);

            g2d.fillRect(t.getX(), t.getY(), 10, 10);
        }

        g2d.setColor(new Color(71, 128, 0));
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

            if (status == GameStatus.NOT_STARTED) {
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
