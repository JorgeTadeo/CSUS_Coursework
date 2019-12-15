package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;
/**
 *Description: A class that allows player to jump to warpspeed.
 *
 */
public class CommandJump extends Command {

	/**
	 *Description: Proxy Game World.
	 *
	 */
	private IGameWorld gw;
	/**
	 *Description: Command that allows the player to jump to warp speed.
	 *@param gw : input gw 
	 */
	public CommandJump(IGameWorld gw) {
		super("Jump*");
		this.gw = gw;
	}
	
	/**
	 *Description: Action listener that waits for command input.
	 *@param ae : input e 
	 */
	public void actionPerformed(ActionEvent e) {
		gw.jumpToHyperSpace();
	}
	
	
}
