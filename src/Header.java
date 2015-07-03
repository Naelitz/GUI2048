import javafx.scene.layout.GridPane;


public class Header extends GridPane
{
	// The header handles the objects located at the top of the main window. 
	// Will display the title and buttons. 
	Title TitlePane;
	Main main;
	Buttons InfoPane;
	
	Header(Main main)
	{
		this.main = main;
		this.InfoPane = new Buttons(main, this);
		this.TitlePane = new Title(main, this);
		this.setMinHeight(800-618);
		this.add(TitlePane, 0, 0);
		this.add(InfoPane, 1, 0);
	}
	void addMove()
	{
		// passes the add move method. 
		this.InfoPane.addMove();
	}
	
	void undo()
	{
		// Passes the undo method. 
		this.InfoPane.undo();
	}
	Buttons getButtons()
	{
		// Passes the buttons class so that main can access it. 
		return InfoPane;
	}
}
