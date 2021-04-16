package com.koreanApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.koreanApp.entity.Text;
import com.koreanApp.service.TextService;
import com.koreanApp.util.FormatUtil;
import com.koreanApp.util.InvalidTranslationException;
import com.koreanApp.util.MissingPropertyException;
import com.koreanApp.util.RepeatedPropertyException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/text")
public class TextController {
	@Autowired
	private TextService textService;
	
	@PreAuthorize("hasRole('PREMIUM') or hasRole('DEVELOP') or hasRole('ADMIN')")
	@GetMapping(path = "")
	public @ResponseBody ResponseEntity<Object> getTextByIdOrTitle(@RequestParam(required = false) Integer id, @RequestParam(required = false) String title) {
		try {
			if(!FormatUtil.isNumberEmpty(id)) {
				return new ResponseEntity<Object>(textService.getTexts(id), HttpStatus.OK);
			} else if(!FormatUtil.isStringEmpty(title)) {
				return new ResponseEntity<Object>(textService.getTexts(title), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(textService.getTexts(), HttpStatus.OK);
			}
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@PostMapping(path = "")
	public @ResponseBody ResponseEntity<Object> addText(@RequestBody Text text) {
		try {
			return new ResponseEntity<Object>(textService.addText(text), HttpStatus.OK);
		} catch(MissingPropertyException | RepeatedPropertyException | InvalidTranslationException ex) {
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@PutMapping(path = "")
	public @ResponseBody ResponseEntity<Object> updateTextById(@RequestBody Text text) {
		try {
			return new ResponseEntity<Object>(textService.updateText(text), HttpStatus.OK);
		} catch(MissingPropertyException | RepeatedPropertyException | InvalidTranslationException ex) {
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@DeleteMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<String> deleteText(@PathVariable Integer id) {
		try {
			textService.deleteText(id);
			return new ResponseEntity<String>("Text deleted with ID " + id, HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			return new ResponseEntity<String>("Text not found", HttpStatus.BAD_REQUEST);
	    } catch (Exception ex) {
	    	return new ResponseEntity<String>("Unexpected error: " +  ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
}
