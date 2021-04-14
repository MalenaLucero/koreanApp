package com.koreanApp.payload;

public class TextRequest {
	private Integer idArtist;
	private String word;
	private String type;
	
	public TextRequest() {}
	
	public TextRequest(Integer idArtist, String word) {
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
