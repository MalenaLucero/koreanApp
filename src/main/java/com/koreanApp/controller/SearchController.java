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

import com.koreanApp.payload.LyricRequest;
import com.koreanApp.payload.SearchRequest;
import com.koreanApp.payload.SearchResponse;
import com.koreanApp.service.SearchService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api")
public class SearchController {
	@Autowired SearchService searchService;
	
	@PostMapping(path = "/search")
	public @ResponseBody ResponseEntity<Object> search(@RequestBody SearchRequest searchRequest){
		if(searchRequest.getWord() == null || searchRequest.getWord().length() == 0) {
			return new ResponseEntity<Object>("Search word missing", HttpStatus.BAD_REQUEST);
		} else {
			Map<String, List<SearchResponse>> searchResult = new HashMap<String, List<SearchResponse>>();
			for (int i = 0; i < searchRequest.getSourceTypes().length; i++) {
				switch(searchRequest.getSourceTypes()[i].name()) {
				case "LYRIC":
					LyricRequest lyricRequest = new LyricRequest(searchRequest.getIdArtist(), searchRequest.getWord());
					List<SearchResponse> lyricResult = searchService.searchLyric(lyricRequest);
					searchResult.put("Lyric", lyricResult);
					break;
				case "VIDEO":
					List<SearchResponse> videoResult = searchService.searchVideo(searchRequest.getWord());
					searchResult.put("Video", videoResult);
					break;
				case "TEXT":
					List<SearchResponse> textResult = searchService.searchText(searchRequest.getWord());
					searchResult.put("Text", textResult);
					break;
				}
			}
			return new ResponseEntity<Object>(searchResult, HttpStatus.OK);
		}
	}
	
	@GetMapping(path = "/search/lyric")
	public @ResponseBody ResponseEntity<Object> searchLyric(@RequestParam String word, @RequestParam(required = false) Integer idArtist){
		LyricRequest lyricRequest = new LyricRequest(idArtist, word);
		List<SearchResponse> lyricResult = searchService.searchLyric(lyricRequest);
		return new ResponseEntity<Object>(lyricResult, HttpStatus.OK);
	}
	
	@GetMapping(path = "/search/video")
	public @ResponseBody ResponseEntity<Object> searchVideo(@RequestParam String word, @RequestParam(required = false) Integer idArtist){
		List<SearchResponse> videoResult = searchService.searchVideo(word);
		return new ResponseEntity<Object>(videoResult, HttpStatus.OK);
	}
	
	@GetMapping(path = "/search/text")
	public @ResponseBody ResponseEntity<Object> searchText(@RequestParam String word, @RequestParam(required = false) Integer idArtist){
		List<SearchResponse> textResult = searchService.searchText(word);
		return new ResponseEntity<Object>(textResult, HttpStatus.OK);
	}
}
