package com.mycompany.a1.gameobjects.moveableobject;

import com.codename1.charts.util.ColorUtil;

public class MissileLauncher extends MoveableObject implements ISteerable , IMoveable{

	private int speed;
	private int direction;
	private int delta; // Rotation amount 
	private PlayerShip owner;
	
	
	public MissileLauncher(PlayerShip owner , int speed , int direction, double x , double y) {
		super(ColorUtil.GREEN , speed, direction , x , y  );
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
	public void rotate() {
		this.direction += delta;
		this.direction %= 360;
		
	}
	
	@Override
	public void turnLeft() {
		// TODO Auto-generated method stub
		int currDirection = this.getDirection();
		if(currDirection == 0) {
			this.setDirection(359);
		}else {
			this.setDirection(currDirection - 1);
		}
		return;
	}

	@Override
	public void turnRight() {
		this.setDirection((this.getDirection() + 1) % 360);
		
	}
	
	@Override
	public void move(){
		this.setLocation(owner.getX(), owner.getY());
	}

}
