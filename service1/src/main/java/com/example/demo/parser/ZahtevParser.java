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
import com.example.demo.model.OrganVlasti;
import com.example.demo.model.PodaciZahteva;
import com.example.demo.model.TipZahteva;
import com.example.demo.model.Zahtev;

@Component
public class ZahtevParser implements Parser<Zahtev> {
		
	@Autowired
	private OrganVlastiParser organVlastiParser;
	
	@Autowired
	private KorisnikParser korisnikParser;
	
	@Autowired
	private PodaciZahtevaParser podaciZahtevaParser;
	
	@Override
	public Zahtev parse(Element element) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		NamedNodeMap attributes = element.getAttributes();
		String broj = attributes.getNamedItem("broj").getNodeValue();
		Date datum = format.parse(attributes.getNamedItem("datum").getNodeValue());
		TipZahteva tip = TipZahteva.valueOf(attributes.getNamedItem("tip_zahteva").getNodeValue());
		OrganVlasti organVlasti = this.organVlastiParser.parse((Element) element.getElementsByTagName("organ_vlasti:Organ_vlasti").item(0));
		Korisnik gradjanin = this.korisnikParser.parse((Element) element.getElementsByTagName("korisnik:Korisnik").item(0));
		PodaciZahteva podaciZahteva = this.podaciZahtevaParser.parse((Element) element.getElementsByTagName("korisnik:Korisnik").item(0));
		return new Zahtev(broj, tip, datum, organVlasti, gradjanin, podaciZahteva);
	}

	@Override
	public Element parse(Zahtev type, Document document) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Element zahtev = document.createElement("zahtev:Zahtev");
		zahtev.setAttributeNS(Constants.XSI_NAMESPACE, "xsi:schemaLocation", "https://github.com/draganagrbic998/xml/odluka ../xsd/odluka.xsd");
		zahtev.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:organ_vlasti", "https://github.com/draganagrbic998/xml/organ_vlasti ../xsd/organ_vlasti.xsd");
		zahtev.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:osnova", "https://github.com/draganagrbic998/xml/osnova ../xsd/osnova.xsd");
		zahtev.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:korisnik", "https://github.com/draganagrbic998/xml/korisnik ../xsd/korisnik.xsd");
		zahtev.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:zahtev", "https://github.com/draganagrbic998/xml/zahtev ../xsd/zahtev.xsd");
		zahtev.setAttribute("broj", type.getBroj());
		zahtev.setAttribute("datum", format.format(type.getDatum()));
		zahtev.setAttribute("tip_zahteva", type.getTipZahteva() + "");
		Element organVlasti = this.organVlastiParser.parse(type.getOrganVlasti(), document);
		Element gradjanin = this.korisnikParser.parse(type.getGradjanin(), document);
		Element podaciZahteva = this.podaciZahtevaParser.parse(type.getPodaci(), document);
		zahtev.appendChild(organVlasti);
		zahtev.appendChild(gradjanin);
		zahtev.appendChild(podaciZahteva);
		return zahtev;
	}
	
}
