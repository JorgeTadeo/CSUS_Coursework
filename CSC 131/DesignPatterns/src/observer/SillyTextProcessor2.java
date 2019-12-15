package observer;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class SillyTextProcessor2 {

	public static void main(String[] args) throws Exception {
		int maxLines;
		LineArchiver archiver;
		LineReader reader;
		ProgressWindow bar;
		UCWordCounter counter;

		// Process the command line parameter
		try {
			maxLines = Integer.parseInt(args[0]);
		} catch (NumberFormatException nfe) {
			maxLines = 5;
		}

		// Initialization
		reader = new LineReader(System.in, maxLines);
		bar = new ProgressWindow(maxLines);
		archiver = new LineArchiver();
		counter = new UCWordCounter();

		reader.addObserver(bar);
		reader.addObserver(archiver);
		reader.addObserver(counter);

		// Prompt the user
		System.out.println("Enter " + maxLines + " lines of text (^Z to end):\n");

		reader.start();
	}

}