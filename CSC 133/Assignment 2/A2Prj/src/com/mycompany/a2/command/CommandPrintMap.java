package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandPrintMap extends Command {

	
	private IGameWorld gw;
	
	public CommandPrintMap(IGameWorld gw) {
		super("Print Map");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		gw.printMap();
		System.out.println("Map Printed.");
	}
	
	
}
