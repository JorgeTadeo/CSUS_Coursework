package com.mycompany.a1.game;

import java.util.ArrayList;
import java.util.Random;
import com.mycompany.a1.gameobjects.GameObject;
import com.mycompany.a1.gameobjects.fixedobject.SpaceStation;
import com.mycompany.a1.gameobjects.moveableobject.Asteroids;
import com.mycompany.a1.gameobjects.moveableobject.Missiles;
import com.mycompany.a1.gameobjects.moveableobject.MoveableObject;
import com.mycompany.a1.gameobjects.moveableobject.NonPlayerShip;
import com.mycompany.a1.gameobjects.moveableobject.PlayerShip;
import com.mycompany.a1.gameobjects.moveableobject.Ship;


@SuppressWarnings("unused")
public class GameWorld {

	/*
	 * GameWorld Fields 
	 */
	final private int gameWorldWidth = 1024;
	final private int gameWorldHeight = 768;
	private int playerScore;
	private int timer = 0;
	private ArrayList<GameObject> gameObjects; //Collection of game objects
	private int asteroidCount;
	private int psCount;
	private int npsCount;
	private int stationCount;
	
	//Singleton GameWorld to enforce only instance of gameworld can ever be created.
	private volatile static GameWorld gw;
	private GameWorld() {};
	public static GameWorld getInstance() {
		if(gw == null) {
			synchronized (GameWorld.class) {
				if(gw == null)
					gw = new GameWorld();
			}
		}
		return gw;
	}
	
	/*
	 * Initialize Game
	 */
	public void init() {
		this.playerScore = 0;
		this.timer = 0;
		gameObjects = new ArrayList<GameObject>();
		this.asteroidCount = 0;
		this.psCount = 0;
		this.npsCount = 0;
		this.stationCount = 0;
	}
	
	
	
