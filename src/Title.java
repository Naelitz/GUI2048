import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class Title extends GridPane
{
	Label title = new Label("2048");
	Help help = new Help();
	Label highScore = new Label("High Score");
	IntegerProperty highScoreProperty = new SimpleIntegerProperty();
	Exit exit = new Exit();
	GridPane bar = new GridPane();
	StackPane saveStack = new StackPane();
	Rectangle saveBackground = new Rectangle();
	Label saveText = new Label("Save");
	Button saveButton = new Button();
	StackPane loadStack = new StackPane();
	Rectangle loadBackground = new Rectangle();
	Label loadText = new Label("Load");
	Button loadButton = new Button();
	
	Main main;
	
	Title(Main main, Header header)
	{
		this.main = main;
		createSaveButton();
		createLoadButton();
		this.setMaxWidth(464);
		this.title.setMinHeight(150);
		this.title.setMinWidth(400);
		this.bar.setMinWidth(400);
		this.highScore.setMinWidth(100);
		
		this.title.setFont(Font.font("Ariel", 80));
		title.setAlignment(Pos.CENTER);
		title.setStyle("-fx-font-weight: bold");
		this.add(title, 0, 0);
		this.add(bar, 0, 1);
		bar.add(help, 0, 0);
		bar.add(exit, 1, 0);
		bar.add(saveStack, 2, 0);
		bar.add(loadStack, 3, 0);
		
		
		
		help.button.setOnAction(e -> {
			main.displayHelpMenu();
		});
		
		saveButton.setOnAction(e -> {
			main.saveGame();
		});
		
		loadButton.setOnAction(e -> {
			main.loadGame();
		});
		
	}
	
	void createSaveButton()
	{
			saveStack.getChildren().add(saveBackground);
			saveBackground.setWidth(100);
			saveBackground.setHeight(22);
			saveBackground.setArcHeight(20);
			saveBackground.setArcWidth(20);
			saveStack.getChildren().add(saveText);
			saveStack.getChildren().add(saveButton);
			saveButton.setOpacity(0);
			saveBackground.setStyle("-fx-fill: #bbada0");
	}
	
	void createLoadButton()
	{
		loadStack.getChildren().add(loadBackground);
		loadBackground.setWidth(100);
		loadBackground.setHeight(22);
		loadBackground.setArcHeight(20);
		loadBackground.setArcWidth(20);
		loadStack.getChildren().add(loadText);
		loadStack.getChildren().add(loadButton);
		loadButton.setOpacity(0);
		loadBackground.setStyle("-fx-fill: #bbada0");
	}
}
