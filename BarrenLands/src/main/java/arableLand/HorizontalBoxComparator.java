package arableLand;

import java.util.Comparator;

import mapData.Box;

public class HorizontalBoxComparator implements Comparator<Box> {

	/**
	 * Compares on Box.left, then box.right, then box.top then box.bottom
	 * Only returns 0 if box dimensions are equal, in order to be consistent with equals. 
	 * This allows different shaped boxes with same left position to not be rejected as equal by TreeSet. 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Box arg0, Box arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
