package com.mycompany.a2.game;

import java.util.Observable;

import com.mycompany.a2.gameobjects.IIterator;

/*
 * a Proxy GameWorld whose job is to forward request to real gameworld if request is valid
 */
public class GameWorldProxy extends Observable implements IGameWorld {

	
	private GameWorld gw;
	
	public GameWorldProxy(GameWorld gw) { this.gw = gw; }
	

	
	@Override
	public void addAsteroid() {
		gw.addAsteroid();
	}

	@Override
	public int getPlayerScore() {
		return gw.getPlayerScore();
	}

	@Override
	public void printMap() {
		 gw.printMap();
	}

	@Override
	public void addNonPlayerShip() {
		gw.addNonPlayerShip();
	}

	@Override
	public void addSpaceStation() {
		gw.addSpaceStation();
	}

	@Override
	public void tick() {
		gw.tick();
	}

	@Override
	public void addPlayerShip() {
		gw.addPlayerShip();
	}

	@Override
	public void addPlayerMissile() {
		gw.addPlayerMissile();
	}

	@Override
	public void addNonPlayerMissile() {
		gw.addNonPlayerMissile();
	}

	@Override
	public void printDisplay() {
		gw.printDisplay();
	}

	@Override
	public void playerShipReload() {
		gw.playerShipReload();
	}

	@Override
	public void killAsteroidByMissile() {
		gw.killAsteroidByMissile();
	}

	@Override
	public void killNonPlayerShipByMissile() {
		gw.killNonPlayerShipByMissile();
	}

	@Override
	public void killPlayerShipByMissile() {
		gw.killPlayerShipByMissile();
	}

	@Override
	public void killPlayerShipByAsteroid() {
		gw.killPlayerShipByAsteroid();
	}

	@Override
	public void killPlayerShipByNPS() {

		gw.killPlayerShipByNPS();
	}

	@Override
	public void asteroidCollision() {
		gw.asteroidCollision();
	}

	@Override
	public void killNonPlayerShipByAsteroid() {
		gw.killNonPlayerShipByAsteroid();
	}

	@Override
	public void increasePlayerShipSpeed() {
		gw.increasePlayerShipSpeed();
	}

	@Override
	public void decreasePlayerShipSpeed() {
		gw.decreasePlayerShipSpeed();
	}

	@Override
	public void turnPSLeft() {
		gw.turnPSLeft();
	}

	@Override
	public void turnPSRight() {
		gw.turnPSRight();
	}

	@Override
	public void jumpToHyperSpace() {
		gw.jumpToHyperSpace();
	}

	@Override
	public void turnMissileLauncherLeft() {
		gw.turnMissileLauncherLeft();
	}

	@Override
	public void turnMissileLauncherRight() {
		gw.turnMissileLauncherRight();
	}

	@Override
	public int getRealTime() {
		return gw.getRealTime();
	}

	@Override
	public int getPSMissileCount() {
		return gw.getPSMissileCount();
	}

	@Override
	public void setSound(boolean b) {
		gw.setSound(b);	
	}
	public IIterator getGameObjectIterator() {
		return gw.getGameObjectIterator();
	}

	@Override
	public int getLife() {
		return gw.getLife();
	}
	
	@Override
	public boolean getSound() {
		return gw.getSound();
	}

	
	
	
	
	
}//END GameWorldProxy
