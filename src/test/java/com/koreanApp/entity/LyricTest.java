package com.koreanApp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class LyricTest {
	@Test
	public void isTranslationValidShouldBeTrue() {
		Lyric lyric = new Lyric();
		lyric.setOriginalText("Yeah, yeah\nOkay, okay, okay, okay\nYeah\n3년이 지났네\nAgust D\n솔직히, 몇 곡 넣을지 잘 모르겠어 걍\n씨, 걍 하는 거지 뭐");
		lyric.setTranslation("Yeah, yeah\nOkay, okay, okay, okay\nYeah\nThree years have passed\nAgust D\nHonestly, I don’t know how many songs to put in,\nFuck, I’m just doing it");
		assertTrue(lyric.isTranslationValid());
	}
	
	@Test
	public void isTranslationValidShouldBeFalse() {
		Lyric lyric = new Lyric();
		lyric.setOriginalText("Yeah, yeah\nOkay, okay, okay, okay\nYeah\n3년이 지났네\nAgust D\n솔직히, 몇 곡 넣을지 잘 모르겠어 걍\n씨, 걍 하는 거지 뭐");
		lyric.setTranslation("Yeah, yeah\nOkay, okay, okay, okay\nYeah\nThree years have passed\nAgust D\nHonestly, I don’t know how many songs to put in");
		assertFalse(lyric.isTranslationValid());
	}
	
	@Test
	public void getLinesContainingShouldEqual() {
		Lyric lyric = new Lyric();
		lyric.setOriginalText("Yeah, yeah\nOkay, okay, okay, okay\nYeah\n3년이 지났네\nAgust D\n솔직히, 몇 곡 넣을지 잘 모르겠어 걍\n씨, 걍 하는 거지 뭐");
		lyric.setTranslation("Yeah, yeah\nOkay, okay, okay, okay\nYeah\nThree years have passed\nAgust D\nHonestly, I don’t know how many songs to put in,\nFuck, I’m just doing it");
		Map<String, String[]> lines = lyric.getLinesContaining("년");
		assertEquals(lines.get("4")[0], "3년이 지났네");
		assertEquals(lines.get("4")[1], "Three years have passed");
	}

}
