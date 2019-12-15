package factorymethod;

import java.io.*;
import java.util.*;

public class DirectoryListing {
	private File dir;
	private File[] files;
	private long lastTimeCheck;

	DirectoryListing(String path) // package visibility
	{
		dir = new File(path);

		lastTimeCheck = 0;
		update();
	}

	public File[] getContents() {
		update();
		return files;
	}

	private void update() {
		long lastModified;

		lastModified = dir.lastModified();
		if (lastTimeCheck != lastModified) {
			lastTimeCheck = lastModified;
			files = dir.listFiles();
			Arrays.sort(files);
		}
	}
}
