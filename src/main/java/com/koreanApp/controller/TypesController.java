package com.koreanApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.koreanApp.enums.SourceTypes;
import com.koreanApp.enums.VideoTypes;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/public")
public class TypesController {
	
	@GetMapping(path = "/sourceTypes")
	public @ResponseBody ResponseEntity<SourceTypes[]> getSourceTypes() {
		return new ResponseEntity<SourceTypes[]>(SourceTypes.values(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/videoTypes")
	public @ResponseBody ResponseEntity<VideoTypes[]> getVideoTypes() {
		return new ResponseEntity<VideoTypes[]>(VideoTypes.values(), HttpStatus.OK);
	}
}
