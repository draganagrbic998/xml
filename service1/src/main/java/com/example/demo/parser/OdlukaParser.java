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
import com.example.demo.model.Odluka;
import com.example.demo.model.PodaciOdluke;
import com.example.demo.model.TipOdluke;
import com.example.demo.model.Zahtev;

@Component
public class OdlukaParser implements Parser<Odluka> {
	
	@Autowired
	private KorisnikParser korisnikParser;
	
	@Autowired
	private ZahtevParser zahtevParser;
	
	@Autowired
	private PodaciOdlukeParser podaciOdlukeParser;

	@Override
	public Odluka parse(Element element) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		NamedNodeMap attributes = element.getAttributes();
		String broj = attributes.getNamedItem("broj").getNodeValue();
		Date datum = format.parse(attributes.getNamedItem("datum").getNodeValue());
		TipOdluke tip = TipOdluke.valueOf(attributes.getNamedItem("tip_odluke").getNodeValue());
		Korisnik sluzbenik = this.korisnikParser.parse((Element) element.getElementsByTagName("korisnik:Korisnik").item(0));
		Zahtev zahtev = this.zahtevParser.parse((Element) element.getElementsByTagName("zahtev:Zahtev").item(0));
		PodaciOdluke podaciOdluke = this.podaciOdlukeParser.parse((Element) element.getElementsByTagName("odluka:Podaci_odluke").item(0));
		return new Odluka(broj, tip, datum, sluzbenik, zahtev, podaciOdluke);
	}

	@Override
	public Element parse(Odluka type, Document document) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Element odluka = document.createElement("odluka:Odluka");
		odluka.setAttributeNS(Constants.XSI_NAMESPACE, "xsi:schemaLocation", "https://github.com/draganagrbic998/xml/odluka ../xsd/odluka.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:organ_vlasti", "https://github.com/draganagrbic998/xml/organ_vlasti ../xsd/organ_vlasti.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:osnova", "https://github.com/draganagrbic998/xml/osnova ../xsd/osnova.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:korisnik", "https://github.com/draganagrbic998/xml/korisnik ../xsd/korisnik.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:zahtev", "https://github.com/draganagrbic998/xml/zahtev ../xsd/zahtev.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:odluka", "https://github.com/draganagrbic998/xml/odluka ../xsd/odluka.xsd");
		odluka.setAttribute("broj", type.getBroj());
		odluka.setAttribute("datum", format.format(type.getDatum()));
		odluka.setAttribute("tip_odluke", type.getTip() + "");
		Element sluzbenik = this.korisnikParser.parse(type.getSluzbenik(), document);
		Element zahtev = this.zahtevParser.parse(type.getZahtev(), document);
		Element podaciOdluke = this.podaciOdlukeParser.parse(type.getPodaci(), document);
		odluka.appendChild(sluzbenik);
		odluka.appendChild(zahtev);
		odluka.appendChild(podaciOdluke);
		return odluka;
	}	
		
}
