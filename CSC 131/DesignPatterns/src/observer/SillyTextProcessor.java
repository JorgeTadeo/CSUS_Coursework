package observer;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class SillyTextProcessor {

	public static void main(String[] args) throws Exception {
		BufferedReader in;
		int lines, maxLines, ucCount;
		JFrame frame;
		JProgressBar bar;
		PrintWriter out;
		String letter, letterUC, line, word;
		StringTokenizer tokenizer;

		// Process the command line parameter
		try {
			maxLines = Integer.parseInt(args[0]);
		} catch (NumberFormatException nfe) {
			maxLines = 5;
		}

		// Initialization
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(new FileOutputStream("archive.txt"));
		bar = new JProgressBar(0, maxLines);
		frame = new JFrame();

		bar.setString("Lines Read");
		bar.setStringPainted(true);
		frame.getContentPane().add(bar);
		frame.setSize(300, 50);
		frame.setVisible(true);

		// Prompt the user
		System.out.println("Enter " + maxLines + " lines of text (^Z to end):\n");

		// Read from the console
		lines = 0;
		while ((line = in.readLine()) != null) {
			// Process each line

			// Indicate the progress
			++lines;
			bar.setValue(lines);

			// Count the number of words that start with
			// uppercase characters (using Java's
			// definition of uppercase)
			tokenizer = new StringTokenizer(line);
			ucCount = 0;
			while (tokenizer.hasMoreTokens()) {
				word = tokenizer.nextToken();
				letter = word.substring(0, 1);
				letterUC = letter.toUpperCase();
				if (letter.equals(letterUC))
					++ucCount;
			}
			System.out.println("Start with uppercase: " + ucCount);

			// Save the text in a file
			out.println(line);

		}

		out.flush();
		out.close();
	}

}
