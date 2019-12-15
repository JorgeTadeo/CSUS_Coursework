package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;
/**
 * description: A class which adds a speed decrease command for player ship.
 *
 */
public class CommandDecreasePlayerShipSpeed extends Command {

	/**
	 * Proxy Gameworld
	 *
	 */
	private IGameWorld gw;
	/**
	 * description: command that decreases playership speed
	 *@param gw : input gw
	 */
	public CommandDecreasePlayerShipSpeed(IGameWorld gw) {
		super("PS Speed (-)*");
		this.gw = gw;
	}
	/**
	 * description: Listener that waits for the command.
	 *@param ae : input e
	 */
	public void actionPerformed(ActionEvent e) {
		gw.decreasePlayerShipSpeed();
	}
	
	
}
