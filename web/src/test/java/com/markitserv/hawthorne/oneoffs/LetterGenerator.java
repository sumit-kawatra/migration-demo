package com.markitserv.hawthorne.oneoffs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LetterGenerator {

	private char generateLetter(int index) {

		if (index > 52) {
			throw new RuntimeException("Sorry, cant do > 52");
		}

		int code = index + 64;
		if (index > 26) {
			code = code + 6;
		}
		return Character.toChars(code)[0];
	}

	@Test
	public void run() {
		assertEquals('A', generateLetter(1));
		assertEquals('Z', generateLetter(26));
		assertEquals('a', generateLetter(27));
		assertEquals('z', generateLetter(52));
	}
}
