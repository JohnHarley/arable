package mapData;
import java.util.List;

public class LandMap {
	
	public Box farmBorder = new Box(0, 0, 399, 599); // default farm size 
	
	public List<Box> barrenLands;
	
	public List<Box> arrableLands;
	
	public List<Area> arrableAreas;
}
