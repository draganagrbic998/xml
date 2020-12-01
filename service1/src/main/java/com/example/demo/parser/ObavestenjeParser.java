package com.example.demo.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import com.example.demo.dom.Constants;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Obavestenje;
import com.example.demo.model.PodaciOdavestenja;
import com.example.demo.model.Zahtev;

@Component
public class ObavestenjeParser implements Parser<Obavestenje> {
	
	@Autowired
	private KorisnikParser korisnikParser;
	
	@Autowired
	private ZahtevParser zahtevParser;
	
	@Autowired
	private PodaciObavestenjaParser podaciObavestenjaParser;

	@Override
	public Obavestenje parse(Element element) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		NamedNodeMap attributes = element.getAttributes();
		String broj = attributes.getNamedItem("broj").getNodeValue();
		Date datum = format.parse(attributes.getNamedItem("datum").getNodeValue());
		Korisnik sluzbenik = this.korisnikParser.parse((Element) element.getElementsByTagName("korisnik:Korisnik").item(0));
		Zahtev zahtev = this.zahtevParser.parse((Element) element.getElementsByTagName("zahtev:Zahtev").item(0));
		PodaciOdavestenja podaciOdluke = this.podaciObavestenjaParser.parse((Element) element.getElementsByTagName("obavestenje:Podaci_obavestenja").item(0));
		return new Obavestenje(broj, datum, sluzbenik, zahtev, podaciOdluke);
	}

	@Override
	public Element parse(Obavestenje obavestenje, Document document) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Element odluka = document.createElement("odluka:Odluka");
		odluka.setAttributeNS(Constants.XSI_NAMESPACE, "xsi:schemaLocation", "https://github.com/draganagrbic998/xml/odluka ../xsd/odluka.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:organ_vlasti", "https://github.com/draganagrbic998/xml/organ_vlasti ../xsd/organ_vlasti.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:osnova", "https://github.com/draganagrbic998/xml/osnova ../xsd/osnova.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:korisnik", "https://github.com/draganagrbic998/xml/korisnik ../xsd/korisnik.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:zahtev", "https://github.com/draganagrbic998/xml/zahtev ../xsd/zahtev.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:obavestenje", "https://github.com/draganagrbic998/xml/obavestenje ../xsd/obavestenje.xsd");
		odluka.setAttribute("broj", obavestenje.getBroj());
		odluka.setAttribute("datum", format.format(obavestenje.getDatum()));
		Element sluzbenik = this.korisnikParser.parse(obavestenje.getSluzbenik(), document);
		Element zahtev = this.zahtevParser.parse(obavestenje.getZahtev(), document);
		Element podaciObavestenja = this.podaciObavestenjaParser.parse(obavestenje.getPodaci(), document);
		odluka.appendChild(sluzbenik);
		odluka.appendChild(zahtev);
		odluka.appendChild(podaciObavestenja);
		return odluka;
	}	
		
}
