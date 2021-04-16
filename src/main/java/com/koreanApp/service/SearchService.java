package com.koreanApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanApp.entity.Lyric;
import com.koreanApp.payload.LyricRequest;
import com.koreanApp.payload.SearchResponse;
import com.koreanApp.payload.TextRequest;
import com.koreanApp.payload.VideoRequest;
import com.koreanApp.entity.Text;
import com.koreanApp.entity.Video;
import com.koreanApp.repository.LyricRepository;
import com.koreanApp.repository.TextRepository;
import com.koreanApp.repository.VideoRepository;

@Service
public class SearchService {
	@Autowired LyricRepository lyricRepository;
	@Autowired VideoRepository videoRepository;
	@Autowired TextRepository textRepository;
	
	public List<SearchResponse> searchLyric(LyricRequest lyricRequest){
		if(lyricRequest.getIdArtist() == null || lyricRequest.getIdArtist() == 0) {
			Iterable<Lyric> lyricsResults = lyricRepository.findByOriginalTextContaining(lyricRequest.getWord());
			return generateSearchResultList(lyricsResults, lyricRequest.getWord());
		} else {
			Iterable<Lyric> lyricsResults = lyricRepository.findByIdArtistAndOriginalTextContaining(lyricRequest.getIdArtist(), lyricRequest.getWord());
			return generateSearchResultList(lyricsResults, lyricRequest.getWord());
		}
	}
	
	public List<SearchResponse> searchVideo(VideoRequest videoRequest){
		if(videoRequest.getIdArtist() == null || videoRequest.getIdArtist() == 0) {
			Iterable<Video> videoResults = videoRepository.findByOriginalTextContaining(videoRequest.getWord());
			return generateVideoSearchResultList(videoResults, videoRequest.getWord());
		} else {
			Iterable<Video> videoResults = videoRepository.findByIdArtistAndOriginalTextContaining(videoRequest.getIdArtist(), videoRequest.getWord());
			return generateVideoSearchResultList(videoResults, videoRequest.getWord());
		}
	}
	
	public List<SearchResponse> searchText(TextRequest textRequest){
		if(textRequest.getIdArtist() == null || textRequest.getIdArtist() == 0) {
			Iterable<Text> textResults = textRepository.findByOriginalTextContaining(textRequest.getWord());
			return generateTextSearchResultList(textResults, textRequest.getWord());
		} else {
			Iterable<Text> textResults = textRepository.findByIdArtistAndOriginalTextContaining(textRequest.getIdArtist(), textRequest.getWord());
			return generateTextSearchResultList(textResults, textRequest.getWord());
		}
	}
	
	private List<SearchResponse> generateTextSearchResultList(Iterable<Text> textResults, String word) {
		List<SearchResponse> searchResults = new ArrayList<SearchResponse>();
		for(Text text: textResults) {
			Map<String, String[]> lines = Text.getLinesContaining(text.getOriginalText(), text.getTranslation(), word);
			SearchResponse searchResult = new SearchResponse(text.getId(), text.getTitle(), lines);
			searchResults.add(searchResult);
		}
		return searchResults;
	}

	public List<SearchResponse> generateVideoSearchResultList(Iterable<Video> videoResults, String word){
		List<SearchResponse> searchResults = new ArrayList<SearchResponse>();
		for(Video video: videoResults) {
			Map<String, String[]> lines = Video.getLinesContaining(video.getOriginalText(), video.getTranslation(), word);
			SearchResponse searchResult = new SearchResponse(video.getId(), video.getTitle(), lines);
			searchResults.add(searchResult);
		}
		return searchResults;
	}
	
	public List<SearchResponse> generateSearchResultList(Iterable<Lyric> lyricsResults, String word){
		List<SearchResponse> searchResults = new ArrayList<SearchResponse>();
		for(Lyric lyric: lyricsResults) {
			Map<String, String[]> lines = lyric.getLinesContaining(word);
			SearchResponse searchResult = new SearchResponse(lyric.getId(), lyric.getTitle(), lines);
			searchResults.add(searchResult);
		}
		return searchResults;
	}
}
