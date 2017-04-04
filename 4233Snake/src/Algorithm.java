
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pichau
 */
public interface Algorithm {
    
    public boolean SimulateMoves(ArrayList<Integer> directions);
    public ArrayList<Integer> getLongestPathToTail();
    public ArrayList<Integer> getShortestPath();
    public int getHeuristic(SNode s);
    public ArrayList<SNode> generateNeighbors(SNode cur);
    public ArrayList<SNode> validate(ArrayList<SNode> a);
    public void run() throws InterruptedException;
}
