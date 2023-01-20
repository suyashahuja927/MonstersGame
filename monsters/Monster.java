package monsters;
import game_utility.Damage;
import game_utility.ElementalType;

public abstract class Monster
{
	protected String name;
	protected ElementalType monsterType;
	protected String monsterBreed;
	protected int monsterLevel;
	protected int maxHealth;
	protected int currentHealth;

	protected final static double RESIST_DAMAGE = 0.5;
	protected final static double VULNERABLE_DAMAGE = 2;
	protected final static int LEVELUP_HEALTH_GAIN = 20;

	public Monster(String name, int monsterLevel, int maxHealth)
	{
		this.name = name;
		this.monsterLevel = monsterLevel;
		currentHealth = maxHealth;
		this.maxHealth = maxHealth;
	}
	
	public abstract int getCurrentReadiness();
	
	public ElementalType getMonsterType()
	{
		return monsterType;
	}

	public void levelUp()
	{
		monsterLevel++;
		maxHealth += LEVELUP_HEALTH_GAIN;
		System.out.print("--" + name + " is now a level " + monsterLevel + " " + monsterBreed + " and they have "
				+ maxHealth + " max health.");
	}
	
	public void rest()
	{
		currentHealth = maxHealth;
	}

	public abstract Damage basicAttack();

	public abstract Damage specialAttack();

	public void takeDamage(Damage damage)
	{
		currentHealth -= damage.getAmount();
		System.out.println(name + " takes " + damage.getAmount() + " " + damage.getType() + " damage.");
		if (currentHealth <= 0)
		{
			currentHealth = 0;
			System.out.println(name + " is defeated");
		}
		if (currentHealth >= maxHealth)
		{
			currentHealth = maxHealth;
			System.out.println(name + " is at full health!");
		}
	}

	public boolean isConscious()
	{
		return currentHealth>0;
	}

	public String makeMonsterDescription()
	{
		return (name.toUpperCase() + " a level " + monsterLevel + " " + monsterBreed);
	}

	public String getName()
	{
		return name;
	}

	public String getMonsterBreed()
	{
		return monsterBreed;
	}

	public int getMonsterLevel()
	{
		return monsterLevel;
	}

	public int getHealth()
	{
		return currentHealth;
	}

	public int getMaxHealth()
	{
		return maxHealth;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
