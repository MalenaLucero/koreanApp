package com.koreanApp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.koreanApp.entity.Text;

public interface TextRepository extends CrudRepository<Text, Integer>{
	public Optional<Text> findByTitle(String title);
	
	public Iterable<Text> findByOriginalTextContaining(String word);
	
	public Iterable<Text> findByIdArtistAndOriginalTextContaining(Integer id, String word);
}
