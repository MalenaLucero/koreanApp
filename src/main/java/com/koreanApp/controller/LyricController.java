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

import com.koreanApp.entity.Lyric;
import com.koreanApp.service.LyricService;
import com.koreanApp.util.FormatUtil;
import com.koreanApp.util.InvalidTranslationException;
import com.koreanApp.util.MissingPropertyException;
import com.koreanApp.util.RepeatedPropertyException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api")
public class LyricController {
	@Autowired LyricService lyricService;
	
	@PreAuthorize("hasRole('PREMIUM') or hasRole('DEVELOP') or hasRole('ADMIN')")
	@GetMapping(path = "/lyric")
	public @ResponseBody ResponseEntity<Object> getLyrics(@RequestParam(required = false) Integer id, @RequestParam(required = false) String title) {
		try {
			if(!FormatUtil.isNumberEmpty(id)) {
				return new ResponseEntity<Object>(lyricService.getLyrics(id), HttpStatus.OK);
			} else if (!FormatUtil.isStringEmpty(title)){
				return new ResponseEntity<Object>(lyricService.getLyrics(title), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(lyricService.getLyrics(), HttpStatus.OK);
			}
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@PostMapping(path = "/lyric")
	public @ResponseBody ResponseEntity<Object> addLyric(@RequestBody Lyric lyric) {
		try {
			return new ResponseEntity<Object>(lyricService.addLyric(lyric), HttpStatus.OK);
		} catch(MissingPropertyException | RepeatedPropertyException | InvalidTranslationException ex) {
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
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
	public @ResponseBody ResponseEntity<Object> updateLyricById(@RequestBody Lyric lyric) {
		try {
			return new ResponseEntity<Object>(lyricService.updateLyric(lyric), HttpStatus.OK);
		} catch(MissingPropertyException | RepeatedPropertyException | InvalidTranslationException ex) {
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
