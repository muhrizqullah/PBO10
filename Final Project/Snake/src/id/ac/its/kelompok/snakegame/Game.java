package id.ac.its.kelompok.snakegame;
import javax.swing.JPanel;
import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel {
 
    private static final long serialVersionUID = 1L;
    // Default font
    private static Font FONT_M = new Font("ArcadeClassic", Font.PLAIN, 24);
    private static Font FONT_M_ITALIC = new Font("ArcadeClassic", Font.ITALIC, 24);
    private static Font FONT_L_ITALIC = new Font("ArcadeClassic", Font.ITALIC, 60);
    private static Font FONT_L = new Font("ArcadeClassic", Font.PLAIN, 84);
    private static Font FONT_XL = new Font("ArcadeClassic", Font.PLAIN, 150);
    // Game Frame width and height
    private static int WIDTH = 760;
    private static int HEIGHT = 520;
    // Timer variable
    private Timer timer;
    private int time, delay;
    private static int DELAY = 50;
    // Object init
    private Snakes snake;
    private Cherry cherry;
    private BigCherry bigcherry;
    // Counter and utility variable
    private int pick_color, pick_menu;
    private int points = 0;
    private int best;
    private int level;
    private int cherry_count;
    private GameStatus status;
    private static HighScore[] h=HighScore.getHighScores();
    private static Color colors[] = {Color.BLUE, Color.DARK_GRAY, Color.YELLOW, Color.GREEN, Color.PINK, Color.WHITE, Color.RED};
    private static String levels[] = {"Mudah", "Sedang", "Sulit", "Extreme"};
    private static String menus[] = {"Mulai Main", "Pengaturan", "Credits"};
    //Sound Effect init
    private SoundEffect se = new SoundEffect();
    private SoundEffect seMusic = new SoundEffect();
    private SoundEffect seSpawn = new SoundEffect();
    
    // Game Class default constructor
    public Game() {	
        addKeyListener(new KeyListener());
        setFocusable(true);
        setBackground(new Color(13, 24, 33));
        setDoubleBuffered(true);

        status = GameStatus.NOT_STARTED;
        snake = new Snakes(WIDTH / 2, HEIGHT / 2);
        snake.setColor(Color.BLUE);
        // Playing in game music
    	seMusic.setFile(".//assets//music.wav");
    	seMusic.playMusic(0.05);
        //start value
        time = 0;
        delay=0;
        pick_color=0;
        pick_menu=0;
        level = 0;
        cherry_count = 1;
        bigcherry = null;
        
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render((Graphics2D) g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void update() {
    	
        snake.move(level+1);

        if (cherry != null && snake.getHead().checkIntersects(cherry, 12)) {
        	se.setFile(".//assets//bite.wav");
            se.play();
            snake.addTail();
            cherry = null;
            points += (level+1);
        }
        
        if (bigcherry != null) {
        	if(snake.getHead().checkIntersects(bigcherry, 21)) {
        		se.setFile(".//assets//bigcherry.wav");
                se.play();
        		snake.addTail();
                bigcherry = null;
                points += 3 * (level+1);
        	}
        	else if(time - bigcherry.getSpawn_time() == 5) {
        		bigcherry=null;
        	}
        	if(bigcherry==null)
        		seSpawn.stop();
        		
            
        }
        if (cherry == null && bigcherry == null) {
        	if(cherry_count % 5 != 0)
        		spawnCherry();
        	else 
        		spawnBigCherry();
            cherry_count++;
        }
                
        checkForGameOver();
    }

    private void reset() {
        time = 0; 
        delay =0;
        points = 0;
        cherry = null;
        cherry_count = 1;
        snake = new Snakes(WIDTH / 2, HEIGHT / 2);
        snake.setColor(colors[pick_color]);
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
                break;
            case GAME_OVER:
                // Play game over sound effect
            	se.setFile(".//assets//gameover.wav");
                se.play();
                if(bigcherry!=null)
                	seSpawn.stop();
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

    private void checkForGameOver() {
        Point head = snake.getHead();
        boolean hitBoundary = head.getX() <= 15
            || head.getX() >= WIDTH + 5
            || head.getY() <= 40
            || head.getY() >= HEIGHT + 50;
        
        if(this.level == 3 && !hitBoundary) {
        	hitBoundary = (head.getX() >= 185 && head.getX() <= 600 && head.getY() >= 285 && head.getY() <= 30)
        				|| (head.getX() >= 485 && head.getX() <= 500 && head.getY() >= 65 && head.getY() <= 235)
        				|| (head.getX() >= 250 && head.getX() <= 265 && head.getY() >= 335 && head.getY() <= 485);
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
    
    private void render(Graphics2D g) {

        // Warna Tulisan, Ubah juga line 186
        g.setColor(new Color(179, 240, 219));
        g.setFont(FONT_M);

        //MAIN MENU
        if (status == GameStatus.NOT_STARTED) {
          drawCenteredString(g, "SNAKE", FONT_XL, 200);
          g.setColor(new Color(185, 49, 79));
          drawCenteredString(g, "GAME", FONT_XL, 300);
          
          g.setColor(new Color(97, 168, 156));
          drawCenteredString(g, menus[pick_menu], FONT_L_ITALIC, 360 + 50 * pick_menu);
          
      	  for(int i=0; i<3; i++) {
      		if(i == pick_menu)
      			continue;
      		g.setColor(new Color(179, 240, 219));
      		drawCenteredString(g, menus[i], FONT_M_ITALIC, 360 + 50 * i);
            }
            
          drawCenteredString(g, "Kesulitan : " + levels[level], FONT_M_ITALIC, 540);
       
          return;
        }

        // GAME SETTINGS
        if (status == GameStatus.SETTINGS) {
            System.out.println("Settings");
            drawCenteredString(g, "SETTINGS", FONT_L_ITALIC, 75);
            
            // Choose Snake Color
            drawCenteredString(g, "Pilih warna ularmu sendiri!", FONT_M_ITALIC, 125);
            for(int i=0; i<7; i++) {
            	g.setColor(colors[i]);
            	g.fillRect(60 + 100 * i, 140, 50, 50);
            }
            
            g.setColor(new Color(179, 240, 219));
            g.fillOval(75 + 100 * pick_color, 155, 20, 20);
            
            // Choose Game Level
            drawCenteredString(g, "Pilih tingkat kesulitan yang kamu inginkan", FONT_M_ITALIC, 250);
            
            g.setColor(new Color(185, 49, 79));
            drawCenteredString(g, levels[level], FONT_M, 300 + 50 * level);
        	for(int i=0; i<4; i++) {
        		if(i == level)
        			continue;
        		g.setColor(new Color(179, 240, 219));
        		drawCenteredString(g, levels[i], FONT_M, 300 + 50 * i);
        	}
            
            g.setColor(new Color(97, 168, 156));
            drawCenteredString(g, "Tekan enter untuk selesai", FONT_M_ITALIC, 560);
            return;
        }
        
        // GAME CREDITS
        if(status == GameStatus.CREDITS) {

        	System.out.println("credits");
            drawCenteredString(g, "CREDITS", FONT_L_ITALIC, 75);
            
        	drawCenteredString(g, "Production", FONT_M_ITALIC, 210);
        	drawCenteredString(g, "0001-Christoper Baptista", FONT_M_ITALIC, 250);
        	drawCenteredString(g, "0030-Bunga Fairuz Wijdan", FONT_M_ITALIC, 280);
        	drawCenteredString(g, "0178-Muhammad Rizqullah Akbar", FONT_M_ITALIC, 310);
        	
        	drawCenteredString(g, "2021", FONT_M_ITALIC, 500);
		    drawCenteredString(g, "Tekan Enter untuk kembali", FONT_M_ITALIC, 560);
        	//System.out.println("credits");

        	return; 
        }
        
        // Penampilan score pada game
        best = h[0].getScore();
        g.setColor(new Color(90, 182, 193)); //Wana tulisan SCORE
        g.drawString("SCORE: " + String.format ("%04d", points), 10, 30);
        drawCenteredString(g, "LEVEL : " + levels[level], FONT_M, 30);
        g.drawString("BEST: " + String.format ("%04d", best), 650, 30);
        
        
        // GAME TOP 10 SCORES
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
        
        // Cherry draw
        if (cherry != null) {
        	cherry.Draw(g);          
        }
        // BigCherry draw
        if (bigcherry != null) {
            bigcherry.Draw(g, delay);
        }

        // GAME OVER
        if (status == GameStatus.GAME_OVER) {
            System.out.println("Gameover");
            g.setColor(new Color(185, 49, 79));
            drawCenteredString(g, "Yah, Nabrak!", FONT_L, 300);
            g.setColor(new Color(239, 199, 95));
            drawCenteredString(g, "Tekan ENTER untuk melihat Top Score", FONT_M_ITALIC, 350);
            g.setColor(new Color(185, 49, 79));
           
			return;
        }

        // GAME PAUSED
        if (status == GameStatus.PAUSED) {
            System.out.println("Paused");
            g.setColor(new Color(185, 49, 79));
            drawCenteredString(g, "PAUSED", FONT_M_ITALIC, 235);
            drawCenteredString(g, "Loh, kok berhenti?", FONT_L_ITALIC, 300);
            return;
        }
        
        // Snake draw
        System.out.printf("x: %d, y: %d\n", snake.getHead().getX(), snake.getHead().getY());
        snake.setColor(colors[pick_color]);
        snake.drawSnake(g);

        // Draw wall on extreme level
        if(level == 3) {
        	g.setColor(new Color(39, 66, 64));
        	g.fillRect(200, 300, 400, 15);
        	g.fillRect(500, 80, 15, 160);
        	g.fillRect(250, 350, 15, 150);
        }

        // GAMEFRAME
        g.setColor(new Color(39, 66, 64));
        g.setStroke(new BasicStroke(4));
        g.drawRect(20, 40, WIDTH, HEIGHT);

        // END OF RENDER FUNCTION
    }

    // SPAWN CHERRY IN RANDOM PLACES
    public void spawnCherry() {
    	cherry = new Cherry((new Random()).nextInt(WIDTH - 60) + 20,
    	            (new Random()).nextInt(HEIGHT - 60) + 40); 	
    }
    
    // SPAWN CHERRY IN RANDOM PLACE AND FOLLOWING TIME RULES
    public void spawnBigCherry() {
    	seSpawn.setFile(".//assets//spawn2.wav");
        seSpawn.playMusic(0.4);
    	bigcherry = new BigCherry((new Random()).nextInt(WIDTH - 60) + 20,
    			(new Random()).nextInt(HEIGHT - 60) + 40);
    	bigcherry.setSpawn_time(time);
    	System.out.println(bigcherry.getSpawn_time());
    }

    // KEYLISTENER FOR EVERY GAMESTATUS
    private class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if (status == GameStatus.SHOW_HIGHSCORE)
            {
            	if(key == KeyEvent.VK_ENTER) {
            		setStatus(GameStatus.NOT_STARTED);
            		key = KeyEvent.VK_0;
            		points = 0;
                    cherry = null;
                    bigcherry = null;
                    snake = new Snakes(WIDTH / 2, HEIGHT / 2);
                    time=0;
                    cherry_count=1;
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
                // Play SoundEffect when choosing color and level
            	se.setFile(".//assets//pick.wav");
                se.play();
            	if(key == KeyEvent.VK_RIGHT ) {
            		pick_color++;
            		if(pick_color > 6)
            			pick_color = 0;
            		repaint();
            	}
            	if(key == KeyEvent.VK_LEFT ) {
            		pick_color--;
            		if(pick_color < 0)
            			pick_color = 6;
            		repaint();
            	}
            	
            	if(key == KeyEvent.VK_DOWN) {
            		level++;
            		if(level > 3)
            			level=0;
            		repaint();
            	}
            	
            	if(key == KeyEvent.VK_UP) {
            		level--;
            		if(level < 0)
            			level=3;
            		repaint();
            	}
            		
                if(key == KeyEvent.VK_ENTER) {
                    setStatus(GameStatus.NOT_STARTED);
                    key = KeyEvent.VK_0;
                    repaint();
                }
            }

            if (status == GameStatus.NOT_STARTED) {
                // Play SoundEffect when choosing menu
            	se.setFile(".//assets//pick.wav");
                se.play();
            	if(key == KeyEvent.VK_DOWN) {
            		pick_menu++;
            		if(pick_menu > 2)
            			pick_menu=0;
            		repaint();
            	}
            	
            	if(key == KeyEvent.VK_UP) {
            		pick_menu--;
            		if(pick_menu < 0)
            			pick_menu=2;
            		repaint();
            	}
            		
                if(key == KeyEvent.VK_ENTER) {
                	if(pick_menu == 0)
                		setStatus(GameStatus.RUNNING);
                	else if(pick_menu == 1) {
                		setStatus(GameStatus.SETTINGS);
                        repaint();
                	}
                	else if(pick_menu == 2) {
                		setStatus(GameStatus.CREDITS);
                		repaint();
                	}
                	key = KeyEvent.VK_0;
                		
                }
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
            
            if (status == GameStatus.CREDITS && key == KeyEvent.VK_ENTER) {
                // PLAY SOUND WHEN BACK TO MAIN MENU
                se.setFile(".//assets//pick.wav");
                se.play();
            	setStatus(GameStatus.NOT_STARTED);
                key = KeyEvent.VK_0;
                repaint();
            }
            
            if ((status == GameStatus.PAUSED || status == GameStatus.RUNNING) && key == KeyEvent.VK_ESCAPE) {
                togglePause();
            }
        }

        // END OF KEYLISTENER FUNCTION
    }
    

    private class GameLoop extends java.util.TimerTask {
        public void run() {
        	delay++;
        	if(delay % 20 == 0) {
        		time++;
        		System.out.println("detik = " + time);
        	}       	
            update();
            repaint();
        }
    }
}