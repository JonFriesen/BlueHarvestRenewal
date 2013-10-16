package core;
/**
 * @author Jon Friesen
 * 
 * The container for the entire game
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import character.Character;
import character.Player;

import maps.Map;
import maps.MapGenerator;
import maps.MapParser;
import resources.Commons;
import resources.Coord;
import resources.FileBrowser;
import resources.Log;
import ui.Chat;

public class Game extends JPanel implements Commons {

	/**
	 * Animation Timer
	 */
	class ScheduleAnimTask extends TimerTask {
		@Override
		public void run() {
			player.walkAnim();
			repaint();
		}
	}
	/**
	 * Char Timer(Movement Speed)
	 */
	class ScheduleCharTask extends TimerTask {
		@Override
		public void run() {
			//checkCollision();
			player.walkToClick();
			player.moveCounter(new Coord(mg.getTileSize().getX(),mg.getTileSize().getY()));
			mg.setCamSmooth(player.getOffset());
			mg.setCharLoc(new Coord(player.getX(), player.getY()));
			repaint();
		}
	}
	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
			chat.newKeyEvent(e);
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
	private class TAdapter2 extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			player.playerMove(mg.getMouseTile());
			mg.setMouseTemp(mg.getMouseTile());
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			mouse.setLoc(e.getX(), e.getY());
			Log.msg("Mouse Released");
		}
	}
	/**
	 * Registers key presses & releases
	 * @return 
	 */
	private class TAdapter3 extends MouseAdapter{
		@Override
		public void mouseMoved(MouseEvent e) {
			mouse.setLoc(e.getX(), e.getY());
			//chara.mouseMoved(e);
		}
	}
	private Chat chat = new Chat();
	private Image ii;
	private Timer timer2, timer;
	private Player player;
	private MapGenerator mg;
	private Map map;
	private Coord playLoc;
	Coord mouse = new Coord();
	static boolean ingame;
	public static boolean help;
	public static boolean levelup;
	Random generator = new Random();
	public Game() {
		setBackground(Color.BLACK);
		setMap(Commons.MAP);
		addKeyListener(new TAdapter());
		addMouseListener(new TAdapter2());
		addMouseMotionListener(new TAdapter3());
		setFocusable(true);
		setDoubleBuffered(true);
		startTimers();
	}
	@Override
	public void addNotify() {
		super.addNotify();
		gameInit();
	}
	public void setMap(String map){
		MapParser mapParser = new MapParser();
		this.map = mapParser.parseIt(new Map(map));
		mg = new MapGenerator(this.map);
		reset();
	}
	/**
	 * Collision checker
	 */
	public void checkCollision() {
		for(int y = 0; y <mg.getMapSize().getY(); y++){
			for(int x = 0; x <mg.getMapSize().getX(); x++){
				if(mg.getCol()[x][y]){//Ends loop if not collision square
					if(player.getColRect().intersects(new Rectangle(mg.getTileSize().getX()*x, mg.getTileSize().getY()*y,
							mg.getTileSize().getX()*x+mg.getTileSize().getX(), mg.getTileSize().getY()*y+mg.getTileSize().getY()))){
						Log.msg("Collision @ "+x+" "+y);
					}
				}
			}
		}
	}
	/**
	 * Initializes game for startup
	 */
	public void gameInit() {
		ingame = true;
		spawnChar();
	}
	/**
	 * Main paint method, hands out paint permission as well
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (ingame) {

			//Gets map
			mg.drawTile(g, 1);

			//Actual location of character
			playLoc = new Coord((player.getX()+(mg.getOffset().getX()*-1))*mg.getTileSize().getX(),
					(player.getY()+(mg.getOffset().getY()*-1))*mg.getTileSize().getY());
			//Draws character
			g.drawImage(player.getImage(), playLoc.getX(), playLoc.getY(),playLoc.getX()+player.getSpriteMod().getX(), playLoc.getY()+player.getSpriteMod().getY(),
					player.getSpriteDir().getX(), player.getSpriteDir().getY(), player.getSpriteDir().getX()+player.getSpriteMod().getX(),
					player.getSpriteDir().getY()+player.getSpriteMod().getY(), this);
			
			//draws roof
			mg.drawTile(g, 2);
			//Draws tile highlighter
			mg.clingToTile(g, mouse);
			//Draws Minimap
			//mg.miniMap(g);
			//Draws character collision box
			/*
			 g.drawRect((int)player.getColRect().getX()+playLoc.getX(),(int)player.getColRect().getY()+playLoc.getY(),
					(int)player.getColRect().getWidth(),(int)player.getColRect().getHeight());
			*/
			//Updates UI chat
			chat.updateChat(g, player.getName());
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	/**
	 * Spawns player character
	 */
	public void spawnChar(){
		player = new Player(mg.getObject(0));
		mg.setCharLoc(new Coord(player.getX(), player.getY()));
	}
	/**
	 * Starts timers
	 */
	public void startTimers(){
		//Speed of the character
		timer2 = new Timer();
		timer2.scheduleAtFixedRate(new ScheduleCharTask(), 1000, 20);
		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleAnimTask(), 1000, 130);
	}
	/**
	 * resets char 
	 */
	private void reset(){
		spawnChar();
	}
	public void stopGame() {
		ingame = false;
		stopTimers();
	}
	public void stopTimers(){
		timer2.cancel();
		timer.cancel();
	}	
}

