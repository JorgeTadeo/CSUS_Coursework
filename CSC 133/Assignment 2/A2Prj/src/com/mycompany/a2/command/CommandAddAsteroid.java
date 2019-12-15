package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;
/**
 * 
 * @author Jorge Tadeo
 *	Description: Handles Add Asteroid to gameObject collection.
 */
public class CommandAddAsteroid extends Command {

	/**
	 * ProxyGameWorld
	 */
	private IGameWorld gw;
	/**
	 * 
	 * @param gw : input gw
	 */
	public CommandAddAsteroid(IGameWorld gw) {
		super("+ Asteroid");
		this.gw = gw;
	}
	/**
	 * @param av : input e
	 */
	public void actionPerformed(ActionEvent e) {
		gw.addAsteroid();
	}
	
	
}
