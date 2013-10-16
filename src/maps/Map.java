package maps;

import java.awt.Image;




import resources.*;

/**
 * 
 * @author Jon
 *
 *This creates a map object, which can be passed around.
 */
public class Map {
	private Coord mapSize;
	private String[] tiles;
	private String mapName;
	private MapObject[] objects;
	private int[][] gids;
	private int[] firstGid;
	private Image[] tileImg;
	private Coord tileSize;
	private int[] layerType;
	
	public Map(){
		Log.err("Improper Call: map(mapName)");
	}
	public Map(String mapName){
		this.mapName = mapName;
	}
	//Setters
	public void setMapSize(Coord mapSize){
		this.mapSize = mapSize;
	}
	public void setTileSource(String[] tiles){
		this.tiles = tiles;	
		ImageCache.setTile(this.tiles);
	}
	public void setObjects(MapObject[] objects){
		this.objects = objects;
	}
	public void setGids(int[][] gids){
		this.gids = gids;
	}
	public void setFirstGid(int[] firstGid){
		this.firstGid = firstGid;
	}
	public void setTileSize(Coord tileSize){
		this.tileSize = tileSize;
	}
	public void setLayerType(int[] layerType){
		this.layerType = layerType;
	}
	//Getters
	public String getMapName(){
		return mapName;
	}
	public Coord getMapSize(){
		return mapSize;
	}
	public Image[] getTileSource(){
		tileImg = new Image[tiles.length];
		for(int img=0; img < tileImg.length; img++){		
			tileImg[img] = ImageCache.getImage(ImageCache.tiles[img]);
		}
		return tileImg;
	}
	public MapObject[] getMapObjects(){
		return objects;
	}
	public int[][] getGids(){
		return gids;
	}
	public int[] getFirstGid(){
		return firstGid;
	}
	public Coord getTileSize(){
		return tileSize;
	}
	public int[] getLayerType(){
		return layerType;
	}
	
	//toString
	public String toString(){
		return "Map: "+mapName+" mapSize: "+mapSize.toString();
	}
}
