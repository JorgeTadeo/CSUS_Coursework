package com.mycompany.a2.gameobjects.moveableobject;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;

public class NonSteerableMissileLauncher extends MoveableObject implements IMoveable{

	private NonPlayerShip owner;
	
	private Point2D tip;
	
	
	public NonSteerableMissileLauncher(NonPlayerShip owner , int objectColor, int speed, int direction, double x, double y) {
		super(objectColor, speed, direction, x, y);
		this.owner = owner;
	}

	@Override
	public void move(){
		this.setLocation(owner.getX(), owner.getY());
	}

	
	protected Point2D getTipLoc() {
		return tip;
	}
	
	protected void setTipLoc(Point2D p) {
		tip = p;
	}
	
	@Override
	public void draw(Graphics g, Point origin) {
		// TODO Auto-generated method stub
		g.setColor(this.getColor());
		Point2D start = new Point2D(this.getX() + origin.getX() , this.getY() + origin.getY());
		double angleRad = Math.toRadians(90 - this.getDirection());
		double deltaX = Math.cos(angleRad);
		double deltaY = Math.sin(angleRad);
		Point2D end = new Point2D(start.getX() + 150 * deltaX ,start.getY() - 150 * deltaY );
		g.drawLine((int)start.getX()+25, 
				   (int)start.getY()+25,
				   (int)end.getX() + 25, 
				   (int)end.getY() + 25);

			setTipLoc(new Point2D(end.getX()-origin.getX(), end.getY()-origin.getY() ));
	}



	

}
