package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;
/**
 * 
 * @author Jorge Tadeo
 *Description: Lets you add a non-player ship to the gameworld
 */
public class CommandAddNonPlayerShip extends Command {

	/**
	 * Proxy gameWorld
	 */
	private IGameWorld gw;
	/**
	 * @param gw : input gw
	 */
	public CommandAddNonPlayerShip(IGameWorld gw) {
		super("+ NPS");
		this.gw = gw;
	}
	/**
	 * @param av : input e
	 */
	public void actionPerformed(ActionEvent e) {
		gw.addNonPlayerShip();
	}
	
	
}