package com.mycompany.a2.gameobjects.moveableobject;

import java.util.Random;

import com.mycompany.a2.game.GameWorld;
import com.mycompany.a2.game.views.MapView;
import com.mycompany.a2.gameobjects.GameObject;

public abstract class MoveableObject extends GameObject implements IMoveable {

	private int speed;
	private int direction;
	
	//Constructors
	public MoveableObject(int objectColor) {
		super(objectColor);
		this.speed = new Random().nextInt(21);
		this.direction = new Random().nextInt(360);
	}
	public MoveableObject(int objectColor , int speed, int direction , double x , double y) {
		super(objectColor, x , y);
		this.speed = speed;
		this.direction = direction;
	}

	
	
	//Getters and Setters
	public int getSpeed() {
		return this.speed;
	}
	public void setSpeed(int x) {
		this.speed = x;
	}
	public int getDirection() {
		return this.direction;
	}
	public void setDirection(int x) {
		this.direction = x;
	}
	

	//Move() 
	public void move() {
		

		
		double newX = this.getX() + Math.cos( Math.toRadians(90-direction) ) * speed;
 		double newY = this.getY() - Math.sin( Math.toRadians(90-direction) ) * speed;
		
		/*//Bounce off right wall 
		if(newX  >= MapView.getMapViewOrigin().getX() + GameWorld.getGameWidth()
				&& (direction != 0 || direction != 180)) {
				setDirection(360-direction);
		}
		//Bounce off left wall 
		if(newX <= MapView.getMapViewOrigin().getX()
				&& (direction != 0 || direction != 180)) {
			setDirection(360-direction);
		}
		//Bounce off bottom wall
		if(newY+50 >= MapView.getMapViewOrigin().getY() + GameWorld.getGameHeight() ) {
			setDirection((360-direction+180)%360);
		}		
		if(newY <= MapView.getMapViewOrigin().getY() ) {
			setDirection((360-direction+180)%360);
		}*/
 		
 		int offset = 0;

 		if(this instanceof Missiles) {
 			offset = 50;
 		}
 		else if(this instanceof Asteroids) {
 			Asteroids a = (Asteroids) this;
 			offset = a.getSize();
 		}else if(this instanceof PlayerShip) {
 			offset = 50;
 		}else if(this instanceof NonPlayerShip) {
 			offset = 50;
 		}
 		
 		
 		int orginX = (int)MapView.getMapViewOrigin().getX();
 		int orginY = (int)MapView.getMapViewOrigin().getY();
 		
		//Bounce off right wall 
 				if(orginX + newX + offset >=   MapView.getMapViewWidth() + orginX
 						&& (direction != 0 || direction != 180)) {
 						setDirection(360-direction);
 				}
 				//Bounce off left wall 
 				if(orginX + newX  <= orginX
 						&& (direction != 0 || direction != 180)) {
 					setDirection(360-direction);
 				}
 				//Bounce off bottom wall
 				if(orginY + newY + offset >= orginY + GameWorld.getGameHeight() ) {
 					setDirection((360-direction+180)%360);
 				}		
 				if(orginY + newY  <= orginY ) {
 					setDirection((360-direction+180)%360);
 				}
 		

		this.setLocation(newX, newY);
	}
}
