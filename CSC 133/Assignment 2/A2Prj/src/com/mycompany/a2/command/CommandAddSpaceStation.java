package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;
/**
 * 
 * @author Jorge Tadeo
 *CommandAddSpaceStation creation of space station and methods to manipulate it. 
 */
public class CommandAddSpaceStation extends Command {
	/**
	 * Proxy gameworld
	 */
	private IGameWorld gw;
	/**
	 *Description: Add a space station to the game world.
	 *@param gw : input gw
	 */
	public CommandAddSpaceStation(IGameWorld gw) {
		super("+ Space Station");
		this.gw = gw;
	}
	
	/**
	 *Description: Add a space station to the game world.
	 *@param ae : input e
	 */
	public void actionPerformed(ActionEvent e) {
		gw.addSpaceStation();
	}
	
	
}
