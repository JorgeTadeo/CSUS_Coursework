package com.mycompany.a1.gameobjects.moveableobject;

import com.codename1.charts.util.ColorUtil;

public class Missiles extends MoveableObject implements IMoveable{

	private Ship owner;
	private int fuel;
	
	public Missiles(PlayerShip owner) {
		super(ColorUtil.GRAY, owner.getSpeed() + 10, owner.getMl().getDirection(), owner.getX(), owner.getY());
		this.owner = owner;
		this.fuel = 15;
		owner.decrementMissile();
		//System.out.println(this.toString()); //DEBUG LINE
	}
	public Missiles(NonPlayerShip owner) {
		super(ColorUtil.BLUE, owner.getSpeed() + 10, owner.getMl().getDirection(), owner.getX(), owner.getY());
		this.fuel = 15;
		this.owner = owner;
		owner.decrementMissile();
		System.out.println(this.toString()); //DEBUG LINE
	}
	public void setSpeed(int x) {
		System.out.println("Cannot alter missile speed");
	}
	public void setDirection(int x) {
		System.out.println("Cannot alter missile direction");
	}
	public int getFuel() {
		return this.fuel;
	}
	public void setFuel(int x ) {
		this.fuel = x;
	}
	public void decrementFuel() {
		this.fuel--;
	}
	
	public Ship getOwner() {
		return this.owner;
	}
	
	@Override 
	public String toString() {
		if(owner instanceof PlayerShip) {
			return (
					"PS's Missile: loc=" + Math.round(this.getX()*10.0)/10.0 + "," + Math.round(this.getY()*10.0)/10.0 + 
					" color=" + this.getColorToString() +
					" speed=" + this.getSpeed() +
					" dir=" + this.getDirection() +
					" fuel=" + this.getFuel()
				);	
		}else {
			return (
					"NPS's Missile: loc=" + Math.round(this.getX()*10.0)/10.0 + "," + Math.round(this.getY()*10.0)/10.0 + 
					" color=" + this.getColorToString() +
					" speed=" + this.getSpeed() +
					" dir=" + this.getDirection() +
					" fuel=" + this.getFuel()
				);	
		}
			
	}

}
