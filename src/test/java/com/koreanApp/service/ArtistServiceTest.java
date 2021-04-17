package com.koreanApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.koreanApp.entity.Artist;
import com.koreanApp.repository.ArtistRepository;

@DataJpaTest
public class ArtistServiceTest {
	@Autowired ArtistRepository artistRepository;
	
	@Autowired ArtistService artistService;
	
	@Test
    public void getArtistTest() throws Exception {
		Artist artist = new Artist();
		artist.setId(1);
		artist.setName("BTS");
        artistRepository.save(artist);
        Optional<Artist> retrievedArtist = artistService.getArtist(1);
        assertEquals("BTS", retrievedArtist.get().getName());
    }
	
}
