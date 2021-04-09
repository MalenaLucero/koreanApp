package com.koreanApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanApp.entity.Lyric;
import com.koreanApp.repository.LyricRepository;
import com.koreanApp.util.InvalidTranslationException;

@Service
public class LyricService {
	@Autowired LyricRepository lyricRepository;
	
	public Iterable<Lyric> getAll(){
		return lyricRepository.findAll();
	}
	
	public Optional<Lyric> getLyricById(Integer id) {
		return lyricRepository.findById(id);
	}
	
	public Optional<Lyric> getLyricByTitle(String title){
		return lyricRepository.findByTitle(title);
	}
	
	public Lyric addLyric(Lyric lyric) {
		return lyricRepository.save(lyric);
	}
	
	public void deleteLyric(Integer id) {
		lyricRepository.deleteById(id);
	}
	
	public Lyric updateLyric(Lyric lyricToUpdate, Lyric lyricRequest) throws Exception {
		if(lyricRequest.getTitle() != null && lyricRequest.getTitle().length() != 0) {
			lyricToUpdate.setTitle(lyricRequest.getTitle());
		}
		if(lyricRequest.getOriginalText() != null && lyricRequest.getOriginalText().length() != 0) {
			lyricToUpdate.setOriginalText(lyricRequest.getOriginalText());
		}
		if(lyricRequest.getTranslation() != null && lyricRequest.getTranslation().length() != 0) {
			boolean isTranslationValid = Lyric.isTranslationValid(lyricToUpdate.getOriginalText(), lyricRequest.getTranslation());
			if(isTranslationValid) {
				lyricToUpdate.setTranslation(lyricRequest.getTranslation());
			} else {
				throw new InvalidTranslationException("Invalid translation format");
			}
		}
		if(lyricRequest.getLink() != null && lyricRequest.getLink().length() != 0) {
			lyricToUpdate.setLink(lyricRequest.getLink());
		}
		if(lyricRequest.getIdArtist() != null && lyricRequest.getIdArtist() != 0) {
			lyricToUpdate.setIdArtist(lyricRequest.getIdArtist());
		}
		return lyricRepository.save(lyricToUpdate);
	}
}
