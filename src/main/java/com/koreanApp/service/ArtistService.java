package com.koreanApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreanApp.entity.Artist;
import com.koreanApp.repository.ArtistRepository;
import com.koreanApp.util.FormatUtil;
import com.koreanApp.util.RepeatedPropertyException;
import com.koreanApp.util.MissingPropertyException;

@Service
public class ArtistService {
	@Autowired
	private ArtistRepository artistRepository;

	public Iterable<Artist> getArtists(){
        return artistRepository.findAll();
    }
	
	public Optional<Artist> getArtists(Integer id) {
		return artistRepository.findById(id);
	}
	
	public Optional<Artist> getArtists(String name){
		return artistRepository.findByName(name);
	}
	
	public Artist save(Artist artist) throws RepeatedPropertyException, MissingPropertyException {
		if(FormatUtil.isStringEmpty(artist.getName())) {
			throw new MissingPropertyException("name");
		} else if (artistRepository.findByName(artist.getName()).isPresent()) {
			throw new RepeatedPropertyException("name");
		} else {
			return artistRepository.save(artist);
		}
	}
	
	public void delete(Integer id) {
		artistRepository.deleteById(id);
	}
	
	public Artist updateArtist(Artist artist) throws MissingPropertyException {
		if(FormatUtil.isNumberEmpty(artist.getId()) || FormatUtil.isStringEmpty(artist.getName())) {
			throw new MissingPropertyException("name or ID");
		} 
		Artist artistToUpdate = artistRepository.findById(artist.getId()).get();
		artistToUpdate.setName(artist.getName());
		return artistRepository.save(artistToUpdate);
	}
}
