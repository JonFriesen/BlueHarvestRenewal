package resources;
/**
 * @author Jon Friesen
 * 
 * Stores map objects such as spawn points, doorways etc.
 */
import java.awt.Rectangle;

public class MapObject {
	private Coord loc, size;
	private Rectangle rect;
	int type;
	public MapObject(){}
	public MapObject(int x, int y, int xsize, int ysize, int type){
		loc = new Coord(x,y);
		size = new Coord(xsize,ysize);
		this.type = type;
	}
	public MapObject(Coord loc, Coord size, int type){
		this.loc = loc;
		this.size = size;
		this.type = type;
	}
	public void setloc(int x, int y){
		loc.setLoc(x, y);
	}
	public void setSize(int x, int y){
		size.setLoc(x, y);
	}
	public void setType(int type){
		this.type = type;
	}
	public Coord getLoc(){
		return loc;
	}
	public Coord getSize(){
		return size;
	}
	public int getType(){
		return type;
	}
	public String toString(){
		return "MapObject: Loc: ("+loc.getX()+","+loc.getY()+") Size: ("+size.getX()+","+size.getY()+") Type: "+type;
	}
	public Rectangle getRect(){
		return new Rectangle(loc.getX(), loc.getY(), size.getX(), size.getY());
	}
}
