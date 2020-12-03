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
		String broj = attributes.getNamedItem("odbijanje:broj").getNodeValue();
		Date datum = format.parse(attributes.getNamedItem("odbijanje:datum").getNodeValue());
		Korisnik sluzbenik = this.korisnikParser
				.parse((Element) element.getElementsByTagName("korisnik:Korisnik").item(0));
		Zahtev zahtev = this.zahtevParser.parse((Element) element.getElementsByTagName("zahtev:Zahtev").item(0));
		String obrazlozenje = element.getElementsByTagName("odbijanje:Obrazlozenje").item(0).getTextContent();
		return new Odbijanje(broj, datum, sluzbenik, zahtev, obrazlozenje);
	}

	@Override
	public Element parse(Odbijanje odbijanje, Document document) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Element novoOdbijanje = document.createElementNS("https://github.com/draganagrbic998/xml/odbijanje", "odbijanje:Odbijanje");
		novoOdbijanje.setAttributeNS(Constants.XSI_NAMESPACE, "xsi:schemaLocation",
				"https://github.com/draganagrbic998/xml/odbijanje ../xsd/odbijanje.xsd");
		novoOdbijanje.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:odbijanje",
				"https://github.com/draganagrbic998/xml/odbijanje ../xsd/odbijanje.xsd");
		novoOdbijanje.setAttributeNS("https://github.com/draganagrbic998/xml/odbijanje", "odbijanje:broj", odbijanje.getBroj());
		novoOdbijanje.setAttributeNS("https://github.com/draganagrbic998/xml/odbijanje", "odbijanje:datum", format.format(odbijanje.getDatum()));
		Element sluzbenik = this.korisnikParser.parse(odbijanje.getSluzbenik(), document);
		Element zahtev = this.zahtevParser.parse(odbijanje.getZahtev(), document);

		Element obrazlozenje = document.createElementNS("https://github.com/draganagrbic998/xml/odbijanje", "odbijanje:Obrazlozenje");
		obrazlozenje.setTextContent(odbijanje.getObrazlozenje());

		novoOdbijanje.appendChild(sluzbenik);
		novoOdbijanje.appendChild(zahtev);
		novoOdbijanje.appendChild(obrazlozenje);
		return novoOdbijanje;
	}

}
