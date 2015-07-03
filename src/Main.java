// Created by David Naelitz on May 1, 2015
// This is the second attempt at a GUI version of 2048 and
// was used for school purposes and purposely mimics the visual aspect
// of 2048 already created. 

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application
{
	Information stats = new Information(this);
	// Keeps track of moves, score, and handles high score.
	Header top;
	// Holds all the objects displayed in top half of board.
	BorderPane MainWindow = new BorderPane();
	GameBoard game = new GameBoard(this);
	// This is where all tiles are handled.
	Scene scene;
	StackPane exitPane;
	Label display = new Label();
	
	@Override
	public void start(Stage PrimaryStage)
	{
		this.top = new Header(this);
		// Creating the main window to be a borderpane
		
		MainWindow.setStyle("-fx-background-color: #E0D1C2");
		
		
		
		scene = new Scene(MainWindow, 618, 800);
		exitPane = new StackPane();
		
		// Creates a label to display the new high score when exiting. 
		// The scale property is set to zero to create an animation on exit.
		
		display.setAlignment(Pos.CENTER);
		display.setScaleX(0);
		display.setMinWidth(600);
		display.setMinHeight(400);
		display.setStyle("-fx-background-color: #bbada0");
		display.setOpacity(100);
		exitPane.getChildren().add(display);
		
		
		
		
		MainWindow.setTop(top);		//Places title and buttons at top of window
		MainWindow.setCenter(game);	//Places the game board.
		// Will only be using top and center. Top will hold buttons and scores
		// Center will hold the gameboard.
		
		game.nextNum();		//Starts the game with two numbers on the board. 
		game.update();
		game.nextNum();
		MainWindow.requestFocus();
		//Makes sure the game starts with focus on the board. 
				
		// Make the stage non-resizable to minimize scaling issues.
		PrimaryStage.setResizable(false);
		PrimaryStage.setTitle("GUI 2048");
		PrimaryStage.setScene(scene);
		PrimaryStage.show();
		
		
		
		top.TitlePane.exit.button.setOnAction(e -> 
		{
			exitInitiated();
		});
		
				
				MainWindow.setOnKeyPressed(e ->
				{
					game.listOfZero();
					// MainWindow handles all key presses 
					// Switch is set up to execute code depending on what command is given
					// integers (a) and (b) are used to verify when a valid move is made. 
					if ( game.zeroes.size() == 0)
					{
						MainWindow.setCenter(exitPane);
						exitPane.setAlignment(Pos.CENTER);
						final Timeline timeline = new Timeline();
						timeline.setCycleCount(1);
						timeline.setAutoReverse(false);
						final KeyValue kv = new KeyValue(display.scaleXProperty(), 1.0);
						final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
						timeline.getKeyFrames().add(kf);

						timeline.play();
						timeline.setOnFinished(a -> 
						{
							display.setScaleX(1); // Used to keep display there after last animation
							display.setText("Game over. Sorry Hope you do better next time");
							MainWindow.setCenter(exitPane);
							exitPane.setAlignment(Pos.CENTER);
							final Timeline timeline2 = new Timeline();
							timeline2.setCycleCount(1);
							timeline2.setAutoReverse(false);
							final KeyValue kv2 = new KeyValue(display.scaleXProperty(), 1);
							final KeyFrame kf2 = new KeyFrame(Duration.millis(5000), kv2);
							timeline2.getKeyFrames().add(kf2);
	
							timeline2.play();
							timeline2.setOnFinished(b -> 
							{
								System.exit(0);
							});
						});
					}
					
					int a = 0;
					int b = 0;
					// This handles all key input.
					switch (e.getCode())
					{
					
					case DOWN:
						game.update(); 				// Will reset the board with current values
						game.updateMove();			// Will increase the current board.
						a = game.pushDown();		// returns a value if a valid move was made.
						b = game.mergeDown(top);	// returns value if merge was made.
						game.pushDown();
						if ( a != 0 || b != 0 )
						{
							top.InfoPane.addMove();	// If a valid move was made add one to move counter
							game.tilepushDown();		// Animate the movement
							game.nextNum();
						}
						MainWindow.requestFocus();
						break;

					case UP:
						game.update();
						game.updateMove();
						a = game.pushUp();
						b = game.mergeUp(top);
						game.pushUp();
						if ( a != 0 || b != 0 )
						{
							top.InfoPane.addMove();
							game.tilepushUp();
							game.nextNum();
						}
						MainWindow.requestFocus();
						break;

					case LEFT:
						game.update();
						game.updateMove();
						a = game.pushLeft();
						b = game.mergeLeft(top);
						game.pushLeft();
						if ( a != 0 || b != 0 )
						{
							top.InfoPane.addMove();
							game.tilepushLeft();
							game.nextNum();
						}
						MainWindow.requestFocus();
						break;

					case RIGHT:
						game.update();
						game.updateMove();
						a = game.pushRight();
						b = game.mergeRight(top);
						game.pushRight();
						if ( a != 0 || b != 0 )
							{
								top.InfoPane.addMove();
								game.tilepushRight();
								game.nextNum();
							}
						MainWindow.requestFocus();
						break;
						
					case Z: if(e.isControlDown()) // Key combination to call undo
					{
						top.getButtons().undoCalled();
					}
					
					case S: if (e.isAltDown())
					{
						saveGame();
					}
					
					case X: if (e.isAltDown())
					{
						exitInitiated();
					}
					
					case H: if (e.isAltDown())
					{
						displayHelpMenu();
					}

					default:
						MainWindow.requestFocus(); // If any other key is pressed just maintain focus.
					}
				});
				MainWindow.requestFocus();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	
	// The following get methods is for passing references to other classes. 
	public Header getHeader()
	{
		return top;
	}
	
	public Information getInformation()
	{
		return stats;
	}
	
	public GameBoard getGame()
	{
		return game;
	}
	
	public void saveGame()
	{
		// this saves all important information and loops through all tile values and 
		// undo values to write into file. 
		try(DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("2048.dat"))))
		{
			out.writeInt(game.getCurrentBoard());
			for(int a = 0; a < 10; a++)
			{
				for(int b = 0; b < 4; b++)
				{
					for(int c = 0; c < 4; c++)
					{
						int value = game.getCellValue(a, b, c);
						out.writeInt(value);
					}
				}
			}
			out.writeInt(stats.getScore().getValue());
			out.writeInt(stats.getMoves().getValue());
			out.flush();
		}catch(IOException ex){
			ex.printStackTrace();
		};
	
		System.out.print("Save used");
	}
	
	public void loadGame()
	{
		// This loads all the important information in the same order that 
		// was saved. 
		try(DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("2048.dat"))))
		{
			int currentBoard = in.readInt();
			game.setCurrentBoard(currentBoard);
			for(int a = 0; a < 10; a++)
			{
				for(int b = 0; b < 4; b++)
				{
					for(int c = 0; c < 4; c++)
					{
						int value = in.readInt();
						game.setCellValue(a, b, c, value);
					}
				}
			}
			stats.getScore().setValue(in.readInt());
			stats.getMoves().setValue(in.readInt());
		}catch(IOException ex){
			ex.printStackTrace();
		};
		game.update();
	}
	
	public void displayHelpMenu()
	{
		HelpMenu.display("Title of window",  "Wow this alert box is awesome");
	}
	
	public void exitInitiated()
	{
		if (stats.wasHighScoreSet())
		{
			// Will only display the new high score if it is larger then the old high score.
			// If the old high score is still the high score the system will just exit. 
			// Animation just creates a box that expands the screen displaying score.
			MainWindow.setCenter(exitPane);
			exitPane.setAlignment(Pos.CENTER);
			final Timeline timeline = new Timeline();
			timeline.setCycleCount(1);
			timeline.setAutoReverse(false);
			final KeyValue kv = new KeyValue(display.scaleXProperty(), 1.0);
			final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
			timeline.getKeyFrames().add(kf);
	
			timeline.play();
			timeline.setOnFinished(a -> 
			{
				display.setScaleX(1); // Used to keep display there after last animation
				display.setText("New high score was set! You got: " + stats.getHighScore().getValue());
				MainWindow.setCenter(exitPane);
				exitPane.setAlignment(Pos.CENTER);
				final Timeline timeline2 = new Timeline();
				timeline2.setCycleCount(1);
				timeline2.setAutoReverse(false);
				final KeyValue kv2 = new KeyValue(display.scaleXProperty(), 1);
				final KeyFrame kf2 = new KeyFrame(Duration.millis(5000), kv2);
				timeline2.getKeyFrames().add(kf2);
		
				timeline2.play();
				timeline2.setOnFinished(b -> 
				{
					System.exit(0);
				});
			});
		}
		else System.exit(0);
	}
}

