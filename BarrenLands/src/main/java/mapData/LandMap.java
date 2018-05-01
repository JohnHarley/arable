package mapData;
import java.util.ArrayList;
import java.util.List;

public class LandMap {
	
	public Box border = new Box(0, 0, 399, 599); // default farm size 
	
	public List<Box> barrenLands;
	
	public List<Shape> arrableAreas = new ArrayList<>();
}
