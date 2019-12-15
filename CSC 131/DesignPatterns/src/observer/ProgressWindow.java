package observer;

import java.awt.*;
import javax.swing.*;

public class ProgressWindow extends JFrame implements LineObserver {
	int lines;
	JProgressBar bar;

	public ProgressWindow(int max) {
		super();

		lines = 0;
		bar = new JProgressBar(0, max);
		performLayout();
		setVisible(true);
	}

	public void handleLine(LineSubject source) {
		indicateProgress();
	}

	private void indicateProgress() {
		lines++;
		bar.setValue(lines);
	}

	private void performLayout() {
		setSize(300, 50);

		bar.setString("Lines Read");
		bar.setStringPainted(true);

		getContentPane().add(bar);
	}
}
