package com.koreanApp.util;

@SuppressWarnings("serial")
public class InvalidTypeException extends Exception{
	public InvalidTypeException(String type) {
		super("Invalid type " + type);
	}
}
