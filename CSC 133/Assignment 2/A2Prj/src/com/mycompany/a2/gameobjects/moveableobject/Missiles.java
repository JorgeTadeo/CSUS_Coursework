package com.mycompany.a2.gameobjects.moveableobject;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;

public class Missiles extends MoveableObject implements IMoveable{

	private Ship owner;
	private int fuel;
	
	public Missiles(PlayerShip owner) {
		super(ColorUtil.rgb(255, 0, 0), owner.getSpeed() + 10, owner.getMl().getDirection(), owner.getMl().getTipLoc().getX(), owner.getMl().getTipLoc().getY());
		this.owner = owner;
		this.fuel = 15;
		owner.decrementMissile();
		System.out.println(this.toString()); //DEBUG LINE
	}
	public Missiles(NonPlayerShip owner) {
		super(ColorUtil.BLUE, owner.getSpeed() + 10, owner.getMl().getDirection(), owner.getMl().getTipLoc().getX(), owner.getMl().getTipLoc().getY());
		this.fuel = 15;
		this.owner = owner;
		owner.decrementMissile();
		System.out.println(this.toString()); //DEBUG LINE
	}
	public void setSpeed(int x) {
		//System.out.println("Cannot alter missile speed");
	}
	public void setDirection(int x) {
		//System.out.println("Cannot alter missile direction");
		
		//Uncomment if want playership's missile to do wall bounce
		if(owner instanceof PlayerShip) {
			//super.setDirection(x);
		}
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
	@Override
	public void draw(Graphics g, Point origin) {
		// TODO Auto-generated method stub
		//g.setColor(this.getColor());
		g.setColor(color);
		Point2D p = new Point2D(this.getX()  + origin.getX() , this.getY() + origin.getY());

		g.drawArc((int)p.getX(),(int)p.getY() ,  50, 50, 0, 360);
		g.fillArc((int)p.getX(),(int)p.getY() ,  50, 50, 0, 360);
	//	System.out.println("Missile drawn : " + p.getX() + "," + p.getY());

	}

	

}
