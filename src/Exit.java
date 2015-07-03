import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


public class Exit extends StackPane
{
	// This class was used to design the exit button. 
	// It takes a stack pane and places a background behind it
	// using a rectangle and then displays text. The button is 
	// invisible on top of the label. 
	Rectangle background = new Rectangle();
	Label text = new Label("Exit");
	Button button = new Button();
	Exit()
	{
		this.setMinWidth(100);
		this.getChildren().add(background);
		background.setWidth(100);
		background.setHeight(22);
		background.setArcHeight(20);
		background.setArcWidth(20);
		this.getChildren().add(text);
		this.getChildren().add(button);
		button.setOpacity(0);
		background.setStyle("-fx-fill: #bbada0");
		
		
		
	}
}
