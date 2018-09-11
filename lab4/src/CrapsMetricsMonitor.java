/**
 * Haitao Ye 
 * ID: 21497606
 * This is the file containing class that tracks all the stats running in the game and altering them so 
 * it can be printed at the end of the game
 */

public class CrapsMetricsMonitor {
	int games;
	int won;
	int lost;
	int maxNum;
	int naturalCounts;
	int crapCounts;
	int winStk;
	int loseStk;
	int maxValue;
	int gameOfMax;
	
	
	public CrapsMetricsMonitor()
	{}
	
	public CrapsMetricsMonitor(CrapsMetricsMonitor cmm)
	{
		games = cmm.games;
		won = cmm.won;
		lost = cmm.lost;
		maxNum = cmm.maxNum;
		naturalCounts = cmm.naturalCounts;
		crapCounts = cmm.crapCounts;
		winStk = cmm.winStk;
		loseStk = cmm.loseStk;
		maxValue = cmm.maxValue;
		gameOfMax = cmm.gameOfMax;
	}
	
//	public void increaseValue(int n, int v)
//	{
//		n += v;
//	}
//	
//	public void decreaseValue(int n, int v)
//	{
//		n -= v;
//	}
	
	public void reset()
	{
		this.games = 0;
		this.won = 0;
		this.lost = 0;
		this.maxNum = 0;
		this.naturalCounts = 0;
		this.crapCounts = 0;
		this.winStk = 0;
		this.loseStk = 0;
		this.maxValue = 0;
		this.gameOfMax = 0;
	}
	
	public String Statistics()
	{
		String part1 = "*****************************\n"
					+ "*** SIMULATION STATISTICS ***\n"
					+ "*****************************\n";
		String part2 = "Games played: " + games
					+ "\nGames won: " + won
					+ "\nGames lost: " + lost
					+ "\nMaximum Rolls in a single game: " + maxNum
					+ "\nNatural Count: " + naturalCounts
					+ "\nCraps Count: " + crapCounts
					+ "\nMaximum Winning Streak: " + winStk
					+ "\nMaximum Loosing Streak: " + loseStk
					+ "\nMaximum balance: " + maxValue + " during game " + gameOfMax;
		return part1 + part2;
	}
}
