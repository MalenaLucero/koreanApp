package com.koreanApp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.koreanApp.entity.Video;
import com.koreanApp.enums.VideoTypes;

public interface VideoRepository extends CrudRepository<Video, Integer>{
	public Optional<Video> findByTitle(String title);
	
	public Iterable<Video> findByOriginalTextContaining(String word);
	
	public Iterable<Video> findByIdArtistAndOriginalTextContaining(Integer id, String word);
	
	public Iterable<Video> findByTypeAndOriginalTextContaining(VideoTypes type, String word);
	
	public Iterable<Video> findByIdArtistAndTypeAndOriginalTextContaining(Integer id, VideoTypes type, String word);
}
