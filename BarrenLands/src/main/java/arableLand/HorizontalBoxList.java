package arableLand;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import mapData.Box;

/**
 * List of boxes sorted by box.left position.
 * Provides verticalFilter method to return list of boxes that cover a vertical position.
 * @author johnh
 *
 */
public class HorizontalBoxList extends TreeSet<Box> {
	
	public HorizontalBoxList(Collection<Box> boxes) {
		super(new HorizontalBoxComparator());
		this.addAll(boxes);
	}
	
	public HorizontalBoxList() {
		super(new HorizontalBoxComparator());
	}
	
	public HorizontalBoxList verticalFilter(int verticalPos) {
		HorizontalBoxList matchedBoxes = new HorizontalBoxList();
		// TODO
		return matchedBoxes;
	}

}
