package io.github.berkayelken.advocate.telegram.bot.ai.ext.call.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GeminiPart {
	private static final String PROMPT = "Seninle t\u0131rnak i\u00e7inde payla\u015faca\u011f\u0131m soruyu bir avukat gibi t\u00fcrk hukukuna uygun \u015fekilde cevaplay\u0131p devam\u0131nda payla\u015faca\u011f\u0131m json modeline parse edebilir misin? \"%s\" json modeli -> {question: string, answer: string, abstractAnswer: string, abstractQuestion: string, category: string, issueType: string} question sorunun kendisi, answer senin verece\u011fin cevap, abstractAnswer verdi\u011fin cevab\u0131n \u00f6zeti, abstractQuestion sorulan sorunun \u00f6zeti, category soruna kar\u015f\u0131 ihtiya\u00e7 olan avukatl\u0131k tipi(bo\u015fanma avukatl\u0131\u011f\u0131 ticaret avukatl\u0131\u011f\u0131 gibi), issueType sorulan soru ile ilgilenecek mahkeme \u00e7e\u015fidi. Bana sadece json'\u0131 d\u00f6necek \u015fekilde cevap verebilir misin?";

	private String text;

	GeminiPart(String text) {
		this.text = String.format(PROMPT, text);
	}
}
