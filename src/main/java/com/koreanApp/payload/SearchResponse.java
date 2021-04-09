package com.koreanApp.payload;

import java.util.Map;

public class SearchResponse {
	private Integer id;
	private String title;
	private Map<String, String[]> lines;
	
	public SearchResponse(Integer id, String title, Map<String, String[]> lines) {
		this.id = id;
		this.title = title;
		this.lines = lines;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, String[]> getLines() {
		return lines;
	}

	public void setLines(Map<String, String[]> lines) {
		this.lines = lines;
	}
}
