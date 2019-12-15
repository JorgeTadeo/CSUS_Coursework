package com.mycompany.a2.gameobjects.moveableobject;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a2.graphics.IDrawable;

public class MissileLauncher extends MoveableObject implements ISteerable , IMoveable , IDrawable{

	private int speed;
	private int direction;
	private int delta; // Rotation amount 
	private PlayerShip owner;
	
	
	private Point2D tip;
	
	
	public MissileLauncher(PlayerShip owner , int speed , int direction, double x , double y) {
		super(ColorUtil.BLACK , speed, direction , x , y  );
		this.owner = owner;
		this.delta = 5;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getDirection() {
		return this.direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	@Override
	public void turnLeft() {
		// TODO Auto-generated method stub
		int currDirection = this.getDirection();
		if(currDirection == 0) {
			this.setDirection(359);
		}else {
			this.setDirection(currDirection - delta); //WAS 1
		}
		return;
	}

	@Override
	public void turnRight() {
		this.setDirection((this.getDirection() + delta) % 360); //WAS 1
		
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
		double angleRad = Math.toRadians(90 - this.direction);
		double deltaX = Math.cos(angleRad);
		double deltaY = Math.sin(angleRad);
		Point2D end = new Point2D(start.getX() + 150 * deltaX ,start.getY() - 150 * deltaY );
		g.drawLine((int)start.getX()+25, 
				   (int)start.getY()+25,
				   (int)end.getX() + 25, 
				   (int)end.getY() + 25);

			setTipLoc(new Point2D(end.getX()-origin.getX(), end.getY()-origin.getY() ));
	
		
	}

	public void setOwner(PlayerShip ps) {
		owner = ps;
	}


}
