import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class describes a 10 by 10 map of areas.
 * @author jackroberts
 *
 */
public class Map {
	
	private Area[][] grid; // 2D array representing 2D grid of cells.
	private final int MAX_MAP_SIZE = 5; // Maximum allowed horiz and vert size of map.
	private final int MAX_DESCRIPTION_LENGTH = 300; // Maximum length of location description
	private final int MIN_LEVEL_NO = 0; // Minimum level number
	private final int MAX_LEVEL_NO = 100; // Maximum level number
	private final int MAX_LENGTH_MONSTER_NAME = 100;
	
	/**
	 * Constructor for Map(). Initialises a blank map, or a map loaded from file.
	 */
	public Map() {
		generateBlankMap();
		//Define file name
		String mapFileName = "world.txt";
		//Try creating file and catch if file not found
		try {
			File mapFile = new File(mapFileName);
			Scanner readFile = new Scanner(mapFile);
			//Use an ArrayList to read in the split strings from the file
			ArrayList<String[]> mapLines = new ArrayList<String[]>();
			while (readFile.hasNextLine()) {
				//Split input file by "/" delimiter
				String[] splitAreaFile = readFile.nextLine().split("/");
				//Add to ArrayList
				mapLines.add(splitAreaFile);
			}
			readFile.close();
			// Check map provided can fit in the game
			if (mapLines.size() > MAX_MAP_SIZE * MAX_MAP_SIZE) {
				throw new IllegalArgumentException("Map file too large!! File should contain no more than 25 lines.");
			}
			//Pass to loadMap method to read in file
			loadMap(mapLines);
			
		} catch (FileNotFoundException e) {
			// Error message
			System.out.println("Error: File " + mapFileName + " not found!!");
		}
	}
	/**
	 * Private method to read in lines from the file, which have been passed to an ArrayList.
	 * @param mapLines is the ArrayList holding the map data.
	 */
	private void loadMap(ArrayList<String[]> mapLines) {
		int horizNum = 0; // Horizontal grid number
		int vertNum = 0; // Vertical grid number
		int lineNo = 1; // Starting line number
		
		// Iterate through each line, create an area and pass to the grid array which stores the map details
		for (int i = 0; i < mapLines.size(); i++) {
			// Handle errors
			// Handle error if location description is more than the maximum allowed length
			if (mapLines.get(i)[0].length() > MAX_DESCRIPTION_LENGTH) {
				throw new IllegalArgumentException("Error: (Line No. " + lineNo
						+ ") Location description should be no longer than 300 characters.");
			// Handle error if level provided is outside of the allowable boundaries
			} else if (Integer.parseInt(mapLines.get(i)[1]) < MIN_LEVEL_NO || Integer.parseInt(mapLines.get(i)[1]) > MAX_LEVEL_NO) {
				throw new IllegalArgumentException("Error: (Line No. " + lineNo
						+ ") Level must be between 0 and 100.");
			// Handle error if monster name is longer than the maximum allowed length
			} else if (mapLines.get(i)[2].length() >= MAX_LENGTH_MONSTER_NAME) {
				throw new IllegalArgumentException("Error: (Line No. " + lineNo
						+ ") Monster name should be no longer than 100 characters.");
			}
			
			// If monster name is null then create area without a monster
			if (mapLines.get(i)[2].equals("null")) {
				Area newArea = new Area(mapLines.get(i)[0],Integer.parseInt(mapLines.get(i)[1]),null);
				grid[horizNum][vertNum] = newArea;
			// Otherwise create an area with a monster
			} else {
				Monster newMonster = new Monster(Integer.parseInt(mapLines.get(i)[1]),mapLines.get(i)[2]);
				Area newArea = new Area(mapLines.get(i)[0],Integer.parseInt(mapLines.get(i)[1]),newMonster);
				grid[horizNum][vertNum] = newArea;
			}
			
			//Increase vertical/horizontal numbers. If horizontal number less than max then increment
			if (horizNum < (MAX_MAP_SIZE - 1)) {
				horizNum++;
			//If horizontal number at the maximum then set to 0 and increment vertical number
			} else {
				horizNum = 0;
				vertNum++;
			}
			lineNo++;
		}
	}
	
	/**
	 * Provides a description of the area at given coordinates.
	 * @param x The x coordinate of the desired area.
	 * @param y The y coordinate of the desired area.
	 * @return String description of specified area.
	 */

	public String getDescription(int x, int y) {
			return grid[x][y].getDescription();
	}
	
	/**
	 * Initialises this map to a blank map.
	 */
	private void generateBlankMap() {
		
		grid = new Area[MAX_MAP_SIZE][MAX_MAP_SIZE]; //Map grid becomes 2D array of Areas.
		
		//Iterate through map, adding new Area to each cell.
		for (int i = 0; i < MAX_MAP_SIZE; i++) {
			for (int j = 0; j < MAX_MAP_SIZE; j++) {
				//New blank Area gets added to every cell.
				Area blankArea = new Area("You look around. There's nothing here.", 0, null);
				grid[i][j] = blankArea;
			}
		}
	}

	/**
	 * Provides any Monster currently in specified area.
	 * @param x The x coordinate of the desired area.
	 * @param y The y coordinate of the desired area.
	 * @return Actor that is at the specified area. If no Actor is found, returns null.
	 */
	public Monster getMonsterAt(int x, int y) {
		return grid[x][y].getMonster();
	}
	
	/**
	 * Provides the maximum size of the map
	 * @return the constant variable MAX_MAP_SIZE
	 */
	public int getMaxMapSize () {
		return MAX_MAP_SIZE;
	}
}

