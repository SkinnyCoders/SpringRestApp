package com.tanto.functions;

public class FileNameReplace {
	
	public String replaceName(String nameFile) {
		String result = nameFile.replaceAll("\\s+", "_").toLowerCase();
		
		return System.currentTimeMillis()+"-"+result;
	}
}