	/*
	 * GAME METHODS :
	 */
	
	
	/*
	 * Game tick
	 */
	public void tick() {
		timer++;
		for(int i = 0 ; i < gameObjects.size(); i++) {

			if(gameObjects.get(i) instanceof Missiles) {
				if(((Missiles) gameObjects.get(i)).getFuel() == 1) {
					gameObjects.remove(i);
					System.out.println("Missile out of fuel and is removed");
					return;
				}else {
					((Missiles) gameObjects.get(i)).decrementFuel();
				}
			}
			
			((MoveableObject) gameObjects.get(i)).move();
		}
	}
	
	
	public void addAsteroid() {
		gameObjects.add(new Asteroids());
		this.asteroidCount++;
	}
	public void addPlayerShip() {
			PlayerShip ps = PlayerShip.getInstance();
			if(psCount == 0) {			
				gameObjects.add(ps);
				this.psCount = 1;
			}
	}
	public void addNonPlayerShip() {
		gameObjects.add(new NonPlayerShip());
		this.npsCount++;
	}
	public void addSpaceStation() {
		gameObjects.add(new SpaceStation());
		this.stationCount++;
	}
	public void addPlayerMissile() {	
	
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip ) {
				if(((Ship) gameObjects.get(i)).getMissileCount() > 0 ) {
					gameObjects.add(new Missiles((PlayerShip) gameObjects.get(i)));
					//Print Fired Missile
					System.out.println("Player ship fired missile");
					System.out.println(((Ship)gameObjects.get(i)).getMissileCount() + " missiles left");
				}else {
					//Print Ran out of missile 
					System.out.println("Player ship ran out of missile");
				}
				return;
			}
		}
		//No PlayerShip
		System.out.println("There exists no playership to fire missile");
		return;
	
	}
	public void addNonPlayerMissile() {
		
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof NonPlayerShip ) {
				if(((Ship) gameObjects.get(i)).getMissileCount() > 0) {
					gameObjects.add(new Missiles((NonPlayerShip) gameObjects.get(i)));
					System.out.println("Non-Player ship fired missile");
					System.out.println(((Ship)gameObjects.get(i)).getMissileCount() + " missiles left");
				}else {
					System.out.println("Non-Player ship ran out of missile");
				}
				return;
			}
		}
		//No PlayerShip
		System.out.println("There exists no non-playership to fire missile");
		return;
	}

	public void printDisplay() {
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				System.out.println( "Current Score=" + this.playerScore +
						" Number of missile in ship=" + ((Ship) gameObjects.get(i)).getMissileCount() + 
						" Current elapased time=" + this.timer + 
						" Number of lives=" + ((PlayerShip) gameObjects.get(i)).getLife()
				);
			}
		}

	}
	
	public void printMap() {
		for(int i = 0 ; i< gameObjects.size() ; i++) {
			System.out.println(gameObjects.get(i).toString());
		}
	}
	
	public void rotatePlayerMl() {
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				PlayerShip ps = (PlayerShip) gameObjects.get(i);
				ps.getMl().rotate();
				System.out.println("PlayerShip rotated by 5 degree");
				System.out.println(ps.toString());
			}
		}
	}
	public void playerShipReload() {
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				PlayerShip ps = (PlayerShip) gameObjects.get(i);
				if(ps.getMissileCount() == ps.getMaxMissileCount()) return;
				ps.reload();
				System.out.println("PlayerShip's missile has been reloaded");
				System.out.println(ps.toString());
				return;
			}
		}
		System.out.println("No Playership to reload");
	}
	public void killAsteroid() { 
		if(asteroidCount >= 1 && psCount == 1) {
			for(int i = 0 ; i < gameObjects.size(); i++) {
				if(gameObjects.get(i) instanceof Missiles) {
					((Missiles) gameObjects.get(i)).getOwner().decrementMissile();
					gameObjects.remove(i);
					this.playerScore += 10;
					for(int j = 0 ; j < gameObjects.size(); j++) {
						if(gameObjects.get(j) instanceof Asteroids) {
							gameObjects.remove(j);
							asteroidCount--;
							System.out.println("Asteroid is destroyed by Playership Missile");
							return;
						}
					}
				}
			}
			System.out.println("Error : Asteroid or Playership or PlayerMissile does not exists");
			return;
		}else {
			System.out.println("Error : Asteroid or Playership or PlayerMissile does not exists");
		}
	}
	public void killNonPlayerShipByMissile() {

	if(npsCount >= 1) {
        for(int i = 0; i < gameObjects.size(); i++) {
            if(gameObjects.get(i) instanceof Missiles) {
                if(((Missiles) gameObjects.get(i)).getOwner() instanceof PlayerShip) {
                	//Remove missile and decrease missile count for PS 
                	((Missiles) gameObjects.get(i)).getOwner().decrementMissile();
                	this.playerScore += 10;
                  	gameObjects.remove(i);
                    for(int j = 0 ; j < gameObjects.size(); j++) {
                    	if(gameObjects.get(j) instanceof NonPlayerShip) {
                    		gameObjects.remove(j);
                    		npsCount--;
                    		System.out.println("NonPlayerShip was destroyed by PlayerShip Missile");
                    		return;
                    	}
                    }
                 }
            }  
        }
    	System.out.println("Either PlayerShip,NonplayerShip,NonPlayerShip's missile does not exist");
    	return;
	}else {
    	System.out.println("Either PlayerShip,NonplayerShip,NonPlayerShip's missile does not exist");
	}
    	
}
    	

	
	public void killPlayerShipByMissile() {

        for(int i = 0; i < gameObjects.size(); i++) {
            if(gameObjects.get(i) instanceof Missiles &&  psCount == 1) {
                if(((Missiles) gameObjects.get(i)).getOwner() instanceof NonPlayerShip) {
                	//Remove missile and decrease missile count for NPS 
                	((Missiles) gameObjects.get(i)).getOwner().decrementMissile();
                  	gameObjects.remove(i);
                  	//Update Ps status 
                  	for(int j = 0 ; j < gameObjects.size(); j++) {
                  		if(gameObjects.get(j) instanceof PlayerShip) {
                  			if(((PlayerShip) gameObjects.get(j)).getLife() > 0) {
                            	((PlayerShip) gameObjects.get(j)).respawn();
                            	System.out.println("PlayerShip was destroyed by NPS's Missile");
                            	return;
                        	}else {
                        		gameObjects.remove(j);
                        		System.out.println("Game Over");
                        		return;
                        	}
                  		}
                  	}
                }
            }    
        }
    	System.out.println("Either PlayerShip,NonplayerShip,NonPlayerShip's missile does not exist");	
	}
	public void killPlayerShipByAsteroid() {

		if(asteroidCount >= 1 &&  psCount == 1) {
			for(int i = 0; i < gameObjects.size(); i++) {
	            if(gameObjects.get(i) instanceof PlayerShip) {
	            	if(((PlayerShip) gameObjects.get(i)).getLife() > 0) {
	                	((PlayerShip) gameObjects.get(i)).respawn();
	                	System.out.println("Playership is destroyed by Asteroid. Life decreased by 1");
	            	}else {
	            		gameObjects.set(i,null);
	            		gameObjects.remove(i);
	            		psCount = 0;
	            		System.out.println("Game Over");
	            	}
	                break;
	            }
	        }
			for(int i = 0; i < gameObjects.size(); i++) {
	            if(gameObjects.get(i) instanceof Asteroids) {
	                gameObjects.remove(i);
	                asteroidCount--;
	                break;
	            }
	        }
		}else {
			System.out.println("Either PlayerShip or Asteroid does not exist");
		}
	}
	public void killPlayerShipByNPS() {

		if(psCount == 1 && npsCount >= 1) {
			for(int i = 0; i < gameObjects.size(); i++) {
	            if(gameObjects.get(i) instanceof PlayerShip) {
	            	if(((PlayerShip) gameObjects.get(i)).getLife() > 0) {
	                	((PlayerShip) gameObjects.get(i)).respawn();
	                	System.out.println("Playership is destroyed by NonPlayerShip. Life decreased by 1");
	            	}else {
	            		gameObjects.set(i,null);
	            		gameObjects.remove(i);
	            		psCount = 0;
	            		System.out.println("Game Over");
	            	}
	                break;
	            }
	        }
			for(int i = 0; i < gameObjects.size(); i++) {
	            if(gameObjects.get(i) instanceof NonPlayerShip) {
	                gameObjects.remove(i);
	                npsCount--;
	                break;
	            }
	        }	
		}else {
			System.out.println("Either PlayerShip or NonPlayerShip does not exist");
		}
	}
	public void asteroidCollision() {

		if(asteroidCount >= 2) {
			for(int i = 0 ; i < gameObjects.size(); i++) {
				if(gameObjects.get(i) instanceof Asteroids) {
					gameObjects.remove(i);
					asteroidCount--;
					break;
				}
			}
			for(int i = 0 ; i < gameObjects.size(); i++) {
				if(gameObjects.get(i) instanceof Asteroids) {
					gameObjects.remove(i);
					asteroidCount--;
					break;
				}
			}
			System.out.println("2 Asteroids Collided and both were destroyed.");
		}else {
			System.out.println("Error : NO 2 Asteroid to kill each other");
		}	
	}
	public void killNonPlayerShipByAsteroid() {
		
		if(asteroidCount >=1 && npsCount >= 1) {
			for(int i = 0 ; i < gameObjects.size(); i++) {
				if(gameObjects.get(i) instanceof Asteroids) {
					gameObjects.remove(i);
					asteroidCount--;
					break;
				}
			}
			for(int i = 0 ; i < gameObjects.size(); i++) {
				if(gameObjects.get(i) instanceof NonPlayerShip) {
					gameObjects.remove(i);
					npsCount--;
					break;
				}
			}
			System.out.println("NonPlayer Ship was destroyed by an Asteroid");
		}else {
			System.out.println("Error : NO asteroid or NonPlayerShip to kill each other");
		}
	}
	public void increasePlayerShipSpeed() {
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				((PlayerShip) gameObjects.get(i)).increaseSpeed();
				System.out.println("PlayerShip speed increased to " + ((MoveableObject) gameObjects.get(i)).getSpeed());
				return;
			}
		}
		System.out.println("There exists no playership to increase speed");
	}
	public void decreasePlayerShipSpeed() {
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				((PlayerShip) gameObjects.get(i)).decreaseSpeed();
				System.out.println("PlayerShip speed decreased to " + ((MoveableObject) gameObjects.get(i)).getSpeed());
				return;
			}
		}
		System.out.println("There exists no playership to decrease speed");	
	}
	public void turnPSLeft() {
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				((PlayerShip) gameObjects.get(i)).turnLeft();
				System.out.println("Playership turned left by 1");
				return;
			}
		}
		System.out.println("There exists no playership to turn left");	
	}
	public void turnPSRight() {
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				((PlayerShip) gameObjects.get(i)).turnRight();
				System.out.println("Playership turned right by 1");
				return;
			}
		}
		System.out.println("There exists no playership to turn right");		
	}
	public void jumpToHyperSpace() {
		for(int i = 0 ; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof PlayerShip) {
				((PlayerShip) gameObjects.get(i)).resetPosition();;
				return;
			}
		}
		System.out.println("There exists no playership to jump through hyperspace");		
	}

	
	
	
	
}//END GameWorld
