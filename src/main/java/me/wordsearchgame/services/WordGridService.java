package me.wordsearchgame.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class WordGridService {

	private class Coordinate {
		int line;
		int row;
		private Coordinate(int line, int row) {
			this.line = line;
			this.row = row;
		}
	}
	
	private enum Direction {
		HORIZONTAL_FORWARD,
		HORIZONTAL_BACKWARD,
		VERTICAL_DOWN,
		VERTICAL_UP,
		DIAGONAL_FORWARD_DOWN,
		DIAGONAL_BACKWARD_UP,
		DIAGONAL_FORWARD_UP,
		DIAGONAL_BACKWARD_DOWN
	}
	
	public char[][] generateGrid(int gridSize, List<String> words) {
		
		char[][] contents = new char[gridSize][gridSize];
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		
		for(int line=0; line<gridSize; line++) {
			for(int row=0; row<gridSize; row++) {
				// create linear list of all coordinates in the grid
				coordinates.add(new Coordinate(line, row));
				contents[line][row] = '_';
			}
		}
		
		Collections.shuffle(coordinates);
		for(String word : words) {
			for(Coordinate coordinate : coordinates) {
				int line = coordinate.line;
				int row = coordinate.row;
				Direction selectedDirection = getDirectionForFit(contents, word, coordinate);
				if(selectedDirection != null) {
					switch(selectedDirection) {
						case HORIZONTAL_FORWARD :
							for(char c : word.toCharArray()) {
								contents[line][row++] = c;
							}
							break;
						case HORIZONTAL_BACKWARD :
							for(char c : word.toCharArray()) {
								contents[line][row--] = c;
							}
							break;
						case VERTICAL_DOWN :
							for(char c : word.toCharArray()) {
								contents[line++][row] = c;
							}
							break;
						case VERTICAL_UP :
							for(char c : word.toCharArray()) {
								contents[line--][row] = c;
							}
							break;
						case DIAGONAL_FORWARD_DOWN :
							for(char c : word.toCharArray()) {
								contents[line++][row++] = c;
							}
							break;
						case DIAGONAL_BACKWARD_UP :
							for(char c : word.toCharArray()) {
								contents[line--][row--] = c;
							}
							break;
						case DIAGONAL_FORWARD_UP :
							for(char c : word.toCharArray()) {
								contents[line--][row++] = c;
							}
							break;
						case DIAGONAL_BACKWARD_DOWN :
							for(char c : word.toCharArray()) {
								contents[line++][row--] = c;
							}
							break;
					}
					break;
				}
			}
		}
		this.randomFillGrid(contents);
		return contents;
	}
	
	public void displayGrid(char[][] contents) {
		int gridSize = contents[0].length;
		for(int i=0; i<gridSize; i++) {
			for(int j=0; j<gridSize; j++) {
				System.out.print(contents[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	private void randomFillGrid(char[][] contents) {
		int gridSize = contents[0].length;
		String allCapsLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int i=0; i<gridSize; i++) {
			for(int j=0; j<gridSize; j++) {
				if(contents[i][j] == '_') {
					int randomIndex = ThreadLocalRandom.current().nextInt(0, allCapsLetters.length());
					contents[i][j] = allCapsLetters.charAt(randomIndex);
				}
			}
		}
	}
	
	private Direction getDirectionForFit(char[][] contents, String word, Coordinate coordinate) {
		List<Direction> directions = Arrays.asList(Direction.values());
		// randomize list of directions
		Collections.shuffle(directions);
		for(Direction direction : directions) {
			if(doesFit(contents, word, coordinate, direction)) {
				return direction;
			}
		}
		return null;
		
	}
	
	private boolean doesFit(char[][] contents, String word, Coordinate coordinate, Direction direction) {
		int gridSize = contents[0].length;
		int wordLength = word.length();
		switch (direction) {
			case HORIZONTAL_FORWARD :
				if(coordinate.row + wordLength > gridSize) return false;
				for(int wordCharPos = 0; wordCharPos <wordLength; wordCharPos++) {
					if(contents[coordinate.line][coordinate.row + wordCharPos] != '_') return false;
				}
				break;
			case HORIZONTAL_BACKWARD :
				if(coordinate.row < wordLength) return false;
				for(int wordCharPos = 0; wordCharPos <wordLength; wordCharPos++) {
					if(contents[coordinate.line][coordinate.row - wordCharPos] != '_') return false;
				}
				break;
			case VERTICAL_DOWN :
				if(coordinate.line + wordLength > gridSize) return false;
				for(int wordCharPos = 0; wordCharPos <wordLength; wordCharPos++) {
					if(contents[coordinate.line + wordCharPos][coordinate.row] != '_') return false;
				}
				break;
			case VERTICAL_UP :
				if(coordinate.line < wordLength) return false;
				for(int wordCharPos = 0; wordCharPos <wordLength; wordCharPos++) {
					if(contents[coordinate.line - wordCharPos][coordinate.row] != '_') return false;
				}
				break;
			case DIAGONAL_FORWARD_DOWN :
				if(coordinate.row + wordLength > gridSize || coordinate.line + wordLength > gridSize) return false;
				for(int wordCharPos = 0; wordCharPos <wordLength; wordCharPos++) {
					if(contents[coordinate.line + wordCharPos][coordinate.row + wordCharPos] != '_') return false;
				}
				break;
			case DIAGONAL_BACKWARD_UP :
				if(coordinate.row < wordLength || coordinate.line < wordLength) return false;
				for(int wordCharPos = 0; wordCharPos <wordLength; wordCharPos++) {
					if(contents[coordinate.line - wordCharPos][coordinate.row - wordCharPos] != '_') return false;
				}
				break;
			case DIAGONAL_FORWARD_UP :
				if(coordinate.row + wordLength > gridSize || coordinate.line < wordLength) return false;
				for(int wordCharPos = 0; wordCharPos <wordLength; wordCharPos++) {
					if(contents[coordinate.line - wordCharPos][coordinate.row + wordCharPos] != '_') return false;
				}
				break;
			case DIAGONAL_BACKWARD_DOWN :
				if(coordinate.row < wordLength || coordinate.line + wordLength > gridSize) return false;
				for(int wordCharPos = 0; wordCharPos <wordLength; wordCharPos++) {
					if(contents[coordinate.line + wordCharPos][coordinate.row - wordCharPos] != '_') return false;
				}
				break;
		}
		return true;
	}
}
