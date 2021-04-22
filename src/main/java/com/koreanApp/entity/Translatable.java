package com.koreanApp.entity;

import java.util.Map;

public interface Translatable {
	public boolean isTranslationValid();
	
	public Map<String, String[]> getLinesContaining(String word);
}
