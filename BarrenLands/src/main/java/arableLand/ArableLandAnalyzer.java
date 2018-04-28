package arableLand;

import java.util.Iterator;
import arableLand.VerticalBoxPositionList.VBPList;
import mapData.Box;
import mapData.LandMap;

/**
 *  The ArableLandAnalyzer generates the set arable land areas within the map by analyzing the barren land 
 *  box data. An Arable land area is a set of non-overlapping boxes that describe a contiguous area. 
 *  Areas do not touch each other across a line boundary, but can share a corner point.  
 *  
 *  No assumptions are made regarding the barren land box data, other than the coordinates are integers.
 *  Boxes my overlap or be partially or wholly outside the area.
 *  
 *  
 * @author johnh
 *
 */
public class ArableLandAnalyzer {
	private LandMap land;
	
	ArableLandAnalyzer(LandMap land) {
		this.land = land;
	}
	
	public void Analyze() {
		cropBarrenLands(land);
		generateArableAreas(land);
	}

	/**
	 * Crop barren land boxes to be within the border of the map. 
	 * Delete a barren land if it is completely outside of the map.
	 * @param land
	 */
	private void cropBarrenLands(LandMap land) {
		// TODO Auto-generated method stub
		
	}

	
	/* Outline of algorithm		
	  Find vertical positions of all box top and bottoms, as well as the map top and bottom
		 for each vertical position found
		 find horizontal barrenBox list for vertical position
				
		 find barrenBoxes that start at this vertical position and add to activebarrenBoxes 
		 find barrenBoxes that end at this vertical position and remove from activebarrenBoxes
		 scan activebarrenBoxes from left side to right side to find open area line segments going down
		   of this is bottom of map, treat as having no empty line segments
		
		 compare open area line segments to existing partialBoxes horizontal dimensions
			 if open area line segment is same as existing partialBox, keep it and don't create new one 
			 else has different start or end than line segment above, start new partialBox
				 For all existing partialBoxes that overlap line segment
					 Join their areas into one
				 Create new partialArableBox.
				 add partialArableBox to Area above it.	
	 */
	
	public void generateArableAreas(LandMap land) {
		// Find vertical positions of all box top and bottoms, as well as the map top and bottom
		VerticalBoxPositionList verticalPositions = new VerticalBoxPositionList(land);

		// Keep a list of arable boxes that are partially created
		VBPList partialArableBoxes = new VBPList();
		
		// Keep a list of barren boxes that are active at the current position
		VBPList activeBarrenBoxes = new VBPList();
		
		// start at top left corner
		// for each vertical position found, get the boxes with top or bottom at that position
		Iterator<VerticalBoxPositionList.VBPList> iterator = verticalPositions.getBoxesByVerticalPositionIterator();
				
		while(iterator.hasNext()) {
			VBPList boxesAtPosition = iterator.next();
			
			for (VerticalBoxPosition verticalBoxPosition : boxesAtPosition) {			

				if (verticalBoxPosition.isTopOfBarren) {
					// for barrenBoxes that start at this vertical position and add to activebarrenBoxes 
					activeBarrenBoxes.add(verticalBoxPosition);
				} else {
					// for barrenBoxes that end at this vertical position and remove from activebarrenBoxes
					activeBarrenBoxes.remove(verticalBoxPosition);
				}
			}
			
			// scan activebarrenBoxes from left side to right side to find open area line segments going down
			// If this is bottom of map, treat as having no empty line segments
			int hPos = 0;
			for (VerticalBoxPosition verticalBoxPosition : activeBarrenBoxes) {
				Box box = verticalBoxPosition.getBox();
				
			}
		
		// compare open area line segments to existing partialBoxes horizontal dimensions
			// if open area line segment is same as existing partialBox, keep it and don't create new one 
			// else has different start or end than line segment above, start new partialBox
				// For all existing partialBoxes that overlap line segment
					// Join their areas into one
				// Create new partialArableBox.
				// add partialArableBox to Area above it.	
		}
		
	}
	

	
}
