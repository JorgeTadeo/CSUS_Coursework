package com.mycompany.a2.gameobjects;



import java.util.ArrayList;


public class SpaceCollection  implements ICollection{

	private ArrayList<GameObject> gameObjectCollection;
	
	public SpaceCollection() {
		gameObjectCollection = new ArrayList<GameObject>();
	}

	@Override
	public void add(GameObject newObject) {
		// TODO Auto-generated method stub
		gameObjectCollection.add( newObject);
	}

	@Override
	public IIterator getIterator() {
		// TODO Auto-generated method stub
		return new SpaceCollectionIterator(this.gameObjectCollection);
	}
	
	
	/*
	 * Inner class iterator
	 */
	private class SpaceCollectionIterator implements IIterator {

		private ArrayList<GameObject> gameObjects;
		private int pos;
		
		public SpaceCollectionIterator(ArrayList<GameObject> gameObjects) {
			this.gameObjects = gameObjects;
			this.pos = 0;
		}
		
		@Override
		public boolean hasNext() {
			return (pos >= gameObjects.size() || gameObjects.get(pos) == null) ? false : true;
		}

		@Override
		public GameObject getNext() {
			GameObject obj = gameObjects.get(pos);
			pos += 1;
			return obj;
		}

		@Override //Remove element at current pos
		public void remove(int index) {
			gameObjects.remove(index);	
		}

		@Override
		public GameObject peek() {
			// TODO Auto-generated method stub
			return gameObjects.get(pos);
		}

		/**
		 * 
		 * @Override gets the index to access the current element pointed by index.
		 * @return index pointer to current element
		 */
		
		public int getCurrIndex() {
			// TODO Auto-generated method stub
			return pos;
		}

		@Override
		public void setEmpty(int index) {
			// TODO Auto-generated method stub
			gameObjects.set(index, null);
		}
		
		
	}



}
