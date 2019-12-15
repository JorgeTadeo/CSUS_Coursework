package com.mycompany.a1.gameobjects.moveableobject;

import java.util.Random;
import com.mycompany.a1.gameobjects.GameObject;

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
		double rad = ( 90 - this.getDirection()) * Math.PI / 180;
		double newX = this.getX() + Math.cos(rad) * this.getSpeed();
		double newY = this.getY() + Math.sin(rad) * this.getSpeed();
		this.setLocation(newX, newY);
	}
}
