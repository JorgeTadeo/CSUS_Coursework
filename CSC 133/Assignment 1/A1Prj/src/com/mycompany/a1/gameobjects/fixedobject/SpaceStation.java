package com.mycompany.a1.gameobjects.fixedobject;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;

public class SpaceStation extends FixedObject {

	
	private int blinkRate;
	private boolean isOn;
	private int myId;
	public SpaceStation() {
		super(ColorUtil.YELLOW);
		this.blinkRate = new Random().nextInt(7);
		myId = FixedObject.id++;
		System.out.println("Added Blinking SpaceStation");
		System.out.println(this.toString()); //DEBUG LINE
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
	
	@Override 
	public String toString() {
		return (
			"Station: loc=" + Math.round(this.getX()*10.0)/10.0 + "," + Math.round(this.getY()*10.0)/10.0 + 
			" color=" + this.getColorToString() +
			" rate=" + this.getBlinkRate()
		);				
	}

}
