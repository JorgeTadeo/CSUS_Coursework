package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;
/**
 * Description: A class that up's the playership speed.
 *
 */
public class CommandIncreasePlayerShipSpeed extends Command {

	/**
	 * Description: Proxy Game World
	 *
	 */
	private IGameWorld gw;
	/**
	 *Description: Command that up's the playship speed.
	 *@param gw : input gw
	 */
	public CommandIncreasePlayerShipSpeed(IGameWorld gw) {
		super("PS Speed (+)*");
		this.gw = gw;
	}
	/**
	 * Description: Action listener that waits for command input.
	 *@param ae : input e
	 */
	public void actionPerformed(ActionEvent e) {
		gw.increasePlayerShipSpeed();
	}
	
	
}
