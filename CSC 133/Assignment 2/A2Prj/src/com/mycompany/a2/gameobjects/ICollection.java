package com.mycompany.a2.gameobjects;

public interface ICollection {
	/**
	 * Description : Adds GameObject to game collection. <br>
	 * @param newObject : GameObject to be added
	 */
	void add(GameObject newObject);
	
	
	/**
	 * Description : Returns iterator that can iterate through game collection. <br>
	 * @return Iterator of game collection
	 */
	IIterator getIterator();
}
