package guojian.smart_snake;

public interface AI {
	/**
	 * Simple AI
	 * 
	 * @return coordinate for next step
	 */
	int run(Maze m);
	
	
	/**
	 * clear all the data, when turn off the AI
	 */
	void clear();
}
