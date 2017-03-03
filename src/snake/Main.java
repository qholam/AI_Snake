package snake;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		//Creating the window with all its awesome snaky features
		Window f1= new Window();
		// initial position of the snake
		Tuple position = new Tuple(10,10);
		// passing this value to the controller
		ThreadsController c = new ThreadsController(position);
		//Let's start the game now..
		c.start();
		//Setting up the window settings
		f1.setTitle("Snake");
		f1.setSize(1500,1500);
		f1.setVisible(true);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   

		//AI
		while(ThreadsController.gameStatus)
		{
			AI ai = new AI(c);
		}
	}
}
