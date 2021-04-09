package com.koreanApp.payload;

import java.util.Set;

public class SimpleSearchRequest {
	private String word;
	private Set<String> type;
	private Set<String> artist;
	
	public SimpleSearchRequest(){}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Set<String> getType() {
		return type;
	}

	public void setType(Set<String> type) {
		this.type = type;
	}

	public Set<String> getArtist() {
		return artist;
	}

	public void setArtist(Set<String> artist) {
		this.artist = artist;
	};
}
