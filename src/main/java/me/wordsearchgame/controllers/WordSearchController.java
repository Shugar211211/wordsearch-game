package me.wordsearchgame.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.wordsearchgame.services.WordGridService;

@RestController
@RequestMapping("/wordgrid")
public class WordSearchController {

	@Autowired
	WordGridService wordGridService;
	
//	@PostMapping
	@GetMapping
	@CrossOrigin(origins="http://localhost:1234")
	public String createWordGrid(@RequestParam int gridSize, @RequestParam List<String> words) {
		char[][] grid = wordGridService.generateGrid(gridSize, words);
		String gridToString = "";
		for(int line = 0; line < gridSize; line++) {
			for(int row = 0; row < gridSize; row++) {
				gridToString += grid[line][row] + " ";
			}
//			gridToString += "\r\n";
		}
		return gridToString;
	}
}
