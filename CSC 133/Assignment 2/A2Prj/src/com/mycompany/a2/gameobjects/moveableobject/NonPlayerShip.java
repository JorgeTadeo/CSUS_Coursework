package com.mycompany.a2.gameobjects.moveableobject;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a2.gameobjects.GameObject;
import com.mycompany.a2.gameobjects.ICollider;


public class NonPlayerShip extends Ship implements IMoveable , ICollider{

	
	private NonSteerableMissileLauncher ml;
	private boolean collisionFlag;
	
	public NonPlayerShip() {
		super(ColorUtil.MAGENTA); //Speed,Direction,LocationX,LocationY Auto generate from Ship
		setSize(new Random().nextInt(2) == 0 ? 15 : 25);
		ml = new NonSteerableMissileLauncher(this , ColorUtil.BLACK, this.getSpeed(), this.getDirection(), this.getX(), this.getY());
		this.setMaxMissileCount(4);
		this.setMissileCount(4);
		System.out.println("Added NonPlayerShip");
		System.out.println(this.toString()); //DEBUG LINE
	}
	
	public NonSteerableMissileLauncher getMl() {
		return this.ml;
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

	@Override
	public void draw(Graphics g, Point origin) {
		// TODO Auto-generated method stub
		
		int x = (int)this.getX() + (int)origin.getX();
		int y = (int)this.getY() + (int)origin.getY();

		g.setColor(color);
		g.fillRect(x , y , 50 ,50 );
		
		ml.draw(g, origin);
	}

	@Override
	public boolean collidesWith(ICollider other) {
		// TODO Auto-generated method stub
		boolean result = false;
		double thisCenterX = this.getX();
		double thisCenterY = this.getY();
		
		double otherCenterX = ((GameObject)other).getX();
		double otherCenterY = ((GameObject)other).getY();
		
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		
		double distBetweenCentersSqr = (dx * dx + dy * dy);
		
		// find square of sum of radii
		int thisRadius= this.getSize() / 2;
		int otherRadius= ((GameObject)other).getSize() / 2;
		
		int radiiSqr= (thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius);
		
		if (distBetweenCentersSqr <= radiiSqr) { result = true ; }
		
		return result;
	}

	@Override
	public void handleCollision(ICollider other) {
		// TODO Auto-generated method stub
		if (other instanceof Asteroids   || other instanceof NonPlayerShip){
			this.setCollisionFlag();
			other.setCollisionFlag();
		}
	}

	@Override
	public void setCollisionFlag() {
		// TODO Auto-generated method stub
		collisionFlag = true;
	}

	@Override
	public boolean getCollisionFlag() {
		// TODO Auto-generated method stub
		return collisionFlag;
	}


	



}
