package com.example.demo.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import com.example.demo.dom.Constants;
import com.example.demo.model.Adresa;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Uloga;

@Component
public class KorisnikParser implements Parser<Korisnik> {
	
	@Autowired
	private AdresaParser adresaParser;

	@Override
	public Korisnik parse(Element element) {
		NamedNodeMap attributes = element.getAttributes();
		String id = attributes.getNamedItem("id").getNodeValue();
		Uloga uloga = Uloga.valueOf(attributes.getNamedItem("uloga").getNodeValue());
		String email = element.getElementsByTagName("korisnik:Email").item(0).getTextContent();
		String lozinka = element.getElementsByTagName("korisnik:Lozinka").item(0).getTextContent();
		String ime = element.getElementsByTagName("korisnik:Ime").item(0).getTextContent();
		String prezime = element.getElementsByTagName("korisnik:Prezime").item(0).getTextContent();
		String potpis = element.getElementsByTagName("korisnik:Potpis").item(0).getTextContent();
		Adresa adresa = this.adresaParser.parse((Element) element.getElementsByTagName("korisnik:Adresa").item(0));
		return new Korisnik(id, uloga, email, lozinka, ime, prezime, adresa, potpis);
	}

	@Override
	public Element parse(String namespace, Korisnik type, Document document) {
		Element korisnik = document.createElement(namespace + ":Korisnik");
		korisnik.setAttributeNS(Constants.XSI_NAMESPACE, "xsi:schemaLocation", "https://github.com/draganagrbic998/xml/korisnik ../xsd/korisnik.xsd");
		korisnik.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:osnova", "https://github.com/draganagrbic998/xml/osnova ../xsd/osnova.xsd");
		korisnik.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:korisnik", "https://github.com/draganagrbic998/xml/korisnik ../xsd/korisnik.xsd");
		korisnik.setAttribute("id", type.getId());
		korisnik.setAttribute("uloga", type.getUloga() + "");
		Element email = document.createElement(namespace + ":Email");
		email.setTextContent(type.getEmail());
		Element lozinka = document.createElement(namespace + ":Lozinka");
		lozinka.setTextContent(type.getLozinka());
		Element ime = document.createElement(namespace + ":Ime");
		ime.setTextContent(type.getIme());
		Element prezime = document.createElement(namespace + ":Prezime");
		prezime.setTextContent(type.getPrezime());
		Element adresa = this.adresaParser.parse(namespace, type.getAdresa(), document);
		Element potpis = document.createElement(namespace + ":Potpis");
		korisnik.appendChild(email);
		korisnik.appendChild(lozinka);
		korisnik.appendChild(ime);
		korisnik.appendChild(prezime);
		korisnik.appendChild(adresa);
		korisnik.appendChild(potpis);
		return korisnik;
	}
	
}
