package game_utility;
import java.util.Comparator;

import monsters.Monster;

public class SortByType implements Comparator<Monster>
{

	public int compare(Monster m1, Monster m2)
	{
		if(m1.getMonsterType().compareTo(m2.getMonsterType())==0)
		{
			if (m1.getMonsterBreed().compareTo(m2.getMonsterBreed())==0)
			{
				return m1.getName().compareTo(m2.getName());
			}
			else
			{
				return m1.getMonsterBreed().compareTo(m2.getMonsterBreed());
			}
		}
		else
		{
			return m1.getMonsterType().compareTo(m2.getMonsterType());
		}
	}
}
