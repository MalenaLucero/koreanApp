package com.koreanApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.koreanApp.entity.Artist;
import com.koreanApp.repository.ArtistRepository;
import com.koreanApp.util.MissingPropertyException;
import com.koreanApp.util.RepeatedPropertyException;

@SpringBootTest
public class ArtistServiceTest {
	@Autowired ArtistRepository artistRepository;
	
	@Autowired ArtistService artistService;
	
	@Test
    public void getArtistByIdShouldEqual() throws Exception {
        Optional<Artist> artist = artistService.getArtistById(1);
        assertEquals("BTS", artist.get().getName());
    }
	
	@Test
    public void getArtistByNameShouldEqual() throws Exception {
        Optional<Artist> artist = artistService.getArtistByName("BTS");
        assertEquals(1, artist.get().getId());
    }
	
	@Test
    public void getArtistsShouldBeTrue() throws Exception {
        Iterable<Artist> artists = artistService.getArtists();
        assertTrue(artists.iterator().hasNext());
    }
	
	@Test
	public void saveShouldThrowMissingPropertyException() {
		Artist artist = new Artist();
		Exception exception = assertThrows(MissingPropertyException.class, () -> {
	        artistService.save(artist);
	    });
	    assertEquals(exception.getMessage(), "Missing property name");
	}
	
	@Test
	public void saveShouldThrowRepeatedPropertyException() {
		Artist artist = new Artist();
		artist.setName("BTS");
		Exception exception = assertThrows(RepeatedPropertyException.class, () -> {
	        artistService.save(artist);
	    });
	    assertEquals(exception.getMessage(), "Element with that name already exists");
	}
	
	@Test
	public void saveShouldEqual() throws RepeatedPropertyException, MissingPropertyException {
		Artist artist = new Artist();
		artist.setName("Junit test");
		Artist addedArtist = artistService.save(artist);
	    assertEquals(addedArtist.getName(), "Junit test");
	    artistService.delete(addedArtist.getId());
	}
	
	@Test
	public void updateShouldThrowMissingPropertyException() throws RepeatedPropertyException, MissingPropertyException {
		Artist artist = new Artist();
		Exception exception = assertThrows(MissingPropertyException.class, () -> {
	        artistService.updateArtist(artist);
	    });
	    assertEquals(exception.getMessage(), "Missing property name or ID");
	}
	
	@Test
	public void updateShouldThrowRepeatedPropertyException() {
		Artist artist = new Artist();
		artist.setId(1);
		artist.setName("BTS");
		Exception exception = assertThrows(RepeatedPropertyException.class, () -> {
	        artistService.updateArtist(artist);
	    });
	    assertEquals(exception.getMessage(), "Element with that name already exists");
	}
	
	@Test
	public void updateShouldEqual() throws RepeatedPropertyException, MissingPropertyException {
		Artist artist = new Artist();
		artist.setName("Junit test");
		Artist addedArtist = artistService.save(artist);
		addedArtist.setName("Junit test edited");
		Artist updatedArtist = artistService.updateArtist(addedArtist);
	    assertEquals(updatedArtist.getName(), "Junit test edited");
	    artistService.delete(updatedArtist.getId());
	}
	
	@Test
	public void deleteShouldNotBePresent() throws RepeatedPropertyException, MissingPropertyException {
		Artist artist = new Artist();
		artist.setName("Junit test");
		Artist addedArtist = artistService.save(artist);
	    artistService.delete(addedArtist.getId());
	    Optional<Artist> artistByName = artistService.getArtistByName(addedArtist.getName());
	    assertFalse(artistByName.isPresent());
	}
}
