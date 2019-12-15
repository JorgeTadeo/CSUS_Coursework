package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;
/**
 *Description: A class that allows for the asteroid to destroy another asteroid.
 *
 */
public class CommandKillAsteroidByAsteroid extends Command {

	/**
	 *Description: Proxy Game World.
	 *
	 */
	private IGameWorld gw;
	/**
	 *Description: Command that allows an asteroid to crash with another asteroid.
	 *@param gw : input gw 
	 */
	public CommandKillAsteroidByAsteroid(IGameWorld gw) {
		super("Asteroid*\n(Asteroid)");
		this.gw = gw;
	}
	/**
	 *Description: Action listener that waits for the crash.
	 *@param ae : input e 
	 */
	public void actionPerformed(ActionEvent e) {
		gw.asteroidCollision();
	}
	
	
}
