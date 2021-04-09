package com.koreanApp.util;

import java.util.HashMap;
import java.util.Map;

public class FormatUtil {
	public static String[] textFromStringToArray(String text) {
		return text.split("\\n");
	}
	
	public static String[] textFromParagraphToSentenceArray(String text) {
		return text.split("\\.");
	}
	
	public static boolean isStringEmpty(String word) {
		return word == null || word.length() == 0;
	}
	
	public static boolean isNumberEmpty(Integer number) {
		return number == null || number == 0;
	}
	
	public static Map<String, String[]> deleteMapDuplicates(Map<String, String[]> lines){
		Map<String, String[]> linesWithoutDuplicates = new HashMap<String, String[]>();
		for(String key: lines.keySet()) {
			boolean isDuplicate = false;
			for(String secondKey: linesWithoutDuplicates.keySet()) {
				if(lines.get(key)[0].equals(linesWithoutDuplicates.get(secondKey)[0])) {
					isDuplicate = true;
				}
			}
			if(!isDuplicate) {
				linesWithoutDuplicates.put(key, lines.get(key));
			}
		}
		return linesWithoutDuplicates;
	}
}
