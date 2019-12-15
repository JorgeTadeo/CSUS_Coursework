package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandTurnPlayerShipLeft extends Command {

	
	private IGameWorld gw;
	
	public CommandTurnPlayerShipLeft(IGameWorld gw) {
		super("PS Left*");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.turnPSLeft();
	}
	
	
}
