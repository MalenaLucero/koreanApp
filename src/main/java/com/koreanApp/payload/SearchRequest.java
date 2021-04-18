package com.koreanApp.payload;

import com.koreanApp.enums.SourceTypes;

public class SearchRequest {
	private String word;
	private SourceTypes[] sourceTypes;
	private Integer idArtist;
	private String videoType;
	
	public SearchRequest() {}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public SourceTypes[] getSourceTypes() {
		return sourceTypes;
	}

	public void setSourceTypes(SourceTypes[] sourceTypes) {
		this.sourceTypes = sourceTypes;
	}
	public Integer getIdArtist() {
		return idArtist;
	}
	public void setIdArtist(Integer idArtist) {
		this.idArtist = idArtist;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}
	
	
}
