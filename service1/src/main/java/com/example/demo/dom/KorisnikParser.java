package com.example.demo.dom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.common.Namespaces;
import com.example.demo.common.Schemas;
import com.example.demo.model.Adresa;
import com.example.demo.model.Korisnik;
import com.example.demo.model.enums.Uloga;

@Component
public class KorisnikParser implements Parser<Korisnik> {
	
	@Autowired
	private AdresaParser adresaParser;
	
	@Override
	public Korisnik parse(Element element) {
		Uloga uloga = Uloga.valueOf(element.getElementsByTagNameNS(Namespaces.KORISNIK, "uloga").item(0).getTextContent());
		String ime = element.getElementsByTagNameNS(Namespaces.KORISNIK, "ime").item(0).getTextContent();
		String prezime = element.getElementsByTagNameNS(Namespaces.KORISNIK, "prezime").item(0).getTextContent();
		if (!element.getAttributeNS(Namespaces.XSI, "type").equals("korisnik:TPodnosilac")) {
			return new Korisnik(uloga, ime, prezime);
		}
		Adresa adresa = this.adresaParser.parse((Element) element.getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0));
		String email = element.getElementsByTagNameNS(Namespaces.KORISNIK, "email").item(0).getTextContent();
		String potpis = element.getElementsByTagNameNS(Namespaces.KORISNIK, "potpis").item(0).getTextContent();
		//String jmbg = element.getElementsByTagNameNS(Namespaces.KORISNIK, "jmbg").item(0).getTextContent();
		//String lozinka = element.getElementsByTagNameNS(Namespaces.KORISNIK, "lozinka").item(0).getTextContent();
		return new Korisnik(uloga, ime, prezime, adresa, email, potpis);
	}

	@Override
	public Element parse(Korisnik type, Document document) {
		Element korisnik = document.createElementNS(Namespaces.KORISNIK, "korisnik:Korisnik");
		korisnik.setAttributeNS(Namespaces.XMLNS, "xmlns:korisnik", Namespaces.KORISNIK);
		Element uloga = document.createElementNS(Namespaces.KORISNIK, "korisnik:uloga");
		uloga.setTextContent(type.getUloga() + "");
		Element ime = document.createElementNS(Namespaces.KORISNIK, "korisnik:ime");
		ime.setTextContent(type.getIme());
		Element prezime = document.createElementNS(Namespaces.KORISNIK, "korisnik:prezime");
		prezime.setTextContent(type.getPrezime());
		korisnik.appendChild(uloga);
		korisnik.appendChild(ime);
		korisnik.appendChild(prezime);
		if (type.getAdresa() != null) {
			korisnik.setAttributeNS(Namespaces.XSI, "xsi:type", "korisnik:TPodnosilac");
			Element adresa = this.adresaParser.parse(type.getAdresa(), document);
			Element email = document.createElementNS(Namespaces.KORISNIK, "korisnik:email");
			email.setTextContent(type.getEmail());
			Element potpis = document.createElementNS(Namespaces.KORISNIK, "korisnik:potpis");
			potpis.setTextContent(type.getPotpis());
			korisnik.appendChild(adresa);
			korisnik.appendChild(email);
			korisnik.appendChild(potpis);
			//Element jmbg = document.createElementNS(Namespaces.KORISNIK, "korisnik:jmbg");
			//jmbg.setTextContent(type.getJmbg());
			//Element lozinka = document.createElementNS(Namespaces.KORISNIK, "korisnik:lozinka");
			//lozinka.setTextContent(type.getLozinka());
			//korisnik.appendChild(jmbg);
			//korisnik.appendChild(lozinka);
		}
		return korisnik;
	}

	@Override
	public String getSchema() {
		return Schemas.KORISNIK;
	}

}
