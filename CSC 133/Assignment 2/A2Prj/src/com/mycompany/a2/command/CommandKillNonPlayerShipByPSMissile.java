package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandKillNonPlayerShipByPSMissile extends Command {

	
	private IGameWorld gw;
	
	public CommandKillNonPlayerShipByPSMissile(IGameWorld gw) {
		super("PS Missile (NPS*)");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.killNonPlayerShipByMissile();
	}
	
	
}
