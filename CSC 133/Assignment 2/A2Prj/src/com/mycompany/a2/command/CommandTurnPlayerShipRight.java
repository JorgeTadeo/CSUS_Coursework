package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandTurnPlayerShipRight extends Command {

	
	private IGameWorld gw;
	
	public CommandTurnPlayerShipRight(IGameWorld gw) {
		super("PS Right*");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.turnPSRight();
	}
	
	
}
