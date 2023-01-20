package monsters;
import game_utility.Damage;
import game_utility.ElementalType;

public class Dragonfish extends WaterFamily{

	public Dragonfish(String name, int level, int health)
	{
		super(name, level, health);
		monsterBreed="Dragonfish";
	}
	
	public Damage specialAttack()
	{
		if(!charged)
		{
			takeDamage(new Damage(ElementalType.NORMAL,10));
			System.out.println(name + " hurt themselves trying to use their special attack when it wasn't charged!");
			return super.specialAttack();
		}
		return super.specialAttack();
	}

}
