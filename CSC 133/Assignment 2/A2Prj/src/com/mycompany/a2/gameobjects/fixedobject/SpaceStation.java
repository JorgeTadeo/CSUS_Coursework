package com.mycompany.a2.gameobjects.fixedobject;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;

public class SpaceStation extends FixedObject {

	
	private int blinkRate;
	private boolean isOn;
	private int myId;
	private int timer;
	public SpaceStation() {
		super(ColorUtil.BLACK);
		this.blinkRate = new Random().nextInt(7);
		myId = FixedObject.id++;
		System.out.println("Added Blinking SpaceStation");
		System.out.println(this.toString()); //DEBUG LINE
		timer = 0;
	}
	
	public boolean getBlinkStatus() {
		return isOn;
	}
	public void setBlinkStatus(boolean x) {
		isOn = x;
	}
	public int getBlinkRate() {
		return this.blinkRate;
	}
	public void setBlinkRate(int x) {
		this.blinkRate = x;
	}
	public int getId() {
		return this.myId;
	}
	public void updateStatus() {
		timer++;
		if (timer == blinkRate) {
			isOn = true;
			timer = 0;
		}else {
			isOn = false;
		}
	}
	
	@Override 
	public String toString() {
		return (
			"Station: loc=" + Math.round(this.getX()*10.0)/10.0 + "," + Math.round(this.getY()*10.0)/10.0 + 
			" color=" + this.getColorToString() +
			" rate=" + this.getBlinkRate()
		);				
	}

	@Override
	public void draw(Graphics g, Point origin) {
		// TODO Auto-generated method stub
		g.setColor(color);
		
		//Start point of blinking station
		Point2D p = new Point2D(this.getX() + origin.getX()  ,
								this.getY() + origin.getY()   );
		if(getBlinkStatus() == false) {
			g.drawArc((int)p.getX(), (int)p.getY(), 50, 50, 0, 360);
		}else {
			g.fillArc((int)p.getX(), (int)p.getY(), 50, 50, 0, 360);
		}
	}

}
