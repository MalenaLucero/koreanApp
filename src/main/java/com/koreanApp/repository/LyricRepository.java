package com.koreanApp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.koreanApp.entity.Lyric;

public interface LyricRepository extends CrudRepository<Lyric, Integer>{
	public Optional<Lyric> findByTitle(String title);
	
	public Iterable<Lyric> findByOriginalTextContaining(String word);
	
	public Iterable<Lyric> findByArtistId(Integer id);
	
	public Iterable<Lyric> findByIdArtistAndOriginalTextContaining(Integer id, String word);
}
