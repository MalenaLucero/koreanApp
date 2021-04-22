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
public class Text implements Translatable{
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
	
	public Text() {}

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
		boolean isValid = true;
		String[] originalTextArray = FormatUtil.textFromStringToArrayWithoutEmptyStrings(originalText);
		String[] translationArray = FormatUtil.textFromStringToArrayWithoutEmptyStrings(translation);
		if(originalTextArray.length != translationArray.length) {
			System.out.println("Array lengths are not the same");
			isValid = false;
		} 
		
		//checks for equal number of sentences per paragraph
		for (int i = 0; i < originalTextArray.length; i++) {
			if(originalTextArray[i].split("\\.").length != translationArray[i].split("\\.").length) {
				System.out.println("line: " + originalTextArray[i]);
				System.out.println("paragraph: " + (i + 1));
				System.out.println("original length: " + originalTextArray[i].split("\\.").length);
				System.out.println("translation length: " + translationArray[i].split("\\.").length);
				isValid = false;
			}
		}
		return isValid;
	}

	public Map<String, String[]> getLinesContaining(String word) {
		String[] originalTextArray = FormatUtil.textFromStringToArrayWithoutEmptyStrings(originalText);
		String[] translationArray = FormatUtil.textFromStringToArrayWithoutEmptyStrings(translation);
		Map<String, String[]> lines = new HashMap<String, String[]>();
		
		for (int i = 0; i < originalTextArray.length; i++) {
			if(originalTextArray[i].contains(word)) {
				int paragraphNumber = i + 1;	
				String[] originalSentences = FormatUtil.textFromParagraphToSentenceArray(originalTextArray[i]);
				String[] translationSentences = FormatUtil.textFromParagraphToSentenceArray(translationArray[i]);
				for (int j = 0; j < originalSentences.length; j++) {
					if(originalSentences[j].contains(word)) {
						int sentenceNumber = j+1;
						String[] linesArray = {originalSentences[j].trim(), translationSentences[j].trim()};
						lines.put(String.format("%s: %s", paragraphNumber, sentenceNumber), linesArray);
					}
				}
			}
		}
		return FormatUtil.deleteMapDuplicates(lines);
	}
}