package com.koreanApp.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

import com.koreanApp.enums.VideoTypes;
import com.koreanApp.util.FormatUtil;

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
	private VideoTypes type;
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
		this.originalText = formatText(originalText);
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = formatText(translation);
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public VideoTypes getType() {
		return type;
	}

	public void setType(VideoTypes type) {
		this.type = type;
	}
	
	public Integer getIdArtist() {
		return idArtist;
	}

	public void setIdArtist(Integer idArtist) {
		this.idArtist = idArtist;
	}
	
	private Map<String, String> textFromStringToMap(String text) {
		String[] textArray = text.split("\\n");
		Map<String, String> textMap = new HashMap<String, String>();
		String temporization = null;
		for (int i = 0; i < textArray.length; i++) {
			if(textArray[i].contains("00:0") && textArray[i].length() == 8) {
				temporization = textArray[i];
			} else {
				textMap.put(temporization, textArray[i]);
			}
		}
		return textMap;
	}
	
	public Map<String, String[]> getLinesContaining(String word) {
		Map<String, String> originalTextMap = textFromStringToMap(originalText);
		Map<String, String> translationMap = textFromStringToMap(translation);
		Map<String, String[]> lines = new HashMap<String, String[]>();
		
		for (String key: originalTextMap.keySet()) {
			if(originalTextMap.get(key).contains(word)) {
				String[] linesArray = {originalTextMap.get(key), translationMap.get(key)};
				lines.put(key, linesArray);
			}
		}
		return FormatUtil.deleteMapDuplicates(lines);
	}
	
	public boolean isTranslationValid() {
		boolean isValid = true;
		Map<String, String> originalTextMap = textFromStringToMap(originalText);
		Map<String, String> translationMap = textFromStringToMap(translation);
		for(String key: originalTextMap.keySet()) {
			if(!translationMap.containsKey(key)) {
				isValid = false;
			}
		}
		return isValid;
	}
	
	public String formatText(String text) {
		String[] textArray = text.split("\\n");
		List<String> formattedTextList = new LinkedList<String>();
		for (int i = 0; i < textArray.length; i++) {
			if(textArray[i].contains("-->")) {
				String temporization = textArray[i];
				formattedTextList.add(formatTemporization(temporization));
			} else {
				String line = textArray[i];
				while(i < textArray.length - 1 && !textArray[i + 1].contains("-->")) {
					i++;
					line = line + " " + textArray[i];
				}
				formattedTextList.add(formatLine(line));
			}
		}
		String listToString = "";
		for(int i = 0; i < formattedTextList.size(); i++) {
			if(i == formattedTextList.size() - 1) {
				listToString = listToString + formattedTextList.get(i);
			} else {
				listToString = listToString + formattedTextList.get(i) + "\n";
			}
		}
		return listToString;
	}
	
	public String formatTemporization(String temporization) {
		return temporization.substring(0, 8);
	}
	
	public String formatLine(String line) {
		return line.replace("\"", "'");
	}
}
