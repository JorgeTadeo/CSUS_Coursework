package com.mycompany.a2.gameobjects.moveableobject;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a2.game.GameWorld;


public class PlayerShip extends Ship implements ISteerable{

	
	final static int x = GameWorld.getGameWidth()/2;
	final static int y = GameWorld.getGameHeight()/2;
	final static int MAX_SPEED = 100;
	
	private int life;
	private static boolean dead = true;
	static private MissileLauncher ml;
	
	
	
	//Singleton PlayerShip
	private volatile static PlayerShip ps;
	private PlayerShip() {
		super(ColorUtil.GREEN,0,0,x,y,10,10);//Color,Speed,Direction,LocationX,LocationY,missileCount,MaxMissileCount
		life = 3;
		PlayerShip.ml = new MissileLauncher(ps , this.getSpeed(),0, x, y);
		setMaxMissileCount(10);
		setMissileCount(10);
		dead = false;
		setSize(50);
		//System.out.println(this.toString()); //DEBUG LINE
	}
	//TODO Update getInstance with no psCount , Use dead flag instead
	public static PlayerShip getInstance(int psCount) {
		if(ps == null) {
			synchronized (PlayerShip.class) {
				if(ps == null)
					ps = new PlayerShip();
					ml.setOwner(ps);
					dead = false;
					System.out.println("Added Playership");
			}
		}else {
			ps.setLife(3);
			dead = false;
			System.out.println("Added Playership");	
		}
		return ps;
	}
	
	public static boolean isDead() {
		return dead;
	}


	
	

	
	public void increaseSpeed() {
		int currSpeed = getSpeed();
		if(currSpeed < MAX_SPEED) {
			setSpeed(currSpeed + 5);
			ml.setSpeed(getSpeed());
		}else {
			System.out.println("Already at max speed of " + MAX_SPEED);
		}
	}
	public void decreaseSpeed() {
		int currSpeed = getSpeed();
		if(currSpeed > 0) {
			setSpeed(currSpeed - 5);
			ml.setSpeed(getSpeed());
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
	
	//NOT USED
	public void respawn() {
		this.setX(x);
		this.setY(y);
		this.setDirection(0);
		this.setSpeed(0);
		this.reload();
		life--;
	}
	
	public void setDead(boolean d) {
		PlayerShip.dead = d;
	}
	
	//private void setML()
	public MissileLauncher getMl() {
		return PlayerShip.ml;
	}

	@Override
	public void turnLeft() {
		// TODO Auto-generated method stub
		int currDirection = this.getDirection();
		if(currDirection == 0) {
			this.setDirection(359);
		}else {
			//this.setDirection(currDirection - 1);
			this.setDirection(currDirection - 30);
		}
		return;
	}

	@Override
	public void turnRight() {
	//	this.setDirection((this.getDirection() + 1) % 360);
		this.setDirection((this.getDirection() + 30) % 360);
		
	}
	
	@Override 
	public String toString() {
		return (
			"Player Ship: loc=" + Math.round(this.getX()*10.0)/10.0 + "," + Math.round(this.getY()*10.0)/10.0 + 
			" color=" + this.getColorToString() +
			" speed=" + this.getSpeed() +
			" dir=" + this.getDirection() +
			" missiles=" + this.getMissileCount() + 
			" Missile Launcher dir = " + PlayerShip.ml.getDirection()
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
	

}