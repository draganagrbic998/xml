package com.example.demo.fuseki;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.demo.common.Namespaces;

public class SearchUtil {
	
	public static String predicatePart(Node document) {
		String suma = "";
		NodeList children = document.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			String metapodatak = children.item(i).getLocalName();
			if (logop(metapodatak) == null) {
				suma += predicate(metapodatak);
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
			if (logop(metapodatak) == null) {
				suma += filter(metapodatak, child.getTextContent(), child.getAttributes().getNamedItem("not") != null);
			}
			else {
				suma += logop(metapodatak);
			}
		}
		return suma;
	}

	private static String predicate(String metapodatak) {
		return String.format("?doc <%s> ?%s .%n", Namespaces.PREDIKAT + metapodatak, metapodatak);
	}
	
	private static String filter(String metapodatak, String param, boolean not) {
		String temp = String.format("CONTAINS(UCASE(str(?%s)), UCASE(\"%s\"))%n", metapodatak, param);
		if (not) {
			return String.format("!(%s)", temp);
		}
		return temp;
	}
	
	private static String logop(String op) {
		if (op.equals("and")) {
			return "&&";
		}
		else if (op.equals("or")) {
			return "||";
		}
		return null;
	}

}
