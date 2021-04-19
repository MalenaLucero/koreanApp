package com.koreanApp.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.koreanApp.enums.VideoTypes;

public class FormatUtil {
	public static String[] textFromStringToArray(String text) {
		String[] textArray = text.split("\\n");
		String[] textArrayWithoutEmptyStrings = Arrays.stream(textArray).filter(x -> x.length() > 0).toArray(String[]::new);
		return textArrayWithoutEmptyStrings;
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
	
	public static boolean isVideoTypeValid(String type) {
		String[] videoTypes = Arrays.toString(VideoTypes.values()).replaceAll("^.|.$", "").split(", ");
		boolean isValid = false;
		for (String videoType: videoTypes) {
			if(videoType.equals(type)) {
				isValid = true;
			}
		}
		return isValid;
	}
	
	public static String deleteFromString(String fullString, String stringToDelete) {
		if(fullString.contains(stringToDelete)) {
			String newString = fullString.replace(stringToDelete, "");
			return deleteFromString(newString, stringToDelete);
		} else {
			return fullString;
		}
	}
}
