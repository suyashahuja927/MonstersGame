package game_controllers;

import java.util.List;
import java.util.Scanner;


import game_utility.Mode;
import game_utility.MonsterMaker;
import items.Item;
import monsters.Dragonfish;
import monsters.Monster;
import monsters.Scorchitile;
import monsters.Seedatops;

public class GameWorld
{
	Mode gameMode = Mode.STARTUP;
	private boolean gameStillGoing = true;
	private Trainer trainer = new Trainer();
	Monster wildMonster;
	private double encounterLikelihoodRamp = 0;
	
	static final double TEN_PERCENT = 0.1;
	static final double FIFTY_PERCENT = 0.5;
	static final double SEVENTY_PERCENT = 0.7;
	static final String NEWLINE = System.getProperty("line.separator");
	static final int UNSELECTED = -1;
	static final int BASIC = 1;
	static final int SPECIAL = 2;
	static final int LEVEL_UP_THRESHOLD = 5;

	
	public GameWorld()
	{
		gameLoop();
		System.out.println("Well done - you caught " + trainer.getMonsters().size() + " monsters!");
	}

	private void gameLoop()
	{
		Scanner userInput = new Scanner(System.in);
		while (gameStillGoing)
		{
			outputOptions();
			while (!userInput.hasNext());
			resolveInput(userInput.nextLine());
		}
		userInput.close();
	}

	private void outputOptions()
	{
		switch (gameMode)
		{
		case EXPLORE:
			outputExploreChoices();
			break;
		case FIGHT_WILD:
			ouputFightWildChoices();
			break;
		case STARTUP:
			ouputStartupChoices();
			break;
		case AFTER_WILD:
			ouputAfterWildChoices();
			break;
		case MANAGE_TEAM:
			ouputManageTeamChoices();
			break;
		default:
			break;
		}
	}

	private void resolveInput(String input)
	{
		if (input.equals("q"))
		{
			gameStillGoing = false;
		}
		else
		{
			switch (gameMode)
			{
			case STARTUP:
				resolveStartup(input);
				break;
			case EXPLORE:
				resolveExplore(input);
				break;
			case FIGHT_WILD:
				resolveFightWild(input);
				break;
			case AFTER_WILD:
				resolveAfterWild(input);
				break;
			case MANAGE_TEAM:
				resolveManageTeam(input);
				break;
			default:
				break;
			}
		}
	}

	private void resolveExplore(String input)
	{
		switch (input)
		{
		case "n":
			System.out.println("You travel North");
			break;
		case "e":
			System.out.println("You travel East");
			break;
		case "w":
			System.out.println("You travel West");
			break;
		case "s":
			System.out.println("You travel South");
			break;
		case "r":
			for (Monster m : trainer.getMonsters())
			{
				m.rest();
			}
			System.out.println("You rest and your monsters heal fully!");
			break;
		case "m":
			gameMode = Mode.MANAGE_TEAM;
			break;
		default:
			System.out.println("I didn't quite understand that!");
			break;
		}
		if (gameMode == Mode.EXPLORE)
		{
			randomEncounterCheck();
		}
	}

	private void resolveManageTeam(String input)
	{
		// Task 3 
		String[] choice = new String[2];
		int monster = UNSELECTED;
		try {
			
			if (input.equals("r"))
			{
			System.out.println("Enough of this - time to move on!");
			gameMode = Mode.EXPLORE;
			}
			else
			{
				choice = input.split(" ");
				monster = Integer.parseInt(choice[0])-1;
				int itemNumber = Integer.parseInt(choice[1])-1;
				List<Item> currentItems = trainer.getTrainerItems();
				String itemName = currentItems.get(itemNumber).getItemType().name();
						
				trainer.useItem(itemName,monster);
			}
		}
		catch (Exception e) {
			try {
				choice = input.split(" ");
				monster = (Integer.parseInt(choice[0]))-1;
				List<Monster> trainerMonsters = trainer.getRosterByReadiness();
				trainerMonsters.get(monster).setName(choice[1]);
					}
			catch (Exception f) {
				System.out.println("I didn't quite get that, please enter values from above..");
			}
		}
	}

	private void resolveStartup(String input)
	{
		// task 2_2
		try
		{	
			String[] choice = input.split(" ");
			switch (choice[0])
			{
			case "1":
				trainer.addMonster(new Scorchitile(choice[1], trainer.getTrainerLevel(),
						trainer.getTrainerLevel() * 12, trainer.getTrainerLevel() * 10));
				break;
			case "2": 
				trainer.addMonster(new Dragonfish(choice[1], trainer.getTrainerLevel(),
						trainer.getTrainerLevel() * 12));
				break;
		
			case "3": 
				trainer.addMonster(new Seedatops(choice[1], trainer.getTrainerLevel(),
						trainer.getTrainerLevel() * 12, trainer.getTrainerLevel() * 10));
				break;
			}
			System.out.println("The first member of your team! " + NEWLINE
					+ trainer.getMonsters().get(0).makeMonsterDescription() + NEWLINE
					+ "The following items have been added to help you on your way!");
			
			int max = 2;
			int min = 0;
			
			int randomItem1 = (int)Math.floor(Math.random()*(max-min+1)+min);
			int randomItem2 = (int)Math.floor(Math.random()*(max-min+1)+min);
			
			switch(randomItem1) {
			case 0:
				trainer.addItem("HEALING_POTION");
				break;
			case 1:
				trainer.addItem("CRYSTAL");
				break;
			case 2:
				trainer.addItem("RESTORATION_POTION");
				break;
			}
			
			switch(randomItem2) {
			case 0:
				trainer.addItem("HEALING_POTION");
				break;
			case 1:
				trainer.addItem("CRYSTAL");
				break;
			case 2:
				trainer.addItem("RESTORATION_POTION");
				break;
			}
			gameMode = Mode.EXPLORE;
		}
		catch (Exception e)
		{
			System.out.println("I didn't quite understand that!");
		}
	}

