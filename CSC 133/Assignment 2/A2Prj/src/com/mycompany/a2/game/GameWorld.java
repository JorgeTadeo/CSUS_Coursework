package com.mycompany.a2.game;

import java.util.Observable;

import com.mycompany.a2.gameobjects.moveableobject.Asteroids;
import com.mycompany.a2.gameobjects.moveableobject.NonPlayerShip;
import com.mycompany.a2.gameobjects.fixedobject.FixedObject;
import com.mycompany.a2.gameobjects.fixedobject.SpaceStation;
import com.mycompany.a2.gameobjects.moveableobject.Missiles;
import com.mycompany.a2.gameobjects.moveableobject.MoveableObject;
import com.mycompany.a2.gameobjects.moveableobject.PlayerShip;
import com.mycompany.a2.gameobjects.moveableobject.Ship;
import com.mycompany.a2.sound.BGSound;
import com.mycompany.a2.sound.Sound;
import com.codename1.ui.Dialog;
import com.mycompany.a2.game.GameWorld;
import com.mycompany.a2.gameobjects.GameObject;
import com.mycompany.a2.gameobjects.ICollider;
import com.mycompany.a2.gameobjects.IIterator;
import com.mycompany.a2.gameobjects.SpaceCollection;

/*
 * MODEL 
 */
public class GameWorld extends Observable implements IGameWorld{

	/*
	 * Fields 
	 */

	
	static private int gameWorldHeight = 1024;
	static private int gameWorldWidth = 768;
	private int playerScore;
	private int	realTime = 0;
	private int gameTick = 0;
	private SpaceCollection gameObjects;
	private int asteroidCount;
	private int psCount;
	private int npsCount;
	private int stationCount;
	private boolean soundOn;
	private int life;
	private boolean GameOver;
	
	private Sound createMissileSound,removeMissileSound,rotateLauncherSound,gameOverSound;
	
	private BGSound bgm;
	/**
	 * Constructor
	 * 
	 * NOTE : Singleton GameWorld to enforce only instance of gameworld can ever be created.
	 */
	private volatile static GameWorld gw;
	GameWorld() {};
	public static GameWorld getInstance() {
		if(gw == null) {
			synchronized (GameWorld.class) {
				if(gw == null)
					gw = new GameWorld();
			}
		}
		return gw;
	}

