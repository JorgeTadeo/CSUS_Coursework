package com.mycompany.a2.command;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandQuit extends Command {

	
	private IGameWorld gw;

	
	public CommandQuit(IGameWorld gw) {
		super("Quit*");
		this.gw = gw;
	}
	

	public void actionPerformed(ActionEvent e) {
		if (Dialog.show("Quit", "Are you sure you want to quit?", "Yes", "No"))
		{
			System.exit(0);
		}
	}
	
	
}
