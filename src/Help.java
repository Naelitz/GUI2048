import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


public class Help extends StackPane
{
	// This class is used to design the help button. 
	// Uses the same format as the other horizontal buttons. 
	Rectangle background = new Rectangle();
	Label text = new Label("Help");
	Button button = new Button();
	Help()
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
