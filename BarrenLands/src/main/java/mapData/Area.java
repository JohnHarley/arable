package mapData;

import java.util.List;

public class Area {
	
	List<Box> boxes;
	
	public int size() {
		int size = 0;
		for (Box box : boxes) {
			size += box.area();
		}
		return size;
	}

}
