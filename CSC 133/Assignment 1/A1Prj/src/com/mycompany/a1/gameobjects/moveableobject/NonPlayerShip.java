package com.mycompany.a1.gameobjects.moveableobject;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;

public class NonPlayerShip extends Ship implements IMoveable {

	
	private int size;
	private NonSteerableMissileLauncher ml;
	
	public NonPlayerShip() {
		super(ColorUtil.MAGENTA); //Speed,Direction,LocationX,LocationY Auto generate from Ship
		size = new Random().nextInt(2) == 0 ? 15 : 25;
		ml = new NonSteerableMissileLauncher(this , ColorUtil.MAGENTA, this.getSpeed(), this.getDirection(), this.getX(), this.getY());
		this.setMaxMissileCount(4);
		this.setMissileCount(4);
		System.out.println("Added NonPlayerShip");
		System.out.println(this.toString()); //DEBUG LINE
	}
	
	public NonSteerableMissileLauncher getMl() {
		return this.ml;
	}
	
	public int getSize() {
		return this.size;
	}
	public void setSize(int x) {
		this.size = x;
	}
		
		
	@Override 
	public String toString() {
		return (
			"Non-Player Ship: loc=" + Math.round(this.getX()*10.0)/10.0 + "," + Math.round(this.getY()*10.0)/10.0 + 
			" color=" + this.getColorToString() +
			" speed=" + this.getSpeed() +
			" dir=" + this.getDirection() +
			" size=" + this.getSize()
		);				
	}


	



}
