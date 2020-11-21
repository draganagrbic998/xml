package com.example.demo.parser;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.model.Adresa;

@Component
public class AdresaParser implements Parser<Adresa> {

	@Override
	public Adresa parse(Element element) {
		String mesto = element.getElementsByTagName("osnova:Mesto").item(0).getTextContent();
		String postanskiBroj = element.getElementsByTagName("osnova:Postanski_broj").item(0).getTextContent();
		String ulica = element.getElementsByTagName("osnova:Ulica").item(0).getTextContent();
		String broj = element.getElementsByTagName("osnova:Broj").item(0).getTextContent();
		return new Adresa(mesto, postanskiBroj, ulica, broj);
	}

	@Override
	public Element parse(String namespace, Adresa type, Document document) {
		Element adresa = document.createElement(namespace + ":Adresa");
		Element mesto = document.createElement("osnova:Mesto");
		mesto.setTextContent(type.getMesto());
		Element postanskiBroj = document.createElement("osnova:Postanski_broj");
		postanskiBroj.setTextContent(type.getPostanskiBroj());
		Element ulica = document.createElement("osnova:Ulica");
		ulica.setTextContent(type.getUlica());
		Element broj = document.createElement("osnova:Broj");
		broj.setTextContent(type.getBroj());
		adresa.appendChild(mesto);
		adresa.appendChild(postanskiBroj);
		adresa.appendChild(ulica);
		adresa.appendChild(broj);
		return adresa;
	}
	
}
