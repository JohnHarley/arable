package arableLand;

import mapData.Box;

public class HorizontalBoxPosition {

	final boolean isLeftOfBarren;
	final int position;
	final Box box;
	
	/**
	 * 
	 * @param isLeftOfBarren
	 * @param position
	 * @param box
	 */
	public HorizontalBoxPosition(int position, boolean isLeftOfBarren, Box box) {
		this.isLeftOfBarren = isLeftOfBarren;
		this.position = position;
		this.box= box;
	}

	public boolean isLeftOfBarren() {
		return isLeftOfBarren;
	}

	public int getPosition() {
		return position;
	}

	public Box getBox() {
		return box;
	}

}
