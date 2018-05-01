package arableLand;

import mapData.Box;

public class BoxPosition {

	private final boolean isStartOfBarren;
	private final int position;
	private final Box box;
	
	/**
	 * 
	 * @param isStartOfBarren
	 * @param position
	 * @param box
	 */
	public BoxPosition(int position, boolean isStartOfBarren, Box box) {
		this.isStartOfBarren = isStartOfBarren;
		this.position = position;
		this.box= box;
	}

	public boolean isStartOfBarren() {
		return isStartOfBarren;
	}

	public int getPosition() {
		return position;
	}

	public Box getBox() {
		return box;
	}

}
