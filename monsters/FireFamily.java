package monsters;
import game_utility.Damage;
import game_utility.ElementalType;

public abstract class FireFamily extends Monster
{
	protected static final int BASIC_ATTACK_DAMAGE = 10;
	protected static final int SPECIAL_MULTIPLIER = 15;
	protected int fireMana;
	protected int maxFireMana;

	public FireFamily(String name, int level, int health, int maxFireMana)
	{
		super(name, level, health);
		fireMana = maxFireMana;
		this.maxFireMana = maxFireMana;
		monsterType=ElementalType.FIRE;
	}
	
	public int getCurrentReadiness()
	{
		if(!isConscious())
		{
			return -1;
		}
		else
		{
			return currentHealth+fireMana*SPECIAL_MULTIPLIER;
		}
	}

	public void levelUp()
	{
		super.levelUp();
		maxFireMana += 12;
		System.out.println("They now have " + maxFireMana + "max fire mana!");
	}

	public void takeDamage(Damage damage)
	{
		switch (damage.getType())
		{
		case FIRE:
			super.takeDamage(new Damage(damage.getType(), (int) (damage.getAmount() * RESIST_DAMAGE)));
			break;
		case WATER:
			super.takeDamage(new Damage(damage.getType(), (int) (damage.getAmount() * VULNERABLE_DAMAGE)));
			break;
		default:
			super.takeDamage(damage);
		}
	}

	public Damage specialAttack()
	{
		int firepower = monsterLevel * SPECIAL_MULTIPLIER;
		if (fireMana >= firepower)
		{
			System.out.println(name + " breathes fire");
			fireMana -= firepower;
			return new Damage(ElementalType.FIRE, firepower);
		}
		else
		{
			System.out.println(name + " breathes fire weakly");
			int amountDamage = fireMana;
			fireMana = 0;
			return new Damage(ElementalType.FIRE, amountDamage);
		}
	}

	public Damage basicAttack()
	{
		return new Damage(ElementalType.NORMAL, BASIC_ATTACK_DAMAGE);
	}

	public String makeMonsterDescription()
	{
		return super.makeMonsterDescription() + ", a fire monster with " + fireMana + "/" + maxFireMana
				+ " fire mana and " + currentHealth + "/" + maxHealth + " health";
	}

	public void rest()
	{
		fireMana = maxFireMana;
		super.rest();
	}
}
