package com.example.demo.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.constants.Namespaces;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.ZalbaRepository;

@Service
public class ZalbaService {

	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private ZalbaRepository zalbaRepository;
		
	public void save(String xml) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Document document = this.domParser.buildDocument(xml);
		document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).setTextContent(format.format(new Date()));
		document.getElementsByTagNameNS(Namespaces.OSNOVA, "potpis").item(0).setTextContent("");
		document.getElementsByTagNameNS(Namespaces.OSNOVA, "odgovoreno").item(0).setTextContent("false");
		this.zalbaRepository.save(this.domParser.buildXml(document));
	}
	
}
