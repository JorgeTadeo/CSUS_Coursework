package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandKillPlayerShipByMissile extends Command {

	
	private IGameWorld gw;
	
	public CommandKillPlayerShipByMissile(IGameWorld gw) {
		super("NPS Missile (PS)*");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.killPlayerShipByMissile();
	}
	
	
}
