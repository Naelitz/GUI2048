
public class underArray extends Tile
{
	int[] location = { 18, 168, 318, 468 };
	int locationX = 0;
	int locationY = 0;
	int initialX = 0;
	int initialY = 0;
	int value = 0;
	
	underArray(int row, int column)
	{
		this.initialX = column;
		this.initialY = row;
		this.value = 0;
		
	}
	
	void setIdentity(int row, int column)
	{
		this.initialX = column;
		this.initialY = row;
	}
	
	void setValue(int newValue)
	{
		this.value = newValue;
	}
	
	int getValue()
	{
		return value;
	}
	
	int getIdentityX()
	{
		return initialX;
	}
	
	int getIdentityY()
	{
		return initialY;
	}
	
	void setLocation(int row, int column)
	{
		locationX = column;
		locationY = row;
	}
	
	int getLocationX()
	{
		return locationX;
	}
	
	int getLocationY()
	{
		return locationY;
	}
}
