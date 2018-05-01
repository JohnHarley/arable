package mapData;

import java.util.ArrayList;
import java.util.List;

public class Shape extends ArrayList<Box>{
	
	public void merge(Shape shapeToAbsorb) {
		addAll(shapeToAbsorb);
		for (Box box: shapeToAbsorb){
			box.setShape(this);
		}
		shapeToAbsorb.clear(); // remove references to Boxes for garbage collection
	}
	
	public int size() {
		int size = 0;
		for (Box box : this) {
			size += box.area();
		}
		return size;
	}

}
