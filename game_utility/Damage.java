package game_utility;

public class Damage
{
	private ElementalType type;
	private int amount;
	
	public Damage(ElementalType type, int amount)
	{
		this.type=type;
		this.amount=amount;
	}
	
	public ElementalType getType()
	{
		return type;
	}

	public void setType(ElementalType type)
	{
		this.type = type;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}
}
