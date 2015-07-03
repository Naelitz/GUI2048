import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Tile extends StackPane
{
	Rectangle tile = new Rectangle();
	Label number = new Label();
	int[] location = { 18, 168, 318, 468 };
	int value = 0;
	int moves = 0;

	Tile()
	{
		// This creates all the tiles and sets their size to 132 x 132. It also sets 
		// the font and binds their scale property for an animation used later
		// when a new tile is added to the board. Sets the rounded edges to match the 
		// empty cells displayed behind them.
		tile.setWidth(132);
		tile.setHeight(132);
		this.changeValue(0);
		tile.setArcHeight(30);
		tile.setArcWidth(30);
		number.setFont(Font.font("Comic Sans MS", 36));
		this.scaleXProperty().bindBidirectional(this.scaleYProperty());
		this.getChildren().add(tile);
		this.getChildren().add(number);

	}

	void changeValue(int newNumber)
	{
		// When the value is changed the text and the color has to be changed. 
		// This switch statement handles this and when a tile goes over the value of 2048 
		// it just stays a cetain color. 
		switch (newNumber)
		{
		case 0:
			this.tile.setStyle("-fx-fill: #CCB299");
			break;
		case 2:
			this.number.setText(String.valueOf(newNumber));
			this.value = newNumber;
			this.tile.setStyle("-fx-fill: #eee4da");
			break;
		case 4:
			this.number.setText(String.valueOf(newNumber));
			this.value = newNumber;
			this.tile.setStyle("-fx-fill: #ede0c8");
			break;
		case 8:
			this.number.setText(String.valueOf(newNumber));
			this.value = newNumber;
			this.tile.setStyle("-fx-fill: #f2b179");
			break;
		case 16:
			this.number.setText(String.valueOf(newNumber));
			this.value = newNumber;
			this.tile.setStyle("-fx-fill: #f59563");
			break;
		case 32:
			this.number.setText(String.valueOf(newNumber));
			this.value = newNumber;
			this.tile.setStyle("-fx-fill: #f67c5f");
			break;
		case 64:
			this.tile.setStyle("-fx-fill: #f65e3b");
			this.value = newNumber;
			this.number.setText(String.valueOf(newNumber));
			break;
		case 128:
			this.tile.setStyle("-fx-fill: #edcf72");
			this.value = newNumber;
			this.number.setText(String.valueOf(newNumber));
			break;
		case 256:
			this.tile.setStyle("-fx-fill: #edcc61");
			this.value = newNumber;
			this.number.setText(String.valueOf(newNumber));
			break;
		case 512:
			this.setStyle("-fx-fill:  #edc850");
			this.value = newNumber;
			this.number.setText(String.valueOf(newNumber));
			break;
		case 1024:
			this.setStyle("-fx-fill: #edc53f");
			this.value = newNumber;
			this.number.setText(String.valueOf(newNumber));
			break;
		case 2048:
			this.setStyle("-fx-fill: #edc22e");
			this.value = newNumber;
			this.number.setText(String.valueOf(newNumber));
			break;
		default:
			this.setStyle("-fx-fill: #3c3a32");
			this.value = newNumber;
			this.number.setText(String.valueOf(newNumber));
		}
	}

	void Place(int y, int x, int num)
	{
		// When the board is updated this allows the tile to be places at a certain pixel location
		// without having to know the pixels. 
		this.setLayoutX(location[x]);
		this.setLayoutY(location[y]);
		this.changeValue(num);
	}
	
	void moveCount(int zeroes)
	{
		// Returns how many zeroes it had to move over in the animation. 
		this.moves = zeroes;
	}

	DoubleProperty scaleUp()
	{
		// Sets the scale property to 0 so that when the animation calls this method
		// it can start from the center of a tile and then expand to create a bloom effect. 
		this.setScaleX(0);
		return this.scaleXProperty();
	}
	
	DoubleProperty scaleDown()
	{
		// Just like above sets the scale property to create the small shrink effect used
		// at the end of the bloom animation. 
		this.setScaleX(1.2);
		return this.scaleXProperty();
	}
}
