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
public class VBPList extends TreeSet<Box> {
	
	public VBPList(Collection<Box> boxes) {
		super(new HorizontalBoxComparator());
		this.addAll(boxes);
	}
	
	public VBPList() {
		super(new HorizontalBoxComparator());
	}
	
	public VBPList verticalFilter(int verticalPos) {
		VBPList matchedBoxes = new VBPList();
		// TODO
		return matchedBoxes;
	}

}
