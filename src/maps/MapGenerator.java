package maps;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import resources.*;

/**
 * @author Jon Friesen
 * 
 * Gets formatted data from MapParser and renders map, view, objects.
 */

public class MapGenerator extends JPanel{
	private Coord tileSize;
	private Coord mapSize;
	private Coord mouse = new Coord();
	private Coord mouseTile = new Coord();
	private Coord mouseClick = new Coord(10,10);
	private Coord camera = new Coord();
	private Coord tXY = new Coord();
	private Coord charLoc;
	private Coord camSmooth = new Coord();
	private Coord mapLoc = new Coord();
	private Coord offset = new Coord();
	private int[][][] mapData;
	private int[][] tileSet;
	private int[][] gids;
	private int[] tileAmount;
	private int[] firstGID;
	private int[] layerType;
	private MapObject[] objects;
	private boolean[][] col;
	private int fGID;
	private int tR; // amount of rows in tileset
	private Image[] tileset;
	private Coord imageSmooth = new Coord();
	public MapGenerator(Map map) {
		setupMap(map);
	}
	public void setupMap(Map map){
		//Gets map resource
		tileset = map.getTileSource();
		firstGID = map.getFirstGid();
		tileSize = map.getTileSize();
		//gets amount of tiles in row of tileset
		tR = (256/tileSize.getX());
		mapSize = map.getMapSize();
		gids = map.getGids();
		mapData = new int[gids.length][mapSize.getX()][mapSize.getY()];
		col = new boolean[mapSize.getX()][mapSize.getY()];
		layerType = map.getLayerType();
		objects = map.getMapObjects();
		tilesetFinder();
		setupMapArray();
	}
	public void tilesetFinder(){
		for(int i=0; i < firstGID.length; i++){
			// Set Transparent Colour
			Color TransparentColor = new Color(0x000000);
			tileset[i] = Transparency.makeColorTransparent(tileset[i], TransparentColor);
			//fGID = firstGID[i];
		}
	}
	protected void setupMapArray(){
		for(int lay=0; lay < gids.length; lay++){
			int i=0;
			for(int y = 0; y < mapSize.getY() ; y++){
				for(int x = 0; x < mapSize.getX(); x++){
					mapData[lay][x][y] = gids[lay][i];
					if(layerType[lay] == 1 && !col[x][y]){
						col[x][y] = (gids[lay][i] != 0);
					}

					i++;
				}
			}
		}
	}
	public void drawTile(Graphics g, int layType){
		//offset used for alter the coordinates
		offset.setLoc((charLoc.getX()-((Commons.WIDTH/tileSize.getX())/2)),
				(charLoc.getY()-((Commons.HEIGHT/tileSize.getY())/2)));
		int set = 0;
		int key = 256;
		for(int lay=0; lay < gids.length; lay++){

			camera = new Coord(0,0);
			for(int y = (charLoc.getY()-((Commons.HEIGHT/tileSize.getY())/2)); y < (charLoc.getY()+((Commons.HEIGHT/tileSize.getY())/2)+2); y++){
				camera.setX(0);
				for(int x = (charLoc.getX()-((Commons.WIDTH/tileSize.getX())/2)); x < (charLoc.getX()+((Commons.WIDTH/tileSize.getX())/2)+2); x++){
					//Draws roof
					if(x >= 0 && y >= 0 && x < mapSize.getX() && y < mapSize.getY()){
						if(layType == 2){
							if(layerType[lay] == 2){
								//sets what tileset to read from
								set = 0;
								set = mapData[lay][x][y]/key;
								//Location of image on tileset
								int tile = mapData[lay][x][y] - firstGID[set];
								tXY.setX(((tile) % tR) * tileSize.getX());
								tXY.setY(((tile) / tR) * tileSize.getY());
								//draws image
								g.drawImage(tileset[set], camera.getX()*tileSize.getX()+camSmooth.getX(), camera.getY()*tileSize.getY()+camSmooth.getY(),//Places the image
										camera.getX()*tileSize.getX()+tileSize.getX()+camSmooth.getX(), camera.getY()*tileSize.getY()+tileSize.getY()+camSmooth.getY(),
										//Retrieves img from tileSource
										tXY.getX(), tXY.getY(), tXY.getX() +  tileSize.getX(), tXY.getY() +  tileSize.getY(), this );

							}
						}
						//Draws everything else
						else{
							if(layerType[lay] != 2){
								//sets what tileset to read from
								set = 0;
								set = mapData[lay][x][y]/key;
								//Location of image on tileset
								int tile = mapData[lay][x][y] - firstGID[set];

								tXY.setX(((tile) % tR) * tileSize.getX());
								tXY.setY(((tile) / tR) * tileSize.getX());
								//draws image

								g.drawImage(tileset[set], camera.getX()*tileSize.getX()+camSmooth.getX(), camera.getY()*tileSize.getY()+camSmooth.getY(),//Places the image
										camera.getX()*tileSize.getX()+tileSize.getX()+camSmooth.getX(), camera.getY()*tileSize.getY()+tileSize.getY()+camSmooth.getY(),
										//Retrieves img from tileSource
										tXY.getX(), tXY.getY(), tXY.getX() +  tileSize.getX(), tXY.getY() +  tileSize.getY(), this );
							}
						}
						//Draws Debug collision & objects
						if(Log.verbose){
							if(col[x][y]){
								g.setColor(Color.RED);
								g.drawRect((camera.getX()*tileSize.getX()+camSmooth.getX()), (camera.getY()*tileSize.getY()+camSmooth.getY()), (tileSize.getX()), (tileSize.getY()));
							}
							for(int i = 0; i < objects.length; i++){
								g.setColor(Color.BLUE);
								if(objects[i].getLoc().getX() == x
										&& objects[i].getLoc().getY() == y){
									g.drawRect((camera.getX()*tileSize.getX()+camSmooth.getX()), (camera.getY()*tileSize.getY()+camSmooth.getY()), (tileSize.getX()), (tileSize.getY()));

									/**
									 * @TODO Fix transparent backgrounds
									 * 
									Image warp =  ImageCache.getImage(ImageCache.warp);
									Color TransparentColor = new Color(0x000000);
									warp = Transparency.makeColorTransparent(warp, TransparentColor);
									//g.drawImage(warp, (camera.getX()*tileSize.getX()+camSmooth.getX()), (camera.getY()*tileSize.getY()+camSmooth.getY()), this);
									ImageIcon warpi = new ImageIcon(warp);
									warpi.paintIcon(this, g,(camera.getX()*tileSize.getX()+camSmooth.getX()), (camera.getY()*tileSize.getY()+camSmooth.getY()));
									 */

								}
							}
						}
					}
					camera.setX(camera.getX()+1);
				}
				camera.setY(camera.getY()+1);
			}
		}
	}

