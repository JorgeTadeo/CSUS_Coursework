package com.mycompany.a1.gameobjects.fixedobject;

import com.mycompany.a1.gameobjects.GameObject;

public abstract class FixedObject extends GameObject {

	static protected int id = 100;
	
	public FixedObject(int objectColor) {
		super(objectColor);
	}
	
	public void setLocation(int x , int y) {
		System.out.println("Space Station location is already set.");
	}




}
