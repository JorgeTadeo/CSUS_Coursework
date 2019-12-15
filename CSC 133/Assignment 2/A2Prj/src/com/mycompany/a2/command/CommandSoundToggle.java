package com.mycompany.a2.command;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.game.IGameWorld;

public class CommandSoundToggle extends Command {

	
	private IGameWorld gw;

	
	public CommandSoundToggle(IGameWorld gw) {
		super("Sound");
		this.gw = gw;
	}
	

	public void actionPerformed(ActionEvent e) {
		if(((CheckBox) e.getComponent()).isSelected()) {
			gw.setSound(true);
		}else {
			gw.setSound(false);
		}
	}
	
	
}
