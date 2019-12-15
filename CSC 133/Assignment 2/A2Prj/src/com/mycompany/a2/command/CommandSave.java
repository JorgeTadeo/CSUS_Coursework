package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandSave extends Command {

	
	private IGameWorld gw;

	
	public CommandSave(IGameWorld gw) {
		super("Save");
		this.gw = gw;
	}
	

	public void actionPerformed(ActionEvent e) {
		//TODO
	}
	
	
}
