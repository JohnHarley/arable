package arableLand;

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
		cropBarrenLands();
		orderBarrenLands();
		generateArableAreas();
	}

	private void cropBarrenLands() {
		// TODO Auto-generated method stub
		
	}

	private void orderBarrenLands() {
		// TODO Auto-generated method stub
		// sort list of barren land boxes vertically by top and bottom coordinates. Each box has a top 
		// entry in the list and a bottom entry.
		VerticalBoxList verticalBoxes = new VerticalBoxList();
	}
	
	public void generateArableAreas() {
		
		// start at top left corner
		
		// list of arable boxes that are partially created
		HorizontalBoxList partialArableBoxes = new HorizontalBoxList();
		
		// Find vertical positions of all box top and bottoms, as well as the map top and bottom
		// for each vertical position found
		// find horizontal barrenBox list for vertical position
		
		
		// find barrenBoxes that start at this vertical position and add to activebarrenBoxes 
		// find barrenBoxes that end at this vertical position and remove from activebarrenBoxes
		// scan activebarrenBoxes from left side to right side to find open area line segments going down
		//   of this is bottom of map, treat as having no empty line segments
		
		// compare open area line segments to existing partialBoxes horizontal dimensions
			// if open area line segment is same as existing partialBox, keep it and don't create new one 
			// else has different start or end than line segment above, start new partialBox
				// For all existing partialBoxes that overlap line segment
					// Join their areas into one
				// Create new partialArableBox.
				// add partialArableBox to Area above it.
		
		
	}
}
