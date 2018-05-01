package arableLand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.ToIntFunction;

import mapData.Box;
import mapData.LandMap;

public class HorizontalBoxPositionList implements Iterable<BoxPositionList> {
	// the ListArray that stores the VerticalBoxPositionList
	private final BoxPositionList theList; 
	
	/**
	 * Sort list of barren land boxes in the map vertically by top and bottom coordinates. 
	 * Each box has a top entry in the list and a bottom entry. 
	 * Include the border top and bottom positions as if they were boxes above and below the border box.
	 *
	 * @param map
	 */
	public HorizontalBoxPositionList(LandMap map) {
		theList = new BoxPositionList();
		
		Box border = map.border;
	}
	
	public void addLeftAndRightPosition(Box box) {
		BoxPosition pos;		
		 pos = new BoxPosition(box.getLeft(), true, box);
		 theList.add(pos);
		 pos = new BoxPosition(box.getRight(), false, box);
		 theList.add(pos);		
	}
	
	public boolean add(BoxPosition bp) {
		return theList.add(bp);
	}
	
	public void remove(Box box) {
		int boxCount = 0;
		for (Iterator<BoxPosition> iterator = theList.iterator(); iterator.hasNext();) {
			BoxPosition boxPosition = iterator.next();
			if(box.equals(boxPosition.getBox())) {
				iterator.remove();
				boxCount++;
			}
			if (boxCount == 2) {
				break;
			}
		}
	}
	
	/**
	 * Sort the boxes by position from left to right
	 * @param comparingInt
	 */
	public void sort() {
		theList.sort(Comparator.comparingInt((boxPosition)-> boxPosition.getPosition()));
	}

	/**
	 * Return the list. Used for testing only
	 * @return
	 */
	public BoxPositionList getBoxPositionList() {
		return theList;
	}
	
	/**
	 * Iterator to find the next position and the set of boxes with top or bottom heights that match that position 
	 * @return HorizontalBoxList
	 */
	public Iterator<BoxPositionList> getBoxesByPositionIterator() {
		return theList.getBoxesByPositionIterator();
	}

	@Override
	public Iterator<BoxPositionList> iterator() {
		return theList.getBoxesByPositionIterator();
	}



}