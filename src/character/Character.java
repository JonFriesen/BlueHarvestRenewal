package character;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import resources.Commons;
import resources.Coord;
import resources.ImageCache;
import resources.Log;
import resources.Sprite;

public class Character extends Sprite implements Commons {
	private Coord offset = new Coord(0, 0);//not being used currently
	private boolean moving, walkCount;
	private int charDirection = 0;
	private Coord[] spriteDir = new Coord[13];
	private Coord newLoc = new Coord();

	private String name = "NameMissing";


	public Character() {/*Should never be called*/};
	public Character(Coord loc) {
		charSetup();
		setCharImage();
		setSpawn(loc);
	}
	public void setCharImage(){
		image = ImageCache.getImage(ImageCache.characters[0]);
		size.setLoc(image.getWidth(null), image.getHeight(null));
	}

	public void charSetup(){
		//Forward
		spriteDir[0] = new Coord(55, 150);
		spriteDir[1] = new Coord(105, 150);
		spriteDir[2] = new Coord(5, 150);
		//Left
		spriteDir[3] = new Coord(55, 215);
		spriteDir[4] = new Coord(105, 215);
		spriteDir[5] = new Coord(5, 215);
		//Back
		spriteDir[6] = new Coord(55, 20);
		spriteDir[7] = new Coord(105, 20);
		spriteDir[8] = new Coord(5, 20);
		//Right
		spriteDir[9] = new Coord(55, 85);
		spriteDir[10] = new Coord(105, 85);
		spriteDir[11] = new Coord(5, 85);
		//modifier
		spriteDir[12] = new Coord(30, 44);
	}
	//Determines which foot goes first 
	public void footDeter(int direction){
		//forward
		if(direction == 0){
			if(walkCount && moving){
				charDirection = 1;
			}
			else{
				charDirection = 2;
			}
			if(!moving){
				charDirection = 0;
			}
		}
		//left
		if(direction == 1){
			if(walkCount && moving){
				charDirection = 4;
			}
			else{
				charDirection = 5;
			}
			if(!moving){
				charDirection = 3;
			}
		}
		//down
		if(direction == 2){
			if(walkCount && moving){
				charDirection = 7;
			}
			else{
				charDirection = 8;
			}
			if(!moving){
				charDirection = 6;
			}
		}
		//right
		if(direction == 3){
			if(walkCount && moving){
				charDirection = 10;
			}
			else{
				charDirection = 11;
			}
			if(!moving){
				charDirection = 9;
			}
		}
		setCharImage();
	}

	/**
	 * Walks to Point
	 */
	public void walkToClick(){
		int lastFoot = 0;
		if(newLoc.getX() > loc.getX()){
			footDeter(3);
			lastFoot = 3;
			loc.setX(loc.getX());
		}
		if(newLoc.getX() < loc.getX()){
			footDeter(1);
			lastFoot = 1;
			loc.setX(loc.getX());
		}
		if(newLoc.getY() > loc.getY()){
			footDeter(0);
			lastFoot = 0;
			loc.setY(loc.getY());
		}
		if(newLoc.getY() < loc.getY()){
			footDeter(2);
			lastFoot = 2;
			loc.setY(loc.getY());
		}
		if((newLoc.getX() == loc.getX()) && (newLoc.getY() == loc.getY())){
			moving = false;
			footDeter(lastFoot);
		}
		else{
			moving = true;
			footDeter(lastFoot);
		}
	}
	public void walkAnim(){
		if(moving){
			if(walkCount){
				walkCount = false;
			}
			else{
				walkCount = true;
			}
		}
	}
	public void moveCounter(Coord distance){
		if(newLoc.getX()>loc.getX()){
			offset.setX(offset.getX()+1);
		}
		if(newLoc.getX()<loc.getX()){
			offset.setX(offset.getX()-1);
		}
		if(newLoc.getY()>loc.getY()){
			offset.setY(offset.getY()+1);
		}
		if(newLoc.getY()<loc.getY()){
			offset.setY(offset.getY()-1);
		}
		if(offset.getX() == distance.getX()
				&& offset.getX() > 0){
			loc.setX(loc.getX()+1);
			offset.setX(0);
		}
		if((offset.getX()*-1) == distance.getX()
				&& offset.getX() < 0){
			loc.setX(loc.getX()-1);
			offset.setX(0);
		}
		if(offset.getY() == distance.getY()
				&& offset.getY() > 0){
			loc.setY(loc.getY()+1);
			offset.setY(0);
		}
		if((offset.getY()*-1) == distance.getY()
				&& offset.getY() < 0){
			loc.setY(loc.getY()-1);
			offset.setY(0);
		}
	}
	public void resetState() {	}
	public void playerMove(Coord mouseTile) {
		newLoc.setLoc( mouseTile.getX(), mouseTile.getY());
		walkToClick();
	}
	public Coord getSpriteDir(){
		return spriteDir[charDirection];
	}
	public Coord getSpriteMod(){
		return spriteDir[12];
	}
	public Coord getOffset(){
		return offset;
	}
	public Rectangle getColRect(){
		return new Rectangle(loc.getX()-4, loc.getY()+12,
				spriteDir[12].getX()/3, spriteDir[12].getY()/4);
	}
	public void haultMove(){
		newLoc.setLoc(loc.getX(), loc.getY());
	}
	public void setSpawn(Coord spawn){
		setLoc(spawn);
		newLoc.setLoc(loc.getX(), loc.getY());
	}
	public void toString(String outputit){
		System.out.println(outputit);
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}

}
