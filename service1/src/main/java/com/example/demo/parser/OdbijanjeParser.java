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
import com.example.demo.model.Odbijanje;
import com.example.demo.model.Zahtev;

@Component
public class OdbijanjeParser implements Parser<Odbijanje> {

	@Autowired
	private KorisnikParser korisnikParser;

	@Autowired
	private ZahtevParser zahtevParser;

	@Override
	public Odbijanje parse(Element element) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		NamedNodeMap attributes = element.getAttributes();
		String broj = attributes.getNamedItem("broj").getNodeValue();
		Date datum = format.parse(attributes.getNamedItem("datum").getNodeValue());
		Korisnik sluzbenik = this.korisnikParser
				.parse((Element) element.getElementsByTagName("korisnik:Korisnik").item(0));
		Zahtev zahtev = this.zahtevParser.parse((Element) element.getElementsByTagName("zahtev:Zahtev").item(0));
		String obrazlozenje = element.getElementsByTagName("odbijanje:Obrazlozenje").item(0).getTextContent();
		return new Odbijanje(broj, datum, sluzbenik, zahtev, obrazlozenje);
	}

	@Override
	public Element parse(Odbijanje odbijanje, Document document) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Element odluka = document.createElement("odluka:Odluka");
		odluka.setAttributeNS(Constants.XSI_NAMESPACE, "xsi:schemaLocation",
				"https://github.com/draganagrbic998/xml/odluka ../xsd/odluka.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:organ_vlasti",
				"https://github.com/draganagrbic998/xml/organ_vlasti ../xsd/organ_vlasti.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:osnova",
				"https://github.com/draganagrbic998/xml/osnova ../xsd/osnova.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:korisnik",
				"https://github.com/draganagrbic998/xml/korisnik ../xsd/korisnik.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:zahtev",
				"https://github.com/draganagrbic998/xml/zahtev ../xsd/zahtev.xsd");
		odluka.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:odbijanje",
				"https://github.com/draganagrbic998/xml/odbijanje ../xsd/odbijanje.xsd");
		odluka.setAttribute("broj", odbijanje.getBroj());
		odluka.setAttribute("datum", format.format(odbijanje.getDatum()));
		Element sluzbenik = this.korisnikParser.parse(odbijanje.getSluzbenik(), document);
		Element zahtev = this.zahtevParser.parse(odbijanje.getZahtev(), document);

		Element obrazlozenje = document.createElement("odbijanje:obrazlozenje");
		obrazlozenje.setTextContent(odbijanje.getObrazlozenje());

		odluka.appendChild(sluzbenik);
		odluka.appendChild(zahtev);
		odluka.appendChild(obrazlozenje);
		return odluka;
	}

}
