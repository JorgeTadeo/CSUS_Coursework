package composite;

import java.util.*;

public class DistributionList implements Distribution {
	private Collection<Distribution> aggregate;

	public DistributionList() {
		aggregate = new HashSet<Distribution>();
	}

	public void add(Distribution d) {
		aggregate.add(d);
	}

	public void sendMessage(String msg) {
		for (Distribution d : aggregate) {
			d.sendMessage(msg);
		}
	}

	public void remove(Distribution d) {
		aggregate.remove(d);
	}
}
