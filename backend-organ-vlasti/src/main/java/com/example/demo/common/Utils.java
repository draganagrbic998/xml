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
	
	public static String getId(Document document) {
		return ((Element) document.getFirstChild()).getAttribute("about");
	}

	public static String getBroj(Document document) {
		String[] array = ((Element) document.getFirstChild()).getAttribute("about").split("\\/");
		return array[array.length - 1];
	}
	
	public static String getReferences(List<String> ids) {
		String xpathExp = "[(";
		for (int i = 0; i < ids.size(); ++i) {
			if (i == 0) {
				xpathExp += "@about = '" + ids.get(i) + "'";
			} 
			else {
				xpathExp += " or @about = '" + ids.get(i) + "'";
			}
		}
		xpathExp += ")]";
		return xpathExp;
	}

	public static void setReferences(Document document, Node references, List<String> ids, String type) {
		for (String id: ids) {
			Element ref = document.createElementNS(Namespaces.OSNOVA, "ref");
			ref.setAttribute("tip", type);
			String[] array = id.split("\\/");
			ref.setTextContent(array[array.length - 1]);
			references.appendChild(ref);
		}
	}
	
}
