package com.mycompany.a1.gameobjects.moveableobject;

import com.codename1.charts.util.ColorUtil;
import com.mycompany.a1.game.GameWorld;

@SuppressWarnings("unused")
public class PlayerShip extends Ship implements ISteerable{

	//Singleton PlayerShip
	private volatile static PlayerShip ps;
	private PlayerShip() {
		super(ColorUtil.GREEN,0,0,x,y,10,10);//Color,Speed,Direction,LocationX,LocationY,missileCount,MaxMissileCount
		life = 3;
		ml = new MissileLauncher(ps , this.getSpeed(),this.getDirection(), x, y);
		setMaxMissileCount(10);
		setMissileCount(10);
		System.out.println(this.toString()); //DEBUG LINE
	}
	public static PlayerShip getInstance() {
		if(ps == null) {
			synchronized (PlayerShip.class) {
				if(ps == null)
					ps = new PlayerShip();
					System.out.println("Added Playership");
			}
		}else {
			if(ps.getLife()  != 0) {
				System.out.println("PlayerShip Already Exists...");
			}else {
				ps.setLife(3);
				System.out.println("Added Playership");
			}
		}
		return ps;
	}
	
	
	final static int x = 512;
	final static int y = 384;
	final static int MAX_SPEED = 20;
	
	private int life;
	private MissileLauncher ml;
	

	
	public void increaseSpeed() {
		int currSpeed = getSpeed();
		if(currSpeed < MAX_SPEED) {
			setSpeed(currSpeed + 1);
		}else {
			System.out.println("Already at max speed of " + MAX_SPEED);
		}
	}
	public void decreaseSpeed() {
		int currSpeed = getSpeed();
		if(currSpeed > 0) {
			setSpeed(currSpeed - 1);
		}else {
			System.out.println("Already at minimum speed of 0");
		}
	}
	
	public void resetPosition() {
		this.setLocation(x, y);
	}
	
	public int getLife() {
		return life;
	}
	
	public void setLife(int x) {
		life = x;
	}
	
	public void respawn() {
		this.setX(x);
		this.setY(y);
		this.setDirection(0);
		this.setSpeed(0);
		this.reload();
		life--;
	}
	
	
	public MissileLauncher getMl() {
		return this.ml;
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
	public String toString() {
		return (
			"Player Ship: loc=" + Math.round(this.getX()*10.0)/10.0 + "," + Math.round(this.getY()*10.0)/10.0 + 
			" color=" + this.getColorToString() +
			" speed=" + this.getSpeed() +
			" dir=" + this.getDirection() +
			" missiles=" + this.getMissileCount() + 
			" Missile Launcher dir = " + this.ml.getDirection()
		);				
	}
	/*
	@Override
	public void move() {
		double rad = ( 360 - this.getDirection()) * Math.PI / 180;
		double newX = this.getX() + Math.cos(rad) * this.getSpeed();
		double newY = this.getY() + Math.sin(rad) * this.getSpeed();
		this.setLocation(newX, newY);
	}
	*/
	

}
