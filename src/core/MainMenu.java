package core;
/**
 * @author Jon Friesen
 * 
 * MainMenu sets JFrame.
 */
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import resources.Commons;
import resources.ImageCache;
import resources.Log;
import resources.FileBrowser;
import resources.Transparency;
import ui.About;
import ui.Help;
import ui.Login;
import ui.SplashScreenLoader;





public class MainMenu extends JFrame implements Commons{
	private JMenuBar menuBar;
	private JMenuItem quit;
	private JMenuItem help;
	private JMenuItem about;
	private JMenuItem debug;
	private JMenuItem newGame;
	private JMenu options;
	private JMenu map;
	private JMenuItem[] mapList;
	private FileBrowser mc = new FileBrowser();
	private Game game;
	protected Image cursor;


	public MainMenu()
	{
		//Login l = new Login();
		//add(l);
		//SplashScreenLoader ss = new SplashScreenLoader();
		Log.msg("Starting Game");
		game = new Game();
		add(game);

		setTitle("Blue Harvest");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Commons.WIDTH, Commons.HEIGHT);
		setLocationRelativeTo(null);
		setIgnoreRepaint(true);
		setResizable(true);
		setVisible(true);

		//Cursor
			cursor = ImageCache.getImage(ImageCache.cursor);
			Color TransparentColor = new Color(0, 0, 0);
			Toolkit tk = Toolkit.getDefaultToolkit();
			Cursor myPointer= tk.createCustomCursor((Transparency.makeColorTransparent(cursor, TransparentColor)), new Point(5,5), "Cursor");
			setCursor(myPointer);

		//Menu
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		{
			options = new JMenu();
			menuBar.add(options);
			options.setText("Options");
			{
				newGame = new JMenuItem();
				options.add(newGame);
				newGame.setText("Game Settings");
			}
			{
				debug = new JMenuItem();
				options.add(debug);
				debug.setText("Debug Mode");
			}
			{
				about = new JMenuItem();
				options.add(about);
				about.setText("About");
			}
			{
				help = new JMenuItem();
				options.add(help);
				help.setText("Help (H)");
			}
			{
				quit = new JMenuItem();
				options.add(quit);
				quit.setText("Quit (Esc)");
			}
			//Maplist
			map = new JMenu();
			menuBar.add(map);
			map.setText("Map List");

			String[] mlist = mc.fileFinder();
			for(int m = 0; m < mlist.length; m++){
				{
					map.add(new JMenuItem(new mapListener(mlist[m])));
				}
			}
		}
		newGame.addActionListener(new newGameListener());
		debug.addActionListener(new debugListener());
		about.addActionListener(new aboutListener());
		help.addActionListener(new helpListener());
		quit.addActionListener(new quitListener());

	}

	/**
	 * Calls function to set map
	 * @TODO fix mapListener
	 */
	private class mapListener extends AbstractAction{
		public mapListener(String s){
			super(s);
		}
		public void actionPerformed(ActionEvent e){
			String s = e.getActionCommand();
			Log.msg("Changing map to: " + s);
			game.setMap(s);
		}
	}
	/**
	 * Calls function to create new game
	 */
	private class newGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null, "Coming Soon");
		}
	}
	/**
	 * Calls function to call high score menu
	 */
	private class debugListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if(Log.verbose){
				Log.verbose = false;
			} else {
				Log.verbose = true;
			}
		}
	}
	/**
	 * Calls function to call about menu
	 */
	private class aboutListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			new About();
		}
	}
	/**
	 * Calls function to call help menu
	 */
	private class helpListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			new Help();
		}
	}
	/**
	 * Calls function to exit
	 */
	private class quitListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}

	public static void main(String[] args) {

		new MainMenu();
	}
}
