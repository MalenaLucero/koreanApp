package com.koreanApp.payload;

import java.util.Arrays;

import com.koreanApp.enums.SourceTypeEnum;

public class SearchRequest {
	private String word;
	private SourceTypeEnum[] sourceTypes;
	private Integer idArtist;
	
	public SearchRequest() {}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public SourceTypeEnum[] getSourceTypes() {
		return sourceTypes;
	}

	public void setSourceTypes(SourceTypeEnum[] sourceTypes) {
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
