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
	int xpos;
	int ypos;
	
	public AstarAI(int xpos, int ypos){
		this.xpos = xpos;
		this.ypos = ypos;
	}
	
	public void astarSearch(NewWorld world, int heuristic) {
		int nodesExpanded = 0;// number of expanded nodes

		PriorityQueue<Node> pq = new PriorityQueue<Node>(); // Priority queue of
															// unexpanded nodes
															// ordered from
															// least total cost
															// to greatest total
															// cost so far
		LinkedList<Node> closed = new LinkedList<Node>(); // list of all nodes
															// that have been
															// already expanded
		Node cur; // current node we are examining

		/* Create the starting node */
		Terrain startTerrain = world.getAllTerrains().get(world.startRow).get(world.startCol);
		Node start = new Node(startTerrain);
		start.parent = null;
		start.costToGetHere = 1;
		start.heuristicCost = 0;
		start.totalCost = start.costToGetHere + start.heuristicCost;
		start.actions.add("");
		start.root.setDirection(NORTH);
		pq.add(start);

		/* Search through paths until goal is reached */
		cur = start;
		while (!cur.root.getGoal()) {
			/* get the top node in the priority queue */
			cur = pq.poll();

			/*
			 * If cur has not been expanded yet, expand it and create the
			 * children node
			 */

			/*
			 * List of terrains that we can potentially move to from the current
			 * terrain we are on
			 */
			LinkedList<Terrain> potentialTerrains = new LinkedList<Terrain>();
			/*
			 * HashMap where the key is a terrain t and the value is the set of
			 * actions need to go from the current terrain to t
			 */
			HashMap<Terrain, LinkedList<String>> actionsToGetTo = new HashMap<Terrain, LinkedList<String>>();
			/*
			 * HashMap where the key is a terrain t and the value is the cost to
			 * get there from the current terrain we are
			 */
			HashMap<Terrain, Integer> costToGetTo = new HashMap<Terrain, Integer>();

			/* CHECK WHAT POTENTIAL MOVES YOU CAN MAKE */
			// remember direction we are facing
			String facing = cur.root.getDirection();
			cur.root.setDirection(facing);
			// leap
			Terrain t1 = doLeap(world, cur.root);
			if (t1 != null) {
				/* list actions we must take to get here */
				LinkedList<String> a = new LinkedList<String>();
				a.add("leap");
				actionsToGetTo.put(t1, a);
				/* determine cost to go from cur node to this node */
				int cost = 20;
				costToGetTo.put(t1, cost);
				t1.setDirection(cur.root.getDirection());
				/* add t1 to potential nodes we can land on */
				potentialTerrains.add(t1);
			}
			// forward
			cur.root.setDirection(facing);
			Terrain t2 = doForwardMove(world, cur.root);
			if (t2 != null) {
				/* list actions we must take to get here */
				LinkedList<String> a = new LinkedList<String>();
				a.add("move forward");
				actionsToGetTo.put(t2, a);
				/* determine cost to go from cur node to this node */
				int cost = t2.getComplexity();
				costToGetTo.put(t2, cost);
				t2.setDirection(cur.root.getDirection());
				/* add t1 to potential nodes we can land on */
				potentialTerrains.add(t2);
			}
			// turn right, leap
			cur.root.setDirection(facing);// make sure we are facing right direction
			doTurn(cur.root, "right");
			Terrain t3 = doLeap(world, cur.root);
			if (t3 != null) {
				/* list actions we must take to get here */
				LinkedList<String> a = new LinkedList<String>();
				a.add("turn right");
				a.add("leap");
				actionsToGetTo.put(t3, a);
				/* determine cost to go from cur node to this node */
				int cost = (int) (Math.ceil((double) cur.root.getComplexity() / 3.0)) + 20;
				costToGetTo.put(t3, cost);
				t3.setDirection(cur.root.getDirection());
				/* add t1 to potential nodes we can land on */
				potentialTerrains.add(t3);
			}
			// turn right, forward
			cur.root.setDirection(facing);// make sure we are facing right direction
			doTurn(cur.root, "right");
			Terrain t4 = doForwardMove(world, cur.root);
			if (t4 != null) {
				/* list actions we must take to get here */
				LinkedList<String> a = new LinkedList<String>();
				a.add("turn right");
				a.add("move forward");
				actionsToGetTo.put(t4, a);
				/* determine cost to go from cur node to this node */
				int cost = (int) (Math.ceil((double) cur.root.getComplexity() / 3.0) + t4.getComplexity());
				costToGetTo.put(t4, cost);
				t4.setDirection(cur.root.getDirection());
				/* add t1 to potential nodes we can land on */
				potentialTerrains.add(t4);
			}
			// turn left, leap
			cur.root.setDirection(facing);// make sure we are facing right direction
			doTurn(cur.root, "left");
			Terrain t5 = doLeap(world, cur.root);
			if (t5 != null) {
				/* list actions we must take to get here */
				LinkedList<String> a = new LinkedList<String>();
				a.add("turn left");
				a.add("leap");
				actionsToGetTo.put(t5, a);
				/* determine cost to go from cur node to this node */
				int cost = (int) (Math.ceil((double) cur.root.getComplexity() / 3.0)) + 20;
				costToGetTo.put(t5, cost);
				t5.setDirection(cur.root.getDirection());
				/* add t1 to potential nodes we can land on */
				potentialTerrains.add(t5);
			}
			// turn left, forward
			cur.root.setDirection(facing);// make sure we are facing right direction
			doTurn(cur.root, "left");
			Terrain t6 = doForwardMove(world, cur.root);
			if (t6 != null) {
				/* list actions we must take to get here */
				LinkedList<String> a = new LinkedList<String>();
				a.add("turn left");
				a.add("move forward");
				actionsToGetTo.put(t6, a);
				/* determine cost to go from cur node to this node */
				int cost = (int) ((double) Math.ceil(cur.root.getComplexity() / 3.0)) + t6.getComplexity();
				costToGetTo.put(t6, cost);
				t6.setDirection(cur.root.getDirection());
				/* add t1 to potential nodes we can land on */
				potentialTerrains.add(t6);
			}
			// turn right, turn right(basically doing a 180,) leap
			cur.root.setDirection(facing);// make sure we are facing right direction
			doTurn(cur.root, "right");
			doTurn(cur.root, "right");
			Terrain t7 = doLeap(world, cur.root);
			if (t7 != null) {
				/* list actions we must take to get here */
				LinkedList<String> a = new LinkedList<String>();
				a.add("turn right");
				a.add("turn right");
				a.add("leap");
				actionsToGetTo.put(t7, a);
				/* determine cost to go from cur node to this node */
				int cost = 2 * ((int) ((double) Math.ceil(cur.root.getComplexity() / 3.0))) + 20;
				costToGetTo.put(t7, cost);
				t7.setDirection(cur.root.getDirection());
				/* add t1 to potential nodes we can land on */
				potentialTerrains.add(t7);
			}
			// turn right, turn right, forward
			cur.root.setDirection(facing);// make sure we are facing right direction
			doTurn(cur.root, "right");
			doTurn(cur.root, "right");
			Terrain t8 = doForwardMove(world, cur.root);
			if (t8 != null) {
				/* list actions we must take to get here */
				LinkedList<String> a = new LinkedList<String>();
				a.add("turn right");
				a.add("turn right");
				a.add("move forward");
				actionsToGetTo.put(t8, a);
				/* determine cost to go from cur node to this node */
				int cost = 2 * ((int) ((double) Math.ceil(cur.root.getComplexity() / 3.0))) + t8.getComplexity();
				costToGetTo.put(t8, cost);
				t8.setDirection(cur.root.getDirection());
				/* add t1 to potential nodes we can land on */
				potentialTerrains.add(t8);
			}

			/*
			 * Go through create children nodes using each Terrain in
			 * potentialTerrains
			 */
			for (int i = 0; i < potentialTerrains.size(); i++) {
				/* n is a child node of cur node */
				Node n = new Node(potentialTerrains.get(i));

				/* Set values for n */
				n.parent = cur;
				n.costToGetHere = n.parent.costToGetHere + costToGetTo.get(n.root);
				// heuristic 1 by default
				n.heuristicCost = calculateHeuristic(world, n.root, heuristic);
				n.totalCost = n.costToGetHere + n.heuristicCost;
				n.actions = actionsToGetTo.get(n.root);

				/* add n to our priority queue */
				if (pq.contains(n)) {
					if (pq.peek().totalCost > n.totalCost){
						pq.add(n);
					}
				} else if (!closed.contains(n))
					pq.add(n);

				if (!cur.children.contains(n))
					cur.children.add(n);

				/* cur node has been fully expanded, add it to closed */
				closed.add(cur);
				nodesExpanded++;
			}
		}
}
