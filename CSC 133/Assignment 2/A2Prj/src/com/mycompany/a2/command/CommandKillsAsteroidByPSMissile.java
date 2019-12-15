package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandKillsAsteroidByPSMissile extends Command {

	
	private IGameWorld gw;
	
	public CommandKillsAsteroidByPSMissile(IGameWorld gw) {
		super("PS Missile*\n(Asteroid)");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.killAsteroidByMissile();
	}
	
	
}


