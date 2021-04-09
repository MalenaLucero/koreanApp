package com.koreanApp.payload;

public class LyricRequest {
	private Integer idArtist;
	private String word;
	
	public LyricRequest() {}
	
	public LyricRequest(Integer idArtist, String word) {
		this.idArtist = idArtist;
		this.word = word;
	}
	
	public Integer getIdArtist() {
		return idArtist;
	}
	public void setIdArtist(Integer idArtist) {
		this.idArtist = idArtist;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public String toString() {
		return "LyricRequest [idArtist=" + idArtist + ", word=" + word + "]";
	}
}
