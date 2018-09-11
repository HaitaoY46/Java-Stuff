/**
 * Haitao Ye 
 * ID: 21497606
 * This is the file containing the main running class and the thread implemented class that would run every thread
 */

import java.util.Scanner;
import java.io.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CrapsSimulation implements Runnable
{
	static String name;
	static int balance;
	static int bet;
	static int cWinStk;
	static int cLoseStk;
	CrapsMetricsMonitor M = new CrapsMetricsMonitor();
	Scanner reader = new Scanner(System.in);
	static int gs; 
	
	//thread objects
	private Thread t;
	private String threadName;
	private String time;
	private PrintWriter pw;
	
	public CrapsSimulation(String name, int bet, int balance, String tn, PrintWriter p)
	{
		CrapsSimulation.name = name;
		CrapsSimulation.bet = bet;
		CrapsSimulation.balance = balance;
		this.threadName = tn;
		this.pw = p;
	}
	
	public void ConsoleWriter(PrintWriter pw, String in)
	{	
		
		pw.println(in);
				
	}
	
	@Override
	public void run() 
	{

		File output = new File("src/" + this.threadName + ".txt");
		
		CrapsGame game = new CrapsGame(M, output);
		
		synchronized(game) 
		{
			game.playGame();
//			this.ConsoleWriter(pw, "Games Played :" + CrapsMetricsMonitor.allGames);
		}
		synchronized(M) 
		{
			M.Statistics();
		}
	}

	
	public void starting()
	{	
		if (t==null)
		{
			time = Instant.now().atZone(ZoneId.systemDefault()).
					format( DateTimeFormatter.ISO_LOCAL_DATE_TIME ).replace("T"," ");
			System.out.println("Starting thread: " + this.threadName + " at " + time);
			synchronized(this)
			{
				this.ConsoleWriter(pw, "Starting thread: " + this.threadName + " at " + time);
			}
			
			t = new Thread(this, threadName);
			t.start();

			time = Instant.now().atZone(ZoneId.systemDefault()).
					format( DateTimeFormatter.ISO_LOCAL_DATE_TIME ).replace("T"," ");
			System.out.println("Thread : " + this.threadName + " ended at " + time);
			synchronized(this)
			{
				this.ConsoleWriter(pw, "Thread : " + this.threadName + " ended at " + time);
				this.ConsoleWriter(pw, "Games Played :" + CrapsGame.allGames);
			}
		}
	}
}

