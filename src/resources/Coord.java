package resources;
/**
 * @author Jon Friesen
 * 
 * Replica of 'point' with ints
 * Will hold two ints for x and y
 */
public class Coord {
	int x, y;
	public Coord(){	}
	public Coord(Coord c){
		x = c.getX();
		y = c.getY();
	}
	public Coord(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setLoc(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void setLoc(Coord mouse) {
		x = mouse.getX();
		y = mouse.getY();
	}
	public boolean equals(Coord point){
		if(x == point.getX() && y == point.getY()){
			return true;
		}
		else{
			return false;
		}
	}
	public String toString(){
		return "Coord: ("+x+","+y+")";
	}
	
}
