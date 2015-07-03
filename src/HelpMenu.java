import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpMenu {
	
	
	public static void display(String title, String message)
	{
		// This is the menu that gets displayed if the help button is clicked. 
		// Just displays a new window when the help button is clicked.
		// Also holds the focus until the window is closed so they can not 
		// go back to playing until the window is closed. 
		BorderPane layout = new BorderPane();
		StackPane top = new StackPane();
		Rectangle header = new Rectangle();
		Rectangle background = new Rectangle();
		Label help = new Label();
		Label instruction = new Label();
		
		StackPane center = new StackPane();
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Help");
		window.setMinWidth(600);
		window.setMaxWidth(600);
		window.setMinHeight(600);
		
		layout.setStyle("-fx-background-color: #eee4da");
		layout.setTop(top);
		top.getChildren().add(header);
		top.getChildren().add(help);
		top.setMinSize(600, 150);
		top.setStyle("-fx-background-color: #eee4da");
		header.setWidth(400);
		header.setHeight(125);
		header.setArcHeight(40);
		header.setArcWidth(40);
		header.setStyle("-fx-fill: #DCC9B9");
		help.setText("Help Menu");
		help.setFont(Font.font("Ariel", 50));
		layout.setCenter(center);
		center.getChildren().add(background);
		background.setWidth(550);
		background.setHeight(400);
		background.setArcHeight(60);
		background.setArcWidth(60);
		background.setStyle("-fx-fill: #DCC9B9");
		center.getChildren().add(instruction);
		instruction.setText(
				  "\n   -> Use the arrow keys to move around the board. Object is to reach 2048."
				+ "\n "
				+ "\n "
				+ "\n   -> You can use Ctrl-Z to undo or press the button located in the top right."
				+ "\n"
				+ "\n"
				+ "\n   -> Feel free to press the save button(Alt - S) to save and continue your game"
				+ "\n   at another time."
				+ "\n"
				+ "\n"
				+ "\n   -> You can also use Alt - X to exit anytime."
				+ "\n");
		
		
		
		Button closeButton = new Button("Close the window");
		closeButton.setOnAction(e -> window.close());
		
	
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}