package com.mycompany.a1.gameobjects.moveableobject;

public class NonSteerableMissileLauncher extends MoveableObject implements IMoveable{

	private NonPlayerShip owner;
	
	public NonSteerableMissileLauncher(NonPlayerShip owner , int objectColor, int speed, int direction, double x, double y) {
		super(objectColor, speed, direction, x, y);
		this.owner = owner;
	}

	@Override
	public void move() {
		this.setLocation(owner.getX(), owner.getY());	
	}



	

}
