package game_utility;
import monsters.Dragonfish;
import monsters.Monster;
import monsters.Scorchitile;
import monsters.Seedatops;

public class MonsterMaker {
	
	private static final int SCORCHITILE = 0;
	private static final int SEEDATOPS = 1;
	private static final int DRAGONFISH = 2;
	private static final int TOTAL_NUMBER_OF_BREEDS=3;
	
	
	public static Monster makeRandomMonster(String name, int level)
	{			
		int monster = randomMonster(0,TOTAL_NUMBER_OF_BREEDS-1);
		switch (monster) {
		case SCORCHITILE:
			return new Scorchitile(name,level,level*10,level*10+5);
		case SEEDATOPS:
			return new Seedatops(name,level,level*10,level*10+5);
		case DRAGONFISH:
			return new Dragonfish(name,level,level*10);
		default:
			return new Scorchitile("Should be unreachable",level,level*10,level*10+5);
		}	
	}
	
	public static Monster makeRandomWildMonster(int level)
	{
		int monster = randomMonster(0,TOTAL_NUMBER_OF_BREEDS-1);
		switch (monster) {
		case SCORCHITILE:
			System.out.println("There's a strange glow behind that rock....");
			return new Scorchitile("Wild Scorchitile",level,level*10,level*10+5);
		case SEEDATOPS:
			System.out.println("There's a strange smell behind in that bush....");
			return new Seedatops("Wild Seedatops",level,level*10,level*10+5);
		case DRAGONFISH:
			System.out.println("There's a strange bubble in that pond.....");
			return new Dragonfish("Wild Dragonfish",level,level*10);
		default:
			return new Scorchitile("Should be unreachable",level,level*10,level*10+5);
		}	
	}
	
	private static int randomMonster(int lowerBound, int upperBound) 
	{
		return (int)(Math.random()*(upperBound-lowerBound+1)+lowerBound); 
	}

	

}
