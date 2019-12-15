package iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;

public class IteratorDemo {
	public static void main(String[] args) {
		//ArrayList<String> cities = new ArrayList<String>();
		HashSet<String> cities = new HashSet<String>();
		cities.add("Chicago");
		cities.add("Denver");
		cities.add("Miami");
		cities.add("Los Angeles");
		cities.add("Seattle");
		
		Iterator<String> iterator = cities.iterator();
		System.out.println("Iterator type for the datastructure is: " + iterator.toString());
		for (String city : cities) {
			System.out.println(city);
		}
	}
}

