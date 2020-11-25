package com.example.demo.parser;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.model.Adresa;

@Component
public class AdresaParser implements Parser<Adresa> {

	@Override
	public Adresa parse(Element element) {
		String mesto = element.getElementsByTagName("osnova:mesto").item(0).getTextContent();
		String postanskiBroj = element.getElementsByTagName("osnova:postanski_broj").item(0).getTextContent();
		String ulica = element.getElementsByTagName("osnova:ulica").item(0).getTextContent();
		String broj = element.getElementsByTagName("osnova:broj").item(0).getTextContent();
		return new Adresa(mesto, postanskiBroj, ulica, broj);
	}

	@Override
	public Element parse(Adresa type, Document document) {
		Element adresa = document.createElement("osnova:Adresa");
		Element mesto = document.createElement("osnova:mesto");
		mesto.setTextContent(type.getMesto());
		Element postanskiBroj = document.createElement("osnova:postanski_broj");
		postanskiBroj.setTextContent(type.getPostanskiBroj());
		Element ulica = document.createElement("osnova:ulica");
		ulica.setTextContent(type.getUlica());
		Element broj = document.createElement("osnova:broj");
		broj.setTextContent(type.getBroj());
		adresa.appendChild(mesto);
		adresa.appendChild(postanskiBroj);
		adresa.appendChild(ulica);
		adresa.appendChild(broj);
		return adresa;
	}
	
}
