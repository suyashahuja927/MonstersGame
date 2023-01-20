package game_utility;
import java.util.Comparator;

import monsters.Monster;

public class SortByName implements Comparator<Monster>
{
	public int compare(Monster m1, Monster m2)
	{
		return m1.getName().compareTo(m2.getName());
	}
}
