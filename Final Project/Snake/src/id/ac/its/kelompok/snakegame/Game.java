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
    private Timer timerBigCherry;
    private Snakes snake;
    private Point cherry;
    private BigCherry bigcherry;
    private int pick_color;
    private int points = 0;
    private int best;
    private int cherry_count;
    private BufferedImage image;
    private GameStatus status;
    private boolean didLoadCherryImage = true;
    private int level;
    private String strlevel;
    //private String biteFile;
    
    //private SoundEffect bite = new SoundEffect();
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

        status = GameStatus.NOT_STARTED;
        snake = new Snakes(WIDTH / 2, HEIGHT / 2);
        pick_color = 1;
        snake.setColor(Color.BLUE);
        level = 1;
        strlevel = "Mudah";
        cherry_count = 1;
        bigcherry = null;
        repaint();
        
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        render(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void update() {
    	
        snake.move(level);
        //biteFile = ".//bite.wav";
        if (cherry != null && snake.getHead().checkIntersects(cherry, 12)) {
            //bite.setFile(biteFile);
            //bite.play();
            snake.addTail();
            cherry = null;
            points += 1 * level;
        }
        
        if (bigcherry != null && snake.getHead().checkIntersects(bigcherry, 21)) {
            //bite.setFile(biteFile);
            //bite.play();
            snake.addTail();
            bigcherry = null;
            points += 3 * level;
            
        }

        if (cherry == null && bigcherry == null && cherry_count % 5 != 0) {
            spawnCherry();
            cherry_count++;
        }
        
        if(bigcherry == null && cherry == null && cherry_count % 5 == 0) {
        	spawnBigCherry();
        	cherry_count++;
        }
        
       // System.out.println(cherry_count);
        checkForGameOver();
    }

    private void reset() {
        points = 0;
        cherry = null;
        cherry_count = 1;
        snake = new Snakes(WIDTH / 2, HEIGHT / 2);
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
        boolean hitBoundary = head.getX() <= 10
            || head.getX() >= WIDTH
            || head.getY() <= 40
            || head.getY() >= HEIGHT + 30;
            
        System.out.println(head.getX() + " " + head.getY());
        
        if(this.level == 4 && !hitBoundary) {
        	hitBoundary = (head.getX() >= 185 && head.getX() <= 585 && head.getY() >= 285 && head.getY() <= 315)
        				|| (head.getX() >= 500 && head.getX() <= 515 && head.getY() >= 65 && head.getY() <= 235)
        				|| (head.getX() >= 250 && head.getX() <= 265 && head.getY() >= 335 && head.getY() <= 400);
        }
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
    
    private void colorpicker(Graphics2D g) {
    	g.setColor(new Color(179, 240, 219));
    	if(this.pick_color == 1) {
    		g.fillOval(75, 155, 20, 20);
    		snake.setColor(Color.BLUE);
    	}
    	if(this.pick_color == 2) {
            g.fillOval(175, 155, 20, 20);
            snake.setColor(Color.DARK_GRAY);
    	}
    	if(this.pick_color== 3) {
            g.fillOval(275, 155, 20, 20);
            snake.setColor(Color.YELLOW);
    	}
    	if(this.pick_color == 4) {
            g.fillOval(375, 155, 20, 20);
            snake.setColor(Color.GREEN);
    	}
    	if(this.pick_color == 5) {
            g.fillOval(475, 155, 20, 20);
            snake.setColor(Color.PINK);
    	}
    	if(this.pick_color == 6) {
            g.fillOval(575, 155, 20, 20);
            snake.setColor(Color.WHITE);
    	}
    	if(this.pick_color == 7) {
            g.fillOval(675, 155, 20, 20);
            snake.setColor(Color.RED);
    	}
    }
    
    private void levelpicker(Graphics2D g) {

    	if(this.level == 1) {
    		strlevel = "Mudah";
    		g.setColor(new Color(185, 49, 79));
            drawCenteredString(g, "Mudah", FONT_M, 300);
        	g.setColor(new Color(179, 240, 219));
            drawCenteredString(g, "Sedang", FONT_M, 350);
            drawCenteredString(g, "Sulit", FONT_M, 400);
            drawCenteredString(g, "Extreme", FONT_M, 450);
    	}
    	
    	if(this.level == 2) {
    		strlevel = "Sedang";
    		g.setColor(new Color(179, 240, 219));
            drawCenteredString(g, "Mudah", FONT_M, 300);
            g.setColor(new Color(185, 49, 79));
            drawCenteredString(g, "Sedang", FONT_M, 350);
            g.setColor(new Color(179, 240, 219));
            drawCenteredString(g, "Sulit", FONT_M, 400);
            drawCenteredString(g, "Extreme", FONT_M, 450);
    	}
    	if(this.level == 3) {
    		strlevel = "Sulit";
    		g.setColor(new Color(179, 240, 219));
            drawCenteredString(g, "Mudah", FONT_M, 300);   
            drawCenteredString(g, "Sedang", FONT_M, 350);
            g.setColor(new Color(185, 49, 79));
            drawCenteredString(g, "Sulit", FONT_M, 400);
            g.setColor(new Color(179, 240, 219));
            drawCenteredString(g, "Extreme", FONT_M, 450);
    	}
    	if(this.level == 4) {
    		strlevel = "Extreme";
    		g.setColor(new Color(179, 240, 219));
            drawCenteredString(g, "Mudah", FONT_M, 300);   
            drawCenteredString(g, "Sedang", FONT_M, 350);
            drawCenteredString(g, "Sulit", FONT_M, 400);
            g.setColor(new Color(185, 49, 79));
            drawCenteredString(g, "Extreme", FONT_M, 450);
    	}
    	
    }
    
    private void render(Graphics2D g) {
        
        //gambar garis kotak2
//        for(int i=0; i<51; i++)
//    		g.drawLine(i*15, 0, i*15, 520);
//        for(int i=0; i<35; i++)
//    		g.drawLine(0, i*15, 760, i*15);
        
        // Warna Tulisan, Ubah juga line 186
        g.setColor(new Color(179, 240, 219));
        g.setFont(FONT_M);

        if (status == GameStatus.NOT_STARTED) {
        	System.out.println("Masuk");
          drawCenteredString(g, "SNAKE", FONT_XL, 200);
          g.setColor(new Color(185, 49, 79));
          drawCenteredString(g, "GAME", FONT_XL, 300);
          g.setColor(new Color(97, 168, 156));
          drawCenteredString(g, "Tekan keyboard untuk mulai main!", FONT_M_ITALIC, 360);
          drawCenteredString(g, "Tekan Space untuk pengaturan!", FONT_M_ITALIC, 390);
          drawCenteredString(g, "Kesulitan : " + strlevel, FONT_M_ITALIC, 540);
       
          return;
        }

        if (status == GameStatus.SETTINGS) {
            System.out.println("Settings");
            drawCenteredString(g, "SETTINGS", FONT_L_ITALIC, 75);
            
            //pilih color
            drawCenteredString(g, "Pilih warna ularmu sendiri!", FONT_M_ITALIC, 125);
            g.setColor(Color.BLUE);
            g.fillRect(60, 140, 50, 50);
            
            g.setColor(Color.DARK_GRAY);
            g.fillRect(160, 140, 50, 50);
            
            g.setColor(Color.YELLOW);
            g.fillRect(260, 140, 50, 50);
            
            g.setColor(Color.GREEN);
            g.fillRect(360, 140, 50, 50);
            
            g.setColor(Color.PINK);
            g.fillRect(460, 140, 50, 50);
            
            g.setColor(Color.WHITE);
            g.fillRect(560, 140, 50, 50);
            
            g.setColor(Color.RED);
            g.fillRect(660, 140, 50, 50);
            
            colorpicker(g);
            
            //pilih level
            drawCenteredString(g, "Pilih tingkat kesulitan yang kamu inginkan", FONT_M_ITALIC, 250);            
            levelpicker(g);
            
            g.setColor(new Color(97, 168, 156));
            drawCenteredString(g, "Tekan enter untuk selesai", FONT_M_ITALIC, 560);
            return;
        }

        // Penampilan score pada game
        best = h[0].getScore();
        g.setColor(new Color(90, 182, 193)); //Wana tulisan SCORE
        g.drawString("SCORE: " + String.format ("%04d", points), 10, 30);
        drawCenteredString(g, "LEVEL : " + strlevel, FONT_M, 30);
        if(best >= points)
            g.drawString("BEST: " + String.format ("%04d", best), 650, 30);
        if(best < points)
            g.drawString("BEST: " + String.format ("%04d", points), 650, 30);
        
        if (status == GameStatus.SHOW_HIGHSCORE) {
        	
        	System.out.println("Show HighScores");

            g.setColor(new Color(185, 49, 79));
        	g.drawString("Name", 100,80);
			g.drawString("Score", 320, 80);
			g.drawLine(60, 90, 400, 90);
            g.setFont(new Font("Arial", Font.BOLD, 22));
            
            
			for (int i=0; i<h.length; i++)
				if (HighScore.getHighScores()[i].getScore()>0)
				{
                    g.setColor(new Color(239, 199, 95)); // Warna Nama
					g.drawString(new Integer(i+1).toString()+".", 65, 115+i*40);
					g.drawString(h[i].getName(), 100, 115+i*40);
					g.drawString(new Integer(h[i].getScore()).toString(), 320, 115+i*40);
				}
            
            g.setColor(new Color(185, 49, 79)); // Warna Tulisan
            g.drawString("Tekan Enter untuk kembali ke main menu!", 200, 500);
            g.drawString("Tekan keyboard untuk main lagi!", 250, 535);
			return;
        }
        
        if (cherry != null) {
          if (didLoadCherryImage) {
            g.drawImage(image, cherry.getX(), cherry.getY(), 60, 60, null);
          } else {
            g.setColor(new Color(209, 52, 91)); // Warna Cherry
            g.fillOval(cherry.getX(), cherry.getY(), 15, 15);
            g.setColor(new Color(185, 49, 79)); // Warna Tulisan
          }
          
        }
        if (bigcherry != null) {
            if (didLoadCherryImage) {
              g.drawImage(image, bigcherry.getX(), bigcherry.getY(), 80, 80, null);
            } else {
              g.setColor(new Color(209, 52, 91)); // Warna Cherry
              g.fillOval(bigcherry.getX(), bigcherry.getY(), 25, 25);
              g.setColor(new Color(185, 49, 79)); // Warna Tulisan
            }
          }

        if (status == GameStatus.GAME_OVER) {
        	System.out.println("Gameover");
            drawCenteredString(g, "Yah, Nabrak!", FONT_L, 300);
            g.setColor(new Color(239, 199, 95));
            drawCenteredString(g, "Tekan ENTER untuk melihat Top Score", FONT_M_ITALIC, 350);
            g.setColor(new Color(185, 49, 79));
           
			return;
        }

        if (status == GameStatus.PAUSED) {
            System.out.println("Paused");
            drawCenteredString(g, "Loh, kok berhenti?", FONT_L_ITALIC, 300);
            return;
        }
        
        //Snake
        snake.drawSnake(g);

        if(level == 4) {
        	g.setColor(new Color(39, 66, 64));
        	g.fillRect(200, 300, 400, 15);
        	g.fillRect(500, 80, 15, 160);
        	g.fillRect(250, 350, 15, 150);
        }
        //OutLine
        g.setColor(new Color(39, 66, 64));
        g.setStroke(new BasicStroke(4));
        g.drawRect(10, 40, WIDTH, HEIGHT);
    }

    public void spawnCherry() {
    	cherry = new Point((new Random()).nextInt(WIDTH - 60) + 20,
    	            (new Random()).nextInt(HEIGHT - 60) + 40); 	
    }
    
    public void spawnBigCherry() {
    	bigcherry = new BigCherry((new Random()).nextInt(WIDTH - 60) + 20,
    			(new Random()).nextInt(HEIGHT - 60) + 40);
    }

    private class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if (status == GameStatus.SHOW_HIGHSCORE)
            {
            	if(key == KeyEvent.VK_ENTER) {
            		setStatus(GameStatus.NOT_STARTED);
            		points = 0;
                    cherry = null;
                    snake = new Snakes(WIDTH / 2, HEIGHT / 2);
            		repaint();
            	}
            		
            	else
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
            	if(key == KeyEvent.VK_RIGHT ) {
            		pick_color++;
            		if(pick_color > 7)
            			pick_color = 1;
            		repaint();
            	}
            	if(key == KeyEvent.VK_LEFT ) {
            		pick_color--;
            		if(pick_color < 1)
            			pick_color = 7;
            		repaint();
            	}
            	
            	if(key == KeyEvent.VK_DOWN) {
            		level++;
            		if(level > 4)
            			level=1;
            		repaint();
            	}
            	
            	if(key == KeyEvent.VK_UP) {
            		level--;
            		if(level < 1)
            			level=4;
            		repaint();
            	}
            		
                if(key == KeyEvent.VK_ENTER) {
                    setStatus(GameStatus.NOT_STARTED);
                    repaint();
                }
            }

            if (status == GameStatus.NOT_STARTED && key != KeyEvent.VK_ENTER) {
                if(key == KeyEvent.VK_SPACE)
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

            if ((status == GameStatus.PAUSED || status == GameStatus.RUNNING) && key == KeyEvent.VK_ESCAPE) {
                togglePause();
            }
        }
    }

    private class GameLoop extends java.util.TimerTask {
    	int delay=0, detik=0;
        public void run() {
        	delay++;
        	if(delay % 20 == 0) {
        		detik++;
        		System.out.println("detik = " + detik);
        	}
        	
            update();
            repaint();
        }
    }
    
}
