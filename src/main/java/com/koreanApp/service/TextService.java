package com.koreanApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanApp.entity.Text;
import com.koreanApp.repository.TextRepository;
import com.koreanApp.util.InvalidTranslationException;

@Service
public class TextService {
	@Autowired
	private TextRepository textRepository;
	
	public Iterable<Text> getAll(){
        return textRepository.findAll();
    }
	
	public Optional<Text> getTextById(Integer id) {
		return textRepository.findById(id);
	}
	
	public Optional<Text> getTextByTitle(String title){
		return textRepository.findByTitle(title);
	}
	
	public Text addText(Text text) {
		return textRepository.save(text);
	}
	
	public void deleteText(Integer id) {
		textRepository.deleteById(id);
	}
	
	public Text updateText(Text textToUpdate, Text textRequest) throws Exception {
		if(textRequest.getTitle() != null && textRequest.getTitle().length() != 0) {
			textToUpdate.setTitle(textRequest.getTitle());
		}
		if(textRequest.getOriginalText() != null && textRequest.getOriginalText().length() != 0) {
			textToUpdate.setOriginalText(textRequest.getOriginalText());
		}
		if(textRequest.getTranslation() != null && textRequest.getTranslation().length() != 0) {
			boolean isTranslationValid = Text.isTranslationValid(textToUpdate.getOriginalText(), textRequest.getTranslation());
			if(isTranslationValid) {
				textToUpdate.setTranslation(textRequest.getTranslation());
			} else {
				throw new InvalidTranslationException("Invalid translation format");
			}
		}
		if(textRequest.getLink() != null && textRequest.getLink().length() != 0) {
			textToUpdate.setLink(textRequest.getLink());
		}
		if(textRequest.getIdArtist() != null && textRequest.getIdArtist() != 0) {
			textToUpdate.setIdArtist(textRequest.getIdArtist());
		}
		return textRepository.save(textToUpdate);
	}
}
