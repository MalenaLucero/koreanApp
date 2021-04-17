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
	public void textFromStringToArrayTest(){
		String text = "Yeah, yeah\nOkay, okay, okay, okay\nYeah\n3년이 지났네\nAgust D";
		String[] textArray = FormatUtil.textFromStringToArray(text);
		assertEquals(5, textArray.length);
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
}
