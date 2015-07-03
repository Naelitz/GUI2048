import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class Buttons extends Pane
{
	Main main = null;
	Info RUndo = new Info(main, "undo");
	Info RMoves = new Info(main, "moves");
	GridPane InfoPane = new GridPane();
	Info Rscore;
	Label highScore = new Label();
	Label highScoreLabel = new Label("\nHighScore:");
	StackPane highScoreStack = new StackPane();
	Rectangle highScoreBackground = new Rectangle();
	GridPane highScoreGrid = new GridPane();
	GameBoard game;
	Header top;
	
	Buttons(Main main, Header top)
	{
		// This will maintain score, moves, highscore, and undo button.
		// The GridPanes make it so that there is text to show what value is being displayed. 
		this.main = main;
		this.Rscore = new Info(main, "score");
		this.game = main.getGame();
		this.top = top;
		GridPane ButtonsPane = new GridPane();
		createHighScore(); // Creates the highscore display.
		this.getChildren().add(InfoPane);
		this.setHeight(Double.MAX_VALUE);
		this.setWidth(Double.MAX_VALUE);
		InfoPane.setVgap(5);
		InfoPane.setHgap(5);
		InfoPane.add(Rscore, 0, 0);
		InfoPane.add(RMoves, 1, 0);
		InfoPane.add(RUndo, 0, 1);
		InfoPane.add(highScoreStack, 1, 1);
		
		RUndo.button.setOnAction(e -> {
			// When the undo button is clicked the undo will be called. 
			undoCalled();
			
		});
	}
	
	void addMove()
	{
		// Used to pass the add move method if called from another class. 
		this.RMoves.addMove();
	}
	
	void undo()
	{
		// Used to pass the undo method if called from another class. 
		this.RMoves.undoMove();
	}
	void undoCalled()
	{
		// this is the method used if the undo button is clicked or ctrl z is used. 
		if (game.current > 0)  // this makes sure that there has been a previous move first.
		{
		game.printBoard();
		game.undo(); 
		top.undo();
		game.printBoard();
		game.requestFocus(); // makes sure the game gets focus again after clicking the button. 
		}
	}
	
	void createHighScore()
	{
		// Creates a stackpane and puts a grid in it and then 
		// Creates the high score button. Places it in a grid
		// and displays the highscore value. 
		highScoreStack.getChildren().add(highScoreBackground);
		highScoreStack.getChildren().add(highScoreGrid);
		highScoreGrid.add(highScore, 0, 1);
		highScoreGrid.add(highScoreLabel, 0, 0);
		highScore.setAlignment(Pos.CENTER);
		highScoreGrid.setMinSize(80, 80);
		highScoreGrid.setMaxSize(80, 80);
		highScoreLabel.setFont(Font.font("Comic Sans MS", 14));
		highScoreLabel.setStyle("-fx-font-weight: bold");
		highScore.textProperty().bind(Bindings.convert(main.getInformation().getHighScore()));;
		this.getChildren().add(highScoreStack);
		highScore.setFont(Font.font("Comic Sans MS", 16));
		highScoreBackground.setStyle("-fx-fill: #bbada0");
		highScoreBackground.setWidth(90);
		highScoreBackground.setHeight(90);
		highScoreBackground.setArcHeight(40);
		highScoreBackground.setArcWidth(40);
	}
	
	
}
