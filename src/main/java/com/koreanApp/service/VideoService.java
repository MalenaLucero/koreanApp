package com.koreanApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanApp.entity.Video;
import com.koreanApp.repository.VideoRepository;
import com.koreanApp.util.FormatUtil;
import com.koreanApp.util.InvalidTranslationException;
import com.koreanApp.util.MissingPropertyException;
import com.koreanApp.util.RepeatedPropertyException;

@Service
public class VideoService {
	@Autowired
	private VideoRepository videoRepository;
	
	public Iterable<Video> getVideos(){
        return videoRepository.findAll();
    }
	
	public Optional<Video> getVideo(Integer id) {
		return videoRepository.findById(id);
	}
	
	public Optional<Video> getVideo(String title){
		return videoRepository.findByTitle(title);
	}
	
	public Video addVideo(Video video) throws MissingPropertyException, RepeatedPropertyException, InvalidTranslationException {
		if(FormatUtil.isStringEmpty(video.getTitle())) {
			throw new MissingPropertyException("title");
		}
		if (videoRepository.findByTitle(video.getTitle()).isPresent()) {
			throw new RepeatedPropertyException("title");
		} 
		if (!FormatUtil.isStringEmpty(video.getTranslation())) {
			if(!video.isTranslationValid()) {
				throw new InvalidTranslationException();
			}
		}
		return videoRepository.save(video);
	}
	
	public Video updateVideo(Video video) throws MissingPropertyException, RepeatedPropertyException {
		if(FormatUtil.isNumberEmpty(video.getId())) {
			throw new MissingPropertyException("ID");
		}
		Video videoToUpdate = videoRepository.findById(video.getId()).get();
		if(!FormatUtil.isStringEmpty(video.getTitle())) {
			if (videoRepository.findByTitle(video.getTitle()).isPresent()) {
				throw new RepeatedPropertyException("title");
			} else {
				videoToUpdate.setTitle(video.getTitle());
			}
		}
		if(!FormatUtil.isStringEmpty(video.getOriginalText())) {
			videoToUpdate.setOriginalText(video.getOriginalText());
		}
		if(!FormatUtil.isStringEmpty(video.getTranslation())) {
			videoToUpdate.setTranslation(video.getTranslation());
		}
		if(!FormatUtil.isStringEmpty(video.getLink())) {
			videoToUpdate.setLink(video.getLink());
		}
		if(!FormatUtil.isNumberEmpty(video.getIdArtist())) {
			videoToUpdate.setIdArtist(video.getIdArtist());
		}
		return videoRepository.save(videoToUpdate);
	}
	
	public void deleteVideo(Integer id) {
		videoRepository.deleteById(id);
	}
}
