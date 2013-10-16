package resources;


import java.awt.Image;
import java.util.Hashtable;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

/**
 *
 * @author Loren
 * Loren uses hashtables to retrieve sprites, hoping to apply this method to the entire game
 */
public class ImageCache {

        // image paths
		public static String tiles[];
		public static final String warp = "resources/cursors/warp.gif";
        public static final String cursor = "resources/cursors/cursor.png";
        public static final String groundTarget = "resources/cursors/ground.png";
        public static final String[] characters = new String[]{
        	"resources/sprites/images/player1.png",
        	"resources/sprites/images/player2.png"
        };
        public static final String[] backgrounds = new String[] {
        	"resources/backgrounds/1.png",
        	"resources/backgrounds/2.png",
        	"resources/backgrounds/3.png",
        	"resources/backgrounds/4.png"
        };
        
        // static members
        private static ImageCache instance;
        private static void initialize(){ instance = new ImageCache(); }
        public static Image getImage(String path)
        {
            if(instance == null)
                initialize();
            return instance.getImageInternal(path);
        }
        
        // member variables
        private Hashtable<String, Image> images;
        
        // constructors
        private ImageCache()
        {
            images = new Hashtable<String, Image>();
        }

        // methods
        private Image getImageInternal(String path)
        {
            if(images.containsKey(path))
                return images.get(path);
           ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource(path));
           Image image = icon.getImage();
           images.put(path, image);
           return image;
        }
        public static void setTile(String[] tilesUsed){
        	tiles = tilesUsed;
        }
}