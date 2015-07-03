import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Information 
{
	// This class stores the values used in the display and also makes it easier 
	// to save moves and score to file. 
	IntegerProperty highScore = new SimpleIntegerProperty();
	IntegerProperty score = new SimpleIntegerProperty();
	IntegerProperty moves = new SimpleIntegerProperty();
	boolean newHighScore = false;
	
	Information(Main main)
	{
		score.setValue(0); // Initialize score first so if saved before any moves are made
							// there is still a value to write to file. 
		try(DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("HighScore.dat"))))
		{
		highScore.setValue(in.readInt()); // Loads high score from file as soon as the game
											// is started. 
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		
		score.addListener((o, oldValue, newValue) -> {
			if(oldValue.intValue() > highScore.getValue())
			{
				highScore.setValue(newValue);
				this.newHighScore = true;
				// If new score was set, then set to true so that we know to display the 
				// high score on exit. Then write the new score to file. 
				try(DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("HighScore.dat"))))
				{
					out.writeInt(highScore.getValue());
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			};
		}
		);
	}
	
	public IntegerProperty getHighScore()
	{
		return highScore;
	}



	public void setHighScore(IntegerProperty highScore)
	{
		this.highScore = highScore;
	}



	public IntegerProperty getScore()
	{
		return score;
	}
	
	
	
	public void addScore(int i)
	{
		score.setValue(score.getValue() + i);
	}


	public IntegerProperty getMoves()
	{
		return moves;
	}
	
	public boolean wasHighScoreSet()
	{
		
		return newHighScore;
	}
}
