package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.Game;

public class CommandPause extends Command {

	
	private Game g;

	
	public CommandPause(Game g) {
		super("Pause");
		this.g = g;
	}
	

	public void actionPerformed(ActionEvent e) {
		g.pauseAndUnPause();
	}
	
	
}
