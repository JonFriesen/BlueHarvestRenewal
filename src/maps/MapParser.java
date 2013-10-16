package maps;
/**
 * @author Jon Friesen
 * 
 * Retrieves raw data from map files and sorts it.
 */
import java.io.*;
import java.util.zip.GZIPInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import resources.*;



public class MapParser {
	//Map specifics
	private Coord mapSize=new Coord(), tileSize=new Coord();
	//private int gid;
	private int[][] gids;
	private int[] layerType;
	//Map resource image specifics
	private String[] tileSource;
	private int[] firstGID;
	private MapObject[] objects;
	
	public MapParser(){	}
	
	public Map parseIt(Map map) {
		try {
			InputStream input = this.getClass().getClassLoader().getResourceAsStream("resources/maps/"+map.getMapName());
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte buf[] = new byte[10240];
			int len = 0;
			while((len=input.read(buf))> -1){
				output.write(buf,0,len);
			}
			input.close();
			output.close();
			
			//Builds document
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(output.toByteArray()));
			doc.getDocumentElement().normalize();

			//Grabs map specifics
			mapSize.setX(Integer.parseInt(doc.getDocumentElement().getAttribute("width")));
			mapSize.setY(Integer.parseInt(doc.getDocumentElement().getAttribute("height")));
			tileSize.setX(Integer.parseInt(doc.getDocumentElement().getAttribute("tilewidth")));
			tileSize.setY(Integer.parseInt(doc.getDocumentElement().getAttribute("tileheight")));
			//layer info node
			NodeList layerList = doc.getElementsByTagName("layer");
			//tileset info node
			NodeList tilesetList = doc.getElementsByTagName("tileset");
			//objects
			NodeList objectList = doc.getElementsByTagName("objectgroup");
			/**
			 * Tileset Data
			 */
			firstGID = new int[tilesetList.getLength()];
			tileSource = new String[tilesetList.getLength()];
			gids = new int[layerList.getLength()][mapSize.getX()*mapSize.getY()];

			for (int s = 0; s < tilesetList.getLength(); s++) {

				Node tilesetNode = tilesetList.item(s);

				if (tilesetNode.getNodeType() == Node.ELEMENT_NODE) {

					Element tileElmnt = (Element) tilesetNode;
					firstGID[s] = Integer.parseInt(tileElmnt.getAttribute("firstgid"));

					NodeList tileNmElmntLst = tileElmnt.getElementsByTagName("image");
					Element tileNmElmnt = (Element) tileNmElmntLst.item(0);
					NodeList tileNm = tileNmElmnt.getChildNodes();
					//gets image location
					tileSource[s] = "resources/maps/"+tileNmElmnt.getAttribute("source");
				}
			}
			/**
			 * Object Data
			 */
			objects = new MapObject[objectList.getLength()];
			for (int s = 0; s < objectList.getLength(); s++) {

				Node objectNode = objectList.item(s);

				if (objectNode.getNodeType() == Node.ELEMENT_NODE) {

					Element objectElmnt = (Element) objectNode;
					NodeList objectNmElmntLst = objectElmnt.getElementsByTagName("object");
					Element objectNmElmnt = (Element) objectNmElmntLst.item(0);
					NodeList objectNm = objectNmElmnt.getChildNodes();
					int ot;
					if(objectElmnt.getAttribute("name").startsWith("s")){
						ot = 0;
					}
					else{
						ot = -1;
					}
					objects[s] = new MapObject(Integer.parseInt(objectNmElmnt.getAttribute("x"))/tileSize.getX(),Integer.parseInt(objectNmElmnt.getAttribute("y"))/tileSize.getY(),
							Integer.parseInt(objectNmElmnt.getAttribute("width")),Integer.parseInt(objectNmElmnt.getAttribute("height")), ot);

				}

			}
			/**
			 * Layer Data
			 */
			//Layer arrays
			String[] layerData = new String[layerList.getLength()];
			layerType = new int[layerList.getLength()];
			for (int s = 0; s < layerList.getLength(); s++) {

				Node fstNode = layerList.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;
					//Grabs layer type ie. Collision, Ground, Roof
					if(fstElmnt.getAttribute("name").startsWith("g")){
						layerType[s] = 0;
						//Log.msg(fstElmnt.getAttribute("name")+" "+layerType[s]);
					}
					if(fstElmnt.getAttribute("name").startsWith("c")){
						layerType[s] = 1;
						//Log.msg(fstElmnt.getAttribute("name")+" "+layerType[s]);
					}
					if(fstElmnt.getAttribute("name").startsWith("r")){
						layerType[s] = 2;
						//Log.msg(fstElmnt.getAttribute("name")+" "+layerType[s]);
					}

					//Grabs Layer attributes
					NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("data");
					Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
					NodeList fstNm = fstNmElmnt.getChildNodes();
					//Saves the retrieved data to an array
					layerData[s] = ((Node) fstNm.item(0)).getNodeValue();
					//changes the recorded string to a byte array
					byte[] buffer = Base64.decode(layerData[s].toCharArray());
					//puts the byte array into a inputStream
					ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
					//Sends array GZIP input stream
					GZIPInputStream gis = new GZIPInputStream(byteStream);

					int o = 0;

					while( gis.available() == 1 ){
						int gid = gis.read() |
						gis.read() << 8 |
						gis.read() << 16 |
						gis.read() << 24;
						if(gid < 0){
							break;
						}
						gids[s][o] = gid;
						o++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//setting map up
		map.setFirstGid(firstGID);
		map.setGids(gids);
		map.setMapSize(mapSize);
		map.setObjects(objects);
		map.setTileSource(tileSource);
		map.setTileSize(tileSize);
		map.setLayerType(layerType);
		return map;
	}
}