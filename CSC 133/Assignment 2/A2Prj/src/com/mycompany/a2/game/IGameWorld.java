package com.mycompany.a2.game;

import com.mycompany.a2.gameobjects.IIterator;

public interface IGameWorld {


	int getPlayerScore();
	int getRealTime();
	int getPSMissileCount();
	int getLife();
	void printMap();
	void tick();
	void addAsteroid();
	void addPlayerShip();
	void addNonPlayerShip();
	void addSpaceStation();
	void addPlayerMissile();
	void addNonPlayerMissile();
	void printDisplay();
	void turnMissileLauncherLeft();
	void turnMissileLauncherRight();
	void playerShipReload();
	void killAsteroidByMissile();
	void killNonPlayerShipByMissile();
	void killPlayerShipByMissile();
	void killPlayerShipByAsteroid();
	void killPlayerShipByNPS();
	void asteroidCollision();
	void killNonPlayerShipByAsteroid();
	void increasePlayerShipSpeed();
	void decreasePlayerShipSpeed();
	void turnPSLeft();
	void turnPSRight();
	void jumpToHyperSpace();
	void setSound(boolean b);
	boolean getSound();
	public IIterator getGameObjectIterator();

	
}
