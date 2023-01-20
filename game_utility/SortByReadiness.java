package game_utility;
import java.util.Comparator;

import monsters.Monster;

public class SortByReadiness implements Comparator<Monster>
{
	public int compare(Monster m1, Monster m2)
	{
		return m2.getCurrentReadiness()-m1.getCurrentReadiness();
	}
}
