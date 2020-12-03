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
		String broj = attributes.getNamedItem("zalba:broj").getNodeValue();
		Date datum = format.parse(attributes.getNamedItem("zalba:datum").getNodeValue());
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
		Element zalba = document.createElementNS("https://github.com/draganagrbic998/xml/zalba", "zalba:Zalba");
		zalba.setAttributeNS(Constants.XSI_NAMESPACE, "xsi:schemaLocation", "https://github.com/draganagrbic998/xml/zalba ../xsd/zalba.xsd");
		zalba.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:zalba", "https://github.com/draganagrbic998/xml/zalba ../xsd/zalba.xsd");
		zalba.setAttributeNS("https://github.com/draganagrbic998/xml/zalba", "zalba:broj", type.getBroj());
		zalba.setAttributeNS("https://github.com/draganagrbic998/xml/zalba", "zalba:datum", format.format(type.getDatum()));
		
		Element obrazlozenje = document.createElementNS("https://github.com/draganagrbic998/xml/zalba", "zalba:obrazlozenje");
		obrazlozenje.setTextContent(type.getObrazlozenje());
		zalba.appendChild(obrazlozenje);
		
		if (type.getZahtev() != null) {
			Element zahtev = this.zahtevParser.parse(type.getZahtev(), document);
			zalba.appendChild(zahtev);
		}
	
		if (type.getObavestenje() != null) {
			Element obavestenje = document.createElement("obavestenje:Obavestenje");
			zalba.appendChild(obavestenje);
		}
		
		if (type.getOdbijanje() != null) {
			Element odbijanje = document.createElement("odbijanje:Odbijanje");
			zalba.appendChild(odbijanje);
		}
		
		return zalba;
	}
	
}
