package arableLand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import mapData.Box;
import mapData.LandMap;
import arableLand.VerticalBoxPositionList.VBPList;


public class VerticalBoxPositionListTest {
	private LandMap map;

	@Before
	public void setUp() throws Exception {
		map = new LandMap();
		map.farmBorder = new Box( 0, 0, 10, 10);
		map.barrenLands = new ArrayList<>();
	}

	@Test
	public void testgetBoxPositionList_1() throws Exception {
		
		Box box = new Box( 1, 2, 3, 4);
		map.barrenLands.add(box);
		
		VerticalBoxPositionList BoxPositions = new VerticalBoxPositionList(map);		

		BoxPositions.sort();
		
		VBPList vbps =  BoxPositions.getBoxPositionList();
		// then
		// Expect border box + added box
		assertEquals("size", 4, vbps.size());

		assertEquals("box", map.farmBorder, vbps.get(3).getBox());
		assertEquals("pos", 0, vbps.get(3).getPosition());
		assertEquals("box", true, vbps.get(3).isTopOfBarren());
		
		assertEquals("box", box, vbps.get(2).getBox());
		assertEquals("pos", 1, vbps.get(2).getPosition());
		assertEquals("box", false, vbps.get(2).isTopOfBarren());
		
		assertEquals("box", box,  vbps.get(1).getBox());
		assertEquals("pos", 3, vbps.get(1).getPosition());
		assertEquals("box", true, vbps.get(1).isTopOfBarren());	
		
		assertEquals("box", map.farmBorder, vbps.get(0).getBox());
		assertEquals("pos", 10, vbps.get(0).getPosition());
		assertEquals("box", false, vbps.get(0).isTopOfBarren());
	}
	
	@Test
	public void testgetBoxPositionList_2() throws Exception {
		
		Box box1 = new Box( 0, 0, 1, 1);
		Box box2 = new Box( 1, 1, 2, 2);
		Box[] barrenBoxes = { box1, box2};
		Collections.addAll(map.barrenLands, barrenBoxes);
		
		VerticalBoxPositionList BoxPositions = new VerticalBoxPositionList(map);		

		BoxPositions.sort();
		
		VBPList vbps =  BoxPositions.getBoxPositionList();
		// then
		// Expect border box + added box
		assertEquals("size", 6, vbps.size());
		
		assertEquals("box", map.farmBorder, vbps.get(0).getBox());
		assertEquals("pos", 10, vbps.get(0).getPosition());
		assertEquals("box", false, vbps.get(0).isTopOfBarren());
		
		assertEquals("box", box2,  vbps.get(1).getBox());
		assertEquals("pos", 2, vbps.get(1).getPosition());
		assertEquals("box", true, vbps.get(1).isTopOfBarren());	
		
		assertEquals("box", box1, vbps.get(2).getBox());
		assertEquals("pos", 1, vbps.get(2).getPosition());
		assertEquals("box", true, vbps.get(2).isTopOfBarren());
		
		assertEquals("box", box2, vbps.get(3).getBox());
		assertEquals("pos", 1, vbps.get(3).getPosition());
		assertEquals("box", false, vbps.get(3).isTopOfBarren());
		
		assertEquals("box", box1, vbps.get(5).getBox());
		assertEquals("pos", 0, vbps.get(5).getPosition());
		assertEquals("box", false, vbps.get(5).isTopOfBarren());
		
		assertEquals("box", map.farmBorder, vbps.get(4).getBox());
		assertEquals("pos", 0, vbps.get(4).getPosition());
		assertEquals("box", true, vbps.get(4).isTopOfBarren());
	}

	@Test
	public void testGetBoxesByVerticalPositionIterator() throws Exception {
		Box box1 = new Box( 0, 0, 1, 1);
		Box box2 = new Box( 1, 1, 2, 2);
		Box[] barrenBoxes = { box1, box2};
		Collections.addAll(map.barrenLands, barrenBoxes);
		
		VerticalBoxPositionList BoxPositions = new VerticalBoxPositionList(map);		
		BoxPositions.sort();
		
		Iterator<VBPList> positionIterator = BoxPositions.getBoxesByVerticalPositionIterator();
		 
		
		VerticalBoxPositionList.VBPList vbps;
		assertTrue("missing box position", positionIterator.hasNext());		
		vbps = (VerticalBoxPositionList.VBPList) positionIterator.next();
		 
		assertEquals("size", 1, vbps.size());

		assertEquals("box", map.farmBorder, vbps.get(0).getBox());
		assertEquals("pos", 10, vbps.get(0).getPosition());
		assertEquals("box", false, vbps.get(0).isTopOfBarren());
				 
		assertTrue("missing box position", positionIterator.hasNext());		
		vbps = (VerticalBoxPositionList.VBPList) positionIterator.next();
		 
		assertEquals("size", 1, vbps.size());	
		
		assertEquals("box", box2,  vbps.get(0).getBox());
		assertEquals("pos", 2, vbps.get(0).getPosition());
		assertEquals("box", true, vbps.get(0).isTopOfBarren());	
		
		assertTrue("missing box position", positionIterator.hasNext());		
		vbps = (VerticalBoxPositionList.VBPList) positionIterator.next();
		
		assertEquals("size", 2, vbps.size());	

		assertEquals("box", box1, vbps.get(0).getBox());
		assertEquals("pos", 1, vbps.get(0).getPosition());
		assertEquals("box", true, vbps.get(0).isTopOfBarren());
		
		assertEquals("box", box2, vbps.get(1).getBox());
		assertEquals("pos", 1, vbps.get(1).getPosition());
		assertEquals("box", false, vbps.get(1).isTopOfBarren());

		assertTrue("missing box position", positionIterator.hasNext());		
		vbps = (VerticalBoxPositionList.VBPList) positionIterator.next();
		
		assertEquals("size", 2, vbps.size());	
		
		assertEquals("box", box1, vbps.get(1).getBox());
		assertEquals("pos", 0, vbps.get(1).getPosition());
		assertEquals("box", false, vbps.get(1).isTopOfBarren());
		
		assertEquals("box", map.farmBorder, vbps.get(0).getBox());
		assertEquals("pos", 0, vbps.get(0).getPosition());
		assertEquals("box", true, vbps.get(0).isTopOfBarren());
	}
}
