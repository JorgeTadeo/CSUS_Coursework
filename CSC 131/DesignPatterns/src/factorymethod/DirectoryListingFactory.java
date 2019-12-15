package factorymethod;

import java.util.*;

public class DirectoryListingFactory {
	private Map<String, DirectoryListing> pool;

	public DirectoryListingFactory() {
		pool = new HashMap<String, DirectoryListing>();
	}

	public DirectoryListing createDirectoryListing(String path) {
		DirectoryListing dl;

		dl = pool.get(path);
		if (dl == null) {
			dl = new DirectoryListing(path);
			pool.put(path, dl);
		}

		return dl;
	}
}
