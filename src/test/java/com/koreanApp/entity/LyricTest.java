package com.koreanApp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LyricTest {
	Lyric mockLyric;
	
	@BeforeEach
	public void generateMockLyric() {
		Lyric lyric = new Lyric();
		lyric.setOriginalText("Yeah, yeah\n"
				+ "Okay, okay, okay, okay\n"
				+ "Yeah\n3년이 지났네\n"
				+ "Agust D\n"
				+ "솔직히, 몇 곡 넣을지 잘 모르겠어 걍\n"
				+ "씨, 걍 하는 거지 뭐");
		lyric.setTranslation("Yeah, yeah\n"
				+ "Okay, okay, okay, okay\n"
				+ "Yeah\n"
				+ "Three years have passed\n"
				+ "Agust D\n"
				+ "Honestly, I don’t know how many songs to put in,\n"
				+ "Fuck, I’m just doing it");
		mockLyric = lyric;
	}
	
	
	@Test
	public void isTranslationValidShouldBeTrue() {
		assertTrue(mockLyric.isTranslationValid());
	}
	
	@Test
	public void isTranslationValidShouldBeFalse() {
		mockLyric.setTranslation("Yeah, yeah\n"
				+ "Okay, okay, okay, okay\n"
				+ "Yeah\n"
				+ "Three years have passed\n"
				+ "Agust D\n"
				+ "Honestly, I don’t know how many songs to put in");
		assertFalse(mockLyric.isTranslationValid());
	}
	
	@Test
	public void getLinesContainingShouldEqual() {
		Map<String, String[]> lines = mockLyric.getLinesContaining("년");
		assertEquals(lines.get("4")[0], "3년이 지났네");
		assertEquals(lines.get("4")[1], "Three years have passed");
	}

}
