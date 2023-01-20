package monsters;
import game_utility.Damage;
import game_utility.ElementalType;

public class Seedatops extends PlantFamily
{
	public Seedatops(String name, int monsterLevel, int maxHealth, int maxCharges)
	{
		super(name, monsterLevel, maxHealth, maxCharges);
		monsterBreed = "Seedatops";
	}

	public Damage basicAttack()
	{
		Damage damage = super.basicAttack();
		System.out.println(name + "Heals itself with it's attack");
		takeDamage(new Damage(ElementalType.NORMAL, -monsterLevel * BASIC_MULTIPLIER));
		return damage;
	}
}
