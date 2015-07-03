import javafx.scene.shape.Rectangle;


public class EmptyCell extends Rectangle
{
	EmptyCell()
	{
		// This is used to construct the empty squares in the background. 
		// This is to create a square in the background where there are no tiles.
		// Used instead of lines so that there could be rounded corners that fit
		// the tiles. Sets the size and sets the rounded edges to the same size as
		// the tiles. 
		this.setWidth(132);
		this.setHeight(132);
		this.setStyle("-fx-fill: #DCC9B9");
		this.setArcWidth(40);
		this.setArcHeight(40);
	}
	
	void Place(int x, int y)
	{
		// allows a loop to call this method and place all the empty cells.  
		this.setLayoutX(x);
		this.setLayoutY(y);
	}
}
