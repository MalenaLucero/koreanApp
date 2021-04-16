package com.koreanApp.util;

@SuppressWarnings("serial")
public class InvalidTranslationException extends Exception{
	public InvalidTranslationException() {
		super("Invalid translation format");
	}
}
