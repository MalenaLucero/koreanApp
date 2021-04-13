package com.koreanApp.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FormatUtilTest{
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
}
