package com.koreanApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanApp.entity.Text;
import com.koreanApp.repository.TextRepository;
import com.koreanApp.util.FormatUtil;
import com.koreanApp.util.InvalidTranslationException;
import com.koreanApp.util.MissingPropertyException;
import com.koreanApp.util.RepeatedPropertyException;

@Service
public class TextService {
	@Autowired
	private TextRepository textRepository;
	
	public Iterable<Text> getTexts(){
        return textRepository.findAll();
    }
	
	public Optional<Text> getTexts(Integer id) {
		return textRepository.findById(id);
	}
	
	public Optional<Text> getTexts(String title){
		return textRepository.findByTitle(title);
	}
	
	public Text addText(Text text) throws MissingPropertyException, RepeatedPropertyException, InvalidTranslationException{
		if(FormatUtil.isStringEmpty(text.getTitle())) {
			throw new MissingPropertyException("title");
		}
		if (textRepository.findByTitle(text.getTitle()).isPresent()) {
			throw new RepeatedPropertyException("title");
		} 
		if(!FormatUtil.isStringEmpty(text.getTranslation())) {
			if(!text.isTranslationValid()) {
				throw new InvalidTranslationException();
			}
		}
		return textRepository.save(text);
	}
	
	public Text updateText(Text text) throws MissingPropertyException, RepeatedPropertyException, InvalidTranslationException {
		if(FormatUtil.isNumberEmpty(text.getId())) {
			throw new MissingPropertyException("ID");
		}
		Text textToUpdate = textRepository.findById(text.getId()).get();
		if(!FormatUtil.isStringEmpty(text.getTitle())) {
			if (textRepository.findByTitle(text.getTitle()).isPresent()) {
				throw new RepeatedPropertyException("title");
			} else {
				textToUpdate.setTitle(text.getTitle());
			}
		}
		if(!FormatUtil.isStringEmpty(text.getOriginalText())) {
			textToUpdate.setOriginalText(text.getOriginalText());
		}
		if(!FormatUtil.isStringEmpty(text.getTranslation())) {
			textToUpdate.setTranslation(text.getTranslation());
			if(!textToUpdate.isTranslationValid()) {
				throw new InvalidTranslationException();
			}
		}
		if(!FormatUtil.isStringEmpty(text.getLink())) {
			textToUpdate.setLink(text.getLink());
		}
		if(!FormatUtil.isNumberEmpty(text.getIdArtist())) {
			textToUpdate.setIdArtist(text.getIdArtist());
		}
		return textRepository.save(textToUpdate);
	}
	
	public void deleteText(Integer id) {
		textRepository.deleteById(id);
	}
}
