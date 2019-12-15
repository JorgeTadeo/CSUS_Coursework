package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandTurnMissileLauncherLeft extends Command {

	
	private IGameWorld gw;
	
	public CommandTurnMissileLauncherLeft(IGameWorld gw) {
		super("MSL Left*");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.turnMissileLauncherLeft();
	}
	
	
}
