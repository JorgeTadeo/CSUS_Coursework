package observer;

import java.io.*;

public class LineArchiver implements LineObserver {
	PrintWriter out;

	public LineArchiver() throws IOException {
		out = new PrintWriter(new FileOutputStream("archive.txt"));
	}

	public void close() throws IOException {
		out.flush();
		out.close();
	}

	public void handleLine(LineSubject source) {
		String line;

		try {
			line = source.getLine();
			save(line);
		} catch (IOException ioe) {
			// Ignore
		}
	}

	private void save(String line) throws IOException {
		out.println(line);
	}
}
