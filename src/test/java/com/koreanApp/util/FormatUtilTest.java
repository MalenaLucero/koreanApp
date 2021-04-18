package com.koreanApp.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.koreanApp.KoreanAppTest;

public class FormatUtilTest extends KoreanAppTest{
	@Test 
	public void textFromStringToArrayWithOneParagraphShouldEqual(){
		String text = "Yeah, yeah\nOkay, okay, okay, okay\nYeah\n3년이 지났네\nAgust D";
		String[] textArray = FormatUtil.textFromStringToArray(text);
		assertEquals(5, textArray.length);
	}
	
	@Test 
	public void textFromStringToArrayWithMoreThanOneParagraphShouldEqual(){
		String text = "SUGA has this way of talking passionately with a deadpan look on his face. Full of passion about his life and music.\n\n"
				+ "How is your shoulder?\n"
				+ "SUGA: Good. I think it’ll get even better once I take off this brace.";
		String[] textArray = FormatUtil.textFromStringToArray(text);
		assertEquals(3, textArray.length);
	}
	
	@Test
	public void isStringEmptyTest() {
		String nullWord = null;
		String emptyString = "";
		String fullWord = "word";
		assertTrue(FormatUtil.isStringEmpty(nullWord));
		assertTrue(FormatUtil.isStringEmpty(emptyString));
		assertFalse(FormatUtil.isStringEmpty(fullWord));
	}
	
	@Test
	public void deleteMapDuplicatesShouldEqual() {
		Map<String, String[]> map = new HashMap<String, String[]>();
		String[] firstArray = {"original text", "translated text"};
		String[] secondArray = {"original text", "text translated slightly different"};
		map.put("1", firstArray);
		map.put("2", secondArray);
		Map<String, String[]> mapWithoutDuplicates = FormatUtil.deleteMapDuplicates(map);
		assertTrue(mapWithoutDuplicates.containsKey("1"));
		assertFalse(mapWithoutDuplicates.containsKey("2"));
	}
	
	@Test
	public void isVideoTypeValidTest() {
		String validType = "RADIO";
		String invalidType = "invalidType";
		assertTrue(FormatUtil.isVideoTypeValid(validType));
		assertFalse(FormatUtil.isVideoTypeValid(invalidType));
	}
}
