
/**
 * This class is the open source that we found from the website.
 * We made some modification to match our AI class.
 * All the modification has been marked as "Added"
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;

import javax.swing.*;
/*
 * Time : 2011.11.24
 * Snake Game By XuboWen
 * AI added By CS4341 Group 9
 * */

public class Snake extends JPanel {
	private static final long serialVersionUID = -7844810652256449836L;
	final int height = 20;
	final int width = 30;
	final int unit = 45;
	private ArrayList<SNode> Snake = new ArrayList<SNode>(); /* Stores the snake body, 0 index is the head*/
	private int Direction = Define.UP; // Store direction
	private SNode foodNode = new SNode(1, 1, Color.BLACK); /* Node which holds info on the food */
	private int Length = 0; /*Store the length of the snake*/
	//Timer timer = new Timer(0, new TimerListener());
	ImageIcon imgico = createImageIcon("images.jpg");
	Image img = imgico.getImage(); /*ImageIcon for the snake's head*/
	// Added
	static boolean isGameOn = true;
	
	/**
	 * returns the head of the snake
	 * @return
	 */
	public SNode getHead(){
		return Snake.get(0);
	}

	/**
	 * returns the snake
	 */
	public ArrayList<SNode> getSnake(){
		return Snake;
	}
	
	/**
	 * Get the direction of the snake
	 * 
	 * @return int representing direction
	 */
	public int getDirection() {
		return Direction;
	}

	/**
	 * Set the direction of the snake. 1:up -1:down 2:left -2:right
	 * 
	 * @param direction
	 */
	public void setDirection(int direction) {
		Direction = direction;
	}

	/**
	 * Return the current food node on the grid.
	 * 
	 * @return
	 */
	public SNode getfoodNode() {
		return foodNode;
	}

	/**
	 * Set the food node to whats given.
	 * @param foodNode
	 */
	public void setfoodNode(SNode foodNode) {
		this.foodNode = foodNode;
	}

	/**
	 * Get the length of the snake.
	 * @return int indicating length
	 */
	public int getLength() {
		return Length;
	}

	/**
	 * Set the length of the snake.
	 * @param length
	 */
	public void setLength(int length) {
		Length = length;
	}

	// Added
	/**
	 * Initializes the snake.
	 */
	private void initSnake() {
		this.Snake.add(new SNode((int) height / 2, (int) width / 2, Color.black));
		this.Snake.add(new SNode((int) height / 2 + 1, (int) width / 2, Color.red));
		this.Snake.add(new SNode((int) height / 2 + 2, (int) width / 2, Color.blue));
	}

	/**
	 * Default constructor for Snake game. 
	 * Creates a snake.
	 * Spawns a food node.
	 */
	public Snake() {
		initSnake();
		this.Direction = Define.LEFT;
		CreateFoodNode();
		this.Length = 3;
		//timer.start();
	}

	/**
	 * Moves the snake one unit in the current direction it is facing.
	 */
	public void Move() {
		//timer.stop();
		int nextX = Snake.get(0).getX();
		int nextY = Snake.get(0).getY();
		switch (this.Direction) {
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

		if ((nextX == foodNode.getX()) && (nextY == foodNode.getY())){
			/*snake has found the food*/
			Eat();
			//timer.start();
			return;
		}

		if (nextX > width - 1 || nextX < 0 || nextY > height - 1 || nextY < 0) {
			System.out.println("ending dir: "+ this.Direction);
			GameOver("HitTheWall~~~!"); //Hit the wall
			isGameOn = false;
			return;
		}

		for (int i = 0; i < Length; i++) // Bite it self
		{
			if ((Snake.get(i).getX() == nextX) && (Snake.get(i).getY() == nextY)) {
				GameOver("BitItSelf~~~!");
				isGameOn = false;
				return;
			}
		}

		for (int j = Length - 1; j > 0; j--) {
			Snake.get(j).setX(Snake.get(j - 1).getX());
			Snake.get(j).setY(Snake.get(j - 1).getY());
		}
		Snake.get(0).setX(nextX);
		Snake.get(0).setY(nextY);
		repaint();
		//timer.start();
	}

	/**
	 * Assumes head of snake has reached food. This function simulates the snake
	 * eating the food and growing one unit in size.
	 */
	public void Eat() {
		Snake.add(new SNode());
		Length++;
		for (int j = Length - 1; j > 0; j--) {
			Snake.get(j).setX(Snake.get(j - 1).getX());
			Snake.get(j).setY(Snake.get(j - 1).getY());
			Snake.get(j).setColor(Snake.get(j - 1).getColor()); // ----daice
		}
		Snake.get(0).setX(foodNode.getX());
		Snake.get(0).setY(foodNode.getY());
		Snake.get(0).setColor(foodNode.getColor());
		CreateFoodNode();
		repaint();
	}

	/**
	 * Creates a random food node and places it on the grid.
	 */
	public void CreateFoodNode() {
		boolean flag = true;
		int newX = 0;
		int newY = 0;
		while (flag) {
			newX = (int) (Math.random() * (width - 1));
			newY = (int) (Math.random() * (height - 1));

			for (int i = 0; i < Length; i++) {
				if ((Snake.get(i).getX() == newX) && (Snake.get(i).getY() == newY))
					break;
			}
			flag = false;
		}
		// choose a random color for the new food
		Color color = new Color(50 + (int) (Math.random() * 205), 50 + (int) (Math.random() * 205),
				50 + (int) (Math.random() * 205));
		foodNode.setX(newX);
		foodNode.setY(newY);
		foodNode.setColor(color);
		Snake.get(0).setColor(foodNode.getColor());
	}

	/**
	 * Assuming end game conditions are met, this function will terminate the
	 * program and print a message.
	 * 
	 * @param str
	 */
	public void GameOver(String str) {
		isGameOn = false;
		Calendar cal = new GregorianCalendar();
		str = str + "\n EndGame..." + "\nCurrTime: " + cal.getTime().toString();
		JOptionPane.showMessageDialog(null, str, "GameOver", 1);
		System.exit(0);
	}

	@Override
	/**
	 * paints the panel.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(foodNode.getColor()); // The Random Food Node
		g.fillOval(foodNode.getX() * unit, foodNode.getY() * unit, unit, unit);
		g.setColor(foodNode.getColor());
		g.drawRect(0, 0, width * unit, height * unit);
		for (int i = 0; i < Length; i++) {
			g.setColor(Snake.get(i).getColor());
			g.fillOval(Snake.get(i).getX() * unit, Snake.get(i).getY() * unit, unit, unit);
		}
		g.drawImage(img, Snake.get(0).getX() * unit - 3, Snake.get(0).getY() * unit - 3, unit + 6, unit + 6, this);
	}

	/**
	 * Given a path, returns the image icon
	 * 
	 * @param path
	 * @return ImageIcon
	 */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Snake.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path); // err
			return null;
		}
	}

//	public class TimerListener implements ActionListener {
//		public void actionPerformed(ActionEvent arg0) {
//			//Move();
//			//repaint();
//		}
//	}

	public static void main(String args[]) throws InterruptedException {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Snake-bY mR.xU");
		Snake snk = new Snake();
		snk.setBackground(Color.white);
		frame.add(snk);
		frame.setSize(1400, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		snk.requestFocus(); // JPanel
		// Added
		while (isGameOn) {
			//AI ai = new AI(snk);
			Algorithm ai = new AI(snk);
                        ai.run();
		}
		System.out.println("done?");
	}
}
