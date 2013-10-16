package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import resources.Commons;
import resources.Coord;
import resources.Log;

public class Chat extends JPanel{
	private String[] text = new String[10];
	private boolean status;
	private String current = " ";
	private Coord loc = new Coord();
	private int spacing = 12;
	private String name;
	public Chat(){}
	/**
	 * Updates the chat box
	 * @param g
	 * @param name
	 */
	public void updateChat(Graphics g, String name){

		getLoc();
		this.name = name;
		drawText(g);
		drawCurrent(g);
	}
	/**
	 * Calculates where to show chat based on window size
	 */
	public void getLoc(){
		loc.setLoc(15, Commons.HEIGHT/2);
	}
	/**
	 * Draws previously printed chat
	 * @param g
	 */
	public void drawText(Graphics g){
		g.setColor(Color.BLUE);
		for(int i=0; i<text.length; i++){
			if(text[i] != null){
				g.drawString(text[i],loc.getX() ,loc.getY()-spacing*i-spacing);
			}
		}
	}
	public void drawCurrent(Graphics g){
		if(status){
			g.setColor(Color.RED);
			g.drawString("Say: "+current,loc.getX() ,loc.getY());
		}
	}
	public void addMsg(){
		if(current.length()>1){
			for(int i=text.length-1; i>0;i--){
				text[i] = text[i-1];
			}
			text[0] = name+": " + filterMsg(current);
			current = "";
		}
	}
	/**
	 * Text Filter
	 */
	private String filterMsg(String msg){
		String newMsg = "";
		String filter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWRYZ01234567890!@#$%^&*()_+~{}\":?><|\\,./ ";
		for(int i=0; i<msg.length(); i++){
			if(filter.contains(""+msg.charAt(i))){
				newMsg = newMsg+msg.charAt(i);
			}
		}
		return newMsg;
	}
	/**
	 * Backspace registry
	 */
	public void backspace(){
		int pos = current.length()-1;
		StringBuffer buf = new StringBuffer( current.length() - 1 );
		buf.append( current.substring(0,pos) ).append( current.substring(pos+1) );
		current = buf.toString();
	}
	/**
	 * Sets/gets if game reads text
	 * @param status
	 */
	public void setStatus(boolean status){
		this.status = status;
	}
	public boolean getStatus(){
		return status;
	}
	public void nextChar(char c){
		current = filterMsg(current+c);
	}
	public void newKeyEvent(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(getStatus()){
				setStatus(false);
				addMsg();
			}
			else{
				setStatus(true);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			backspace();
		}
		
		if(getStatus()){
			nextChar(e.getKeyChar());
		}
	}
}
