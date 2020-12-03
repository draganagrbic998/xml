package com.example.demo.parser;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.dom.Constants;
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
		Element adresa = document.createElementNS("https://github.com/draganagrbic998/xml/osnova", "osnova:Adresa");
		adresa.setAttributeNS(Constants.XSI_NAMESPACE, "xsi:schemaLocation", "https://github.com/draganagrbic998/xml/osnova ../xsd/osnova.xsd");
		adresa.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:osnova", "https://github.com/draganagrbic998/xml/osnova ../xsd/osnova.xsd");
		Element mesto = document.createElementNS("https://github.com/draganagrbic998/xml/osnova", "osnova:mesto");
		mesto.setTextContent(type.getMesto());
		Element postanskiBroj = document.createElementNS("https://github.com/draganagrbic998/xml/osnova", "osnova:postanski_broj");
		postanskiBroj.setTextContent(type.getPostanskiBroj());
		Element ulica = document.createElementNS("https://github.com/draganagrbic998/xml/osnova", "osnova:ulica");
		ulica.setTextContent(type.getUlica());
		Element broj = document.createElementNS("https://github.com/draganagrbic998/xml/osnova", "osnova:broj");
		broj.setTextContent(type.getBroj());
		adresa.appendChild(mesto);
		adresa.appendChild(postanskiBroj);
		adresa.appendChild(ulica);
		adresa.appendChild(broj);
		return adresa;
	}
	
}
