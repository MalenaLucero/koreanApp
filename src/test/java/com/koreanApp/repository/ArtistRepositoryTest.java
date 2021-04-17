package com.koreanApp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.koreanApp.entity.Artist;

@DataJpaTest
public class ArtistRepositoryTest {
	@Autowired ArtistRepository artistRepository;
	
	@Test
    public void saveTest() throws Exception {
		Artist artist = new Artist();
		artist.setId(1);
		artist.setName("BTS");
        Artist savedArtist = artistRepository.save(artist);
        assertEquals("BTS", savedArtist.getName());
    }

}
