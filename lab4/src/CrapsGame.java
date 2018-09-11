/**
 * Haitao Ye 
 * ID: 21497606
 * This is the file containing class that runs the main game logic 
 * and also a writer method the writes the info into a file
 */

import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.io.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CrapsGame {
	private int rolls = 0;
	Scanner re = new Scanner(System.in);
	CrapsMetricsMonitor m = new CrapsMetricsMonitor();
	String currentDateAndTime;
	static int allGames;
	
	private File output;
	private String time;
	private int ba = CrapsSimulation.balance;
	private int be = CrapsSimulation.bet;
	
	public CrapsGame(CrapsMetricsMonitor monitor, File o)
	{
		m = monitor;
		output = o;
	}
	
	public void Writer(PrintWriter pw, String in)
	{
		pw.println(in);
	}
	
	public void playGame()
	{
		//Sets for Craps/Natural
		Random rand  = new Random();
		Set<Integer> craps = new HashSet<Integer>();
		craps.addAll(Arrays.asList(new Integer[] {2,3,12}));
		Set<Integer> naturals = new HashSet<Integer>();
		naturals.addAll(Arrays.asList(new Integer[] {7,11}));
		
		//setting for new game
		m.reset();
		m.maxValue = ba;
		m.gameOfMax = 1;

		try
		{
			//Set up for writer
			PrintWriter pw = new PrintWriter(output);
			
			while (ba > 0)
			{
				time = Instant.now().atZone(ZoneId.systemDefault())
						.format( DateTimeFormatter.ISO_LOCAL_DATE_TIME ).replace("T"," ");
				synchronized(this) 
				{	
					this.Writer(pw, "Bet made at " + time);
				}
				
//				pw.println("Bet made at " + time);
				
				synchronized(this) 
				{	
					this.Writer(pw, CrapsSimulation.name + " bets " + be);
				}

				//Begins the actual game, roll the dice
				synchronized(this) 
				{	
					this.Writer(pw, "Plying a new game...");
				}
//				pw.println("Plying a new game...");
				
				time = Instant.now().atZone(ZoneId.systemDefault())
						.format( DateTimeFormatter.ISO_LOCAL_DATE_TIME ).replace("T"," ");
				synchronized(this) 
				{	
					this.Writer(pw, "Dice rolled at " + time);
				}
//				pw.println("Dice rolled at " + time);
				int dice1 = rand.nextInt(6) + 1;
				int dice2 = rand.nextInt(6) + 1;
				int dice = dice1 + dice2;
				++rolls;
				
				//set the Monitor
				m.games += 1;
				allGames += 1;
	
				if (dice > m.maxNum)
				{
					m.maxNum = dice;
				}
				
				//Its a crap
				if (craps.contains(dice))
				{
					synchronized(this) 
					{	
						this.Writer(pw, "Rolled a " + dice);	
						this.Writer(pw, "*****Craps! You lose.*****");
					}
					ba = ba - be;
					
					if (ba < 0)
					{
						ba = 0;
					}
					
					m.lost += 1;
					m.crapCounts += 1;
					//Manage streaks
					if (CrapsSimulation.cLoseStk > 0)
					{
						CrapsSimulation.cLoseStk += 1;
						if (m.loseStk < CrapsSimulation.cLoseStk)
						{
							m.loseStk = CrapsSimulation.cLoseStk;
						}
					}
					else
					{
						CrapsSimulation.cWinStk = 0;
						CrapsSimulation.cLoseStk += 1;
					}
					time = Instant.now().atZone(ZoneId.systemDefault())
							.format( DateTimeFormatter.ISO_LOCAL_DATE_TIME ).replace("T"," ");
					synchronized(this) 
					{	
						this.Writer(pw, "Game ended at " + time);	
					}
				}
				
				//Its a natural
				else if (naturals.contains(dice))
				{
					synchronized(this) 
					{	
						this.Writer(pw, "Rolled a " + dice);
						this.Writer(pw, "*****Natural! You win.*****");
					}
					ba = ba + be;
					m.won += 1;
					m.naturalCounts += 1;
					if (CrapsSimulation.balance > m.maxValue)
					{
						m.maxValue = CrapsSimulation.balance;
						m.gameOfMax = m.games;
					}
					//manage max Value
					if (CrapsSimulation.balance > m.maxValue)
					{
						m.maxValue = CrapsSimulation.balance;
						m.gameOfMax = m.games;
					}
					//Manage streaks
					if (CrapsSimulation.cWinStk > 0)
					{
						CrapsSimulation.cWinStk += 1;
						if (m.winStk < CrapsSimulation.cWinStk)
						{
							m.winStk = CrapsSimulation.cWinStk;
						}
					}
					else
					{
						CrapsSimulation.cWinStk += 1;
						CrapsSimulation.cLoseStk = 0;
					}
					
					time = Instant.now().atZone(ZoneId.systemDefault())
							.format( DateTimeFormatter.ISO_LOCAL_DATE_TIME ).replace("T"," ");
					synchronized(this) 
					{	
						this.Writer(pw, "Game ended at " + time);	
					}
				}
				
				//either
				else
				{
					synchronized(this) 
					{	
						this.Writer(pw, "Rolled a " + dice);	
					}
					
					int point = dice;
					
					time = Instant.now().atZone(ZoneId.systemDefault())
							.format( DateTimeFormatter.ISO_LOCAL_DATE_TIME ).replace("T"," ");
					synchronized(this) 
					{	
						this.Writer(pw, "Dice rolled at " + time);
					}
					dice1 = rand.nextInt(6) + 1;
					dice2 = rand.nextInt(6) + 1;
					dice = dice1 + dice2;
					++rolls;
					
					//
					if (dice > m.maxNum)
					{
						m.maxNum = dice;
					}
					synchronized(this) 
					{	
						this.Writer(pw, "Rolled a " + dice);	
					}
					
					while (dice != point)
					{
						time = Instant.now().atZone(ZoneId.systemDefault())
								.format( DateTimeFormatter.ISO_LOCAL_DATE_TIME ).replace("T"," ");
						synchronized(this) 
						{	
							this.Writer(pw, "Dice rolled at " + time);
						}
						dice1 = rand.nextInt(6) + 1;
						dice2 = rand.nextInt(6) + 1;
						dice = dice1 + dice2;
						++rolls;
						//
						if (dice > m.maxNum)
						{
							m.maxNum = dice;
						}
						synchronized(this) 
						{	
							this.Writer(pw, "Rolled a " + dice);	
						}
						
						if (dice == 7)
						{
							synchronized(this) 
							{	
								this.Writer(pw, "*****Crap out! You lose.*****");	
							}
							ba = ba - be;
							
							if (ba < 0)
							{
								ba = 0;
							}
							
							m.lost += 1;
							//Manage streaks
							if (CrapsSimulation.cLoseStk > 0)
							{
								CrapsSimulation.cLoseStk += 1;
								if (m.loseStk < CrapsSimulation.cLoseStk)
								{
									m.loseStk = CrapsSimulation.cLoseStk;
								}
							}
							else
							{
								CrapsSimulation.cWinStk = 0;
								CrapsSimulation.cLoseStk += 1;
							}
							time = Instant.now().atZone(ZoneId.systemDefault())
									.format( DateTimeFormatter.ISO_LOCAL_DATE_TIME ).replace("T"," ");
							synchronized(this) 
							{	
								this.Writer(pw, "Game ended at " + time);	
							}
							break;
						}
					}
					
					if (dice == point)
					{
						synchronized(this) 
						{	
							this.Writer(pw, "*****Rolled the point! You win!*****");	
						}
						ba = ba + be;
						m.won += 1;
						//manage max balance
						if (CrapsSimulation.balance > m.maxValue)
						{
							m.maxValue = CrapsSimulation.balance;
							m.gameOfMax = m.games;
						}
						//Manage streaks
						if (CrapsSimulation.cWinStk > 0)
						{
							CrapsSimulation.cWinStk += 1;
							if (m.winStk < CrapsSimulation.cWinStk)
							{
								m.winStk = CrapsSimulation.cWinStk;
							}
						}
						else
						{
							CrapsSimulation.cWinStk += 1;
							CrapsSimulation.cLoseStk = 0;
						}
						time = Instant.now().atZone(ZoneId.systemDefault())
								.format( DateTimeFormatter.ISO_LOCAL_DATE_TIME ).replace("T"," ");
						synchronized(this) 
						{	
							this.Writer(pw, "Game ended at " + time);	
						}
					}			
	
				}
				rolls += 1;
				
				synchronized(this) 
				{	
					this.Writer(pw, CrapsSimulation.name + "'s balance: " 
							+ ba + ".");
					this.Writer(pw, "\n====================================\n");
				}
			}
			
			//print the stats
			synchronized(this) 
			{	
				this.Writer(pw, m.Statistics());
			}
			pw.close();
		}
		catch(FileNotFoundException e)
		{
			
		}
	}	

}
