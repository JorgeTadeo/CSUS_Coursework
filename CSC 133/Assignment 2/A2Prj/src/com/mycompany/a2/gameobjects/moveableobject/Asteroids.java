package com.mycompany.a2.gameobjects.moveableobject;
import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a2.gameobjects.GameObject;
import com.mycompany.a2.gameobjects.ICollider;
import com.mycompany.a2.gameobjects.moveableobject.IMoveable;
import com.mycompany.a2.gameobjects.moveableobject.MoveableObject;
import com.mycompany.a2.graphics.IDrawable;


public class Asteroids extends MoveableObject implements IMoveable , IDrawable , ICollider {

	private boolean collisionFlag = false;
	
	public Asteroids() {
		super(ColorUtil.rgb(124, 124, 200));
		final int MIN_SIZE = 6;
		final int MAX_SIZE = 30;
		setSize(new Random().nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE);
		System.out.println("Asteroid is created");
	//	System.out.println(this.toString()); //DEBUG LINE
	}
	

	
	@Override 
	public String toString() {
		return (
			"Asteroid: loc=" + Math.round(this.getX()*10.0)/10.0 + "," + Math.round(this.getY()*10.0)/10.0 + 
			" color=" + this.getColorToString() +
			" speed=" + this.getSpeed() +
			" dir=" + this.getDirection() +
			" size=" + this.getSize() 
		);				
	}

	@Override //origin is origin of mapview/gameworld
	public void draw(Graphics g, Point origin) {
		// TODO Auto-generated method stub
		
		g.setColor(this.getColor());
		
		//Start point of asteroid 
		Point2D p = new Point2D(this.getX() + origin.getX() - getSize()/2 ,
								this.getY() + origin.getY() - getSize()/2   );
		
		g.drawArc((int)p.getX(), (int)p.getY(), getSize(), getSize(), 0, 360);
		g.fillArc((int)p.getX(), (int)p.getY(), getSize(), getSize(), 0, 360);
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
		if (other instanceof Asteroids || other instanceof NonPlayerShip )
		{
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