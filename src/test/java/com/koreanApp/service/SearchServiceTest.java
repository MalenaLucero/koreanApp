package com.koreanApp.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.koreanApp.payload.SearchResponse;
import com.koreanApp.util.InvalidSearchWordException;
import com.koreanApp.util.InvalidTypeException;

@SpringBootTest
public class SearchServiceTest {
	@Autowired SearchService searchService;
	
	private Integer idArtist;
	private String word;
	private String videoType;
	
	@BeforeEach
	public void setSearchParams() {
		idArtist = 3;
		word = "는";
		videoType = "RADIO";
	}

	@Test
	public void searchLyricByArtistAndWordTest() throws InvalidSearchWordException {
		idArtist = 5;
		List<SearchResponse> response = searchService.searchLyric(idArtist, word);
		String originalText = getFirstOriginalText(response);
		assertTrue(originalText.contains(word));
	}
	
	@Test
	public void searchLyricByWordTest() throws InvalidSearchWordException {
		idArtist = null;
		List<SearchResponse> response = searchService.searchLyric(idArtist, word);
		String originalText = getFirstOriginalText(response);
		assertTrue(originalText.contains(word));
	}
	
	@Test
	public void searchTextByArtistAndWordTest() throws InvalidSearchWordException {
		word = "스튜디오";
		List<SearchResponse> response = searchService.searchText(idArtist, word);
		String originalText = getFirstOriginalText(response);
		assertTrue(originalText.contains(word));
	}
	
	@Test
	public void searchTextByWordTest() throws InvalidSearchWordException {
		word = "스튜디오";
		List<SearchResponse> response = searchService.searchText(idArtist, word);
		String originalText = getFirstOriginalText(response);
		assertTrue(originalText.contains(word));
	}
	
	@Test
	public void searchVideoByWordTest() throws InvalidSearchWordException, InvalidTypeException {
		idArtist = null;
		videoType = null;
		List<SearchResponse> response = searchService.searchVideo(idArtist, word, videoType);
		String originalText = getFirstOriginalText(response);
		assertTrue(originalText.contains(word));
	}
	
	@Test
	public void searchVideoByArtistAndWordTest() throws InvalidSearchWordException, InvalidTypeException {
		videoType = null;
		List<SearchResponse> response = searchService.searchVideo(idArtist, word, videoType);
		String originalText = getFirstOriginalText(response);
		assertTrue(originalText.contains(word));
	}
	
	@Test
	public void searchVideoByArtistAndWordAndTypeTest() throws InvalidSearchWordException, InvalidTypeException {
		List<SearchResponse> response = searchService.searchVideo(idArtist, word, videoType);
		String originalText = getFirstOriginalText(response);
		assertTrue(originalText.contains(word));
	}
	
	private String getFirstOriginalText(List<SearchResponse> response) {
		List<String> linesMapKeys = new ArrayList<String>(response.get(0).getLines().keySet());
		return response.get(0).getLines().get(linesMapKeys.get(0))[0];
	}
}
