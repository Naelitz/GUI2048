import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GameBoard extends Pane
{
	Tile a = new Tile();
	// The gameboard is where all of the game play takes place.
	int[] location = { 18, 168, 318, 468 }; // Holds values of the pixel
											// locations
	EmptyCell[][] cells = new EmptyCell[4][4]; // Creates the background squares
												// where tiles will be placed.
	static Tile[][] tiles = new Tile[4][4]; // These are the actual number tiles
	static underArray[][][] board = new underArray[10][4][4]; 
	// Creates an array to store values and previous moves. 
	static int current = 0; // has the game play on current board.
	public ArrayList<int[]> zeroes; // Creates a list of where the zeroes on the
									// board are located.
	PathTransition pt = new PathTransition();
	Main main = null;

	GameBoard(Main main)
	{
		// This class is where all the gameplay occurs. 
		// To do this we will set the size of the board and place 
		// empty tiles to create the background. 
		// It goes through a loop to initialize all the objects
		// needed in this class. 
		this.main = main;
		this.setStyle("-fx-background-color:  #bbada0");
		this.setMaxSize(618, 618);
		this.setPrefSize(618, 618);
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				cells[i][j] = new EmptyCell();
				tiles[i][j] = new Tile();
				for (int k = 0; k < 10; k++)
				{
					board[k][i][j] = new underArray(i, j);
				}
				this.getChildren().add(cells[i][j]);
				cells[i][j].Place(location[i], location[j]); // Initialized my
																// cells and
																// tiles and
																// draws board.
			}
		}
		zeroes = listOfZero(); // returns how many empty spaces are on the board. 

	}

	public void nextNum()
	// This puts the next number on the board
	{
		listOfZero(); // updates how many zeroes are on the board. 
		if (zeroes.size() != 0) // If there is a zero on the board
		{
			int nextNumber;
			double rand = Math.random();
			if (rand < .90)			// Creates a percentage of 2's spawned. 
				nextNumber = 2;
			else
				nextNumber = 4;

			int spaces = randomNumGen(zeroes.size());
			int[] RandC = zeroes.get(spaces); // Selects a zero location at
												// random.
			int row = RandC[0];
			int column = RandC[1];
			board[current][row][column] = new underArray(row, column);
			board[current][row][column].setValue(nextNumber);
			this.getChildren().add(a);
			// adds the tile to the board
			a.Place(row, column, nextNumber); 
			// Places the tile and gives it value.

			// update();
			// This updates the board so that the new tile on the
			// board is in the backup board.
			ScaleAnimation(tiles[row][column]);
		}
	}

	public static int randomNumGen(int spacesLeft)
	// This will return a random number based on how many spaces are left. 
	// it allows the board to call a random empty cell for the new number
	// to be spawned. 
	{
		int n;
		n = (int) (int) (Math.random() * (spacesLeft));
		return n;
	}

	public int pushLeft() // Loops for shifting all the numbers in the
	// array left. There are four push methods that do the same thing. 
	// It will loop through the entire current board twice to make sure it pushes all 
	// the zeroes out of the board before a merge is attempted. 
	{
		int pushCount = 0;

		for (int i = 0; i <= 2; i++)
		{
			for (int row = 3; row >= 0; row--)
			{
				for (int column = 3; column >= 1; column--)
				{
					if ((board[current][row][column - 1].getValue() == 0 && (board[current][row][column]
							.getValue() != 0)))
					// If the location next to the tile is blank then move over
					// one tile.
					{
						board[current][row][column - 1].setIdentity(
								board[current][row][column].getIdentityY(),
								board[current][row][column].getIdentityX());
						board[current][row][column - 1]
								.setValue(board[current][row][column].getValue());
						board[current][row][column].setValue(0); 
						pushCount += 1; 

					}
				}
			}
		}
		return pushCount;
	}

	public int mergeLeft(Header top)
	{
		// There are also four merge methods depending on the direction one moves. 
		// this merges like tiles together after all the zeroes have been moved out of
		// the way. It only allows two numbers are the same type to merge even if there
		// are three tiles of the same type. 
		int mergeCount = 0;
		for (int i = 0; i <= 3; i++)
		{
			for (int j = 0; j <= 2; j++)
			{
				if ((board[current][i][j].getValue() == board[current][i][j + 1].getValue()) 
													&& (board[current][i][j].getValue() != 0))
				{
					// If both locations equal each other but are not equal to zero (this stops it from 
					// wasting time adding zeroes together. If they are equal add them together and remove the
					// tile opposite of the direction we are trying to move. It passes an identity to the next cell
					// this identity is used later for animation. To Animate from where it originated
					// to where it ended up. 
					board[current][i][j].setValue((board[current][i][j].getValue() * 2));
					tiles[i][j].changeValue(board[current][i][j + 1].getValue() * 2);
					board[current][i][j].setIdentity(board[current][i][j + 1].getIdentityY(), 
													board[current][i][j + 1].getIdentityX());
					board[current][i][j + 1].setIdentity(-1, -1);
					main.getInformation().addScore(board[current][i][j].getValue());
					board[current][i][j + 1].setValue(0);
					tiles[i][j + 1].changeValue(0);
					this.getChildren().
					remove(tiles[i][j + 1]);
					mergeCount += 1;

				}
			}
		}
		return mergeCount;
	}

	public int pushRight()
	// array left.
	{
		int pushCount = 0;

		for (int i = 0; i <= 2; i++)
		{
			for (int row = 3; row >= 0; row--)
			{
				for (int column = 0; column <= 2; column++)
				{
					if ((board[current][row][column + 1].getValue() == 0 && (board[current][row][column]
							.getValue() != 0)))
					// If the location next to the tile is blank then move over
					// one tile.
					{
						board[current][row][column + 1].setIdentity(
								board[current][row][column].getIdentityY(),
								board[current][row][column].getIdentityX());
						board[current][row][column + 1]
								.setValue(board[current][row][column].getValue());
						board[current][row][column].setValue(0);
						pushCount += 1;
					}
				}
			}
		}
		return pushCount;
	}

	int mergeRight(Header top)
	{
		int mergeCount = 0;
		for (int i = 0; i <= 3; i++)
		{
			for (int j = 3; j >= 1; j--)
			{
				if ((board[current][i][j].getValue() == board[current][i][j - 1].getValue())
													&& (board[current][i][j].getValue() != 0))
				{
					tiles[i][j].changeValue((board[current][i][j].getValue() * 2));
					board[current][i][j].setValue((board[current][i][j].getValue() * 2));
					main.getInformation().addScore(board[current][i][j].getValue());
					board[current][i][j].setIdentity(board[current][i][j - 1].getIdentityY(), 
													board[current][i][j - 1].getIdentityX());
					board[current][i][j - 1].setIdentity(0, 0);
					board[current][i][j - 1].setValue(0);
					tiles[i][j - 1].changeValue(0);
					this.getChildren()
								.remove(tiles[i][j - 1]);
					mergeCount += 1;

				}
			}
		}
		return mergeCount;
	}

	public int pushDown()
	// array left.
	{
		int pushCount = 0;

		for (int i = 0; i <= 2; i++)
		{
			for (int column = 3; column >= 0; column--)
			{
				for (int row = 0; row <= 2; row++)
				{
					if ((board[current][row + 1][column].getValue() == 0 && (board[current][row][column]
																			.getValue() != 0)))
						// Loops through the entire board twice and if there is a zero moves the 
						// Number over. The board keeps track of where that number originated from.
						// 
					{
						board[current][row + 1][column].setIdentity(
								board[current][row][column].getIdentityY(),
								board[current][row][column].getIdentityX());
						board[current][row + 1][column].setValue(board[current][row][column].getValue());
						board[current][row][column].setIdentity(0, 0);

						board[current][row][column].setValue(0);
						pushCount += 1;

					}
				}
			}
		}
		return pushCount;
	}

	public int mergeDown(Header top)
	{
		int mergeCount = 0;
		for (int j = 0; j <= 3; j++)
		{
			for (int i = 3; i >= 1; i--)
			{
				if ((board[current][i][j].getValue() == board[current][i - 1][j].getValue())
						&& (board[current][i][j].getValue() != 0))
				{
					board[current][i][j].setValue((board[current][i][j].getValue() * 2));
					tiles[i][j].changeValue(board[current][i-1][j].getValue() * 2);
					main.getInformation().addScore(board[current][i][j].getValue());
					board[current][i][j].setIdentity(board[current][i - 1][j].getIdentityY(), 
													board[current][i - 1][j].getIdentityX());
					board[current][i - 1][j].setIdentity(0, 0);
					board[current][i - 1][j].setValue(0);
					tiles[i - 1][j].changeValue(0);
					this.getChildren().remove(tiles[i - 1][j]);
					mergeCount += 1;

				}
			}
		}
		return mergeCount;
	}

	public int pushUp() // Pushes all the numbers on current array up
	{
		int pushCount = 0;

		for (int i = 0; i <= 2; i++)
		{
			for (int column = 3; column >= 0; column--)
			{
				for (int row = 3; row >= 1; row--)
				{
					if ((board[current][row - 1][column].getValue() == 0 && (board[current][row][column]
							.getValue() != 0)))
					// If the location next to the tile is blank then move over
					// one tile.
					{
						// If the location next to the tile is blank then move
						// over
						// one tile.
						board[current][row - 1][column].setIdentity(
								board[current][row][column].getIdentityY(),
								board[current][row][column].getIdentityX());
						board[current][row - 1][column]
								.setValue(board[current][row][column]
										.getValue());

						board[current][row][column].setValue(0);
						pushCount += 1;

					}
				}
			}
		}
		return pushCount;
	}
	
	int mergeUp(Header top)
	{
		int mergeCount = 0;
		for (int j = 0; j <= 3; j++)
		{
			for (int i = 0; i <= 2; i++)
			{
				if ((board[current][i][j].getValue() == board[current][i + 1][j].getValue())
						&& (board[current][i][j].getValue() != 0))
				{
					board[current][i][j].setValue((board[current][i][j].getValue() * 2));
					tiles[i][j].changeValue(board[current][i + 1][j].getValue() * 2);
					main.getInformation().addScore(board[current][i][j].getValue());
					board[current][i][j].setIdentity(board[current][i + 1][j].getIdentityY(), 
													board[current][i + 1][j].getIdentityX());
					board[current][i + 1][j].setIdentity(0, 0);
					board[current][i + 1][j].setValue(0);
					tiles[i + 1][j].changeValue(0);
					this.getChildren().remove(tiles[i + 1][j]);

					mergeCount += 1;

				}
			}
		}
		return mergeCount;

	}
	
	void tilepushLeft()
	// Second attempt at animation. This will look at where the tile is currently
	// then look at where it originated from. The tiles that are displayed have not been 
	// moved yet. This will move the tiles to where the underlying array says the values should
	// be. The tiles on the board will then be updated at the end of the animation
	// so that their location is now correct. 
	
	// Designed to handle the animation of moving one tile to the left.
	{
		int oldColumn = 0;
		int oldRow = 0;
		for (int row = 0; row < 4; row++)
		{
			for (int column = 0; column < 4; column++)
			{
				if (board[current][row][column].getValue() != 0)
				{
					oldColumn = (board[current][row][column].getIdentityX());
					oldRow = (board[current][row][column].getIdentityY());
					final Timeline timeline = new Timeline();
					timeline.setCycleCount(1);
					timeline.setAutoReverse(false);
					final KeyValue kv = new KeyValue(
							tiles[oldRow][oldColumn].layoutXProperty(),
							location[column]);
					final KeyFrame kf = new KeyFrame(Duration.millis(100), kv);
					timeline.getKeyFrames().add(kf);
					timeline.play();
					timeline.setOnFinished(e -> {update();});
				}

			}
		}

	}

	void tilepushRight()
	// Second attempt at animation. 

	// Designed to handle the animation of moving one tile to the right.
	{
		int oldColumn = 0;
		int oldRow = 0;
		for (int row = 0; row < 4; row++)
		{
			for (int column = 0; column < 4; column++)
			{
				if (board[current][row][column].getValue() != 0)
				{
					oldColumn = (board[current][row][column].getIdentityX());
					oldRow = (board[current][row][column].getIdentityY());
					final Timeline timeline = new Timeline();
					timeline.setCycleCount(1);
					timeline.setAutoReverse(false);
					final KeyValue kv = new KeyValue(
							tiles[oldRow][oldColumn].layoutXProperty(),
							location[column]);
					final KeyFrame kf = new KeyFrame(Duration.millis((Math.abs(oldColumn - column)) * 30), kv);
					timeline.getKeyFrames().add(kf);

					timeline.play();
					timeline.setOnFinished(e -> {update();});
				}

			}
		}
	}

	void tilepushUp()
	// Second attempt at animation. 
	// Designed to handle the animation of moving one tile to the up.
	{
		int oldColumn = 0;
		int oldRow = 0;
		for (int row = 0; row < 4; row++)
		{
			for (int column = 0; column < 4; column++)
			{
				if (board[current][row][column].getValue() != 0)
				{
					oldColumn = (board[current][row][column].getIdentityX());
					oldRow = (board[current][row][column].getIdentityY());
					final Timeline timeline = new Timeline();
					timeline.setCycleCount(1);
					timeline.setAutoReverse(false);
					final KeyValue kv = new KeyValue(
							tiles[oldRow][oldColumn].layoutYProperty(),
							location[row]);
					final KeyFrame kf = new KeyFrame(Duration.millis((Math.abs(oldRow - row)) * 30), kv);
					timeline.getKeyFrames().add(kf);

					timeline.play();
					timeline.setOnFinished(e -> {update();});
				}

			}
		}
	}

	void tilepushDown()
	// Second attempt at animation. 
	// Designed to handle the animation of moving one tile to the down.
	{
		int oldColumn = 0;
		int oldRow = 0;
		for (int row = 0; row < 4; row++)
		{
			for (int column = 0; column < 4; column++)
			{
				if (board[current][row][column].getValue() != 0)
				{
					oldColumn = (board[current][row][column].getIdentityX());
					oldRow = (board[current][row][column].getIdentityY());
					final Timeline timeline = new Timeline();
					timeline.setCycleCount(1);
					timeline.setAutoReverse(false);
					final KeyValue kv = new KeyValue(
							tiles[oldRow][oldColumn].layoutYProperty(),
							location[row]);
					final KeyFrame kf = new KeyFrame(Duration.millis((Math.abs(oldRow - row)) * 30), kv);
					timeline.getKeyFrames().add(kf);

					timeline.play();
					timeline.setOnFinished(e -> {update();});
				}

			}
		}

	}

	public ArrayList<int[]> listOfZero() // Keeps track of how many
	// zeroes are in the board
	{
		this.zeroes = new ArrayList<int[]>();
		for (int row = 0; row <= 3; row++)// Get row and column value
		{
			for (int column = 0; column <= 3; column++)
			{
				if (board[current][row][column].getValue() == 0)
				{
					zeroes.add(new int[] { row, column }); 
					// adds a location of a zero to the list.
				}
			}
		}
		return zeroes;
	}

	void update()
	{
		this.getChildren().remove(a);
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{

				this.getChildren().remove(tiles[i][j]);
				// This removes all the tiles from the board to replace all
				// tiles with correct
				// location identifiers.

			}
		}
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (board[current][i][j].getValue() != 0)
				{
					this.getChildren().add(tiles[i][j]);
					tiles[i][j].Place(i, j, board[current][i][j].getValue());
					board[current][i][j].setIdentity(i, j);
					// Places tiles back on the board according to location in
					// backup board.
				}
			}
		}
	}

	void printBoard() // Used to make sure tiles matched board. 
	{
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				System.out.printf("%d, ", board[current][i][j].getValue());
			}
			System.out.printf("\n");
		}
		System.out.print("_____________\n");
	}

	void updateMove()
	{
		// If the current board is less then ten then just move up to the next board
		// and transfers all values to new board so the new moves can
		// be stored on the latest move. If the current move is ten or above then 
		// get rid of the move made ten turns ago and transfer all values back one. 
		// this will make it so they can always have ten undos if they have atleast
		// made ten moves. 
		if(zeroes.size() != 0)
		{
			if (current != 9)
			{
				for (int i = 0; i < 4; i++)
				{
					for (int j = 0; j < 4; j++)
					{
						if (board[current][i][j].getValue() != 0)
							board[current + 1][i][j].setValue(board[current][i][j]
									.getValue());
					}
				}
				current += 1;
			}
			if (current == 9)
			{
				for (int i = 1; i < 10; i++)
				{
					for (int j = 0; j < 4; j++)
					{
						for (int k = 0; k < 4; k++)
						{
							board[i - 1][j][k].setValue(board[i][j][k].getValue());
						}
					}
				}
			}
		}
	}

	void undo()
	{
		// Loops through the current board and sets all values to zero so that if an undo
		// is done and then a move is made old values do not spawn on the board. 
		// It then decrements the current board and calls the update method so that all the 
		// tiles are set to the location they were on the last move. 
		if (current > 0)
		{
			for(int i = 0; i < 4; i++)
			{
				for(int j = 0; j < 4; j++)
				{
					board[current][i][j].setValue(0);
				}
			}
			this.current -= 1;
			update();
			System.out.print(current);
		}
	}

	void ScaleAnimation(Tile a)
	{
		// this handles the animation only when a new tile is spawned. 
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);
		final KeyValue kv = new KeyValue(a.scaleUp(), 1.1);
		final KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
		timeline.getKeyFrames().add(kf);

		timeline.play();
		timeline.setOnFinished(e ->
		{
			final Timeline timeline2 = new Timeline();
			timeline.setCycleCount(1);
			timeline.setAutoReverse(false);
			final KeyValue kv2 = new KeyValue(a.scaleDown(), 1);
			final KeyFrame kf2 = new KeyFrame(Duration.millis(20), kv2);
			timeline2.getKeyFrames().add(kf2);

			timeline2.play();
		});
	}
	// The next four get and set methods are used for passing references to other classes
	// for any objects needed by another class. 
	int getCurrentBoard()
	{
		return current;
	}
	
	void setCurrentBoard(int board)
	{
		current = board;
	}
	
	int getCellValue(int a, int b, int c)
	{
		return board[a][b][c].getValue();
	}
	
	void setCellValue(int a, int b, int c, int value)
	{
		board[a][b][c].setValue(value);
	}
}
