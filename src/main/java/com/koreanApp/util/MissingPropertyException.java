package com.koreanApp.util;

@SuppressWarnings("serial")
public class MissingPropertyException extends Exception{
	public MissingPropertyException(String property) {
		super("Missing property " + property);
	}
}
