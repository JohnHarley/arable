package mapData;

public class Box {
	int top, bottom, left, right;

	public Box(int bottom, int left, int top, int right) {
		this.bottom = bottom;
		this.left = left;
		this.top = top;
		this.right = right;
	}
	
	@Override
	public final boolean equals(Object object) {
		if (!(object instanceof Box))
			return false;
		Box other = (Box) object;
		return (bottom == other.bottom) && (left == other.left) && (top == other.top) && (right == other.right);
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override 
	public String toString() {
		return "{ " + bottom + " " + left + " " + top + " " + right + " }"; 
	}
	
	// Getters and Setters
	
	public int area() {
		int size = (top - bottom) * (right - left);
		return size;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

}
