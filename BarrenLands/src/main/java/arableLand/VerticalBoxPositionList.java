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

public class VerticalBoxPositionList implements Iterable<BoxPositionList> {
	// the ListArray that stores the VerticalBoxPositionList
	private final BoxPositionList theList; 
	
	/**
	 * Sort list of barren land boxes in the map vertically by top and bottom coordinates. 
	 * Each box has a top entry in the list and a bottom entry. 
	 * TODO Include the border top and bottom as barren land boxes above and below the border box.
	 *
	 * @param map
	 */
	public VerticalBoxPositionList(LandMap map) {
		int boxPositionCount = 2*(map.barrenLands.size() + 1) ;
		theList = new BoxPositionList(boxPositionCount );

		List<Box> lands = map.barrenLands;
		for (Box box : lands) {
			addTopAndBottomPositions(box);
		}		
	}
	
	public void addTopAndBottomPositions(Box box) {
		BoxPosition pos;		
		 pos = new BoxPosition(box.getTop(), true, box);
		 theList.add(pos);
		 pos = new BoxPosition(box.getBottom(), false, box);
		 theList.add(pos);		
	}
	
	/**
	 * Sort the boxes by position from top to bottom
	 * @param comparingInt
	 */
	public void sort() {
		theList.sort(Comparator.comparingInt(inversePosition));
	}

	// Sort in reverse order (top to bottom) by retrieving  ( -1 * vertical position)
	ToIntFunction<BoxPosition> inversePosition = (verticalBoxPostion)-> - verticalBoxPostion.getPosition();

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