	public void clingToTile(Graphics g, Coord mouseLoc){
		mouse.setX(mouseLoc.getX()/((mapSize.getX()*tileSize.getX())/mapSize.getX())*tileSize.getX());
		mouse.setY(mouseLoc.getY()/((mapSize.getY()*tileSize.getY())/mapSize.getY())*tileSize.getY());

		mouseTile.setLoc((mouse.getX())/tileSize.getX()+offset.getX(),
				mouse.getY()/tileSize.getY()+offset.getY());
		if(Log.verbose){
			g.setColor(Color.ORANGE);
			g.drawRoundRect(mouse.getX(), mouse.getY(),32, 32, 3, 3);
			g.drawString("("+mouseTile.getX()+","+mouseTile.getY()+")",mouse.getX() ,mouse.getY() + 48);
		}
		//Gets groundTarget, flips it into a icon, makes it invisible and displays it
		if(!Log.verbose){
			Image groundTarget =  ImageCache.getImage(ImageCache.groundTarget);
			Color TransparentColor = new Color(0xFF00FF);
			groundTarget = Transparency.makeColorTransparent(groundTarget, TransparentColor);
			ImageIcon gtIcon = new ImageIcon(groundTarget);
			gtIcon.paintIcon(this, g,mouse.getX(), mouse.getY());
		}
	}
	/**
	 * Mini-map
	 *
	 */
	public void miniMap(Graphics g){
		double xmapTileSize = 3 ;
		int mapTileSize = (int) xmapTileSize;
		int alignRightSize =  Commons.WIDTH - (mapTileSize * mapSize.getX());
		//Draws base
		for(int y = 0; y < mapSize.getY(); y++){
			for(int x = 0; x < mapSize.getY(); x++){
				if(col[x][y]){
					//draw grey box on minimap
					g.setColor(Color.darkGray);
					g.fillRect(x*mapTileSize +alignRightSize, y*mapTileSize, mapTileSize, mapTileSize);
				}
				else{
					//draw black box on minimap
					g.setColor(Color.GRAY);
					g.fillRect(x*mapTileSize +alignRightSize, y*mapTileSize, mapTileSize, mapTileSize);
				}	
			}
		}
		//Draws objects
		for(int i = 0; i < objects.length; i++){
			g.setColor(Color.BLUE);
			g.fillRect(objects[i].getLoc().getX()*mapTileSize +alignRightSize,
					objects[i].getLoc().getY()*mapTileSize,
					mapTileSize,
					mapTileSize);
		}
		//Draws character
		g.setColor(Color.red);
		g.fillOval(charLoc.getX()*mapTileSize +alignRightSize, charLoc.getY()*mapTileSize, mapTileSize, mapTileSize);		
	}
	/**
	 * Used to get a particular object
	 * @param object
	 * @return
	 */
	public Coord getObject(int object){
		for(int i = 0; i < objects.length; i++){
			if(objects[i].getType() == object){
				objects[i].setloc(objects[i].getLoc().getX(),
						objects[i].getLoc().getY());
				return objects[i].getLoc();
			}
		}
		return null;
	}
	public boolean[][] getCol(){
		return col;
	}
	public Coord getMapSize(){
		return mapSize;
	}
	public Coord getTileSize(){
		return tileSize;
	}
	public int[][][] getSendMap(){
		return mapData;
	}
	public boolean[][] getCollisionBoolean(){
		return col;
	}
	public int[][] getGIDS(){
		return gids;
	}
	public int getTileSetRow(){
		return tR;
	}
	public int[] getFirstGIDArray(){
		return firstGID;
	}
	public int getfirstGID(){
		return fGID;
	}
	public Coord getMouseTile(){
		return mouseTile;
	}
	public void setMouseTemp(Coord mcl){
		mouseClick.setLoc(mcl);
	}
	public Coord getCharLoc(){
		return charLoc;
	}
	public Coord getOffset(){
		return offset;
	}
	public void setCharLoc(Coord p){
		charLoc = p;
	}
	public void setCamSmooth(Coord camSmooth){
		this.camSmooth.setLoc((camSmooth.getX()*-1), (camSmooth.getY()*-1));
	}
}