package com.koreanApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanApp.entity.Video;
import com.koreanApp.repository.VideoRepository;
import com.koreanApp.util.FormatUtil;

@Service
public class VideoService {
	@Autowired
	private VideoRepository videoRepository;
	
	public Iterable<Video> getAll(){
        return videoRepository.findAll();
    }
	
	public Optional<Video> getVideoById(Integer id) {
		return videoRepository.findById(id);
	}
	
	public Optional<Video> getVideoByTitle(String title){
		return videoRepository.findByTitle(title);
	}
	
	public Video addVideo(Video video) {
		return videoRepository.save(video);
	}
	
	public void deleteVideo(Integer id) {
		videoRepository.deleteById(id);
	}
	
	public Video updateVideo(Video videoToUpdate, Video videoRequest) {
		if(!FormatUtil.isStringEmpty(videoRequest.getTitle())) {
			videoToUpdate.setTitle(videoRequest.getTitle());
		}
		if(!FormatUtil.isStringEmpty(videoRequest.getOriginalText())) {
			videoToUpdate.setOriginalText(videoRequest.getOriginalText());
		}
		if(!FormatUtil.isStringEmpty(videoRequest.getTranslation())) {
			videoToUpdate.setTranslation(videoRequest.getTranslation());	
		}
		if(!FormatUtil.isStringEmpty(videoRequest.getLink())) {
			videoToUpdate.setLink(videoRequest.getLink());
		}
		if(!FormatUtil.isStringEmpty(videoRequest.getLink())) {
			videoToUpdate.setLink(videoRequest.getLink());
		}
		if(!FormatUtil.isStringEmpty(videoRequest.getType().name())) {
			videoToUpdate.setType(videoRequest.getType());
		}
		if(!FormatUtil.isNumberEmpty(videoRequest.getIdArtist())) {
			videoToUpdate.setIdArtist(videoRequest.getIdArtist());
		}
		return videoRepository.save(videoToUpdate);
	}
}
