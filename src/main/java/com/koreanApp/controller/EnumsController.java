package com.koreanApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.koreanApp.enums.SourceTypeEnum;
import com.koreanApp.enums.VideoTypeEnum;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/search")
public class EnumsController {
	
	@GetMapping(path = "/sourceTypes")
	public @ResponseBody ResponseEntity<Object> getSourceTypes() {
		try {
			SourceTypeEnum[] sourceTypes = SourceTypeEnum.values();
			return new ResponseEntity<Object>(sourceTypes, HttpStatus.OK);
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/videoTypes")
	public @ResponseBody ResponseEntity<Object> getVideoTypes() {
		try {
			VideoTypeEnum[] videoTypes = VideoTypeEnum.values();
			return new ResponseEntity<Object>(videoTypes, HttpStatus.OK);
		} catch(Exception ex) {
			return new ResponseEntity<Object>("Unexpected error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
