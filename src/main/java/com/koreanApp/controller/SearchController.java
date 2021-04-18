package com.koreanApp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.koreanApp.payload.SearchRequest;
import com.koreanApp.payload.SearchResponse;
import com.koreanApp.service.SearchService;
import com.koreanApp.util.InvalidSearchWordException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/public/search")
public class SearchController {
	@Autowired SearchService searchService;
	
	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<Object> search(@RequestBody SearchRequest searchRequest) {
		try {
			Map<String, List<SearchResponse>> searchResult = new HashMap<String, List<SearchResponse>>();
			for (int i = 0; i < searchRequest.getSourceTypes().length; i++) {
				switch(searchRequest.getSourceTypes()[i].name()) {
				case "LYRIC":
					List<SearchResponse> lyricResult = searchService.searchLyric(searchRequest.getIdArtist(), searchRequest.getWord());
					searchResult.put("Lyric", lyricResult);
					break;
				case "VIDEO":
					//List<SearchResponse> videoResult = searchService.searchVideo(searchRequest.getIdArtist(), searchRequest.getWord());
					//searchResult.put("Video", videoResult);
					break;
				case "TEXT":
					List<SearchResponse> textResult = searchService.searchText(searchRequest.getIdArtist(), searchRequest.getWord());
					searchResult.put("Text", textResult);
					break;
				}
			}
			return new ResponseEntity<Object>(searchResult, HttpStatus.OK);
		} catch (InvalidSearchWordException ex){
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception ex){
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/lyric")
	public @ResponseBody ResponseEntity<Object> searchLyric(@RequestParam String word, @RequestParam(required = false) Integer idArtist, @RequestParam(required = false) String type){
		try {
			List<SearchResponse> lyricResult = searchService.searchLyric(idArtist, word);
			return new ResponseEntity<Object>(lyricResult, HttpStatus.OK);
		} catch (InvalidSearchWordException ex){
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception ex){
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/video")
	public @ResponseBody ResponseEntity<Object> searchVideo(@RequestParam String word, @RequestParam(required = false) Integer idArtist, @RequestParam(required = false) String type){
		try {
			List<SearchResponse> videoResult = searchService.searchVideo(idArtist, word, type);
			return new ResponseEntity<Object>(videoResult, HttpStatus.OK);
		} catch (InvalidSearchWordException ex){
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception ex){
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/text")
	public @ResponseBody ResponseEntity<Object> searchText(@RequestParam String word, @RequestParam(required = false) Integer idArtist, @RequestParam(required = false) String type){
		try {
			List<SearchResponse> textResult = searchService.searchText(idArtist, word);
			return new ResponseEntity<Object>(textResult, HttpStatus.OK);
		} catch (InvalidSearchWordException ex){
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception ex){
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
