package com.mycompany.a2.game;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Point2D;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.UITimer;
import com.mycompany.a2.command.CommandAbout;
import com.mycompany.a2.command.CommandAddAsteroid;
import com.mycompany.a2.command.CommandAddNonPlayerShip;
import com.mycompany.a2.command.CommandAddPlayerShip;
import com.mycompany.a2.command.CommandAddSpaceStation;
import com.mycompany.a2.command.CommandDecreasePlayerShipSpeed;
import com.mycompany.a2.command.CommandIncreasePlayerShipSpeed;
import com.mycompany.a2.command.CommandJump;
import com.mycompany.a2.command.CommandKillAsteroidByAsteroid;
import com.mycompany.a2.command.CommandKillNonPlayerShipByAsteroid;
import com.mycompany.a2.command.CommandKillNonPlayerShipByPSMissile;
import com.mycompany.a2.command.CommandKillPlayerShipByAsteroid;
import com.mycompany.a2.command.CommandKillPlayerShipByMissile;
import com.mycompany.a2.command.CommandKillPlayerShipByNonPlayerShip;
import com.mycompany.a2.command.CommandKillsAsteroidByPSMissile;
import com.mycompany.a2.command.CommandNew;
import com.mycompany.a2.command.CommandNonPlayerShipFireMissile;
import com.mycompany.a2.command.CommandPause;
import com.mycompany.a2.command.CommandPlayerShipFireMissile;
import com.mycompany.a2.command.CommandPrintMap;
import com.mycompany.a2.command.CommandQuit;
import com.mycompany.a2.command.CommandReloadPlayerShipMissile;
import com.mycompany.a2.command.CommandSave;
import com.mycompany.a2.command.CommandSoundToggle;
import com.mycompany.a2.command.CommandTick;
import com.mycompany.a2.command.CommandTurnMissileLauncherLeft;
import com.mycompany.a2.command.CommandTurnMissileLauncherRight;
import com.mycompany.a2.command.CommandTurnPlayerShipLeft;
import com.mycompany.a2.command.CommandTurnPlayerShipRight;
import com.mycompany.a2.command.CommandUndo;
import com.mycompany.a2.game.views.MapView;
import com.mycompany.a2.game.views.PointsView;

public class Game extends Form implements Runnable{

	/*
	 * Fields 
	 */
	private GameWorld gw;
	private MapView mv;
	private PointsView pv;
	private boolean pause = false;

