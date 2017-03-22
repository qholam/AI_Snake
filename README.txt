Notable bugs:
*Some times when the snake has a big length, there appears to be a delay in the 
spawning of new food. I believe this can be contributed to one of 2 situations:
1. the spawned food is white in color so it looks like there was a delay until
the snake eats the food and a new one that is non-white spawns.
2. the AI may be running too quickly and the original creator of the Snake game
file did not optimize the code to be ran at such speeds.

*Game will sometimes freeze and only way to fix it is by restarting. Not sure why
this happens. 


-----Explanation of current algorithm:-----
The AI will generate the shortest path from the head to the food. This is done
by using A* algorithm with Manhattan heuristic. It will then simulate the move
to see if the move will be good(i.e. it will not cause the game to prematurely end). 
Depending on the simulation there are two things the AI can do:

1)If the move it good, the snake will follow that path
to eat the apple. 
2)If the move is not good, the snake will attempt to move towards
its tail in order to get into a better point in which it can travel along the shortest
between said point and the food.(This is currently a little buggy, the snake will sometimes
move in a way that will result in it eating itself or running into a wall).

After doing either of these 2. The AI will restart the algorithm. 


-----How algorithm was made-----
After doing some research online, it was found the general structure of a good 
Snake AI algorithm would have the snake take the shortest path from its head
to the food if it can. Otherwise, the snake will take the longest path from its 
head to the tail(this is essentially stalling the game into the snake is place
in a more favorable position).


----Running the program----
To run the program, run the main method in the Snake.java class.


----Things written by Team 9-----
*All of the code in the AI.java, Define.Java, and Node.Java classes

*Snake.java and SNode.java was originally written by the original 
author, but we modified the following:
	*Replace user input in Snake.java class with our AI
	*Added a few field in SNode to hold more info in order to
	make A* search easier to write