	/**
	 * Description :Initialize Game , called by Game class. 
	 */
	public void init() {
		gameObjects = new SpaceCollection();
		playerScore = 0;
		gameTick = 0;
		realTime = 0;
		asteroidCount = 0;
		psCount = 0;
		npsCount = 0;
		stationCount = 0;
		soundOn = false;
		life = 3;
		GameOver = false;
		
		
		createMissileSound = new Sound("missileLaunch.wav");
		removeMissileSound = new Sound("explosion.wav");
		rotateLauncherSound = new Sound("launcherRotate.wav");
		gameOverSound = new Sound("gameover.wav");
		bgm = new BGSound("bgm.wav");
		
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	
	/*******************************
	 * Observer helper functions : *
	 *******************************/
	
	/**
	 * Description : Returns player score. <br>
	 * @return player score.
	 */
	public int getPlayerScore() {
		return playerScore;	
	}
	
	/**
	 * Description :Returns game timer. <br>
	 * @return timer.
	 */
	public int getRealTime() {
		return realTime;
	}
	
	/**
	 * Description : Returns remain missile count of playership ,  0 if playership does not exist. <br>
	 * @return playership missile count. 
	 */
	public int getPSMissileCount() {
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				return ((PlayerShip)obj).getMissileCount();
			}
		}
		//System.out.println("There exists no playership , returned 0");
		return 0;
	}
	
	/**
	 * Description : Returns iterator that can iterate through game collection. <br>
	 * @return Iterator of game collection
	 */
	public IIterator getGameObjectIterator() {
		return gameObjects.getIterator();
	}
	
	
	/**
	 * 
	 */
	static public int getGameWidth() {
		return gameWorldWidth;
	}
	
	/**
	 * 
	 */
	static public int getGameHeight() {
		return gameWorldHeight;
	}
	
	
	static public void setGameWidth(int w) { gameWorldWidth = w; }
	static public void setGameHeight(int h) { gameWorldHeight = h; }
	
	
	/********************
	 * IN GAME METHODS  *
	 ********************/
	
	
	/**
	 *  Description : Game tick. <br>
	 *  PreCondition  : None. <br>
	 * 	PostCondition : game timer increments by 1 tick. All gameObject should reflect changes.
	 */
	public void tick() {
		boolean moreMissile = true;
		while(moreMissile && !GameOver) {
			moreMissile = false;
			IIterator iter = gameObjects.getIterator();
	
			while(iter.hasNext()) {
				
				int ptr = iter.getCurrIndex();
				GameObject obj = iter.getNext();
				
				//If is a missile with 1 fuel left
				if(obj instanceof Missiles && ((Missiles) obj).getFuel() == 1 ) {
					iter.remove(ptr);
					if(soundOn) removeMissileSound.play();
					System.out.println("Missile out of fuel and is removed");
					moreMissile = true;
				//If is a missile with more than 1 fuel left
				}else if(obj instanceof Missiles && ((Missiles) obj).getFuel() != 1) {
						((Missiles) obj).move();
						if(gameTick + 1== 50) {
							((Missiles) obj).decrementFuel();
							//System.out.println("Fuel decreased by 1");
						}
				//If is a missile launcher 
				}else if(obj instanceof PlayerShip) {
					((PlayerShip) obj).move();
					((PlayerShip) obj).getMl().move();
				}else if(obj instanceof NonPlayerShip) {
					((NonPlayerShip) obj).move();
					((NonPlayerShip) obj).getMl().move();
				//If is a moveable object
				}else if(!(obj instanceof FixedObject)) {
					//System.out.println("ticking moveable");
					((MoveableObject) obj).move();
				//Not a moveable object.
				}else {
					if(obj instanceof SpaceStation && gameTick+1  == 50) { ((SpaceStation) obj).updateStatus(); }
				}
			}//END WHILE 
		}
		
		checkCollision();
		
		gameTick++;
		if(gameTick == 50) {
			gameTick = 0;
			realTime++;
		}
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	//	System.out.println("Ticked :" + timer);
	}
	
	private void checkCollision() {
		//Time to re-evaluate the collection for collisions
		
		IIterator iter = gameObjects.getIterator();
		while (iter.hasNext())
		{
			GameObject obj = iter.getNext();
			if (obj instanceof ICollider)
			{
				ICollider target = (ICollider) obj;
				
				IIterator iter2 = gameObjects.getIterator();
				while (iter2.hasNext())
				{
					GameObject otherObj = iter2.getNext();
					if (otherObj instanceof ICollider && !(obj.equals(otherObj)))
					{
						ICollider otherTarget = (ICollider) otherObj;
						
						if (target.collidesWith(otherTarget))
						{
							target.handleCollision(otherTarget);
						}
					}
				}
			}
		}
		
		killMarkedTargets();
	}
	
	void killMarkedTargets() {
		IIterator iter = gameObjects.getIterator();
				
				while(iter.hasNext())
				{
					int currIndex = iter.getCurrIndex();
					GameObject obj = iter.getNext();
					if (obj instanceof ICollider && ((ICollider)obj).getCollisionFlag())
					{
						iter.remove(currIndex);
					}
				}
	}
	
	/**
	 *  Description: Adds asteroid to game. <br> 
	 * 	PreCondition : life is not 0 / GameOver is not over <br>
	 * 	PostCondition : Asteroid is added to gameObjects and all observers are notified
	 */
	public void addAsteroid() {
		
		if(GameOver) {
			System.out.println("Unable to add Asteroid , GameOver already...");
			return;
		}
		
		gameObjects.add(new Asteroids());
		this.asteroidCount++;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));	
	}
	
	
	
	/**
	 * Description: Adds PlayerShip to game. <br>
	 * 	PreCondition : life is not 0 / Game is not over. <br>
	 * 	PostCondition : Playership added to gameObjects and notify observers
	 */
	public void addPlayerShip() {
		
		if(GameOver) {
			System.out.println("Unable to add PlayerShip , GameOver already...");
			return;
		}
		
		if(PlayerShip.isDead()) {
			gameObjects.add(PlayerShip.getInstance(psCount));
			this.psCount = 1;
			this.setChanged();
			this.notifyObservers(new GameWorldProxy(this));
			return;
		}else {
			System.out.println("PlayerShip Already Exists...");
		}
	}
	
	
	/**
	 *  Description: Adds Non-PlayerShip to game. <br>
	 * 	PreCondition : life is not 0 / Game is not over. <br>
	 * 	PostCondition : Non-Playership added to gameObjects and notify observers
	 */
	public void addNonPlayerShip() {
		
		if(GameOver) {
			System.out.println("Unable to add Non-PlayerShip , GameOver already...");
			return;
		}
		
		gameObjects.add(new NonPlayerShip());
		this.npsCount++;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	
	/**
	 *  Description: Adds Blinking Space Station to game. <br>
	 * 	PreCondition : life is not 0 / Game is not over <br>
	 * 	PostCondition : Blinking Space Station added to gameObjects and notify observers
	 */
	public void addSpaceStation() {
		
		if(GameOver) {
			System.out.println("Unable to add Blinking Space Station , GameOver already...");
			return;
		}	
		gameObjects.add(new SpaceStation());
		this.stationCount++;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	
	/**
	 *  Description : Adds Player Missile to game. <br>
	 * 	PreCondition : life is not 0 / Game is not over && PlayerShip Exists && PlayerShip ML Exists && enough ammo. <br>
	 * 	PostCondition : PlayerShip Missile added to gameObjects and notify observers
	 */
	public void addPlayerMissile() {	
		if(GameOver) {
			System.out.println("Unable to fire playership missile , GameOver already...");
			return;
		}	
		if(psCount != 1) {
			System.out.println("Unable to fire missile , Playership does not exist.");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			
			GameObject obj = iter.getNext();
			//If obj is a Playership with MissileLauncher and ammo  then fire
			if(obj instanceof PlayerShip ) {
				if(((PlayerShip) obj).getMl() != null){
					if(((PlayerShip) obj).getMissileCount() > 0) {
						gameObjects.add(new Missiles((PlayerShip) obj));
						if(soundOn) createMissileSound.play();
						System.out.println("Player ship fired missile");
						System.out.println(((PlayerShip) obj).getMissileCount() + " missiles left");
						this.setChanged();
						this.notifyObservers(new GameWorldProxy(this));
					}else {
						System.out.println("Player ship ran out of missile");
					}
				}else {
					System.out.println("Unable to fire missile , Playership MissileLauncher does not exist.");
				}
				return;
			}
		}
		//This line should never run 
		System.out.println("psCount == 1 , but No Playership is found in GameObjects");
		return;
	}
	
	
	/**
	 *  Description : Adds Non-PlayerShip Missile to game. <br>
	 * 	PreCondition : life is not 0 / Game is not over && NPS Exists && NPS's ML Exists && enough ammo. <br>
	 * 	PostCondition : NPS Missile added to gameObjects and notify observers
	 */
	public void addNonPlayerMissile() {
		if(GameOver) {
			System.out.println("Unable to fire non-playership missile , GameOver already...");
			return;
		}	
		if(npsCount < 1) {
			System.out.println("Unable to fire missile , Non-Playership does not exist.");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			
			GameObject obj = iter.getNext();
			
			if(obj instanceof NonPlayerShip ) {
				if(((NonPlayerShip)obj).getMl() != null) {
					if(((NonPlayerShip) obj).getMissileCount() > 0) {
						gameObjects.add(new Missiles((NonPlayerShip) obj));
						if(soundOn) createMissileSound.play();
						System.out.println("Non-Player ship fired missile");
						System.out.println(((NonPlayerShip) obj).getMissileCount() + " missiles left");
						this.setChanged();
						this.notifyObservers(new GameWorldProxy(this));
					}else {
						System.out.println("Non-Player ship ran out of missile");
					}
				}else {
					System.out.println("Unable to fire missile , Non-Playership MissileLauncher does not exist.");
				}
				return;
			}
		}
		//This line should never run 
		System.out.println("npsCount > 1 , but NPS is not found in GameObjects");
		return;
	}

	
	/**
	 * Description: Prints Player Info. <br> 
	 *  PreCondition : life is not 0/Game is not over and there exists a playership.
	 */
	public void printDisplay() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				System.out.println( "Current Score=" + this.playerScore +
						" Number of missile in ship=" + ((Ship) obj).getMissileCount() + 
						" Current elapased time=" + this.realTime + 
						" Number of lives=" + ((PlayerShip) obj).getLife()
				);
				return;
			}
		}
		System.out.println("There exists no playership to print info");
	}
	
	
	/**
	 * Description : Prints each game object's info. <br>
	 * PreCondition : life is not 0 / Game is not over.
	 */
	public void printMap() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		
		System.out.println("\nMap :");
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			System.out.println(((GameObject)iter.getNext()).toString());
		}
		System.out.println("---------------------------");
	}
	
	/**
	 * Description :Turn missile launcher left by 1 degree. <br>
	 * PreCondition : There exists a PlayerShip and Playership's missile launcher
	 */
	public void turnMissileLauncherLeft() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				if(((PlayerShip) obj).getMl() != null) {
					PlayerShip ps = (PlayerShip) obj;
					ps.getMl().turnLeft();
					System.out.println("MissileLauncher rotated left by 5 degree");
					if(soundOn) rotateLauncherSound.play();
					System.out.println(ps.toString());
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
					return;
				}else {
					System.out.println("Error turning ML left , Playership's missile launcher is missed.");
					return;
				}
			}
		}
		System.out.println("Error turning ml left , there exists no playership");
	}
	
	
	
	/**
	 * Description : Turn missile launcher right by 1 degree. <br>
	 * PreCondition : There exists a PlayerShip and Playership's missile launcher
	 */
	public void turnMissileLauncherRight() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				if(((PlayerShip) obj).getMl() != null) {
					PlayerShip ps = (PlayerShip) obj;
					ps.getMl().turnRight();
					System.out.println("MissileLauncher rotated right by 5 degree");
					if(soundOn) rotateLauncherSound.play();
					System.out.println(ps.toString());
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
				    return;
				}else {
					System.out.println("Error turning ML right , Playership's missile launcher is missed.");
					return;					
				}
			}
		}
		System.out.println("Error turning ml right , there exists no playership");		
	}
	
	
	/**
	 * Description : Reloads playership by blinking station </br> 
	 * PreCondition : There exists a PlayerShip and Blinking station exists
	 * PostCondition : playership's missile count is restored to max_missile_count
	 */
	public void playerShipReload() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip && this.stationCount > 0) {
				PlayerShip ps = (PlayerShip) obj;
				if(ps.getMissileCount() == ps.getMaxMissileCount()) {
					System.out.println("Playership already have max missile");
				}else {
					ps.reload();
					System.out.println("PlayerShip's missile has been reloaded");
					System.out.println(ps.toString());
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
				}
				return;
			}
		}
		System.out.println("No Playership to reload or Blinking Station is not yet created");
	}
	
	
	/**
	 * Description : Asteroid is destroyed by playership missile </br>
	 * PreCondition : There exists a playership missile and an asteroid </br>
	 * PostCondition : 1 playership missile and 1 asteroid object is removed from game collection
	 */
	public void killAsteroidByMissile() { 
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		if(asteroidCount >= 1 && psCount == 1) {
			IIterator iter = gameObjects.getIterator();
			while(iter.hasNext()) {
				int missileIndex = iter.getCurrIndex();
				GameObject obj = iter.getNext();
				if(obj instanceof Missiles) {
					iter.remove(missileIndex); //Remove PS Missile
					this.playerScore += 10;
					IIterator iter2 = gameObjects.getIterator();
					while(iter2.hasNext()) {
						int asteroidIndex = iter2.getCurrIndex();
						GameObject obj2 = iter2.getNext();
						if(obj2 instanceof Asteroids) {
							iter2.remove(asteroidIndex); //Remove Asteroid
							asteroidCount--;
							System.out.println("Asteroid is destroyed by Playership Missile");
							this.setChanged();
							this.notifyObservers(new GameWorldProxy(this));
							return;
						}
					}
				}
			}
			System.out.println("Error : Asteroid or Playership or PlayerMissile does not exists");
		}else {
			System.out.println("Error : Asteroid or Playership or PlayerMissile does not exists");
		}
	}
	
	/**
	 * Description : NPS hit by PS Missile. Both NPS and PS Missile is removed from game collection
	 * <br>
	 * PreCondition : NPS Exists, PS Missile Exists.
	 */
	public void killNonPlayerShipByMissile() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		if(npsCount >= 1) {
			IIterator iter = gameObjects.getIterator();
			while(iter.hasNext()) {
				int missileIndex = iter.getCurrIndex();
				GameObject obj = iter.getNext();
	            if(obj instanceof Missiles) {
	                if(((Missiles) obj).getOwner() instanceof PlayerShip) {
	                	this.playerScore += 10;
	                  	iter.remove(missileIndex);
	            		IIterator iter2 = gameObjects.getIterator();
	            		while(iter2.hasNext()) {
	            			int npsIndex = iter2.getCurrIndex();
	            			GameObject obj2 = iter2.getNext();
	            			if(obj2 instanceof NonPlayerShip) {
	                    		iter2.remove(npsIndex);
	                    		npsCount--;
	                    		System.out.println("NonPlayerShip was destroyed by PlayerShip Missile");
	                    		this.setChanged();
	                    		this.notifyObservers(new GameWorldProxy(this));
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
    	

	/**
	 * Description : PS hit by NPS Missile. Both PS and NPS Missile is removed from game collection
	 * <br>
	 * PreCondition : NPS Missile Exists , PS Exists.
	 */
	public void killPlayerShipByMissile() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			int missileIndex = iter.getCurrIndex();
			GameObject obj = iter.getNext();
			if(obj instanceof Missiles &&  psCount == 1) {
                if(((Missiles) obj).getOwner() instanceof NonPlayerShip) {
                  	iter.remove(missileIndex);
            		IIterator iter2 = gameObjects.getIterator();
            		while(iter2.hasNext()) {
            			int psIndex = iter2.getCurrIndex();
            			GameObject obj2 = iter2.getNext();
            			if(obj2 instanceof PlayerShip) {
                  			if(this.life > 0) {
                  				iter.remove(psIndex);
                  				psCount = 0;
                            	System.out.println("PlayerShip was destroyed by NPS's Missile");
                            	if(this.getLife() == 0) {
                            		GameOver();
                            	}
                        		this.setChanged();
                        		this.notifyObservers(new GameWorldProxy(this));
                        	}else {
                        		iter2.remove(psIndex);
                        		GameOver();
                        	}
                  			((PlayerShip) obj).setDead(true);
                  			return;
                  		}
            		}
                }
            }    
		}
    	System.out.println("Either PlayerShip,NonplayerShip,NonPlayerShip's missile does not exist");	
	}
	
	/**
	 * Description : PS hit by Asteroid. Both PS and Asteroid is removed from the game collection
	 * <br>
	 * PreCondition : Playership exists , Asteroid exists.
	 */
	public void killPlayerShipByAsteroid() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		if(asteroidCount >= 1 &&  psCount == 1) {
			IIterator iter = gameObjects.getIterator();
			while(iter.hasNext()) {
				int psIndex = iter.getCurrIndex();
				GameObject obj = iter.getNext();
				 if(obj instanceof PlayerShip) {
		            	if(this.life > 0) {
		            		this.life--;
		            		iter.remove(psIndex);
		            		psCount = 0;
		                	System.out.println("Playership is destroyed by Asteroid. Life decreased by 1");
		                	if(this.getLife() == 0) {
		                		GameOver();
		                	}
		            		this.setChanged();
		            		this.notifyObservers(new GameWorldProxy(this));
		            	}else {
		            		iter.setEmpty(psIndex);
		            		iter.remove(psIndex);
		            		psCount = 0;
		            		this.setChanged();
		            		this.notifyObservers(new GameWorldProxy(this));
		            		GameOver();
		            	}
		            	((PlayerShip) obj).setDead(true);
		                break;
		            }
			}
			IIterator iter2 = gameObjects.getIterator();
			while(iter2.hasNext()) {
				int asteroidIndex = iter2.getCurrIndex();
				GameObject obj2 = iter2.getNext();
	            if(obj2 instanceof Asteroids) {
	                iter2.remove(asteroidIndex);
	                asteroidCount--;
	        		this.setChanged();
	        		this.notifyObservers(new GameWorldProxy(this));
	                break;
	            }
			}
		}else {
			System.out.println("Either PlayerShip or Asteroid does not exist");
		}
	}
	
	
	/**
	 * Description : PS hit by NPS. Both PS and NPS is removed from game collection. Life decrease by 1.
	 * <br>
	 * PreCondition : PS exists , NPS exists.
	 */
	public void killPlayerShipByNPS() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		if(psCount == 1 && npsCount >= 1) {
			IIterator iter = gameObjects.getIterator();
			while(iter.hasNext()) {
				int psIndex = iter.getCurrIndex();
				GameObject obj = iter.getNext();
				if(obj instanceof PlayerShip) {
	            	if(this.life > 0) {
	            		this.life--;
	            		iter.remove(psIndex);
	            		psCount = 0;
	                	System.out.println("Playership is destroyed by NonPlayerShip. Life decreased by 1");
	                	if(this.getLife() == 0) {
	                		GameOver();
	                	}
	            		this.setChanged();
	            		this.notifyObservers(new GameWorldProxy(this));
	            	}else {
	            		iter.setEmpty(psIndex);
	            		iter.remove(psIndex);
	            		psCount = 0;
	            		this.setChanged();
	            		this.notifyObservers(new GameWorldProxy(this));
	            		GameOver();
	            	}
	            	((PlayerShip) obj).setDead(true);
	                break;
	            }
			}

			IIterator iter2 = gameObjects.getIterator();
			while(iter2.hasNext()) {
				int npsIndex = iter2.getCurrIndex();
				GameObject obj2 = iter2.getNext();
	            if(obj2 instanceof NonPlayerShip) {
	                iter2.remove(npsIndex);
	                npsCount--;
	        		this.setChanged();
	        		this.notifyObservers(new GameWorldProxy(this));
	                break;
	            }
			}
		}else {
			System.out.println("Either PlayerShip or NonPlayerShip does not exist");
		}
	}
	
	/**
	 * Description : Asteroid hit by another Asteroid. Both Asteroid is removed from game collection.
	 * <br>
	 * PreCondition : 2 Asteroid Exists.
	 */
	public void asteroidCollision() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		if(asteroidCount >= 2) {

			IIterator iter = gameObjects.getIterator();
			while(iter.hasNext()) {
				int index = iter.getCurrIndex();
				GameObject obj = iter.getNext();
				if(obj instanceof Asteroids) {
					iter.remove(index);
					asteroidCount--;
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
					break;
				}
			}
			IIterator iter2 = gameObjects.getIterator();
			while(iter2.hasNext()) {
				int index2 = iter2.getCurrIndex();
				GameObject obj2 = iter2.getNext();
				if(obj2 instanceof Asteroids) {
					iter2.remove(index2);
					asteroidCount--;
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
					break;
				}
			}
			System.out.println("2 Asteroids Collided and both were destroyed.");
			return;
		}
		
		System.out.println("Error : NO 2 Asteroid to kill each other");	
	}
	
	/**
	 * Description : NPS hit by Asteroid. Both NPS and Asteroid is removed from game collection.
	 * <br>
	 * PreCondition : NPS Exists, Asteroid Exists.
	 */
	public void killNonPlayerShipByAsteroid() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}		
		if(asteroidCount >=1 && npsCount >= 1) {

			IIterator iter = gameObjects.getIterator();
			while(iter.hasNext()) {
				int index = iter.getCurrIndex();
				GameObject obj = iter.getNext();
				if(obj instanceof Asteroids) {
					iter.remove(index);
					asteroidCount--;
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
					break;
				}
			}
			IIterator iter2 = gameObjects.getIterator();
			while(iter2.hasNext()) {
				int index2 = iter2.getCurrIndex();
				GameObject obj2 = iter2.getNext();
				if(obj2 instanceof NonPlayerShip) {
					iter2.remove(index2);
					npsCount--;
					this.setChanged();
					this.notifyObservers(new GameWorldProxy(this));
					break;
				}
			}
			System.out.println("NonPlayer Ship was destroyed by an Asteroid");
		}else {
			System.out.println("Error : NO asteroid or NonPlayerShip to kill each other");
		}
	}
	
	
	/**
	 * Description : Increment PlayerShip Speed. <br>
	 * PreCondition : PlayerShip Exists.
	 * 
	 */
	public void increasePlayerShipSpeed() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				((PlayerShip) obj).increaseSpeed();
				System.out.println("PlayerShip speed increased to " + ((MoveableObject) obj).getSpeed());
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		System.out.println("There exists no playership to increase speed");
	}
	
	/**
	 * Description : Decrement PlayeShip Speed. <br>
	 * PreCondition : Playership Exists.
	 */
	public void decreasePlayerShipSpeed() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				((PlayerShip) obj).decreaseSpeed();
				System.out.println("PlayerShip speed decreased to " + ((MoveableObject) obj).getSpeed());
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		System.out.println("There exists no playership to decrease speed");	
	}
	
	/**
	 * Description : Turn Playership left by 1 degree. <br>
	 * PreCondition : PlayerShip Exists.
	 */
	public void turnPSLeft() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				((PlayerShip) obj).turnLeft();
				System.out.println("Playership turned left by 1");
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		System.out.println("There exists no playership to turn left");	
	}
	
	
	/**
	 * Description : Turn Playership right by 1 degree. <br>
	 * PreCondition : PlayerShip Exists.
	 */
	public void turnPSRight() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				((PlayerShip) obj).turnRight();
				System.out.println("Playership turned right by 1");
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				return;
			}
		}
		System.out.println("There exists no playership to turn right");		
	}
	
	/**
	 * Description : Move Playership to center of the map. <br>
	 * PreCondition : PlayerShip Exists.
	 */
	public void jumpToHyperSpace() {
		if(GameOver) {
			System.out.println("Unable to do anything , GameOver already...");
			return;
		}
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext()) {
			GameObject obj = iter.getNext();
			if(obj instanceof PlayerShip) {
				((PlayerShip) obj).resetPosition();;
				this.setChanged();
				this.notifyObservers(new GameWorldProxy(this));
				System.out.println("PlayerShip jumped through hyperspace");
				return;
			}
		}
		System.out.println("There exists no playership to jump through hyperspace");		
	}
	
	/**
	 *	@Override 
	 *  
	 *  Description : Sets sound flag and notify any observer of this change.	
	 *	@param b : sound flag , true to turn on , false to turn off.
	 */
	public void setSound(boolean b) {
		this.soundOn = b;
		if(b){
			bgm.play();
		}else {
			bgm.pause();
		}
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	/**
	 * 	@Override
	 * 
	 *  Description : Retrieve sound flag 
	 */

	public boolean getSound() {
		return this.soundOn;
	}
	
	/**
	 * 	@Override
	 * 
	 *  Description : Retrieve current life count of player.
	 *  @return : current life count.
	 */
	public int getLife() {
		return this.life;
	}
	
	public void GameOver() {
		GameOver = true;
		if(soundOn) {
			bgm.pause();
			gameOverSound.play();
		}
		
		String info = "Thank you for playing but the game is over.\n" + 
						"Please restart the program\n" +
						"Final Score :" + playerScore;
		
		if(Dialog.show("GameOver", info, "OK" , null)) {
			System.exit(0);;
		}
		
				
		
	}

	
	
}