package monsters;
import game_utility.Damage;
import game_utility.ElementalType;

public abstract class WaterFamily extends Monster
{
	protected static final double TEN_PERCENT=0.1;
	protected static final int BASIC_MULTIPLIER=10;
	protected static final int SPECIAL_MULTIPLIER=40;
	
	protected boolean charged = true;
	protected double rechargeChance = TEN_PERCENT;
	protected double rechargeGrowth = TEN_PERCENT;

	public WaterFamily(String name, int level, int health)
	{
		super(name, level, health);
		monsterType=ElementalType.WATER;
	}

	public int getCurrentReadiness()
	{
		if(!isConscious())
		{
			return -1;
		}
		else if(charged)
		{
			return currentHealth+monsterLevel*SPECIAL_MULTIPLIER;
		}
		else
		{
			return currentHealth+monsterLevel*BASIC_MULTIPLIER;
		}
	}
	
	public void levelUp()
	{
		rechargeGrowth += TEN_PERCENT;
		super.levelUp();
		System.out.println("They now increase their chance to recharge their special attack by " + rechargeGrowth * 100
				+ "% per basic attack");
	}


	public void takeDamage(Damage damage)
	{
		switch (damage.getType())
		{
		case WATER:
			super.takeDamage(new Damage(damage.getType(),(int)(damage.getAmount()*RESIST_DAMAGE)));
			break;
		case PLANT:
			super.takeDamage(new Damage(damage.getType(),(int)(damage.getAmount()*VULNERABLE_DAMAGE)));
			break;
		default:
			super.takeDamage(damage);
		}
	}

	public Damage specialAttack()
	{
		if (charged)
		{
			System.out.println(name + " shoots a jet of water!");
			charged = false;
			return new Damage(ElementalType.WATER, monsterLevel * SPECIAL_MULTIPLIER);
		}
		else
		{
			System.out.println(name + " is not ready for this attack");
			return new Damage(ElementalType.WATER, 0);
		}
	}

	public String makeMonsterDescription()
	{
		if (charged)
		{
			return super.makeMonsterDescription() + ", a water monster with a charged attack and " + currentHealth + "/"
					+ maxHealth + " health";
		}
		else
		{
			return super.makeMonsterDescription() + ", a water monster with a " + rechargeChance * 100
					+ "% chance to recharge on their next attack and " + currentHealth + "/" + maxHealth + " health";
		}
	}

	public void rest()
	{
		charged = true;
		rechargeChance=TEN_PERCENT;
		super.rest();
	}

	public Damage basicAttack()
	{
		System.out.println(name + " slaps at their enemy!");
		if (!charged)
		{
			rechargeChance += rechargeGrowth;
			if (Math.random() < rechargeChance)
			{
				charged = true;
				System.out.println(name + " recharged their water special!");
			}
		}
		return new Damage(ElementalType.NORMAL, BASIC_MULTIPLIER * monsterLevel);
	}
}
