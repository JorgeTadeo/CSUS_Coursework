package observer;

import java.util.*;

public class UCWordCounter implements LineObserver {
	private int lastCount;

	public UCWordCounter() {
		lastCount = 0;
	}

	private int count(String line) {
		int ucCount;
		StringTokenizer tokenizer;
		String letter, letterUC, word;

		ucCount = 0;
		tokenizer = new StringTokenizer(line);

		while (tokenizer.hasMoreTokens()) {
			word = tokenizer.nextToken();
			letter = word.substring(0, 1);
			letterUC = letter.toUpperCase();
			if (letter.equals(letterUC))
				++ucCount;
		}

		return ucCount;
	}

	public void handleLine(LineSubject source) {
		String line;

		line = source.getLine();
		lastCount = count(line);
		displayCount();
	}

	private void displayCount() {
		System.out.println("Start with uppercase: " + lastCount);
	}
}
