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
import com.example.demo.model.Odluka;
import com.example.demo.model.TipZalbe;
import com.example.demo.model.Zahtev;
import com.example.demo.model.Zalba;

@Component
public class ZalbaParser implements Parser<Zalba> {

	@Autowired
	private ZahtevParser zahtevParser;
	
	@Autowired
	private OdlukaParser odlukaParser;
	
	@Override
	public Zalba parse(Element element) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		NamedNodeMap attributes = element.getAttributes();
		String broj = attributes.getNamedItem("broj").getNodeValue();
		Date datum = format.parse(attributes.getNamedItem("datum").getNodeValue());
		TipZalbe tip = TipZalbe.valueOf(attributes.getNamedItem("tip_zalbe").getNodeValue());
		Zahtev zahtev = this.zahtevParser.parse((Element) element.getElementsByTagName("zahtev:Zahtev").item(0));
		Odluka odluka = null;
		NodeList odlukaNode = element.getElementsByTagName("odluka:Odluka");
		if (odlukaNode.getLength() > 0) {
			odluka = this.odlukaParser.parse((Element) odlukaNode.item(0));
		}
		String obrazlozenje = element.getElementsByTagName("zalba:obrazlozenje").item(0).getTextContent();
		return new Zalba(broj, tip, datum, zahtev, odluka, obrazlozenje);
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
		zalba.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:odluka", "https://github.com/draganagrbic998/xml/odluka ../xsd/odluka.xsd");
		zalba.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:zalba", "https://github.com/draganagrbic998/xml/zalba ../xsd/zalba.xsd");
		zalba.setAttribute("broj", type.getBroj());
		zalba.setAttribute("datum", format.format(type.getDatum()));
		zalba.setAttribute("tip_zalbe", type.getTip() + "");
		Element zahtev = this.zahtevParser.parse(type.getZahtev(), document);
		if (type.getOdluka() != null) {
			Element odluka = document.createElement("odluka:Odluka");
			zalba.appendChild(odluka);
		}
		Element obrazlozenje = document.createElement("zalba:obrazlozenje");
		obrazlozenje.setTextContent(type.getObrazlozenje());
		zalba.appendChild(zahtev);
		zalba.appendChild(obrazlozenje);
		return zalba;
	}
	
}
