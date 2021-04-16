package com.koreanApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanApp.entity.Lyric;
import com.koreanApp.repository.LyricRepository;
import com.koreanApp.util.FormatUtil;
import com.koreanApp.util.InvalidTranslationException;
import com.koreanApp.util.MissingPropertyException;
import com.koreanApp.util.RepeatedPropertyException;

@Service
public class LyricService {
	@Autowired LyricRepository lyricRepository;
	
	public Iterable<Lyric> getLyrics(){
		return lyricRepository.findAll();
	}
	
	public Optional<Lyric> getLyrics(Integer id) {
		return lyricRepository.findById(id);
	}
	
	public Optional<Lyric> getLyrics(String title){
		return lyricRepository.findByTitle(title);
	}
	
	public Lyric addLyric(Lyric lyric) throws MissingPropertyException, RepeatedPropertyException, InvalidTranslationException {
		if(FormatUtil.isStringEmpty(lyric.getTitle())) {
			throw new MissingPropertyException("title");
		}
		if (lyricRepository.findByTitle(lyric.getTitle()).isPresent()) {
			throw new RepeatedPropertyException("title");
		} 
		if(!FormatUtil.isStringEmpty(lyric.getTranslation())) {
			if(!lyric.isTranslationValid()) {
				throw new InvalidTranslationException();
			}
		}
		return lyricRepository.save(lyric);
	}
	
	public Lyric updateLyric(Lyric lyric) throws MissingPropertyException, RepeatedPropertyException, InvalidTranslationException {
		if(FormatUtil.isNumberEmpty(lyric.getId())) {
			throw new MissingPropertyException("ID");
		}
		Lyric lyricToUpdate = lyricRepository.findById(lyric.getId()).get();
		if(!FormatUtil.isStringEmpty(lyric.getTitle())) {
			if (lyricRepository.findByTitle(lyric.getTitle()).isPresent()) {
				throw new RepeatedPropertyException("title");
			} else {
				lyricToUpdate.setTitle(lyric.getTitle());
			}
		}
		if(!FormatUtil.isStringEmpty(lyric.getOriginalText())) {
			lyricToUpdate.setOriginalText(lyric.getOriginalText());
		}
		if(!FormatUtil.isStringEmpty(lyric.getTranslation())) {
			lyricToUpdate.setTranslation(lyric.getTranslation());
			if(!lyricToUpdate.isTranslationValid()) {
				throw new InvalidTranslationException();
			}
		}
		if(!FormatUtil.isStringEmpty(lyric.getLink())) {
			lyricToUpdate.setLink(lyric.getLink());
		}
		if(!FormatUtil.isNumberEmpty(lyric.getIdArtist())) {
			lyricToUpdate.setIdArtist(lyric.getIdArtist());
		}
		return lyricRepository.save(lyricToUpdate);
	}
	
	public void deleteLyric(Integer id) {
		lyricRepository.deleteById(id);
	}
}
