package com.mycompany.a2.gameobjects.moveableobject;

public abstract class Ship extends MoveableObject {

	private int MAX_MISSILE_COUNT;
	private int missileCount;
	
	
	public Ship(int objectColor) {
		super(objectColor);
	}
	
	public Ship(int objectColor, int speed, int direction, double x, double y , int missileCount , int max) {
		super(objectColor, speed, direction, x, y);
		this.missileCount = missileCount;
		this.MAX_MISSILE_COUNT = max;
	}
	
	

	public void reload() {
		if(missileCount == MAX_MISSILE_COUNT) 
			return;
		missileCount = MAX_MISSILE_COUNT;
	}
	
	@SuppressWarnings("unused")
	private boolean isEmpty() { return missileCount == 0 ; }
	
	public int getMissileCount() {
		return this.missileCount;
	}
	
	public void setMissileCount(int x) {
		missileCount = x;
	}
	
	public int getMaxMissileCount() {
		return MAX_MISSILE_COUNT;
	}
	
	public void setMaxMissileCount(int x) {
		x = MAX_MISSILE_COUNT;
	}
	
	public void decrementMissile() {
		missileCount--;
	}
	
	



}
