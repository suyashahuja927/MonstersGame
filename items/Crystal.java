package items;
import monsters.Monster;

public class Crystal extends Item
{
	public Crystal()
	{
		itemType=ItemType.CRYSTAL;
		uses=1;
	}
	
	public void use(Monster target)
	{
		System.out.println("Using crystal on "+target.getName());
		target.levelUp();
		super.use(target);
	}
	
}
