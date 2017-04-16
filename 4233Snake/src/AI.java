
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is written by CS4341 Group 9
 *
 * @author CS4341 Group 9
 *
 */
public class AI extends Algorithm {

    private Snake snk;
    int x;
    int y;
    int foodx;
    int foody;
    int height;
    int width;
    boolean shortestPathValid;//is true if shortest path from head to food will not cause the game to end before player wins

    public AI(Snake snk) {
        this.snk = snk;
        x = snk.getHead().getX();
        y = snk.getHead().getY();
        foodx = snk.getfoodNode().getX();
        foody = snk.getfoodNode().getY();
        height = snk.height;
        width = snk.width;
    }

    @Override
    public void execute() {
        try {
            ArrayList<Integer> path = this.getShortestPath();
            if (!this.shortestPathValid) {
                path = this.getLongestPathToTail();
                //path.clear();
            }
            for (Integer s : path) {
                snk.setDirection(s);
                snk.Move();

                TimeUnit.MILLISECONDS.sleep(20);

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(AI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Simulates current move and returns true if move can be made without
     * causing the game to end before player wins.
     */
    public boolean SimulateMoves(ArrayList<Integer> directions) {
        ArrayList<SNode> snakeClone = new ArrayList<SNode>();
        for (SNode s : snk.getSnake()) {
            snakeClone.add(new SNode(s.getX(), s.getY()));
        }
        for (int dir : directions) {
            int nextX = snakeClone.get(0).getX();
            int nextY = snakeClone.get(0).getY();
            switch (dir) {
                case Define.UP:
                    nextY--;
                    break;
                case Define.DOWN:
                    nextY++;
                    break;
                case Define.LEFT:
                    nextX--;
                    break;
                case Define.RIGHT:
                    nextX++;
                    break;
                default:
                    break;
            }

            if (nextX > width - 1 || nextX < 0 || nextY > height - 1 || nextY < 0) {
                return false;
            }

            for (int i = 0; i < snk.getLength(); i++) // Bite it self
            {
                if ((snakeClone.get(i).getX() == nextX) && (snakeClone.get(i).getY() == nextY)) {
                    return false;
                }
            }

            for (int j = snk.getLength() - 1; j > 0; j--) {
                snakeClone.get(j).setX(snakeClone.get(j - 1).getX());
                snakeClone.get(j).setY(snakeClone.get(j - 1).getY());
            }
            snakeClone.get(0).setX(nextX);
            snakeClone.get(0).setY(nextY);
        }

        return true;
    }

    /**
     * Gets a path from the head to the tail
     *
     * @return
     */
    //TODO: this can be significantly improved
    public ArrayList<Integer> getLongestPathToTail() {
        ArrayList<Integer> directions = new ArrayList<Integer>();
        SNode head = snk.getHead();
        SNode tail = snk.getSnake().get(snk.getLength() - 1);

        ArrayList<SNode> neighbor = this.generateNeighbors(head);
        //check if any nodes in neighbor will cause snake to bite itself
        for (int i = neighbor.size() - 1; i >= 0; i--) {
            if (snk.getSnake().contains(neighbor.get(i))) {
                neighbor.remove(i);
            }
        }
        /*move towards node that will bring us closest to the tail
		 *we find heuristic cost of each neighbor node
		 *the heuristic cost in this situation is the manhattan
		 *cost between the neighbor node and the tail node
		 *we take the neighbor node with the lowest value*/
        int hcost = -1;
        int dir = snk.getDirection();
        for (SNode s : neighbor) {
            int hh = Math.abs(s.getX() - tail.getX()) + Math.abs(s.getY() - tail.getY());
            if (hh > hcost) {
                hcost = hh;
                dir = s.getDir();
            }
        }

        directions.add(dir);
        return directions;
    }

    /**
     * Returns a list of nodes that form the shortest path between the head of
     * the snake and the food.
     *
     * @return
     */
    public ArrayList<Integer> getShortestPath() {
        //ArrayList<SNode> path = new ArrayList<SNode>();
        ArrayList<Integer> directions = new ArrayList<Integer>();

        PriorityQueue<Node> openList = new PriorityQueue<Node>();
        ArrayList<Node> closedList = new ArrayList<Node>();

        /*starting node*/
        snk.getHead().setDir(snk.getDirection());
        Node start = new Node(snk.getHead());
        openList.add(start);

        /*variable to hold the node that we are currently looking at*/
        Node cur = start;

        /*keep searching until list is empty or goal is met*/
        while (!openList.isEmpty()) {
            /*get node from open list with lowest total cost*/
            cur = openList.poll();

            if (cur.cell.equals(snk.getfoodNode())) {
                /*we found goal node*/
                break;
            }

            /*generate neighboring cells that can potentially be moved to*/
            ArrayList<SNode> neighbors = generateNeighbors(cur.cell);

            /*create a node for each neighbor*/
            for (SNode s : neighbors) {
                int gcost;
                if (s.getDir() != cur.cell.getDir()) {
                    /*we are required to turn to get to this cell*/
                    gcost = cur.gcost + 2;
                } else {
                    gcost = cur.gcost + 1;
                }
                int hcost = getHeuristic(s);

                Node newNode = new Node(s, gcost, hcost, cur);

                if (!closedList.contains(newNode)) {
                    openList.add(newNode);
                }
            }

            /*add cur node to closed list*/
            closedList.add(cur);
        }

        //traverse cur node and add nodes to array list for path
        while (cur.parent != null) {
            //path.add(0, cur.cell);
            directions.add(0, cur.cell.getDir());
            cur = cur.parent;
        }

        this.shortestPathValid = this.SimulateMoves(directions);
        return directions;
    }

    /**
     * returns the heuristic value for a given snode
     *
     * @param s
     * @return
     */
    public int getHeuristic(SNode s) {
        int h;

        //lets use Manhattan for now
        h = Math.abs(s.getX() - snk.getfoodNode().getX()) + Math.abs(s.getY() - snk.getfoodNode().getY());

        return h;
    }

    /**
     * generates neighboring cells to head that snake can potentially move to.
     *
     * @param cur
     * @return
     */
    public ArrayList<SNode> generateNeighbors(SNode cur) {
        ArrayList<SNode> neighbor = new ArrayList<SNode>();
        /*get current direction we are facing*/
        int dir = cur.getDir();

        /*get the surrounding cells that we can potentiall move to*/
        if (dir == Define.UP || dir == Define.DOWN) {
            if (dir == Define.UP) {
                /*keep going up*/
                SNode up = new SNode(cur.getX(), cur.getY() - 1);
                up.setDir(Define.UP);
                neighbor.add(up);
            } else {
                /*keep going down*/
                SNode down = new SNode(cur.getX(), cur.getY() + 1);
                down.setDir(Define.DOWN);
                neighbor.add(down);
            }

            /*we can also go left or right, so add those two*/
            SNode left = new SNode(cur.getX() - 1, cur.getY());
            left.setDir(Define.LEFT);
            SNode right = new SNode(cur.getX() + 1, cur.getY());
            right.setDir(Define.RIGHT);
            neighbor.add(left);
            neighbor.add(right);

        }
        if (dir == Define.LEFT || dir == Define.RIGHT) {
            if (dir == Define.LEFT) {
                /*keep going left*/
                SNode left = new SNode(cur.getX() - 1, cur.getY());
                left.setDir(Define.LEFT);
                neighbor.add(left);
            } else {
                /*keep going right*/
                SNode right = new SNode(cur.getX() + 1, cur.getY());
                right.setDir(Define.RIGHT);
                neighbor.add(right);
            }

            /*we can also go up or down, so add those two*/
            SNode up = new SNode(cur.getX(), cur.getY() - 1);
            up.setDir(Define.UP);
            SNode down = new SNode(cur.getX(), cur.getY() + 1);
            down.setDir(Define.DOWN);
            neighbor.add(up);
            neighbor.add(down);
        }

        /*check if all the generated cells are valid(i.e. its not a border or out of bonds)*/
        neighbor = validate(neighbor);

        return neighbor;
    }

    public ArrayList<SNode> validate(ArrayList<SNode> a) {
        for (int i = a.size() - 1; i >= 0; i--) {
            if (x >= width || x < 0 || y >= height || y < 0) {
                /*this node goes out of bonds, remove it*/
                a.remove(i);
            }
        }

        return a;
    }
}
