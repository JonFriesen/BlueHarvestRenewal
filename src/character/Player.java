package character;

import resources.Coord;

public class Player extends Character{
	public Player(Coord loc){
		charSetup();
		setCharImage();
		setSpawn(loc);
	}
	public Player(){}
}
