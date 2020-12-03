package com.example.demo.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
		Uloga uloga = Uloga.valueOf(element.getElementsByTagName("korisnik:uloga").item(0).getTextContent());
		String email = element.getElementsByTagName("korisnik:email").item(0).getTextContent();
		String lozinka = element.getElementsByTagName("korisnik:lozinka").item(0).getTextContent();
		String ime = element.getElementsByTagName("korisnik:ime").item(0).getTextContent();
		String prezime = element.getElementsByTagName("korisnik:prezime").item(0).getTextContent();
		String potpis = element.getElementsByTagName("korisnik:potpis").item(0).getTextContent();
		Adresa adresa = this.adresaParser.parse((Element) element.getElementsByTagName("osnova:Adresa").item(0));
		return new Korisnik(uloga, email, lozinka, ime, prezime, adresa, potpis);
	}

	@Override
	public Element parse(Korisnik type, Document document) {
		Element korisnik = document.createElementNS("https://github.com/draganagrbic998/xml/korisnik", "korisnik:Korisnik");
		korisnik.setAttributeNS(Constants.XSI_NAMESPACE, "xsi:schemaLocation", "https://github.com/draganagrbic998/xml/korisnik ../xsd/korisnik.xsd");
		korisnik.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:korisnik", "https://github.com/draganagrbic998/xml/korisnik ../xsd/korisnik.xsd");
		Element uloga = document.createElementNS("https://github.com/draganagrbic998/xml/korisnik", "korisnik:uloga");
		uloga.setTextContent(type.getUloga() + "");
		Element email = document.createElementNS("https://github.com/draganagrbic998/xml/korisnik", "korisnik:email");
		email.setTextContent(type.getEmail());
		Element lozinka = document.createElementNS("https://github.com/draganagrbic998/xml/korisnik", "korisnik:lozinka");
		lozinka.setTextContent(type.getLozinka());
		Element ime = document.createElementNS("https://github.com/draganagrbic998/xml/korisnik", "korisnik:ime");
		ime.setTextContent(type.getIme());
		Element prezime = document.createElementNS("https://github.com/draganagrbic998/xml/korisnik", "korisnik:prezime");
		prezime.setTextContent(type.getPrezime());
		Element adresa = this.adresaParser.parse(type.getAdresa(), document);
		Element potpis = document.createElementNS("https://github.com/draganagrbic998/xml/korisnik", "korisnik:potpis");
		potpis.setTextContent(type.getPotpis());
		korisnik.appendChild(uloga);
		korisnik.appendChild(email);
		korisnik.appendChild(lozinka);
		korisnik.appendChild(ime);
		korisnik.appendChild(prezime);
		korisnik.appendChild(adresa);
		korisnik.appendChild(potpis);
		return korisnik;
	}
	
}
