package resources;

import java.awt.Image;
import java.awt.Rectangle;

import resources.Coord;

public class Sprite {
	protected Coord loc=new Coord(), size=new Coord();
    protected Image image;

    public void setLoc(int x, int y){
    	loc.setLoc(x, y);
    }
    public void setLoc(Coord loc){
    	this.loc.setLoc(loc);
    }
    public void setX(int x) {
        loc.setX(x);
    }

    public int getX() {
        return  loc.getX();
    }

    public void setY(int y) {
        loc.setY(y);
    }

    public int getY() {
        return loc.getY();
    }
    public Image getImage()
    {
      return image;
    }
    public Coord getLoc(){
    	return loc;
    }
    public Rectangle getRect()
    {
      return new Rectangle(loc.getX(), loc.getY(), image.getWidth(null), image.getHeight(null));
    }
}

