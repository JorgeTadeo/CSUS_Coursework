package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;
/*
 * CommandAbout class is responsible to activate the command command from UI.
 */
public class CommandAbout extends Command {

	/**
	 * Description: Gameworld about the game
	 */
	private IGameWorld gw;

	/**
	 * Description : Constructor that links gameworld to about command.
	 * @param gw : Input gameworld 
	 * 
	 */
	public CommandAbout(IGameWorld gw) {
		super("About");
		this.gw = gw;
	}
	
	/**
	 * Description : Show about page.
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		//TODO
		String info = "Rongguang Ou\nAsteroid V2.5";
		Dialog.show("About", info, "Ok", null);
	}
	
	
}
