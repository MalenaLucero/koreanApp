package com.koreanApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanApp.entity.Artist;
import com.koreanApp.repository.ArtistRepository;

@Service
public class ArtistService {
	@Autowired
	private ArtistRepository artistRepository;

	public Iterable<Artist> getAll(){
        return artistRepository.findAll();
    }
	
	public Optional<Artist> getArtistById(Integer id) {
		return artistRepository.findById(id);
	}
	
	public Optional<Artist> getArtistByName(String name){
		return artistRepository.findByName(name);
	}
	
	public Artist save(Artist artist) {
		return artistRepository.save(artist);
	}
	
	public void delete(Integer id) {
		artistRepository.deleteById(id);
	}
	
	public Artist updateArtist(Artist artistToUpdate, Artist artistRequest) {
		artistToUpdate.setName(artistRequest.getName());
		return artistRepository.save(artistToUpdate);
	}
}
