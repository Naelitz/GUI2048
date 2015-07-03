import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class Info extends Pane
{
	// This class is used to create the square objects in the top right of the window. 
	
	Main main = null;
	GridPane grid = new GridPane();
	StackPane stack = new StackPane();
	Rectangle background = new Rectangle();
	Label text = new Label();
	Label text2 = new Label();
	Button button = new Button();
	int moves = 0;
	
	Info()
	{
		// Allows you to use this class to create a square label with nothing
		// initially displayed. 
		Rectangle background = new Rectangle();
		Label text = new Label();
		Label text2 = new Label();
		this.getChildren().add(background);
		background.setStyle("-fx-fill: #bbada0");
		background.setWidth(90);
		background.setHeight(90);
		background.setArcHeight(40);
		background.setArcWidth(40);
		
	}
	
	Info(Main main,String type)
	// An overloaded constructor that I can pass what I want it to be in a string to create the correct info tile.
	{
		this.main = main;
		switch(type)
		{
		case "score": 	score(); break;
			
		case "moves": 	moves(); break;
			
		case "undo":	undo(); break;
		}
	}
	
	void score()
	{
		// Creates the score tile and binds the text to the current score value. 
		GridPane grid = new GridPane();
		StackPane stack = new StackPane();
		Rectangle background = new Rectangle();
		this.text.setText("Score:");
		this.text2.textProperty().bind(Bindings.convert(main.getInformation().getScore()));
		grid.add(text, 0, 0);
		grid.add(text2, 0, 1);
		text.setFont(Font.font("Comic Sans MS", 16));
		text2.setFont(Font.font("Comic Sans MS",  20));
		text2.setStyle("-fx-font-weight: bold");
		stack.getChildren().add(background);
		stack.getChildren().add(grid);
		this.getChildren().add(stack);
		grid.setAlignment(Pos.CENTER);
		background.setStyle("-fx-fill: #bbada0");
		background.setWidth(90);
		background.setHeight(90);
		background.setArcHeight(40);
		background.setArcWidth(40);
	}
	

	
	void moves()
	{
		// Creates the move tile with the current value bound to the current amount
		// of moves. 
		Rectangle background = new Rectangle();
		this.text.setText("Moves:");
		this.text2.setText(String.valueOf(0));
		grid.add(text, 0, 0);
		grid.add(text2, 0, 1);
		text.setFont(Font.font("Comic Sans MS", 16));
		text2.setFont(Font.font("Comic Sans MS",  20));
		text2.setStyle("-fx-font-weight: bold");
		stack.getChildren().add(background);
		stack.getChildren().add(grid);
		this.getChildren().add(stack);
		grid.setAlignment(Pos.CENTER);
		background.setStyle("-fx-fill: #bbada0");
		background.setWidth(90);
		background.setHeight(90);
		background.setArcHeight(40);
		background.setArcWidth(40);
	}
	void addMove()
	{
		// When a valid move was made it adds a move to the move display. 
		this.moves += 1;
		this.text2.setText(String.valueOf(moves));
	}
	
	void undoMove()
	{
		// When undo is clicked or pressed the moves are decremented by one. 
		this.moves -= 1;
		this.text2.setText(String.valueOf(moves));
	}
	
	void undo()
	{
		// This creates the undo tile.
		stack.getChildren().add(background);
		stack.getChildren().add(text);
		text.setText("Undo");
		stack.getChildren().add(button);
		button.setOpacity(0);
		this.getChildren().add(stack);
		text.setFont(Font.font("Comic Sans MS", 16));
		background.setStyle("-fx-fill: #bbada0");
		background.setWidth(90);
		background.setHeight(90);
		background.setArcHeight(40);
		background.setArcWidth(40);
	}
}
