package com.koreanApp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.koreanApp.entity.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Integer>{
	public Optional<Artist> findByName(String name);
	
	public Optional<Artist> findById(Integer id);
}