	private CommandSoundToggle cmdSoundToggle;
	private CommandQuit cmdQuit;
	private CommandAbout cmdAbout;
	private CommandNew cmdNew;
	private CommandSave cmdSave;
	private CommandUndo cmdUndo;
	private CommandAddAsteroid cmdAddNewAsteroid;
	private CommandAddNonPlayerShip cmdAddNonPlayerShip;
	private CommandAddSpaceStation cmdAddSpaceStation;
	private CommandAddPlayerShip cmdAddPlayerShip;
	private CommandPlayerShipFireMissile cmdPlayerShipFireMissile;
	private CommandJump cmdJump;
	private CommandIncreasePlayerShipSpeed cmdIncreasePlayerShipSpeed;
	private CommandDecreasePlayerShipSpeed cmdDecreasePlayerShipSpeed;
	private CommandTurnPlayerShipLeft cmdTurnPlayerShipLeft;
	private CommandTurnPlayerShipRight cmdTurnPlayerShipRight;
	private CommandTurnMissileLauncherLeft cmdTurnMissileLauncherLeft;
	private CommandTurnMissileLauncherRight cmdTurnMissileLauncherRight;
	private CommandNonPlayerShipFireMissile cmdNonPlayerShipFireMissile;
	private CommandReloadPlayerShipMissile cmdReloadPlayerShipMissile;
	private CommandKillsAsteroidByPSMissile cmdKillsAsteroidByPSMissile;
	private CommandKillNonPlayerShipByPSMissile cmdKillNonPlayerShipByPSMissile;
	private CommandKillPlayerShipByMissile cmdKillPlayerShipByMissile;
	private CommandKillPlayerShipByAsteroid cmdKillPlayerShipByAsteroid;
	private CommandKillPlayerShipByNonPlayerShip cmdKillPlayerShipByNonPlayerShip;
	private CommandKillAsteroidByAsteroid cmdKillAsteroidByAsteroid;
	private CommandKillNonPlayerShipByAsteroid cmdKillNonPlayerShipByAsteroid;
	private CommandTick cmdTick;
	private CommandPause cmdPause;
	
	
	private Button addAsteroidButton;
	private Button mapButton;
	private Button addNPSButton;
	private Button addSpaceStationButton;
	private Button addPlayerShipButton;
	private Button playerShipFireButton;
	private Button jumpButton;
	private Button pauseButton;

	



	
	
	
	UITimer timer = new UITimer(this);
	


	
	/*
	 * Constructor
	 */
	public Game() {
		gw = GameWorld.getInstance();  //Create "Observable" / GameWorld
		gw.init(); //Intialize GameWorld
		mv = new MapView(gw);    //Create "Observer"
		pv = new PointsView(gw); //Create "Observer"
		

		gw.addObserver(mv); //Register Observer : mv
		gw.addObserver(pv); //Register Observer : pv
		
		
		
		
	    cmdSoundToggle = new CommandSoundToggle(gw);
	    cmdQuit = new CommandQuit(gw);
	    cmdAbout = new CommandAbout(gw);
	    cmdNew =  new CommandNew(gw);
	    cmdSave = new CommandSave(gw);
	    cmdUndo = new CommandUndo(gw);
	    cmdAddNewAsteroid = new CommandAddAsteroid(gw);
	    cmdAddNonPlayerShip = new CommandAddNonPlayerShip(gw);
	    cmdAddSpaceStation = new CommandAddSpaceStation(gw);
	    cmdAddPlayerShip = new CommandAddPlayerShip(gw);
	    cmdPlayerShipFireMissile = new CommandPlayerShipFireMissile(gw);
	    cmdJump = new CommandJump(gw);
	    cmdIncreasePlayerShipSpeed = new CommandIncreasePlayerShipSpeed(gw);
	    cmdDecreasePlayerShipSpeed = new CommandDecreasePlayerShipSpeed(gw);
	    cmdTurnPlayerShipLeft = new CommandTurnPlayerShipLeft(gw);
	    cmdTurnPlayerShipRight = new CommandTurnPlayerShipRight(gw);
	    cmdTurnMissileLauncherLeft = new CommandTurnMissileLauncherLeft(gw);
	    cmdTurnMissileLauncherRight = new CommandTurnMissileLauncherRight(gw);
	    cmdNonPlayerShipFireMissile = new CommandNonPlayerShipFireMissile(gw);
	    cmdReloadPlayerShipMissile = new CommandReloadPlayerShipMissile(gw);
	    cmdKillsAsteroidByPSMissile = new CommandKillsAsteroidByPSMissile(gw);
	    cmdKillNonPlayerShipByPSMissile = new CommandKillNonPlayerShipByPSMissile(gw);
	    cmdKillPlayerShipByMissile = new CommandKillPlayerShipByMissile(gw);
	    cmdKillPlayerShipByAsteroid = new CommandKillPlayerShipByAsteroid(gw);
	    cmdKillPlayerShipByNonPlayerShip = new CommandKillPlayerShipByNonPlayerShip(gw);
	    cmdKillAsteroidByAsteroid = new CommandKillAsteroidByAsteroid(gw);
	    cmdKillNonPlayerShipByAsteroid = new CommandKillNonPlayerShipByAsteroid(gw);
	    cmdTick = new CommandTick(gw);
	    cmdPause = new CommandPause(this);
		
		
		timer.schedule(20,true,this);
		
		//Game Form layout setup  
		setLayout(new BorderLayout());

		
		
		/**********LEFTMENU BAR SETUP **********/
		
		
		//Toolbar 
		Toolbar toolBar = new Toolbar();
		Toolbar.setOnTopSideMenu(false);
		setToolbar(toolBar);
		toolBar.setTitle("Asteroid Game");

		//Toolbar Menu Items 
		CheckBox soundCheckBox = new CheckBox();
		Button quitButton = new Button();	
		Button aboutButton = new Button();	
		Button newButton = new Button();		
		Button saveButton = new Button();
		Button undoButton = new Button();


		//Make Menu Items fancy
		soundCheckBox = applyMakeUp(soundCheckBox);
		quitButton = applyMakeup(quitButton);
		aboutButton = applyMakeup(aboutButton);
		newButton = applyMakeup(newButton);
		saveButton = applyMakeup(saveButton);
		undoButton = applyMakeup(undoButton);

		
		//Disable key navigation
		soundCheckBox.setFocusable(false);
		quitButton.setFocusable(false);
		aboutButton.setFocusable(false);
		newButton.setFocusable(false);
		saveButton.setFocusable(false);
		undoButton.setFocusable(false);

		
		
		//Command Listener for checkboxes and buttons 
		soundCheckBox.setCommand(cmdSoundToggle);
		quitButton.setCommand(cmdQuit);
		aboutButton.setCommand(cmdAbout);
		newButton.setCommand(cmdNew);
		saveButton.setCommand(cmdSave);
		undoButton.setCommand(cmdUndo);

		


		//Add components to toolbar side menu 
		toolBar.addComponentToSideMenu(soundCheckBox);
		toolBar.addComponentToSideMenu(quitButton);
		toolBar.addComponentToSideMenu(aboutButton);
		toolBar.addComponentToSideMenu(newButton);
		toolBar.addComponentToSideMenu(saveButton);
		toolBar.addComponentToSideMenu(undoButton);

		
		
		/******* END OF LEFTMENU BAR SETUP ******/
		
		
		
		
		
		
		/****** WEST CONTAINER SETUP  *******/
		
		//West Container
		Container leftContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		
		
		//West Container Buttons 
		 addAsteroidButton = new Button();
		 mapButton = new Button(); //DEBUG USE ONLY
		 addNPSButton = new Button();
		 addSpaceStationButton = new Button();
		 addPlayerShipButton = new Button();
		 playerShipFireButton = new Button();
		 jumpButton = new Button();
		 pauseButton = new Button();
		
		//Make buttons fancy
		addAsteroidButton = applyMakeup(addAsteroidButton);
		mapButton = applyMakeup(mapButton);
		addNPSButton = applyMakeup(addNPSButton);
		addSpaceStationButton = applyMakeup(addSpaceStationButton);
		addPlayerShipButton = applyMakeup(addPlayerShipButton);
		playerShipFireButton = applyMakeup(playerShipFireButton);
		jumpButton = applyMakeup(jumpButton);
		pauseButton = applyMakeup(pauseButton);

		//Disable arrow key navigation for buttons
		addAsteroidButton.setFocusable(false);
		addNPSButton.setFocusable(false);
		addSpaceStationButton.setFocusable(false);
		addPlayerShipButton.setFocusable(false);
		jumpButton.setFocusable(false);
		playerShipFireButton.setFocusable(false);
		pauseButton.setFocusable(false);
		
		//Set commands for buttons
		addAsteroidButton.setCommand(cmdAddNewAsteroid);
		mapButton.setCommand(new CommandPrintMap(gw));
		addNPSButton.setCommand(cmdAddNonPlayerShip);
		addSpaceStationButton.setCommand(cmdAddSpaceStation);
		addPlayerShipButton.setCommand(cmdAddPlayerShip);
		playerShipFireButton.setCommand(cmdPlayerShipFireMissile);
		jumpButton.setCommand(cmdJump);
		pauseButton.setCommand(cmdPause);


		//Add components to West Container
		leftContainer.add(addAsteroidButton);
		leftContainer.add(addNPSButton);
		leftContainer.add(addSpaceStationButton);
		leftContainer.add(addPlayerShipButton);
		leftContainer.add(playerShipFireButton);
		leftContainer.add(jumpButton);
		leftContainer.add(pauseButton);
		
		/****** END OF WEST CONTAINER SETUP  *******/
		
		
		
		
		
		
		
		//Key Bindings
		addKeyListener('m', mapButton.getCommand()); //DEBUG ONLY
		addKeyListener('i', cmdIncreasePlayerShipSpeed);
		addKeyListener(-91 , cmdIncreasePlayerShipSpeed); //Up arrow 
		addKeyListener('d', cmdDecreasePlayerShipSpeed); 
		addKeyListener(-92 , cmdDecreasePlayerShipSpeed); //Down arrow  
		addKeyListener('l', cmdTurnPlayerShipLeft);
		addKeyListener(-93 , cmdTurnPlayerShipLeft); //Left arrow 
		addKeyListener('r', cmdTurnPlayerShipRight);
		addKeyListener(-94, cmdTurnPlayerShipRight); //Right arrow 
		addKeyListener(44 ,  cmdTurnMissileLauncherLeft);
		addKeyListener(46,  cmdTurnMissileLauncherRight);
		addKeyListener(-90,  cmdPlayerShipFireMissile); //Space Bar
		addKeyListener('L' ,  cmdNonPlayerShipFireMissile);
		addKeyListener('j' ,  cmdJump);
		addKeyListener('n',  cmdReloadPlayerShipMissile);
		addKeyListener('k',  cmdKillsAsteroidByPSMissile);
		addKeyListener('e',  cmdKillNonPlayerShipByPSMissile);
		addKeyListener('E',  cmdKillPlayerShipByMissile);
		addKeyListener('c',  cmdKillPlayerShipByAsteroid);
		addKeyListener('h',  cmdKillPlayerShipByNonPlayerShip);
		addKeyListener('x',  cmdKillAsteroidByAsteroid);
		addKeyListener('I',  cmdKillNonPlayerShipByAsteroid);
		addKeyListener('t',  cmdTick);
		addKeyListener('z', cmdQuit); //QUIT
		
		
		//Ignore : Alternative syntax to add listener
		//this.addKeyListener('z', quitButton.getCommand()); //QUIT
		

		
		/*
		 * Add all container to contentpane
		 */
		add(BorderLayout.WEST, leftContainer);
		add(BorderLayout.NORTH , pv);
		add(BorderLayout.CENTER,mv);

		
		this.show();
		
		
		System.out.println("Game GUI Setup Completed with the following stats :");
		
		System.out.println("Form Content pane size : " + this.getWidth() + "," + this.getHeight());
		System.out.println("MapView size : " + mv.getWidth() + "," + mv.getHeight());
		System.out.println("MapView Origin : " + mv.getX() + "," + mv.getY());
		
		GameWorld.setGameWidth(mv.getWidth());
		GameWorld.setGameHeight(mv.getHeight());
		
		mv.setMapViewOrigin(new Point2D(mv.getX() , mv.getY()));
		MapView.setMapViewWidth(mv.getWidth());
		MapView.setMapViewHeight(mv.getHeight());
	}
	
	
	
