package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandTurnMissileLauncherRight extends Command {

	
	private IGameWorld gw;
	
	public CommandTurnMissileLauncherRight(IGameWorld gw) {
		super("MSL Right*");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.turnMissileLauncherRight();
	}
	
	
}
