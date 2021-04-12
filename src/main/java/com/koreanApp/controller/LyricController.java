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

import com.koreanApp.entity.Lyric;
import com.koreanApp.service.LyricService;
import com.koreanApp.util.InvalidTranslationException;

@Controller
@RequestMapping(path = "/api")
public class LyricController {
	@Autowired LyricService lyricService;
	
	@PreAuthorize("hasRole('PREMIUM') or hasRole('DEVELOP') or hasRole('ADMIN')")
	@GetMapping(path = "/lyric")
	public @ResponseBody ResponseEntity<Object> getAllLyrics() {
		try {
			Iterable<Lyric> lyrics = lyricService.getAll();
			return new ResponseEntity<Object>(lyrics, HttpStatus.OK);
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('PREMIUM') or hasRole('DEVELOP') or hasRole('ADMIN')")
	@GetMapping(path = "/lyric/")
	public @ResponseBody ResponseEntity<Object> getLyricByIdOrTitle(@RequestParam(required = false) Integer id, @RequestParam(required = false) String title) {
		if((title == null || title.length() == 0) && (id == null || id == 0)) {
			return new ResponseEntity<Object>("Missing name or id", HttpStatus.BAD_REQUEST);
		}
		try {
			if(id != null) {
				Optional<Lyric> lyric = lyricService.getLyricById(id);
				if(lyric.isPresent()) {
					return new ResponseEntity<Object>(lyric.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>("Lyric not found", HttpStatus.OK);
				}
			} else {
				Optional<Lyric> lyric = lyricService.getLyricByTitle(title);
				if(lyric.isPresent()) {
					return new ResponseEntity<Object>(lyric.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>("Lyric not found", HttpStatus.OK);
				}
			}
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	//@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@PostMapping(path = "/lyric")
	public @ResponseBody ResponseEntity<Object> addLyric(@RequestBody Lyric lyric) {
		if(lyric.getTitle() == null || lyric.getTitle().length() == 0) {
			return new ResponseEntity<Object>("Missing title", HttpStatus.BAD_REQUEST);
		}
		if(lyric.getOriginalText() == null || lyric.getOriginalText().length() == 0) {
			return new ResponseEntity<Object>("Missing original text", HttpStatus.BAD_REQUEST);
		}
		try {
			Optional<Lyric> storedLyric = lyricService.getLyricByTitle(lyric.getTitle());
			if(storedLyric.isPresent()) {
				return new ResponseEntity<Object>("Lyric with that title already exists", HttpStatus.BAD_REQUEST);
			} else {
				Lyric addedLyric = lyricService.addLyric(lyric);
				return new ResponseEntity<Object>(addedLyric, HttpStatus.OK);
			}
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@DeleteMapping(path = "/lyric/{id}")
	public @ResponseBody ResponseEntity<String> deleteLyric(@PathVariable Integer id) {
		try {
			lyricService.deleteLyric(id);
			return new ResponseEntity<String>("Lyrics deleted with ID " + id, HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			return new ResponseEntity<String>("Lyrics not found", HttpStatus.BAD_REQUEST);
	    } catch (Exception ex) {
	    	return new ResponseEntity<String>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@PutMapping(path = "/lyric")
	public @ResponseBody ResponseEntity<String> updateLyricById(@RequestBody Lyric lyric) {
		if(lyric.getId() == null || lyric.getId() == 0) {
			return new ResponseEntity<String>("Missing lyric id", HttpStatus.BAD_REQUEST);
		} 
		if(lyric.getOriginalText() != null && lyric.getOriginalText().length() != 0 &&
			lyric.getTranslation() != null && lyric.getTranslation().length() != 0) {
			boolean isTranslationValid = Lyric.isTranslationValid(lyric.getOriginalText(), lyric.getTranslation());
			if(!isTranslationValid) {
				return new ResponseEntity<String>("Invalid translation format", HttpStatus.BAD_REQUEST);
			} 
		} 
		try {
			Optional<Lyric> lyricToUpdate = lyricService.getLyricById(lyric.getId());
			if(!lyricToUpdate.isPresent()) {
				return new ResponseEntity<String>("Lyric not found", HttpStatus.BAD_REQUEST);
			} else {
				Lyric updatedLyric = lyricService.updateLyric(lyricToUpdate.get(), lyric);
				return new ResponseEntity<String>("Lyric updated with id " + updatedLyric.getId(), HttpStatus.OK);
			}
		} catch(InvalidTranslationException ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch(Exception ex) {
			return new ResponseEntity<String>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