	private Button applyMakeup(Button obj) {
		obj.getAllStyles().setBgTransparency(255);
		obj.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		obj.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		obj.getAllStyles().setPadding(TOP, 5);
		obj.getAllStyles().setPadding(BOTTOM, 5);
		return obj;
	}
	
	private CheckBox applyMakeUp(CheckBox obj) {
		obj.getAllStyles().setBgTransparency(255);
		obj.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 150, 150));
		obj.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		obj.getAllStyles().setPadding(TOP, 5);
		obj.getAllStyles().setPadding(BOTTOM, 5);
		return obj;
	}


	/**
	 * Invoke when timer ticks
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		gw.tick();
		
	}



	public void pauseAndUnPause() {
		// TODO Auto-generated method stub

			pause = !pause;

			addAsteroidButton.setEnabled(!addAsteroidButton.isEnabled());
			mapButton.setEnabled(!mapButton.isEnabled());
			addNPSButton.setEnabled(!addNPSButton.isEnabled());
			addSpaceStationButton.setEnabled(!addSpaceStationButton.isEnabled());
			addPlayerShipButton.setEnabled(!addPlayerShipButton.isEnabled());
			playerShipFireButton.setEnabled(!playerShipFireButton.isEnabled());
			jumpButton.setEnabled(!jumpButton.isEnabled());
			//pauseButton.setEnabled(!pauseButton.isEnabled());
			
		
			if (pause)
			{
				//Game is to be paused
				timer.cancel();
				pv.turnOffSound();
				

				removeKeyListener('i', cmdIncreasePlayerShipSpeed);
				removeKeyListener(-91 , cmdIncreasePlayerShipSpeed); //Up arrow 
				removeKeyListener('d', cmdDecreasePlayerShipSpeed); 
				removeKeyListener(-92 , cmdDecreasePlayerShipSpeed); //Down arrow  
				removeKeyListener('l', cmdTurnPlayerShipLeft);
				removeKeyListener(-93 , cmdTurnPlayerShipLeft); //Left arrow 
				removeKeyListener('r', cmdTurnPlayerShipRight);
				removeKeyListener(-94, cmdTurnPlayerShipRight); //Right arrow 
				removeKeyListener(44 ,  cmdTurnMissileLauncherLeft);
				removeKeyListener(46,  cmdTurnMissileLauncherRight);
				removeKeyListener(-90,  cmdPlayerShipFireMissile); //Space Bar
				removeKeyListener('L' ,  cmdNonPlayerShipFireMissile);
				removeKeyListener('j' ,  cmdJump);
				removeKeyListener('n',  cmdReloadPlayerShipMissile);
				removeKeyListener('k',  cmdKillsAsteroidByPSMissile);
				removeKeyListener('e',  cmdKillNonPlayerShipByPSMissile);
				removeKeyListener('E',  cmdKillPlayerShipByMissile);
				removeKeyListener('c',  cmdKillPlayerShipByAsteroid);
				removeKeyListener('h',  cmdKillPlayerShipByNonPlayerShip);
				removeKeyListener('x',  cmdKillAsteroidByAsteroid);
				removeKeyListener('I',  cmdKillNonPlayerShipByAsteroid);
				removeKeyListener('t',  cmdTick);
				removeKeyListener('z', cmdQuit); //QUIT
			}
			else
			{
				//Game is to resume
				timer.schedule(20, true, this);
				pv.turnOnSound();

				
				//Key Bindings
				addKeyListener('i', cmdIncreasePlayerShipSpeed);
				addKeyListener(-91 , cmdIncreasePlayerShipSpeed); //Up arrow 
				addKeyListener('d', cmdDecreasePlayerShipSpeed); 
				addKeyListener(-92 , cmdDecreasePlayerShipSpeed); //Down arrow  
				addKeyListener('l', cmdTurnPlayerShipLeft);
				addKeyListener(-93 , cmdTurnPlayerShipLeft); //Left arrow 
				addKeyListener('r', cmdTurnPlayerShipRight);
				addKeyListener(-94, cmdTurnPlayerShipRight); //Right arrow 
				addKeyListener(44 ,  cmdTurnMissileLauncherLeft);
				addKeyListener(46,  cmdTurnMissileLauncherRight);
				addKeyListener(-90,  cmdPlayerShipFireMissile); //Space Bar
				addKeyListener('L' ,  cmdNonPlayerShipFireMissile);
				addKeyListener('j' ,  cmdJump);
				addKeyListener('n',  cmdReloadPlayerShipMissile);
				addKeyListener('k',  cmdKillsAsteroidByPSMissile);
				addKeyListener('e',  cmdKillNonPlayerShipByPSMissile);
				addKeyListener('E',  cmdKillPlayerShipByMissile);
				addKeyListener('c',  cmdKillPlayerShipByAsteroid);
				addKeyListener('h',  cmdKillPlayerShipByNonPlayerShip);
				addKeyListener('x',  cmdKillAsteroidByAsteroid);
				addKeyListener('I',  cmdKillNonPlayerShipByAsteroid);
				addKeyListener('t',  cmdTick);
				addKeyListener('z', cmdQuit); //QUIT
			}
		}
	
	
}
