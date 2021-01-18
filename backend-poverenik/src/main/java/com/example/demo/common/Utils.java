package com.example.demo.common;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.example.demo.exception.MyException;

public class Utils {
	
	public static String readFile(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public static String getBroj(Document document) {
		return document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
	}
	
	public static String getReferences(List<Integer> brojevi) {
		String xpathExp = "[(";
		for (int i = 0; i < brojevi.size(); ++i) {
			if (i == 0) {
				xpathExp += "broj = '" + brojevi.get(i) + "'";
			} 
			else {
				xpathExp += " or broj = '" + brojevi.get(i) + "'";
			}
		}
		xpathExp += ")]";
		return xpathExp;
	}

	public static void setReferences(Document document, Node reference, List<Integer> brojevi, String tip) {
		for (int broj: brojevi) {
			Element ref = document.createElementNS(Namespaces.OSNOVA, "ref");
			ref.setAttribute("tip", tip);
			ref.setTextContent(broj + "");
			reference.appendChild(ref);
		}
	}

}
