package com.example.demo.fuseki;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.demo.common.Namespaces;

public class SearchUtil {

	private static String predikat(String metapodatak) {
		return String.format("?doc <%s> ?%s .%n", Namespaces.PREDIKAT + metapodatak, metapodatak);
	}
	
	private static String filter(String metapodatak, String param, boolean not) {
		String temp = String.format("CONTAINS(UCASE(str(?%s)), UCASE(\"%s\"))%n", metapodatak, param);
		if (not) {
			return String.format("!(%s)", temp);
		}
		return temp;
	}
	
	private static String logOp(String op) {
		if (op.equals("and")) {
			return "&&";
		}
		else if (op.equals("or")) {
			return "||";
		}
		return null;
	}
	
	public static String predikatPart(Node document) {
		String suma = "";
		NodeList children = document.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			String metapodatak = children.item(i).getLocalName();
			if (logOp(metapodatak) == null) {
				suma += predikat(metapodatak);
			}
		}
		return suma;
	}
	
	public static String filterPart(Node document) {
		String suma = "";
		NodeList children = document.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			Node child = children.item(i);
			String metapodatak = child.getLocalName();
			if (logOp(metapodatak) == null) {
				suma += filter(metapodatak, child.getTextContent(), child.getAttributes().getNamedItem("not") != null);
			}
			else {
				suma += logOp(metapodatak);
			}
		}
		return suma;
	}

}
