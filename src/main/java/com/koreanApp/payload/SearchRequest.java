package com.koreanApp.payload;

import java.util.Arrays;

import com.koreanApp.enums.SourceType;

public class SearchRequest {
	private String word;
	private SourceType[] sourceTypes;
	private Integer idArtist;
	
	public SearchRequest() {}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public SourceType[] getSourceTypes() {
		return sourceTypes;
	}

	public void setSourceTypes(SourceType[] sourceTypes) {
		this.sourceTypes = sourceTypes;
	}
	public Integer getIdArtist() {
		return idArtist;
	}
	public void setIdArtist(Integer idArtist) {
		this.idArtist = idArtist;
	}

	@Override
	public String toString() {
		return "SearchRequest [word=" + word + ", sourceTypes=" + Arrays.toString(sourceTypes) + ", idArtist="
				+ idArtist + "]";
	}
}
