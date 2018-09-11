/**
 * Haitao Ye 
 * ID: 21497606
 * This is the file containing the main method where user info would be asked and run the threads */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class lab4 {

	public static void main(String[] args) 
	{
		Scanner reader = new Scanner(System.in);
		String name;
		int balance;
		int bet;
		boolean re = false;
		
		while (!re)
		{
			bet = 0;
			//Welcome and ask for name==============================================================
			System.out.println("Welcome to SimCraps! Enter your user name: ");
			name = reader.next();
			System.out.println("Hello " + name + "!");
			
			//Ask for balance
			System.out.println("Enter the amount of money you will bring to the table: ");
			balance = reader.nextInt();
			reader.nextLine();
			
			//Ask for bet
			while (bet < 1 || bet > balance)
			{
				System.out.println("Enter the bet amount between $1 and $" + balance + ": ");
				bet = reader.nextInt();
				if (bet >= 1 && bet <= balance)
					break;
				else
					System.out.println("Invalid bet! Pls enter a bet between &1 and $" + balance + ": ");
			}
			
			//Threads==============================================================
			try
			{
				File console = new File("src/Console.txt");
				PrintWriter pw = new PrintWriter(console);
			
				CrapsSimulation c1 = new CrapsSimulation(name, bet, balance, "Thread1", pw);
				c1.starting();
				
				CrapsSimulation c2 = new CrapsSimulation(name, bet, balance, "Thread2", pw);
				c2.starting();
				
				CrapsSimulation c3 = new CrapsSimulation(name, bet, balance, "Thread3", pw);
				c3.starting();
				
				CrapsSimulation c4 = new CrapsSimulation(name, bet, balance, "Thread4", pw);
				c4.starting();
				
				CrapsSimulation c5 = new CrapsSimulation(name, bet, balance, "Thread5", pw);
				c5.starting();
				
				pw.close();
			}
			catch(FileNotFoundException e)
			{
				
			}
			
			//ask for replay==============================================================
			System.out.println("Replay? Enter 'y' or 'n': ");
			String replay = reader.next();

			if (replay.equals("y") || replay.equals("Y"))
			{
				re = false;
			}
			else if((replay.equals("n") || replay.equals("N")))
			{
				re = true;
			}
		}
		reader.close();
	}

}
