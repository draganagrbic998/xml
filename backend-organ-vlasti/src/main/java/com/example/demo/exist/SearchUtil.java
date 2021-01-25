package com.example.demo.exist;

import com.example.demo.model.ObicnaPretraga;

public class SearchUtil {

	public static String pretragaXpath(ObicnaPretraga pretraga) {
		String xpathExp = "";
		//lowercase???
		for (String fraza: pretraga.getFraze()) {
			xpathExp += String.format("[contains(string(), '%s')]", fraza);
		}
		for (String kljucnaRec: pretraga.getKljucneReci().split("\\s+")) {
			xpathExp += String.format("[contains(string(), '%s')]", kljucnaRec);
		}
		return xpathExp;
	}

}
