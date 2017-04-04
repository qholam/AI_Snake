import java.awt.*;

public class SNode {
	private int x;
	private int y;
	private int dir;
	private Color color;

	
	public int getDir(){
		return dir;
	}
	public void setDir(int d){
		dir = d;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	public SNode()
	{
		x = 0;
		y = 0;
		color = Color.white;
	}
	public SNode(int x,int y,Color clr)
	{
		this.x = x;
		this.y = y;
		this.color = clr;
	}
	public SNode(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Generate the hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * Determines whether two SNode objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
            
            if(obj instanceof SNode){
		SNode other = (SNode) obj;
                if (this.x == other.x && this.y == other.y)
			return true;
            }
		return false;
	}
}