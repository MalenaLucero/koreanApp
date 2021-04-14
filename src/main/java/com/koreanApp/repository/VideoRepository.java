package com.koreanApp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.koreanApp.entity.Video;

public interface VideoRepository extends CrudRepository<Video, Integer>{
	public Optional<Video> findByTitle(String title);
	
	public Iterable<Video> findByOriginalTextContaining(String word);
	
	public Iterable<Video> findByIdArtistAndOriginalTextContaining(Integer id, String word);
}
