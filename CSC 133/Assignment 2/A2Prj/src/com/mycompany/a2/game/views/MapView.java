package com.mycompany.a2.game.views;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.plaf.Border;
import com.mycompany.a2.game.GameWorld;
import com.mycompany.a2.game.IGameWorld;
import com.mycompany.a2.gameobjects.GameObject;
import com.mycompany.a2.gameobjects.IIterator;

/*
 * 1 of 2 Observer that observes GameWorld
 * This is the center region of the game
 * Purpose : 
 */
public class MapView extends Container implements Observer{

	static private int mapViewHeight;
	static private int mapViewWidth;
	
	static private Point2D mapViewOrigin;
	
	//private TextArea mapValueTextArea;
	private IGameWorld gw;
	
	/*
	 * Constructor
	 */
	public MapView(GameWorld myGW) {
		MapView.mapViewHeight = this.getHeight();
		MapView.mapViewWidth = this.getWidth();
	//	this.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		this.getAllStyles().setBorder(Border.createLineBorder(5));
		this.getAllStyles().setBgColor(ColorUtil.BLACK);
		/*
		//Add Component to Container
		mapValueTextArea = new TextArea();
		mapValueTextArea.setEditable(false);
		mapValueTextArea.setFocusable(false);
	
		this.add(mapValueTextArea);
		*/
		
		

	}
	
	
	/*
	 * Getters/Setters for MapView Height & Width
	 */
	public static int getMapViewWidth() { return mapViewWidth; }
	public static int getMapViewHeight() { return mapViewHeight; }
	public static void setMapViewWidth(int width) { MapView.mapViewWidth = width; }
	public static void setMapViewHeight(int height) { MapView.mapViewHeight = height; }
	
	
	public void setMapViewOrigin(Point2D p) { MapView.mapViewOrigin = p; }
	public static Point2D getMapViewOrigin() { return mapViewOrigin; }

	
	
	/**
	 * Description: Draw each object to mapview.
	 */
	@Override
	public void update(Observable observable, Object data) {
		
		/*
		 * Prints all gameObject using GameObjectIterator to textArea
		 */
		/* OLD 
		IGameWorld gw = (IGameWorld) data;
		mapValueTextArea.setText("");
		
		IIterator iter = gw.getGameObjectIterator();
		while(iter.hasNext()) {
			//mapValueTextArea.setText(mapValueTextArea.getText() + "\n" + iter.getNext());
		}
		*/
		
		/*
		 * NEW
		 */
		this.gw = (IGameWorld) data;
		this.repaint();
		
	
	}
	
	//this function triggered by tick() or anything that notify mapview
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Point mapViewOrigin = new Point(getX(),getY());

		if(gw != null) {
			IIterator iter = gw.getGameObjectIterator();
			while(iter.hasNext()) {
				GameObject obj = iter.getNext();
				obj.draw(g, mapViewOrigin);
			}
		}
	}
	

}
