package com.example.demo.parser;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.constants.Namespaces;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class DefaultNamespacePrefixMapper extends NamespacePrefixMapper {

	private Map<String, String> namespaceMap = new HashMap<>();

	public DefaultNamespacePrefixMapper() {
		namespaceMap.put(Namespaces.OSNOVA, "");
		namespaceMap.put(Namespaces.DOKUMENT, "dokument");
	}

	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		String preferredPrefix = this.namespaceMap.get(namespaceUri);
		if (preferredPrefix != null)
			return preferredPrefix;
		return suggestion;
	}
	
}