package guojian.smart_snake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Searcher extends CommFunc {

	public static void main(String[] args) throws InterruptedException {
		long time = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			Maze m = new Maze();
			m.initPalace();
			m.initSnake();
			m.pressSnakeAndPalace();
			m.RandonApple();
			m.pressApple();
			m.log();
			List<Integer> paths = findShortPath(m.palace, m.getHead(), m.getTail());
			System.out.println("paths:" + paths.toString());
			Maze newm = m.clone();
			newm.pressPaths(paths);
			newm.log();
		}
		System.out.println((double) (System.currentTimeMillis() - time) / 1);
	}

	/**
	 * Search for the shortest road
	 * 
	 * @param maze
	 * @param start
	 * @param end
	 * @return 开头到结尾的路径，包含头和尾。如果没有路径，返回只包含尾 1个长度的list
	 * If no route, return a list that is only length 1
	 */
	public static List<Integer> findShortPath(byte[][] maze, int start, int end) {
		List<Integer> list = new ArrayList<>();
		Map<Integer, Integer> map = new HashMap<>();
		Queue<Integer> q = new LinkedList<>();
		Set<Integer> s = new HashSet<>();
		map.put(start, -1);
		list.add(end);
		s.add(start);
		q.add(start);
		while (true) {
			if (q.isEmpty()) {
				return list;
			}
			start = q.poll();
			if (isCloser(start, end)) {
				while (true) {
					list.add(start);
					if (map.get(start) != -1) {
						start = map.get(start);
					} else {
						Collections.reverse(list);
						return list;
					}
				}
			} else {
				q.addAll(getFitIndexs(s, start, maze, map));
			}
		}
	}

	private static Collection<? extends Integer> getFitIndexs(Set<Integer> s, int start, byte[][] maze,
			Map<Integer, Integer> map) {
		List<Integer> list = Arrays.asList(start + 1, start - 1, start - Config.MAZE_COLS, start + Config.MAZE_COLS);
		List<Integer> fitList = list.stream().filter(idx -> {
			int[] coord = indexToCoord(idx);
			byte type = maze[coord[0]][coord[1]];
			boolean fit = type != Define.WALL && type != Define.SNAKE && !s.contains(idx);
			if (fit) {
				map.put(idx, start);
				s.add(idx);
				return true;
			} else {
				return false;
			}
		}).collect(Collectors.toList());

		return fitList;
	}

	/**
	 * If two point is close to each other
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private static boolean isCloser(int start, int end) {
		boolean v = (start % Config.MAZE_COLS == end % Config.MAZE_COLS)
				&& Math.abs(start / Config.MAZE_COLS - end / Config.MAZE_COLS) == 1;
		boolean h = (Math.abs(start - end) == 1) && (start / Config.MAZE_COLS == end / Config.MAZE_COLS);
		return v || h;
	}

	/**
	 * Find the longest path
	 * 
	 * @param palace
	 * @param head
	 * @param tail
	 * @return
	 */
	public static List<Integer> findLongPath(byte[][] palace, int head, int tail) {
		List<Integer> list = getAroundIndexs(head, palace);
		if (list.size() == 0) {
			return Arrays.asList(head, 0);
		}
		List<List<Integer>> paths = new ArrayList<>();
		for (int i : list) {
			paths.add(findShortPath(palace, i, tail));
		}
		List<Integer> longone = paths.stream().max(Comparator.comparingInt(e -> e.size())).get();
		longone.add(0, head);
		return longone;
	}

	private static List<Integer> getAroundIndexs(int start, byte[][] maze) {
		List<Integer> list = Arrays.asList(start + 1, start - 1, start - Config.MAZE_COLS, start + Config.MAZE_COLS);
		List<Integer> fitList = list.stream().filter(idx -> {
			int[] coord = indexToCoord(idx);
			byte type = maze[coord[0]][coord[1]];
			return type != Define.WALL && type != Define.SNAKE;
		}).collect(Collectors.toList());
		return fitList;
	}

	public static List<Integer> findLongPathAndshuffle(byte[][] palace, int head, int tail) {
		List<Integer> list = getAroundIndexs(head, palace);
		if (list.size() == 0) {
			return Arrays.asList(head, 0);
		}
		List<List<Integer>> paths = new ArrayList<>();
		for (int i : list) {
			paths.add(findShortPathAndShuffle(palace, i, tail));
		}
		List<Integer> longone = paths.stream().max(Comparator.comparingInt(e -> e.size())).get();
		longone.add(0, head);
		return longone;
	}

	private static List<Integer> findShortPathAndShuffle(byte[][] maze, int start, int end) {
		List<Integer> list = new ArrayList<>();
		Map<Integer, Integer> map = new HashMap<>();
		Queue<Integer> q = new LinkedList<>();
		Set<Integer> s = new HashSet<>();
		map.put(start, -1);
		list.add(end);
		s.add(start);
		q.add(start);
		while (true) {
			if (q.isEmpty()) {
				return list;
			}
			start = q.poll();
			if (isCloser(start, end)) {
				while (true) {
					list.add(start);
					if (map.get(start) != -1) {
						start = map.get(start);
					} else {
						Collections.reverse(list);
						return list;
					}
				}
			} else {
				q.addAll(getFitIndexsAndShuffle(s, start, maze, map));
			}
		}
	}

	private static Collection<? extends Integer> getFitIndexsAndShuffle(Set<Integer> s, int start, byte[][] maze,
			Map<Integer, Integer> map) {
		List<Integer> list = Arrays.asList(start + 1, start - 1, start - Config.MAZE_COLS, start + Config.MAZE_COLS);
		List<Integer> fitList = list.stream().filter(idx -> {
			int[] cooord = indexToCoord(idx);
			byte type = maze[cooord[0]][cooord[1]];
			boolean fit = type != Define.WALL && type != Define.SNAKE && !s.contains(idx);
			if (fit) {
				map.put(idx, start);
				s.add(idx);
			}
			return fit;
		}).collect(Collectors.toList());
		Collections.shuffle(fitList);
		return fitList;
	}

}
