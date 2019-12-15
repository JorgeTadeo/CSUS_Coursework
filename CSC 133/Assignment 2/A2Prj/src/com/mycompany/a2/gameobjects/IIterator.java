package com.mycompany.a2.gameobjects;

public interface IIterator {
	/**
	 * Checks if there is still more elements to retreieve from game collection.
	 * @return returns true if there is more gameobjects to retreieve from gamecollection. false otherwise.
	 */
	public boolean hasNext();
	
	
	/**
	 * Returns the next GameObject in the collection, then advance ptr by one.
	 * @return Returns GameObject pointed by current index
	 */
	public GameObject getNext();
	
	
	/**
	 * Removes an element from game collection at position "index"
	 * @param index : target element index
	 */
	public void remove(int index);
	
	
	/**
	 * Returns a GameObject pointed by current index without advancing ptr.
	 * @return GameObject at currentIndex.
	 */
	public GameObject peek();
	
	
	/**
	 * 
	 * @Override gets the index to access the current element pointed by index.
	 * @return index pointer to current element
	 */
	public int getCurrIndex();
	
	/**
	 * Set element at "index" to null.
	 * @param index : target index
	 */
	public void setEmpty(int index);
}
