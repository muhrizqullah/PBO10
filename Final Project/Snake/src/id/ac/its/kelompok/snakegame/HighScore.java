package id.ac.its.kelompok.snakegame;

	import java.io.*;

	public class HighScore implements Serializable
	{
		private static final long serialVersionUID = 1L;
		private int score;// level;
		private String name;
		
		//The constructor
//		public HighScore(int s, int l, String n)
//		{
//			score=s;
//			setLevel(l);
//		//	setName(n);
//		}
		
		public HighScore(int s, String n)
		{
			score=s;
		//	setLevel(l);
			setName(n);
		}
		
		//The setters and getters
		public void setScore(int score)
		{
			this.score=score;
		}
		
		public int getScore()
		{
			return score;
		}

//		public void setLevel(int level) 
//		{
//			this.level = level;
//		}
//
//		public int getLevel() 
//		{
//			return level;
//		}

		public void setName(String name) 
		{
			this.name = name;
		}

		public String getName() 
		{
			return name;
		}
		//Static methods
		//Decides whether this HighScore is greater than, less than, or equal to the argument
		public int compareTo(HighScore h)
		{
			return new Integer(this.score).compareTo(h.score);
		}
		
		//This is called when there is an empty file in order prevent exceptions
		private static void initializeFile()
		{
//			HighScore[] h={new HighScore(0,0," "),new HighScore(0,0," "),new HighScore(0,0," "),
//					new HighScore(0,0," "),new HighScore(0,0," "),new HighScore(0,0," "),
//					new HighScore(0,0," "),new HighScore(0,0," "),new HighScore(0,0," "),
//					new HighScore(0,0," ")};
			
			HighScore[] h={new HighScore(0," "),new HighScore(0," "),new HighScore(0," "),
					new HighScore(0," "),new HighScore(0," "),new HighScore(0," "),
					new HighScore(0," "),new HighScore(0," "),new HighScore(0," "),
					new HighScore(0," ")};
			try 
			{
				System.out.println("Initiate HighScores.dat");
				ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("HighScores.dat"));
				o.writeObject(h);
				o.close();
			} catch (FileNotFoundException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();}
		}
		
		//Reads the .dat file and returns the constants
		public static HighScore[] getHighScores()
		{
			if (!new File("HighScores.dat").exists())
				initializeFile();
			try 
			{
				ObjectInputStream o=new ObjectInputStream(new FileInputStream("HighScores.dat"));
				HighScore[] h=(HighScore[]) o.readObject();
				return h;
			} catch (IOException e) {e.printStackTrace();} 
			catch (ClassNotFoundException e) {e.printStackTrace();}
			return null;
		}
		
		//Adds a new HighScore to the .dat file and maintains the proper order
		public static void addHighScore(HighScore h)
		{
			HighScore[] highScores=getHighScores();
			highScores[highScores.length-1]=h;
			for (int i=highScores.length-2; i>=0; i--)
			{
				if (highScores[i+1].compareTo(highScores[i])>0)
				{
					HighScore temp=highScores[i];
					highScores[i]=highScores[i+1];
					highScores[i+1]=temp;
				}
			}
			try 
			{
				ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("HighScores.dat"));
				o.writeObject(highScores);
				o.close();
			} catch (FileNotFoundException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();}
		}
	}	


