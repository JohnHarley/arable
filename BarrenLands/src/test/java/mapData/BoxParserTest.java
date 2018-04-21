package mapData;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
//import org.apache.commons.io;


public class BoxParserTest {
	
	static final private String noBoxes1 = "{}";
	static final private String noBoxes2 = "\n { \n} \n";
	static final private String oneBox = " { \"1 2 3 4\" } ";
	static final private String twoBoxes = " { \"1 2 3 4\", \"5 6 7 8\" } ";
	static final private String twoBoxesWithBreaks = "\n { \n \"1 2 3 4\"\n, \"5 6\n 7 8\" \n} ";


	private BoxParser createBoxParser(String input) {
		InputStream in = toInputStream(input);
		return new BoxParser(in);
	}
	
	public InputStream toInputStream(String input) {
		return new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
	}
	
	@Rule
	    public ExpectedException thrown = ExpectedException.none();
	
	// ReadStringBetweenBrackets Happy path
	@Test
	public void testReadStringBetweenBrackets_noBoxes1() throws BoxParserException {		
		BoxParser parser = createBoxParser(noBoxes1);
		String betweenBrackets = parser.readStringBetweenBrackets();
		assertEquals("ReadStringBetweenBrackets","", betweenBrackets);	
	}
	
	@Test
	public void testReadStringBetweenBrackets_noBoxes2() throws BoxParserException {		
		BoxParser parser = createBoxParser(noBoxes2);
		String betweenBrackets = parser.readStringBetweenBrackets();
		assertEquals("ReadStringBetweenBrackets","", betweenBrackets);	
	}
	
	@Test
	public void testReadStringBetweenBrackets_oneBox() throws BoxParserException {		
		BoxParser parser = createBoxParser(oneBox);
		String betweenBrackets = parser.readStringBetweenBrackets();
		assertEquals("ReadStringBetweenBrackets","\"1 2 3 4\"", betweenBrackets.trim());	
	}
	
	@Test
	public void testReadStringBetweenBrackets_twoBoxes() throws BoxParserException {		
		BoxParser parser = createBoxParser(twoBoxes);
		String betweenBrackets = parser.readStringBetweenBrackets();
		assertEquals("ReadStringBetweenBrackets","\"1 2 3 4\", \"5 6 7 8\"", betweenBrackets.trim());	
	}

	@Test
	public void testReadStringBetweenBrackets_twoBoxesWithBreaks() throws BoxParserException {		
		BoxParser parser = createBoxParser(twoBoxesWithBreaks);
		String betweenBrackets = parser.readStringBetweenBrackets();
		assertEquals("ReadStringBetweenBrackets","\"1 2 3 4\", \"5 6 7 8\"", betweenBrackets.trim());	
	}
	
	// ReadStringBetweenBrackets - Bad Input
	@Test (expected = BoxParserException.class)
	public void testReadStringBetweenBrackets_EmptyString() throws BoxParserException {		
		BoxParser parser = createBoxParser("");
		String betweenBrackets = parser.readStringBetweenBrackets();
		assertEquals("ReadStringBetweenBrackets","", betweenBrackets.trim());	
	}
	
	@Test (expected = BoxParserException.class)
	public void testReadStringBetweenBrackets_MissingClosingBrace() throws BoxParserException {		
		BoxParser parser = createBoxParser( "{ \"1 2 3 4\"");
		String betweenBrackets = parser.readStringBetweenBrackets();
		assertEquals("ReadStringBetweenBrackets","\"1 2 3 4\"", betweenBrackets.trim());	
	}
	
	// ParseBoxStrings Happy Path
	@Test
	public void testParseBoxStrings_noBoxes() {
		BoxParser parser = createBoxParser("");
		String[] boxes = parser.parseBoxStrings("");
		
		String[] expecteds = new String[] {};
		assertArrayEquals("no boxes", expecteds, boxes);
	}
	
	@Test
	public void testParseBoxStrings_oneBox() {
		BoxParser parser = createBoxParser("");
		String[] boxes = parser.parseBoxStrings(" \"1 2 3 4\" ");
		
		String[] expecteds = new String[] {"1 2 3 4"};
		assertArrayEquals("one box", expecteds, boxes);
	}
	
	@Test
	public void testParseBoxStrings_twoBoxes() {
		BoxParser parser = createBoxParser("");
		String[] boxes = parser.parseBoxStrings("\"1 2 3 4\", \"5 6 7 8\"");
		
		String[] expecteds = new String[] {"1 2 3 4", "5 6 7 8"};
		assertArrayEquals("two Boxes", expecteds, boxes);
	}
	
	@Test
	public void testParseBoxStrings_twoBoxesExtraSpaces() {
		BoxParser parser = createBoxParser("");
		String[] boxes = parser.parseBoxStrings(" \" 1 2 3 4 \" , \" 5 6 7 8 \" ");
		
		String[] expecteds = new String[] {" 1 2 3 4 ", " 5 6 7 8 "};
		assertArrayEquals("two Boxes Extra Spaces", expecteds, boxes);
	}
	
	@Test
	public void testParseBoxStrings_threeBoxes() {
		BoxParser parser = createBoxParser("");
		String[] boxes = parser.parseBoxStrings("\"1 2 3 4\", \"5 6 7 8\", \"9 10 11 12\"");
		
		String[] expecteds = new String[] {"1 2 3 4", "5 6 7 8", "9 10 11 12"};
		assertArrayEquals("three boxes", expecteds, boxes);
	}
	
	@Test
	public void testConstructBoxesOne() throws BoxParserException {
		BoxParser parser = createBoxParser("");
		String[] boxStrings = new String[]{"1 2 3 4"};
		List<Box> boxes = parser.constructBoxes(boxStrings);
		Box expected = new Box(1, 2, 3, 4);
		assertEquals("one box length", 1, boxes.size());
		assertEquals("one box", expected, boxes.get(0));
	}
	
	@Test
	public void testConstructBoxesTwo() throws BoxParserException {
		BoxParser parser = createBoxParser("");
		String[] boxStrings = new String[]{"1 2 3 4", " 5  6  7  8 "};
		List<Box> boxes = parser.constructBoxes(boxStrings);
		Box expected = new Box(1, 2, 3, 4);
		assertEquals("one box length", 2, boxes.size());
		assertEquals("one box", expected, boxes.get(0));
		expected = new Box(5, 6, 7, 8);
		assertEquals("one box", expected, boxes.get(1));
	}
	
	@Test
	public void testReadMap() {
		fail("Not yet implemented");
	}


	@Test
	public void testListAreas() {
		fail("Not yet implemented");
	}

}
