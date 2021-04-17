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

import com.koreanApp.entity.Artist;
import com.koreanApp.service.ArtistService;
import com.koreanApp.util.FormatUtil;
import com.koreanApp.util.RepeatedPropertyException;
import com.koreanApp.util.MissingPropertyException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api")

public class ArtistController {
	@Autowired ArtistService artistService;
	
	@GetMapping(path = "/public/artist")
	public @ResponseBody  ResponseEntity<Object> getArtists(@RequestParam(required = false) Integer id, @RequestParam(required = false) String name) {
		try {
			if(!FormatUtil.isNumberEmpty(id)) {
				return new ResponseEntity<>(artistService.getArtistById(id), HttpStatus.OK);
			} else if(!FormatUtil.isStringEmpty(name)) {
				return new ResponseEntity<>(artistService.getArtistByName(name), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(artistService.getArtists(), HttpStatus.OK);
			}
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@PostMapping(path = "/artist")
	public @ResponseBody ResponseEntity<Object> addArtist(@RequestBody Artist artist) {
		try {
			return new ResponseEntity<Object>(artistService.save(artist), HttpStatus.OK);
		} catch(MissingPropertyException | RepeatedPropertyException ex) {
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@PutMapping(path = "/artist")
	public @ResponseBody ResponseEntity<Object> updateArtistById(@RequestBody Artist artist) {
		try {
			return new ResponseEntity<Object>(artistService.updateArtist(artist), HttpStatus.OK);
		} catch(MissingPropertyException | RepeatedPropertyException ex) {
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@DeleteMapping(path = "/artist/{id}")
	public @ResponseBody ResponseEntity<String> deleteArtist(@PathVariable Integer id){
		try {
			artistService.delete(id);
			return new ResponseEntity<String>("Artist deleted with ID " + id, HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			return new ResponseEntity<String>("Artist not found", HttpStatus.BAD_REQUEST);
	    } catch (Exception ex) {
	    	return new ResponseEntity<String>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
}
