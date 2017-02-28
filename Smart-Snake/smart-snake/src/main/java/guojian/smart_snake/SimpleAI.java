package guojian.smart_snake;

import java.util.LinkedList;
import java.util.List;

public class SimpleAI implements AI {

	private static List<Integer> memoryList = new LinkedList<>();
	private static List<Integer> memoryLongPath = new LinkedList<>();

	/**
	 * Longest path from head to tail
	 * 
	 * @return
	 */
	private static List<Integer> getMaxPath(Maze maze) {
		List<Integer> realMaxPath = Searcher.findLongPath(maze.palace, maze.getHead(), maze.getTail());
		if (isSame(realMaxPath, memoryLongPath)) {
			realMaxPath = Searcher.findLongPathAndshuffle(maze.palace, maze.getHead(), maze.getTail());
		}
		memoryLongPath.clear();
		memoryLongPath.addAll(realMaxPath);
		return realMaxPath;
	}

	public static boolean isSame(List<Integer> a, List<Integer> b) {
		if (a.size() != b.size()) {
			return false;
		} else {
			for (int i = 0; i < a.size(); i++) {
				if (a.get(i) != b.get(i)) {
					return false;
				}
			}
			return true;
		}
	}

	@Override
	public int run(Maze m) {
		if (!memoryList.isEmpty()) {
			return memoryList.remove(0);
		}

		List<Integer> findTail = Searcher.findShortPath(m.palace, m.getHead(), m.getTail());

		if (findTail.size() > 1) {// Can find tail
			Maze cm = m.clone();
			List<Integer> findApple = Searcher.findShortPath(cm.palace, cm.getHead(), cm.getApple());

			if (findApple.size() > 1) {// Can find apple

				for (int i = 1; i < findApple.size(); i++) {// Continue until reach the apple
					cm.move(findApple.get(i));
				}

				List<Integer> cm_findTail = Searcher.findShortPath(cm.palace, cm.getHead(), cm.getTail());
				if (cm_findTail.size() > 2) {// Still able to find the apple
					memoryList.clear();
					memoryList.addAll(findApple);
					memoryList.remove(0);
					return memoryList.remove(0);
				} else {// Cannot find the tail
					return getMaxPath(m).get(1);
				}

			} else {
				return getMaxPath(m).get(1);
			}
		} else {
			return 0;
		}
	}

	@Override
	public void clear() {
		memoryList.clear();
		memoryLongPath.clear();
	}
}
