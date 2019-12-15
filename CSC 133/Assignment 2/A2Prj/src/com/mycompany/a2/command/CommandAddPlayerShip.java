package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;
/**
 * 
 * @author Jorge Tadeo
 *
 */
public class CommandAddPlayerShip extends Command {

	/**
	 * Proxy gameWorld
	 */
	private IGameWorld gw;
	/**description: Add player ship to the gameworld. 
	 * @param gw : input gw
	 */
	public CommandAddPlayerShip(IGameWorld gw) {
		super("+ PS (1)");
		this.gw = gw;
	}
	/**
	 * @param ae : input e
	 */
	public void actionPerformed(ActionEvent e) {
		gw.addPlayerShip();
	}
	
	
}
