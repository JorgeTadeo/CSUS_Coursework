package com.mycompany.a2.sound;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;
/*
 * BGSOUND is controlled by PointsView
 */
public class BGSound implements Runnable{

	/**
	 * Media Object to play sound
	 */
	private Media m;
	
	/**
	 * Constructor to setup audio format by passing an InputStream object to MediaManager.creatMedia()
	 * @param fileName : file name of the audio to be played.
	 */
	public BGSound(String fileName) {
		try {
			InputStream inputStream = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
			
			//passing "this" as the runnable object so media manager knows what to re-run
		//	m = MediaManager.createMedia(inputStream,"audio/mp3", this);
			m = MediaManager.createMedia(inputStream,"audio/wav", this);
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Cannot Play Sound");
		}
	}
	/**
	 * Pause media 
	 */
	public void pause() {
		m.pause();
	}
	/**
	 * Play media 
	 */
	public void play() {
		m.play();
		System.out.println("BGM Playing...");
	}
	
	
	
	/**
	 * @Override 
	 * Override Runnable run() , this method is invoke whenever media has finished playing.
	 */

	public void run() {
		// TODO Auto-generated method stub
		m.setTime(0);
		m.play();
	}

}
