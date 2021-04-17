package com.koreanApp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TextTest {
	Text mockText;
	
	@BeforeEach
	public void generateMockText() {
		Text text = new Text();
		text.setOriginalText("슈가는 무표정한 얼굴로 뜨거운 이야기를 하곤 한다. 자신의 삶 그리고 음악에 관한.\n\n"
				+ "어깨는 좀 괜찮나요.\n"
				+ "슈가: 좋아요. 곧 보조기를 완전히 풀면 더 좋아질 것 같아요. 완전한 재활에는 몇 개월 이상 걸린다고 하는데, 최대한 빨리 완치될 수 있도록 노력하고 있어요.");
		text.setTranslation("SUGA has this way of talking passionately with a deadpan look on his face. Full of passion about his life and music.\n\n"
				+ "How is your shoulder?\n"
				+ "SUGA: Good. I think it’ll get even better once I take off this brace. Apparently, it takes several months for a full recovery, but I'm trying to get better as fast as possible.");
		mockText = text;
	}
	
	@Test
	public void isTranslationValidShouldBeTrue() {
		assertTrue(mockText.isTranslationValid());
	}
	
	@Test
	public void isTranslationValidShouldBeFalse() {
		mockText.setTranslation("SUGA has this way of talking passionately with a deadpan look on his face. Full of passion about his life and music.\n\n"
				+ "How is your shoulder?\n");
		assertFalse(mockText.isTranslationValid());
	}
	
	@Test
	public void getLinesContainingShouldEqual() {
		Map<String, String[]> lines = mockText.getLinesContaining("슈가");
		assertEquals(lines.get("1: 1")[0], "슈가는 무표정한 얼굴로 뜨거운 이야기를 하곤 한다");
		assertEquals(lines.get("1: 1")[1], "SUGA has this way of talking passionately with a deadpan look on his face");
		assertEquals(lines.get("3: 1")[0], "슈가: 좋아요");
		assertEquals(lines.get("3: 1")[1], "SUGA: Good");
	}
}