	private void resolveFightWild(String input)
	{
		try
		{
			String[] choices = input.split(" ");
			int monsterChoice = Integer.parseInt(choices[0]) - 1;
			int attack = Integer.parseInt(choices[1]);
			makeAttack(monsterChoice, attack);
			if (wildMonster.isConscious())
			{
				if (Math.random() > FIFTY_PERCENT)
				{
					trainer.receiveAttack(monsterChoice, wildMonster.specialAttack());
				}
				else
				{
					trainer.receiveAttack(monsterChoice, wildMonster.basicAttack());
				}
			}
			else
			{
				trainer.increaseXP(1);
				if(trainer.getXP()>LEVEL_UP_THRESHOLD)
				{
					trainer.setTrainerLevel(trainer.getTrainerLevel() + 1);	
				}
				
				System.out.println("You defeated " + wildMonster.getName() + NEWLINE
						+ "--Congratulations you are now trainer level " + trainer.getTrainerLevel());
				trainer.getMonsters().get(monsterChoice).levelUp();
				gameMode = Mode.AFTER_WILD;
			}
		}
		catch (Exception e)
		{
			System.out.println("I didn't quite get that");
		}
	}

	private void resolveAfterWild(String input)
	{
		try
		{
			switch (input)
			{
			case "1":
				trainer.addMonster(wildMonster);
				System.out.println(wildMonster.getName() + " joined the party!");
				break;
			case "2":
				System.out.println(wildMonster.getName() + " skulks back into the wild!");
				break;
			}
			System.out.println("On with the adventure!");
			gameMode = Mode.EXPLORE;
		}
		catch (Exception e)
		{
			System.out.println("I didn't quite get that");
		}
	}

	private void ouputManageTeamChoices()
	{
		System.out.println("Options:");
		System.out.println("-- Use an item on a monster: 'Monster-Number Item-Number'" + NEWLINE
				+ "-- rename a monster: 'Monster-Number New-Name" + NEWLINE + "-- return to exploring the world: r");
		trainer.getRosterByType();
		trainer.printRoster(false);
		trainer.printItems();
	}

	private void ouputAfterWildChoices()
	{
		System.out.println("Do you want to add " + wildMonster.getName() + " to your party?" + NEWLINE + "1-Yes"
				+ NEWLINE + "2-No");
	}

	private void ouputStartupChoices()
	{
		// task 2_1
		System.out.println("You are about to set off on your adventure! Who do you want to accompany you?");
		System.out.println("Enter the number that corresponds to your choice, a space, then a name for them");
		System.out.println("1 - A Scorhitile, a brave little fire reptile with very sharp claws");
		System.out.println("2 - A Dragonfish, a terrifiying aquatic apex predator with enormous jaws and fang like teeth");
		System.out.println("3 - A Seedatops, a colourful plant with a strong, offensive odour which can be used for self-healing");
		trainer.printRoster(false);
	}

	private void outputExploreChoices()
	{
		System.out.println("What do you want to do now?" + NEWLINE + "n - Head North" + NEWLINE + "s - Head South"
				+ NEWLINE + "e - Head East" + NEWLINE + "w - Head West" + NEWLINE + "r - Take a rest" + NEWLINE
				+ "m - Manage team");
	}

	private void ouputFightWildChoices()
	{
		System.out.println("You face:" + wildMonster.makeMonsterDescription());
		trainer.getRosterByReadiness();
		if (!trainer.getMonsters().get(0).isConscious())
		{
			System.out.println("You have no conscious monsters to defend you! Your adventure is over!");
			gameStillGoing = false;
		}
		else
		{
			System.out.println("Choose a monster to fight and the attack you want them to do in the form 'X X'");
			trainer.printRoster(true);
		}
	}

	private void randomEncounterCheck()
	{
		if (Math.random() + encounterLikelihoodRamp > SEVENTY_PERCENT)
		{
			wildMonster = MonsterMaker.makeRandomWildMonster(trainer.getTrainerLevel());
			System.out.println("--------------------------------------------------");
			encounterLikelihoodRamp = 0;
			gameMode = Mode.FIGHT_WILD;
		}
		else
		{
			encounterLikelihoodRamp += TEN_PERCENT;
		}
	}

	private void makeAttack(int monsterChoice, int attack)
	{
		if (attack == BASIC)
		{
			wildMonster.takeDamage(trainer.getMonsters().get(monsterChoice).basicAttack());
		}
		else if (attack == SPECIAL)
		{
			wildMonster.takeDamage(trainer.getMonsters().get(monsterChoice).specialAttack());
		}
	}

	public static void main(String[] args)
	{
		GameWorld g = new GameWorld();
	}
}
