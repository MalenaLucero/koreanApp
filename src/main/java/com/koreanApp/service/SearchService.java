package com.koreanApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanApp.entity.Lyric;
import com.koreanApp.payload.SearchResponse;
import com.koreanApp.entity.Text;
import com.koreanApp.entity.Video;
import com.koreanApp.enums.VideoTypes;
import com.koreanApp.repository.LyricRepository;
import com.koreanApp.repository.TextRepository;
import com.koreanApp.repository.VideoRepository;
import com.koreanApp.util.FormatUtil;
import com.koreanApp.util.InvalidSearchWordException;
import com.koreanApp.util.InvalidTypeException;

@Service
public class SearchService {
	@Autowired LyricRepository lyricRepository;
	@Autowired VideoRepository videoRepository;
	@Autowired TextRepository textRepository;
	
	public List<SearchResponse> searchLyric(Integer idArtist, String word) throws InvalidSearchWordException{
		if(FormatUtil.isStringEmpty(word)) {
			throw new InvalidSearchWordException();
		} 
		Iterable<Lyric> lyricsResults;
		if(FormatUtil.isNumberEmpty(idArtist)) {
			lyricsResults = lyricRepository.findByOriginalTextContaining(word);
		} else {
			lyricsResults = lyricRepository.findByIdArtistAndOriginalTextContaining(idArtist, word);
		}	
		return generateLyricSearchResult(lyricsResults, word);
	}
	
	public List<SearchResponse> searchVideo(Integer idArtist, String word, String type) throws InvalidSearchWordException, InvalidTypeException{
		if(FormatUtil.isStringEmpty(word)) {
			throw new InvalidSearchWordException();
		} 
		if(!FormatUtil.isStringEmpty(type) && !FormatUtil.isVideoTypeValid(type)) {
			throw new InvalidTypeException(type);
		}
		Iterable<Video> videoResults;
		if(FormatUtil.isNumberEmpty(idArtist) && FormatUtil.isStringEmpty(type)) {
			videoResults = videoRepository.findByOriginalTextContaining(word);
		} else if(FormatUtil.isStringEmpty(type)){
			videoResults = videoRepository.findByIdArtistAndOriginalTextContaining(idArtist, word);
		} else if(FormatUtil.isNumberEmpty(idArtist)) {
			videoResults = videoRepository.findByTypeAndOriginalTextContaining(VideoTypes.valueOf(type), word);
		} else {
			videoResults = videoRepository.findByIdArtistAndTypeAndOriginalTextContaining(idArtist, VideoTypes.valueOf(type), word);
		}
		return generateVideoSearchResult(videoResults, word);
	}
	
	public List<SearchResponse> searchText(Integer idArtist, String word) throws InvalidSearchWordException{
		if(FormatUtil.isStringEmpty(word)) {
			throw new InvalidSearchWordException();
		} 
		Iterable<Text> textResults;
		if(FormatUtil.isNumberEmpty(idArtist)) {
			textResults = textRepository.findByOriginalTextContaining(word);
		} else {
			textResults = textRepository.findByIdArtistAndOriginalTextContaining(idArtist, word);
		}
		return generateTextSearchResult(textResults, word);
	}
	
	private List<SearchResponse> generateTextSearchResult(Iterable<Text> textResults, String word) {
		List<SearchResponse> searchResults = new ArrayList<SearchResponse>();
		for(Text text: textResults) {
			Map<String, String[]> lines = text.getLinesContaining(word);
			SearchResponse searchResult = new SearchResponse(text.getId(), text.getTitle(), lines);
			searchResults.add(searchResult);
		}
		return searchResults;
	}

	private List<SearchResponse> generateVideoSearchResult(Iterable<Video> videoResults, String word){
		List<SearchResponse> searchResults = new ArrayList<SearchResponse>();
		for(Video video: videoResults) {
			Map<String, String[]> lines = video.getLinesContaining(word);
			SearchResponse searchResult = new SearchResponse(video.getId(), video.getTitle(), lines);
			searchResults.add(searchResult);
		}
		return searchResults;
	}
	
	private List<SearchResponse> generateLyricSearchResult(Iterable<Lyric> lyricsResults, String word){
		List<SearchResponse> searchResults = new ArrayList<SearchResponse>();
		for(Lyric lyric: lyricsResults) {
			Map<String, String[]> lines = lyric.getLinesContaining(word);
			SearchResponse searchResult = new SearchResponse(lyric.getId(), lyric.getTitle(), lines);
			searchResults.add(searchResult);
		}
		return searchResults;
	}
}
