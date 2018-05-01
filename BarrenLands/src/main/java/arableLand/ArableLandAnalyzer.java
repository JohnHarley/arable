package arableLand;

import java.util.ArrayList;

import mapData.Box;
import mapData.LandMap;
import mapData.Shape;

/**
 *  The ArableLandAnalyzer generates the set arable land areas within the map by analyzing the barren land 
 *  box data. An Arable land area is a set of non-overlapping boxes that describe a contiguous area. 
 *  Areas do not touch each other across a line boundary, but can share a corner point.  
 *  
 *  No assumptions are made regarding the barren land box data, other than the coordinates are integers.
 *  Boxes my overlap or be partially or wholly outside the area.
 *  
 *  
 * @author John Harley
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
		verticalPositions.addTopAndBottomPositions(getTopBorderWall(land.border));
		verticalPositions.addTopAndBottomPositions(getBottomBorderWall(land.border));
		verticalPositions.addTopAndBottomPositions(getLeftBorderWall(land.border));
		verticalPositions.addTopAndBottomPositions(getRightBorderWall(land.border));
		verticalPositions.sort();
		
		// Keep a list of arable boxes that are partially created
		ArrayList<Box> partialArableBoxes = new ArrayList<>();
		// For each vertical position, keep any new arable boxes separate from the existing partialArableBoxes 
		ArrayList<Box> newArableBoxes = new ArrayList<>();
		// For each vertical position, keep list of closed arable boxes 
		ArrayList<Box> closedArableBoxes = new ArrayList<>();


		// Keep a list of barren boxes that are active at the current vertical position
		HorizontalBoxPositionList activeBarrenBoxes = new HorizontalBoxPositionList(land);

		// start at top left corner
		// for each vertical position found, get the boxes with top or bottom at that position
		for (BoxPositionList boxesAtPosition : verticalPositions) {

			int vPosition = boxesAtPosition.get(0).getPosition();

			if ( (vPosition <= land.border.getTop()) && (vPosition >= land.border.getBottom())) {
				
				newArableBoxes.clear();
				closedArableBoxes.clear();

				for (BoxPosition verticalBoxPosition : boxesAtPosition) {		
					if (verticalBoxPosition.isStartOfBarren()) {
						// for barrenBoxes that start at this vertical position and add to activebarrenBoxes 
						activeBarrenBoxes.addLeftAndRightPosition(verticalBoxPosition.getBox());
					} else {
						// for barrenBoxes that end at this vertical position and remove from activebarrenBoxes
						activeBarrenBoxes.remove(verticalBoxPosition.getBox());
					}
				}
				activeBarrenBoxes.sort();

				// scan activebarrenBoxes from left side to right side to find open area line segments going down
				// If this is bottom of map, treat as having no empty line segments
				int lineStart = 0;
				int lineEnd = 0;
				boolean isLineEnded = false;
				int barrenCount = 1;
				int lastBarrenCount = 1;

				for (BoxPositionList horizontalBoxPositionList : activeBarrenBoxes) {
					// horizontalBoxPositionList is the list of boxes with a top corner at the same point
					int hPosition = horizontalBoxPositionList.get(0).getPosition();

					if ( (hPosition >= land.border.getLeft()) && (hPosition <= land.border.getRight())) {

						for (BoxPosition boxPosition : horizontalBoxPositionList) {
							if (boxPosition.isStartOfBarren()) {
								barrenCount++;
							} else {
								barrenCount--;
							}
						}

						// Check if we transitioned from or to arable section. If barren lands join or overlap, we could have transition between two barren sections.
						isLineEnded = (barrenCount == 0) || (lastBarrenCount == 0);						
						if (isLineEnded) {
							lineEnd = hPosition;

							// Determine if line segment closes partialArableBoxes above.
							boolean closeArableBoxesAbove = true;
							
							if (barrenCount > 0) {
								// At right corner of new arable box going down.
								// compare open area line segments to existing partialBoxes horizontal dimensions
								// if open area line segment is same as existing partialBox, keep it and don't create new one 
								// else has different start or end than line segment above, start new partialBox
								if (matchesHorizontally(partialArableBoxes, lineStart, lineEnd)) {
									closeArableBoxesAbove = false;
								} else {
									Box arableBox = new Box(0, lineStart, vPosition, lineEnd);
									newArableBoxes.add(arableBox);
									closeArableBoxesAbove(partialArableBoxes, closedArableBoxes, vPosition, lineStart, lineEnd);	
								}
							} else {
								closeArableBoxesAbove(partialArableBoxes, closedArableBoxes, vPosition, lineStart, lineEnd);	
							}

							if (closeArableBoxesAbove) {
								// Close all existing partialBoxes that overlap line segment
							}

							lineStart = lineEnd;
							lastBarrenCount = barrenCount;
						}
					}
				}
				
				partialArableBoxes.addAll(newArableBoxes);


				// Assign shapes to new boxes. Add box to shape above and merge areas if new box connects 2 or more closed boxes above.
				for (Box newBox: newArableBoxes) {
					int closedCount = 0;
					for (Box closedBox: closedArableBoxes) {
						if (overlapsHorizontally(newBox,closedBox)) {
							closedCount++;

							if (closedCount == 1) {
								// add box to shape above it
								newBox.setShape(closedBox.getShape());
							} else if  (closedCount > 1) {
								// Merge shapes if new box connects 2 or more closed boxes above.
								// TODO move to Shape.mergeShape()
								Shape shapeToAbsorb = closedBox.getShape();
								newBox.getShape().merge(shapeToAbsorb);
								land.arrableAreas.remove(shapeToAbsorb);
							}
						}
					}
					if (closedCount ==0) {
						// No shapes above so create new shape
						Shape shape = new Shape();
						land.arrableAreas.add(shape);
						shape.add(newBox);
					}

				}

			}
		}
	}

	private void closeArableBoxesAbove(ArrayList<Box> partialArableBoxes, ArrayList<Box> closedArableBoxes,
			int vPosition, int lineStart, int lineEnd) {
		for (Box arableBox : partialArableBoxes) {
			if (overlapsHorizontally(arableBox, lineStart, lineEnd)) {
				arableBox.setBottom(vPosition);
				closedArableBoxes.add(arableBox);
			}
		}
	}

	private boolean matchesHorizontally(ArrayList<Box> partialArableBoxes, int lineStart, int lineEnd) {
		boolean matchesHorizontally = false;
		for (Box partialBox: partialArableBoxes) {
			if (partialBox.getLeft() == lineStart && partialBox.getRight() == lineEnd) {
				matchesHorizontally = false;
				break;
			}
		}
		return matchesHorizontally;
	}


	private boolean overlapsHorizontally(Box arableBox, int lineStart, int lineEnd) {
		boolean overlapLeft = ((arableBox.getLeft() >= lineStart) && (arableBox.getLeft() < lineEnd));
		boolean overlapRight = ((arableBox.getRight() > lineStart) && (arableBox.getRight() <= lineEnd));
		return overlapLeft || overlapRight;
	}
	

	private boolean overlapsHorizontally(Box box1, Box box2) {
		return overlapsHorizontally(box1,box2.getLeft(), box2.getRight());
	}
	
	private Box getLeftBorderWall(Box border) {
		return new Box(border.getBottom() - 1, border.getLeft() - 1, border.getTop(), border.getLeft());
	}
	
	private Box getRightBorderWall(Box border) {
		return new Box(border.getBottom() - 1, border.getRight(), border.getTop(), border.getRight() + 1);
	}

	private Box getTopBorderWall(Box border) {
		return new Box(border.getTop(), border.getLeft(), border.getTop() + 1, border.getRight());
	}
	
	private Box getBottomBorderWall(Box border) {
		return new Box(border.getBottom() - 1, border.getLeft(), border.getBottom(), border.getRight());
	}
	
}
