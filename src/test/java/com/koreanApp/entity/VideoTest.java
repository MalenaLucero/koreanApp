package com.koreanApp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VideoTest {
	Video mockVideo;
	
	@BeforeEach
	public void generateMockVideo() {
		Video video = new Video();
		String originalText = "00:00:13,850 --> 00:00:16,825\n" + 
				"어느덧 5월이 저물어 가고 있습니다\n" + 
				"00:00:16,875 --> 00:00:22,440\n" + 
				"원래였다면 많은 사람들과 함께하는 5월이겠지만\n" + 
				"00:00:22,440 --> 00:00:27,011\n" + 
				"올해는 예전만큼 시끌벅적하지도 않았던 것 같습니다";
		String translation = "00:00:13,850 --> 00:00:16,825\n" + 
				"We're already at the end of May.\n" + 
				"00:00:16,875 --> 00:00:22,440\n" + 
				"May is supposed to be a month where you spend time with other people,\n" + 
				"00:00:22,440 --> 00:00:27,011\n" + 
				"but this year, things were somewhat different.";
		video.setOriginalText(originalText);
		video.setTranslation(translation);
		mockVideo = video;
	}
	
	@Test
	public void getLinesContainingShouldEqual() {
		Map<String, String[]> lines = mockVideo.getLinesContaining("올해");
		assertEquals(lines.get("00:00:22")[0], "올해는 예전만큼 시끌벅적하지도 않았던 것 같습니다");
		assertEquals(lines.get("00:00:22")[1], "but this year, things were somewhat different.");
	}
	
	@Test
	public void isTranslationValidShouldBeTrue() {
		assertTrue(mockVideo.isTranslationValid());
	}
	
	@Test
	public void isTranslationValidShouldBeFalse() {
		mockVideo.setTranslation("10:00:13,850 --> 00:00:16,825\n" + 
				"We're already at the end of May.\n" + 
				"00:00:16,875 --> 00:00:22,440\n" + 
				"May is supposed to be a month where you spend time with other people,\n" + 
				"00:00:22,440 --> 00:00:27,011\n" + 
				"but this year, things were somewhat different.");
		assertFalse(mockVideo.isTranslationValid());
	}
	
	@Test
	public void formatTemporizationTest() {
		String temporization = "00:00:13,850 --> 00:00:16,825\n";
		String formattedTemporization = mockVideo.formatTemporization(temporization);
		assertEquals(formattedTemporization, "00:00:13");
	}
	
	@Test
	public void formatTextTest() {
		String originalText = "00:00:13,850 --> 00:00:16,825\n" + 
				"어느덧 5월이 저물어 가고 있습니다\n" + 
				"어느덧 5월이 저물어 가고 있습니다\n" + 
				"00:00:16,875 --> 00:00:22,440\n" + 
				"원래였다면 많은 사람들과 함께하는 5월이겠지만";
		String expectedString = "00:00:13\n어느덧 5월이 저물어 가고 있습니다 어느덧 5월이 저물어 가고 있습니다\n00:00:16\n원래였다면 많은 사람들과 함께하는 5월이겠지만";
		String result = mockVideo.formatText(originalText);
		assertEquals(expectedString, result);
	}
}
