package com.mycompany.a2.command;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;
/**
 * 
 * @author Jorge Tadeo
 * CommandUndo Class implements undo feature
 */
public class CommandUndo extends Command {

	/**
	 * create proxy gameWorld
	 */
	private IGameWorld gw;

	/**
	 * Description: Constructor that sets gameWorld
	 * @param gw
	 */
	public CommandUndo(IGameWorld gw) {
		super("Undo");
		this.gw = gw;
	}
	
	/**
	 * Description: Todo
	 */
	public void actionPerformed(ActionEvent e) {
		//TODO
	}
	
	
}
