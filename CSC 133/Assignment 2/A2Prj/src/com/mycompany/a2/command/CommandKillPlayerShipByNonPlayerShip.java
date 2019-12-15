package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandKillPlayerShipByNonPlayerShip extends Command {

	
	private IGameWorld gw;
	
	public CommandKillPlayerShipByNonPlayerShip(IGameWorld gw) {
		super("PS (NPS)*");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.killPlayerShipByNPS();
	}
	
	
}
