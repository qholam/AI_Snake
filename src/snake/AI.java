package snake;

import java.util.Random;

public class AI {

	Random rand = new Random();
	ThreadsController c;
	ThreadsController clone;

	public AI(ThreadsController c)
	{
		this.c = c;
		updateThread();
	}
	public void updateThread()
	{

		int randDir = rand.nextInt(4) + 1;
		if(randDir == 1)
		{
			if(ThreadsController.directionSnake != 2 && !c.hitBorder())
			{
				ThreadsController.directionSnake = randDir;
			}
		}
		if(randDir == 2)
		{
			if(ThreadsController.directionSnake != 1 && !c.hitBorder())
			{
				ThreadsController.directionSnake = randDir;
			}	
		}
		if(randDir == 3)
		{
			if(ThreadsController.directionSnake != 4 && !c.hitBorder())
			{
				ThreadsController.directionSnake = randDir;
			}
		}
		if(randDir == 4)
		{
			if(ThreadsController.directionSnake != 3 && !c.hitBorder())
			{
				ThreadsController.directionSnake = randDir;
			} 
		}
	}
}
