package observer;

import java.io.*;
import java.util.*;

public class LineReader implements LineSubject {
	private BufferedReader in;

	private Collection<LineObserver> observers;
	private int maxLines;
	private String line;

	public LineReader(InputStream is, int maxLines) throws IOException {
		this.maxLines = maxLines;

		in = new BufferedReader(new InputStreamReader(is));
		observers = new HashSet<LineObserver>();
	}

	public void addObserver(LineObserver observer) {
		observers.add(observer);
	}

	public String getLine() {
		return line;
	}

	public void notifyObservers() {
		for (LineObserver o : observers) {
			o.handleLine(this);
		}
	}

	public void removeObserver(LineObserver observer) {
		observers.remove(observer);
	}

	public void start() throws IOException {
		// Read from the console and alert listeners
		while ((line = in.readLine()) != null) {
			notifyObservers();
		}
	}
}
