package com.mycompany.a1.gameobjects;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;

public abstract class GameObject {

	private Point2D coord;
	protected int color;
	
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

	public String getColorToString() {
		return (
			"[" + ColorUtil.red(this.color) + ","
				+ ColorUtil.green(this.color) + ","
				+ ColorUtil.blue(this.color) + "]"
		);				
	}
	
	
	
}
