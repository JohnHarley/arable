package arableLand;

import mapData.Box;

public class VerticalBoxPosition {

	final boolean isTopOfBarren;
	final int position;
	final Box box;
	
	/**
	 * 
	 * @param isTopOfBarren
	 * @param position
	 * @param box
	 */
	public VerticalBoxPosition(int position, boolean isTopOfBarren, Box box) {
		this.isTopOfBarren = isTopOfBarren;
		this.position = position;
		this.box= box;
	}

	public boolean isTopOfBarren() {
		return isTopOfBarren;
	}

	public int getPosition() {
		return position;
	}

	public Box getBox() {
		return box;
	}

}
