package items;
import monsters.Monster;

public class Item
{
	protected int uses;
	protected ItemType itemType;
	
	public Item()
	{
	}
	
	public void use(Monster target)
	{
		uses--;
	}
	
	public int getUses()
	{
		return uses;
	}

	public void setUses(int uses)
	{
		this.uses = uses;
	}

	public ItemType getItemType()
	{
		return itemType;
	}

	public String getDescription()
	{
		return itemType.name() + " "+uses;
	}	
}
