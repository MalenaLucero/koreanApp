package com.koreanApp.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.koreanApp.util.FormatUtil;

@Entity
public class Lyric {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;
	@Column(name="original_text")
	private String originalText;
	private String translation;
	private String link;
	@Column(name="id_artist")
	private Integer idArtist;
	@ManyToOne
	@JoinColumn(name="id_artist", insertable=false, updatable=false)
	private Artist artist;
	
	public Lyric() {}
	
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
	public Integer getIdArtist() {
		return idArtist;
	}
	public void setIdArtist(Integer idArtist) {
		this.idArtist = idArtist;
	}
	
	public boolean isTranslationValid() {
		String[] originalTextArray = FormatUtil.textFromStringToArray(originalText);
		String[] translationArray = FormatUtil.textFromStringToArray(translation);
		return originalTextArray.length == translationArray.length;
	}
	
	public Map<String, String[]> getLinesContaining(String word) {
		String[] originalTextArray = FormatUtil.textFromStringToArray(originalText);
		String[] translationArray = FormatUtil.textFromStringToArray(translation);
		Map<String, String[]> lines = new HashMap<String, String[]>();
		
		for (int i = 0; i < originalTextArray.length; i++) {
			if(originalTextArray[i].contains(word)) {
				String[] linesArray = {originalTextArray[i], translationArray[i]};
				lines.put(Integer.toString(i + 1), linesArray);
			}
		}
		return FormatUtil.deleteMapDuplicates(lines);
	}
}
