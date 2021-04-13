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

import com.koreanApp.entity.Video;
import com.koreanApp.service.VideoService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api")
public class VideoController {
	@Autowired
	private VideoService videoService;
	
	@PreAuthorize("hasRole('PREMIUM') or hasRole('DEVELOP') or hasRole('ADMIN')")
	@GetMapping(path = "/video")
	public @ResponseBody ResponseEntity<Object> getAllVideos() {
		try {
			Iterable<Video> videos = videoService.getAll();
			return new ResponseEntity<Object>(videos, HttpStatus.OK);
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('PREMIUM') or hasRole('DEVELOP') or hasRole('ADMIN')")
	@GetMapping(path = "/video/")
	public @ResponseBody ResponseEntity<Object> getVideoByIdOrTitle(@RequestParam(required = false) Integer id, @RequestParam(required = false) String title) {
		if((title == null || title.length() == 0) && (id == null || id == 0)) {
			return new ResponseEntity<Object>("Missing name or id", HttpStatus.BAD_REQUEST);
		}
		try {
			if(id != null) {
				Optional<Video> video = videoService.getVideoById(id);
				if(video.isPresent()) {
					return new ResponseEntity<Object>(video.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>("Video not found", HttpStatus.OK);
				}
			} else {
				Optional<Video> video = videoService.getVideoByTitle(title);
				if(video.isPresent()) {
					return new ResponseEntity<Object>(video.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>("Video not found", HttpStatus.OK);
				}
			}
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@PostMapping(path = "/video")
	public @ResponseBody ResponseEntity<String> addVideo(@RequestBody Video video) {
		if(video.getTitle() == null || video.getTitle().length() == 0) {
			return new ResponseEntity<String>("Missing title", HttpStatus.BAD_REQUEST);
		}
		if(video.getOriginalText() == null || video.getOriginalText().length() == 0) {
			return new ResponseEntity<String>("Missing original text", HttpStatus.BAD_REQUEST);
		}
		try {
			Optional<Video> storedVideo = videoService.getVideoByTitle(video.getTitle());
			if(storedVideo.isPresent()) {
				return new ResponseEntity<String>("Video with that title already exists", HttpStatus.BAD_REQUEST);
			} else {
				Video addedVideo = videoService.addVideo(video);
				return new ResponseEntity<String>("Video added with id " + addedVideo.getId(), HttpStatus.OK);
			}
		} catch(Exception ex) {
			return new ResponseEntity<String>("Unexpected error", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@DeleteMapping(path = "/video/{id}")
	public @ResponseBody ResponseEntity<String> deleteLyric(@PathVariable Integer id) {
		try {
			videoService.deleteVideo(id);
			return new ResponseEntity<String>("Video deleted with ID " + id, HttpStatus.OK);
		} catch (EmptyResultDataAccessException exc) {
			return new ResponseEntity<String>("Video not found", HttpStatus.BAD_REQUEST);
	    }
	}
	
	@PreAuthorize("hasRole('DEVELOP') or hasRole('ADMIN')")
	@PutMapping(path = "/video")
	public @ResponseBody ResponseEntity<Object> updateVideoById(@RequestBody Video video) {
		if(video.getId() == null || video.getId() == 0) {
			return new ResponseEntity<Object>("Missing video id", HttpStatus.BAD_REQUEST);
		}  
		try {
			Optional<Video> videoToUpdate = videoService.getVideoById(video.getId());
			if(!videoToUpdate.isPresent()) {
				return new ResponseEntity<Object>("Video not found", HttpStatus.BAD_REQUEST);
			} else {
				Video updatedVideo = videoService.updateVideo(videoToUpdate.get(), video);
				return new ResponseEntity<Object>(updatedVideo, HttpStatus.OK);
			}
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
