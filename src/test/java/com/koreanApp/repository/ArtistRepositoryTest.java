package com.koreanApp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.koreanApp.entity.Artist;

@SpringBootTest
public class ArtistRepositoryTest {
	@Autowired ArtistRepository artistRepository;
	
	@Test
    public void saveTest() throws Exception {
        Optional<Artist> savedArtist = artistRepository.findById(1);
        assertEquals("BTS", savedArtist.get().getName());
    }

}
