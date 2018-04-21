package mapData;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * BoxParser parses a series of box descriptions from an input stream.
 * Assumes that coordinate values obey rule top >= bottom and right >= left.
 * 
 * Line endings may be present anywhere in the box descriptions. 
 * Note that a line ending is not treated as whitespace between numbers, so "12\n34" becomes 1234.
 * 
 * @author johnh
 *
 */
public class BoxParser {

	private Scanner console;
	private OutputStream out;

	public BoxParser(InputStream source) {
		console = new Scanner(source);
	}

	public void readMap(LandMap map) throws BoxParserException {
		String boxListbody = readStringBetweenBrackets();
		String[] boxStrings = parseBoxStrings(boxListbody);
		map.barrenLands = constructBoxes(boxStrings);
	}

	public List<Box> constructBoxes(String[] boxStrings) throws BoxParserException {
		List<Box> boxes = new ArrayList<>();
		Scanner lineScanner;
		for (String boxString : boxStrings) {
			try {
				lineScanner = new Scanner(boxString);
				int bottom = lineScanner.nextInt();
				int left = lineScanner.nextInt();
				int top = lineScanner.nextInt();
				int right = lineScanner.nextInt();

				Box box = new Box(bottom, left, top, right);
				boxes.add(box);
			} catch (Exception e) {
				throw new BoxParserException("Error: Unable to parse box: \"" + boxString + "\"");
			}
		}
		return boxes;
	}

	public String[] parseBoxStrings(String boxListbody) {
		String[] boxStrings = {};
		int firstQuoteIndex = boxListbody.indexOf('"');
		if (firstQuoteIndex > -1) {
			int LastQuoteIndex = boxListbody.lastIndexOf('"');
			if (LastQuoteIndex > -1) {
				boxListbody = boxListbody.substring(firstQuoteIndex + 1, LastQuoteIndex);
				boxStrings = boxListbody.split("\"\\s*,\\s*\"");
			}
		}
		return boxStrings;
	}

	public String readStringBetweenBrackets() throws BoxParserException {
		Scanner lineScanner;
		String searchresult = "";
		// Find the string after the opening quote
		do {
			String line;
			try {
				line = console.nextLine();
			} catch (NoSuchElementException e) {
				throw new BoxParserException("No input found");
			}
			lineScanner = new Scanner(line);
			boolean found = false;
			try {
				searchresult = lineScanner.findInLine("\\{\\s*");
				if (searchresult != null) {
					
					searchresult = lineScanner.nextLine();
					found = true;
				}
			} catch (NoSuchElementException e) {
				found = true;
				if (console.hasNextLine()) {
					searchresult = console.nextLine();
				}
			} finally {
				lineScanner.close();
				if (found) break;
			}
		} while (console.hasNextLine());

		// read blocks into one string
		String boxListbody = searchresult;
		boolean closingBraceFound = false;
		int endMarker = searchresult.indexOf('}');
		closingBraceFound = (endMarker > -1);
		if (closingBraceFound) {
			boxListbody = searchresult.substring(0, endMarker);
		}
		while (!closingBraceFound && console.hasNextLine()) {
			String currentLine = console.nextLine();
			endMarker = currentLine.indexOf('}');
			closingBraceFound = (endMarker > -1);
			if (endMarker > 0) {
				// line with content before '}'
				currentLine = currentLine.substring(endMarker-1);
			} else if (endMarker == 0)  {  
				// line with no content before '}' 
				currentLine = "";
			}
			boxListbody += currentLine;
		}
		if (!closingBraceFound) {
			throw new BoxParserException("No closing brace found");
		}
		return boxListbody;
	}

	public void listAreas(LandMap map) {
		System.out.println("String: ");
	}

}
