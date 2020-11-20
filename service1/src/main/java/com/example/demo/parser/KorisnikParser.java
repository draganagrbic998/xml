package com.example.demo.parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.example.demo.dom.DOMParser;
import com.example.demo.model.Korisnik;

@Component
public class KorisnikParser implements Parser<Korisnik> {

	@Autowired
	private DOMParser domParser;

	@Override
	public Korisnik parse(Node node) {
		
		try {
			Document document = this.domParser.buildDocumentFromFile("data/xml/zahtev1.xml");
			Element root = document.getDocumentElement();
			System.out.println(root);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	
	
}
