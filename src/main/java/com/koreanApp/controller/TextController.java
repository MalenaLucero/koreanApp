package com.koreanApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreanApp.entity.Text;
import com.koreanApp.service.TextService;
import com.koreanApp.util.InvalidTranslationException;

@Controller
@RequestMapping(path = "/api")
public class TextController {
	@Autowired
	private TextService textService;
	
	@PreAuthorize("hasRole('PREMIUM') or hasRole('DEVELOP') or hasRole('ADMIN')")
	@GetMapping(path = "/text")
	public @ResponseBody ResponseEntity<Object> getAllTexts() {
		try {
			Iterable<Text> texts = textService.getAll();
			return new ResponseEntity<Object>(texts, HttpStatus.OK);
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('PREMIUM') or hasRole('DEVELOP') or hasRole('ADMIN')")
	@GetMapping(path = "/text/")
	public @ResponseBody ResponseEntity<Object> getTextByIdOrTitle(@RequestParam(required = false) Integer id, @RequestParam(required = false) String title) {
		if((title == null || title.length() == 0) && (id == null || id == 0)) {
			return new ResponseEntity<Object>("Missing name or id", HttpStatus.BAD_REQUEST);
		}
		try {
			if(id != null) {
				Optional<Text> text = textService.getTextById(id);
				if(text.isPresent()) {
					return new ResponseEntity<Object>(text.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>("Text not found", HttpStatus.OK);
				}
			} else {
				Optional<Text> text = textService.getTextByTitle(title);
				if(text.isPresent()) {
					return new ResponseEntity<Object>(text.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>("Text not found", HttpStatus.OK);
				}
			}
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@PostMapping(path = "/text")
	public @ResponseBody ResponseEntity<Object> addText(@RequestBody Text text) {
		if(text.getTitle() == null || text.getTitle().length() == 0) {
			return new ResponseEntity<Object>("Missing title", HttpStatus.BAD_REQUEST);
		}
		if(text.getOriginalText() == null || text.getOriginalText().length() == 0) {
			return new ResponseEntity<Object>("Missing original text", HttpStatus.BAD_REQUEST);
		}
		try {
			Optional<Text> storedText = textService.getTextByTitle(text.getTitle());
			if(storedText.isPresent()) {
				return new ResponseEntity<Object>("Text with that title already exists", HttpStatus.BAD_REQUEST);
			} else {
				Text addedText = textService.addText(text);
				return new ResponseEntity<Object>(addedText, HttpStatus.OK);
			}
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@DeleteMapping(path = "/text/{id}")
	public @ResponseBody ResponseEntity<String> deleteText(@PathVariable Integer id) {
		try {
			textService.deleteText(id);
			return new ResponseEntity<String>("Text deleted with ID " + id, HttpStatus.OK);
		} catch (EmptyResultDataAccessException exc) {
			return new ResponseEntity<String>("Text not found", HttpStatus.BAD_REQUEST);
	    } catch (Exception ex) {
	    	return new ResponseEntity<String>("Unexpected error: " +  ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@PutMapping(path = "/text")
	public @ResponseBody ResponseEntity<Object> updateTextById(@RequestBody Text text) {
		if(text.getId() == null || text.getId() == 0) {
			return new ResponseEntity<Object>("Missing text id", HttpStatus.BAD_REQUEST);
		} 
		if(text.getOriginalText() != null && text.getOriginalText().length() != 0 &&
			text.getTranslation() != null && text.getTranslation().length() != 0) {
			boolean isTranslationValid = Text.isTranslationValid(text.getOriginalText(), text.getTranslation());
			if(!isTranslationValid) {
				return new ResponseEntity<Object>("Invalid translation format", HttpStatus.BAD_REQUEST);
			} 
		} 
		try {
			Optional<Text> textToUpdate = textService.getTextById(text.getId());
			if(!textToUpdate.isPresent()) {
				return new ResponseEntity<Object>("Text not found", HttpStatus.BAD_REQUEST);
			} else {
				Text updatedText = textService.updateText(textToUpdate.get(), text);
				return new ResponseEntity<Object>(updatedText, HttpStatus.OK);
			}
		} catch(InvalidTranslationException ex) {
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
