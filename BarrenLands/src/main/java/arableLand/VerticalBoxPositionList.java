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

public class VerticalBoxPositionList {
	// the ListArray that stores the VerticalBoxPositionList
	private final VBPList theList; 
	
	/**
	 * Sort list of barren land boxes in the map vertically by top and bottom coordinates. 
	 * Each box has a top entry in the list and a bottom entry. 
	 * Include the border top and bottom positions as if they were boxes above and below the border box.
	 *
	 * @param map
	 */
	public VerticalBoxPositionList(LandMap map) {
		int boxPositionCount = 2*(map.barrenLands.size() + 1) ;
		theList = new VBPList(boxPositionCount );
		
		Box border = map.farmBorder;
		VerticalBoxPosition pos;		
		pos = new VerticalBoxPosition(border.getTop(), false, border);
		theList.add(pos);
		pos = new VerticalBoxPosition(border.getBottom(), true, border);
		theList.add(pos);
		
		List<Box> lands = map.barrenLands;
		for (Box box : lands) {
			addTopAndBottomPositions(box);
		}		
	}
	
	public void addTopAndBottomPositions(Box box) {
		VerticalBoxPosition pos;		
		 pos = new VerticalBoxPosition(box.getTop(), true, box);
		 theList.add(pos);
		 pos = new VerticalBoxPosition(box.getBottom(), false, box);
		 theList.add(pos);		
	}
	
	/**
	 * Sort the boxes by position from top to bottom
	 * @param comparingInt
	 */
	public void sort() {
		theList.sort(Comparator.comparingInt(inversePosition));
	}

	// enable sort in reverse order (top to bottom) by retrieving  ( -1 * vertical position)
	ToIntFunction<VerticalBoxPosition> inversePosition = (verticalBoxPostion)-> - verticalBoxPostion.getPosition();

	/**
	 * Return the list. Used for testing only
	 * @return
	 */
	public VBPList getBoxPositionList() {
		return theList;
	}
	
	/**
	 * Iterator to find the next position and the set of boxes with top or bottom heights that match that position 
	 * @return HorizontalBoxList
	 */
	public Iterator<VBPList> getBoxesByVerticalPositionIterator() {
		return new PositionIterator(theList);
	}
	
	/*
	 *  Inner Classes
	 */
	
	private static final class PositionIterator implements Iterator<VBPList> {
		private ListIterator<VerticalBoxPosition> verticalBoxPositions;
	    private int verticalPos;

	    public PositionIterator(VBPList theList) {
	    	this.verticalBoxPositions = theList.listIterator();
	    }

	    public boolean hasNext() {
	        return verticalBoxPositions.hasNext();
	    }

	    public VBPList next() {
	        if(!this.hasNext()) {
	            throw new NoSuchElementException();
	        }
	    	VBPList boxesAtPosition = new VBPList();
	    	if (verticalBoxPositions.hasNext()) {
	    		VerticalBoxPosition boxPos = verticalBoxPositions.next();
	    		verticalPos = boxPos.getPosition();
	    		boxesAtPosition.add(boxPos);
	    	
		        while(verticalBoxPositions.hasNext()) {
		    		boxPos = verticalBoxPositions.next();
		    		if (verticalPos == boxPos.getPosition()){
			    		boxesAtPosition.add(boxPos);		    			
		    		} else {
		    			// box has a new position, go back and so it returns with the next iteration
		    			verticalBoxPositions.previous();
		    			break;
		    		}	
		        }
	    	}
    		return boxesAtPosition;
	    }

	    public void remove() {
	        throw new UnsupportedOperationException();
	    }
	}
	
	// Storage class for the VerticalBoxPosition List with a short name
	public final static class VBPList extends ArrayList<VerticalBoxPosition> {

		private static final long serialVersionUID = 1L;

		public VBPList () {
			super();	
		}
		
		public VBPList (int size) {
			super(size);	
		}	
	}


}