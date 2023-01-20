package monsters;
import game_utility.Damage;
import game_utility.ElementalType;

public class Scorchitile extends FireFamily
{
	public Scorchitile(String name, int monsterLevel, int maxHealth, int maxFireMana)
	{
		super(name, monsterLevel, maxHealth, maxFireMana);
		monsterBreed="Scorchitile";
	}

	public Damage basicAttack()
	{
		System.out.println(name + "makes a scratching attack!");
		return new Damage(ElementalType.NORMAL,monsterLevel*BASIC_ATTACK_DAMAGE);
	}
}
