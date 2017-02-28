package snake;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 
 * @author us
 *
 */
public class AstarAI {
	ThreadsController threadsController;
	
	public AstarAI(ThreadsController threadsController){
		this.threadsController = threadsController;
	}
	
	/**
	 * Finds shortest path to the apple
	 */
	public void findShortestPathToApple(){
		//TODO: finish this method. Not sure what return type should be
	}
	
	/**
	 * Finds longest path to the tail
	 */
	public void findLongestPathToTail(){
		//TODO: finish this method. Not sure what return type should be
	}
	
    /**
     * The main A Star Algorithm in Java.
     *
     * finds an allowed path from start to goal coordinates on this map.
     * <p>
     * This method uses the A Star algorithm. The hCosts value is calculated in
     * the given Node implementation.
     * <p>
     * This method will return a LinkedList containing the start node at the
     * beginning followed by the calculated shortest allowed path ending
     * with the end node.
     * <p>
     * If no allowed path exists, an empty list will be returned.
     * <p>
     * <p>
     * x/y must be bigger or equal to 0 and smaller or equal to width/hight.
     *
     * @param oldX x where the path starts
     * @param oldY y where the path starts
     * @param newX x where the path ends
     * @param newY y where the path ends
     * @return the path as calculated by the A Star algorithm
     */
    public final LinkedList<Integer> findPath(int oldX, int oldY, int newX, int newY) {
        LinkedList<Tuple> openList = new LinkedList<Tuple>();
        LinkedList <Tuple>closedList = new LinkedList<Tuple>();
        Tuple startPosition = new Tuple(oldX,oldY);
        openList.add(startPosition); // add starting node to open list

        boolean done = false;
        Tuple current;
        while (!done) {
            current = lowestFInOpen(); // get node with lowest fCosts from openList
            closedList.add(current); // add current node to closed list
            openList.remove(current); // delete current node from open list

            if ((current.getX() == newX)
                    && (current.getY() == newY)) { // found goal
                return calcPath(startPosition, current);
            }

            
            // for all adjacent nodes:
            List<Tuple> adjacentNodes = getAdjacent(current);
            for (int i = 0; i < adjacentNodes.size(); i++) {
                Tuple currentAdj = adjacentNodes.get(i);
                if (!openList.contains(currentAdj)) { // node is not in openList
                    currentAdj.setPrevious(current); // set current node as previous for this node
                    Tuple goal = new Tuple(newX,newY);
                    currentAdj.sethCosts(goal); // set h costs of this node (estimated costs to goal)
                    currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
                    openList.add(currentAdj); // add node to openList
                } else { // node is in openList
                    if (currentAdj.getgCosts() > currentAdj.calculategCosts(current)) { // costs from current node are cheaper than previous costs
                        currentAdj.setPrevious(current); // set current node as previous for this node
                        currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
                    }
                }
            }

            if (openList.isEmpty()) { // no path exists
                return new LinkedList<Integer>(); // return empty list
            }
        }
        return null; // unreachable
    }
}