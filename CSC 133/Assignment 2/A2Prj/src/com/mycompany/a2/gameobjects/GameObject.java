package com.mycompany.a2.gameobjects;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a2.graphics.IDrawable;

public abstract class GameObject implements IDrawable {

	private Point2D coord;
	protected int color;
	private int size;
	

	public GameObject(int objectColor) {
		Random rnd = new Random();
		double x =  rnd.nextDouble() * 1024;
		double y =  rnd.nextDouble() * 768;

		this.coord = new Point2D(x , y);
		this.color = objectColor;
	}
	public GameObject(int objectColor , double x , double y) {
		this.coord = new Point2D(x,y);
		this.color = objectColor;
	}
	
	public double getX() { return coord.getX(); }
	public double getY() { return coord.getY(); }		
	public void setX(double x) { coord.setX(x); }
	public void setY(double y) { coord.setY(y); }
	public int getColor() { return this.color; } 
	public void setLocation(double x , double y) { 
		this.coord.setX(x);
		this.coord.setY(y);
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	public String getColorToString() {
		return (
			"[" + ColorUtil.red(this.color) + ","
				+ ColorUtil.green(this.color) + ","
				+ ColorUtil.blue(this.color) + "]"
		);				
	}
	
	
	
}