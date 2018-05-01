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

public class BoxPositionList extends ArrayList<BoxPosition> {

	private static final long serialVersionUID = 1L;

	public BoxPositionList () {
		super();	
	}
	
	public BoxPositionList (int size) {
		super(size);	
	}
	
	public Iterator<BoxPositionList> getBoxesByPositionIterator() {
		return new PositionIterator(this);
	}
	
	/*
	 *  Inner Classes
	 */
	
	private static final class PositionIterator implements Iterator<BoxPositionList> {
		private ListIterator<BoxPosition> boxPositions;
	    private int verticalPos;

	    public PositionIterator(BoxPositionList theList) {
	    	this.boxPositions = theList.listIterator();
	    }

	    public boolean hasNext() {
	        return boxPositions.hasNext();
	    }

	    public BoxPositionList next() {
	        if(!this.hasNext()) {
	            throw new NoSuchElementException();
	        }
	        BoxPositionList boxesAtPosition = new BoxPositionList();
	    	if (boxPositions.hasNext()) {
	    		BoxPosition boxPos = boxPositions.next();
	    		verticalPos = boxPos.getPosition();
	    		boxesAtPosition.add(boxPos);
	    	
		        while(boxPositions.hasNext()) {
		    		boxPos = boxPositions.next();
		    		if (verticalPos == boxPos.getPosition()){
			    		boxesAtPosition.add(boxPos);		    			
		    		} else {
		    			// box has a new position, go back and so it returns with the next iteration
		    			boxPositions.previous();
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

}