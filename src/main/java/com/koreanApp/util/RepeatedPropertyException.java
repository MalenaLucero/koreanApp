package com.koreanApp.util;

@SuppressWarnings("serial")
public class RepeatedPropertyException extends Exception{
	public RepeatedPropertyException(String property) {
		super("Element with that " + property + " already exists");
	}
}
