package com.koreanApp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreanApp.payload.LyricRequest;
import com.koreanApp.payload.SearchRequest;
import com.koreanApp.payload.SearchResponse;
import com.koreanApp.payload.SimpleSearchRequest;
import com.koreanApp.service.SearchService;

@Controller
@RequestMapping(path = "/api")
public class SearchController {
	@Autowired SearchService searchService;
	
	@GetMapping(path = "/search")
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
}
