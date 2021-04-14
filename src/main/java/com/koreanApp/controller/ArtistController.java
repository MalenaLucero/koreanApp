package com.koreanApp.controller;

import java.util.Optional;

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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api")

public class ArtistController {
	@Autowired ArtistService artistService;
	
	@GetMapping(path = "/search/artist")
	public @ResponseBody ResponseEntity<Object> getAllArtists() {
		try {
			Iterable<Artist> artists = artistService.getAll();
			return new ResponseEntity<Object>(artists, HttpStatus.OK);
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('PREMIUM') or hasRole('DEVELOP') or hasRole('ADMIN')")
	@GetMapping(path = "/artist/")
	public @ResponseBody  ResponseEntity<Object> getArtistById(@RequestParam(required = false) Integer id, @RequestParam(required = false) String name) {
		if((name == null || name.length() == 0) && (id == null || id == 0)) {
			return new ResponseEntity<Object>("Missing name or id", HttpStatus.BAD_REQUEST);
		}
		try {
			if(id != null && id != 0) {
				Optional<Artist> artist = artistService.getArtistById(id);
				if(artist.isPresent()) {
					return new ResponseEntity<Object>(artist.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>("Artist not found", HttpStatus.OK);
				}
			} else {
				Optional<Artist> artist = artistService.getArtistByName(name);
				if(artist.isPresent()) {
					return new ResponseEntity<Object>(artist.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>("Artist not found", HttpStatus.OK);
				}
			}
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@PostMapping(path = "/artist")
	public @ResponseBody ResponseEntity<Object> addArtist(@RequestBody Artist artist) {
		if(artist.getName() == null || artist.getName().length() == 0) {
			return new ResponseEntity<Object>("Missing artist name", HttpStatus.BAD_REQUEST);
		}
		try {
			Optional<Artist> storedArtist = artistService.getArtistByName(artist.getName());
			if(storedArtist.isPresent()) {
				return new ResponseEntity<Object>("Artist with that name already exists", HttpStatus.BAD_REQUEST);
			} else {
				Artist addedArtist = artistService.save(artist);
				return new ResponseEntity<Object>(addedArtist, HttpStatus.OK);
			}
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@PutMapping(path = "/artist")
	public @ResponseBody ResponseEntity<Object> updateArtistById(@RequestBody Artist artist) {
		if(artist.getId() == null || artist.getId() == 0) {
			return new ResponseEntity<Object>("Missing artist id", HttpStatus.BAD_REQUEST);
		} 
		if(artist.getName() == null || artist.getName().length() == 0) {
			return new ResponseEntity<Object>("Missing artist name", HttpStatus.BAD_REQUEST);
		} 
		try {
			Optional<Artist> artistToUpdate = artistService.getArtistById(artist.getId());
			if(!artistToUpdate.isPresent()) {
				return new ResponseEntity<Object>("Artist not found", HttpStatus.BAD_REQUEST);
			} else {
				Optional<Artist> artistStoredByName = artistService.getArtistByName(artist.getName());
				if(artistStoredByName.isPresent()) {
					return new ResponseEntity<Object>("Artist with that name already exists", HttpStatus.BAD_REQUEST);
				} else {
					Artist updatedArtist = artistService.updateArtist(artistToUpdate.get(), artist);
					return new ResponseEntity<Object>(updatedArtist, HttpStatus.OK);
				}
			}
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
		} catch (EmptyResultDataAccessException exc) {
			return new ResponseEntity<String>("Artist not found", HttpStatus.BAD_REQUEST);
	    } catch (Exception ex) {
	    	return new ResponseEntity<String>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
}
