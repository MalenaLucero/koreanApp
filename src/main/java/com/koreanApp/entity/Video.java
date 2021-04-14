package com.koreanApp.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.koreanApp.enums.VideoTypeEnum;

@Entity
public class Video {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;
	@Column(name = "original_text")
	private String originalText;
	private String translation;
	private String link;
	@Enumerated(EnumType.STRING)
	@Column
	private VideoTypeEnum type;
	@Column(name="id_artist")
	private Integer idArtist;
	@ManyToOne
	@JoinColumn(name="id_artist", insertable=false, updatable=false)
	private Artist artist;

	public Video() {}

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

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public VideoTypeEnum getType() {
		return type;
	}

	public void setType(VideoTypeEnum type) {
		this.type = type;
	}
	
	public Integer getIdArtist() {
		return idArtist;
	}

	public void setIdArtist(Integer idArtist) {
		this.idArtist = idArtist;
	}
	
	public static Map<String, String> textFromStringToMap(String text) {
		String[] textArray = text.split("\\n");
		Map<String, String> textMap = new HashMap<String, String>();
		String temporization = null;
		for (int i = 0; i < textArray.length; i++) {
			if(textArray[i].contains("-->")) {
				temporization = textArray[i];
			} else {
				textMap.put(temporization, textArray[i]);
			}
		}
		return textMap;
	}
	
	public static Map<String, String[]> getLinesContaining(String originalText, String translation, String word) {
		Map<String, String> originalTextMap = textFromStringToMap(originalText);
		Map<String, String> translationMap = textFromStringToMap(translation);
		Map<String, String[]> lines = new HashMap<String, String[]>();
		
		for (String key: originalTextMap.keySet()) {
			if(originalTextMap.get(key).contains(word)) {
				String[] linesArray = {originalTextMap.get(key), translationMap.get(key)};
				lines.put(key, linesArray);
			}
		}
		return deleteMapDuplicates(lines);
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
