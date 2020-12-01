package com.example.demo.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.example.demo.dom.Constants;
import com.example.demo.model.Obavestenje;
import com.example.demo.model.Odbijanje;
import com.example.demo.model.Zahtev;
import com.example.demo.model.Zalba;

@Component
public class ZalbaParser implements Parser<Zalba> {

	@Autowired
	private ZahtevParser zahtevParser;
	
	@Autowired
	private ObavestenjeParser obavestenjeParser;
	
	@Autowired
	private OdbijanjeParser odbijanjeParser;
	
	@Override
	public Zalba parse(Element element) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		NamedNodeMap attributes = element.getAttributes();
		String broj = attributes.getNamedItem("broj").getNodeValue();
		Date datum = format.parse(attributes.getNamedItem("datum").getNodeValue());
		Zahtev zahtev = null;
		zahtev = this.zahtevParser.parse((Element) element.getElementsByTagName("zahtev:Zahtev").item(0));
		
		Obavestenje obavestenje = null;
		Odbijanje odbijanje = null;
		
		if (zahtev == null) {
			NodeList obavestenjeNode = element.getElementsByTagName("obavestenje:Obavestenje");
			if (obavestenjeNode.getLength() > 0) {
				obavestenje = this.obavestenjeParser.parse((Element) obavestenjeNode.item(0));
			}
			
			if (obavestenje == null) {
				NodeList odbijanjeNode = element.getElementsByTagName("odbijanje:Odbijanje");
				if (odbijanjeNode.getLength() > 0) {
					odbijanje = this.odbijanjeParser.parse((Element) odbijanjeNode.item(0));
				}
			}
		}
		
		String obrazlozenje = element.getElementsByTagName("zalba:obrazlozenje").item(0).getTextContent();
		return new Zalba(broj, datum, zahtev, obavestenje, odbijanje, obrazlozenje);
	}

	@Override
	public Element parse(Zalba type, Document document) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Element zalba = document.createElement("zalba:Zalba");
		zalba.setAttributeNS(Constants.XSI_NAMESPACE, "xsi:schemaLocation", "https://github.com/draganagrbic998/xml/odluka ../xsd/odluka.xsd");
		zalba.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:organ_vlasti", "https://github.com/draganagrbic998/xml/organ_vlasti ../xsd/organ_vlasti.xsd");
		zalba.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:osnova", "https://github.com/draganagrbic998/xml/osnova ../xsd/osnova.xsd");
		zalba.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:korisnik", "https://github.com/draganagrbic998/xml/korisnik ../xsd/korisnik.xsd");
		zalba.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:zahtev", "https://github.com/draganagrbic998/xml/zahtev ../xsd/zahtev.xsd");
		zalba.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:zalba", "https://github.com/draganagrbic998/xml/zalba ../xsd/zalba.xsd");
		zalba.setAttribute("broj", type.getBroj());
		zalba.setAttribute("datum", format.format(type.getDatum()));
		
		if (type.getZahtev() != null) {
			Element zahtev = this.zahtevParser.parse(type.getZahtev(), document);
			zalba.appendChild(zahtev);
		}
	
		if (type.getObavestenje() != null) {
			zalba.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:obavestenje", "https://github.com/draganagrbic998/xml/obavestenje ../xsd/obavestenje.xsd");
			Element obavestenje = document.createElement("obavestenje:Obavestenje");
			zalba.appendChild(obavestenje);
		}
		
		if (type.getOdbijanje() != null) {
			zalba.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:odbijanje", "https://github.com/draganagrbic998/xml/odbijanje ../xsd/odbijanje.xsd");
			Element odbijanje = document.createElement("odbijanje:Odbijanje");
			zalba.appendChild(odbijanje);
		}
		
		Element obrazlozenje = document.createElement("zalba:obrazlozenje");
		obrazlozenje.setTextContent(type.getObrazlozenje());
		zalba.appendChild(obrazlozenje);
		return zalba;
	}
	
}
