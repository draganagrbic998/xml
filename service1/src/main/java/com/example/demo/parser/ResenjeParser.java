package com.example.demo.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.example.demo.dom.Constants;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Odbrana;
import com.example.demo.model.PodaciResenja;
import com.example.demo.model.Resenje;
import com.example.demo.model.TipResenja;
import com.example.demo.model.Zalba;

@Component
public class ResenjeParser implements Parser<Resenje> {

	@Autowired
	private KorisnikParser korisnikParser;
	
	@Autowired
	private ZalbaParser zalbaParser;
	
	@Override
	public Resenje parse(Element element) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		NamedNodeMap attributes = element.getAttributes();
		String broj = attributes.getNamedItem("broj").getNodeValue();
		Date datum = format.parse(attributes.getNamedItem("datum").getNodeValue());
		TipResenja tip = TipResenja.valueOf(attributes.getNamedItem("tip_resenja").getNodeValue());

		Korisnik korisnik = this.korisnikParser.parse((Element) element.getElementsByTagName("korisnik:Korisnik").item(0));
		Zalba zalba = this.zalbaParser.parse((Element) element.getElementsByTagName("zalba:Zalba").item(0));

		Date datumPrijema = format.parse(element.getElementsByTagName("resenje:Datum_prijema").item(0).getTextContent());
		String odgovor = element.getElementsByTagName("resenje:Odgovor").item(0).getTextContent();
		Odbrana odbrana = new Odbrana(datumPrijema, odgovor);
		
		List<String> dispozitiva = new ArrayList<String>();
		NodeList clanovi = element.getElementsByTagName("resenje:Clan_dispozitive");
		
		for (int i = 0; i < clanovi.getLength(); i++) {
			dispozitiva.add(clanovi.item(i).getTextContent());
		}
		
		List<String> obrazlozenje = new ArrayList<String>();
		NodeList delovi = element.getElementsByTagName("resenje:Deo_obrazlozenja");
		
		for (int i = 0; i < delovi.getLength(); i++) {
			obrazlozenje.add(delovi.item(i).getTextContent());
		}
		
		PodaciResenja podaci = new PodaciResenja(odbrana, dispozitiva, obrazlozenje);
		return new Resenje(broj, datum, tip, korisnik, zalba, podaci);
	}

	@Override
	public Element parse(Resenje resenje, Document document) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Element novoResenje = document.createElement("resenje:Resenje");
		novoResenje.setAttributeNS(Constants.XSI_NAMESPACE, "xsi:schemaLocation", "https://github.com/draganagrbic998/xml/resenje ../xsd/resenje.xsd");
		novoResenje.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:korisnik", "https://github.com/draganagrbic998/xml/korisnik ../xsd/korisnik.xsd");
		novoResenje.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:organ_vlasti", "https://github.com/draganagrbic998/xml/organ_vlasti ../xsd/organ_vlasti.xsd");
		novoResenje.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:zahtev", "https://github.com/draganagrbic998/xml/zahtev ../xsd/zahtev.xsd");
		novoResenje.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:osnova", "https://github.com/draganagrbic998/xml/osnova ../xsd/osnova.xsd");
		novoResenje.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:zalba", "https://github.com/draganagrbic998/xml/zalba ../xsd/zalba.xsd");
		novoResenje.setAttribute("broj", resenje.getBroj());
		novoResenje.setAttribute("datum", format.format(resenje.getDatum()));
		novoResenje.setAttribute("tip_resenja", resenje.getTip() + "");
		
		Element korisnik = this.korisnikParser.parse(resenje.getPoverenik(), document);
		novoResenje.appendChild(korisnik);
		
		Element zalba = this.zalbaParser.parse(resenje.getZalba(), document);
		novoResenje.appendChild(zalba);
		
		Element datumPrijema = document.createElement("resenje:Datum_prijema");
		datumPrijema.setTextContent(format.format(resenje.getPodaci().getOdbrana().getDatumPrijema()));
		Element odgovor = document.createElement("resenje:Odgovor");
		odgovor.setTextContent(resenje.getPodaci().getOdbrana().getOdgovor());

		Element odbrana = document.createElement("resenje:Odbrana");
		odbrana.appendChild(datumPrijema);
		odbrana.appendChild(odgovor);

		novoResenje.appendChild(odbrana);
		
		Element dispozitiva = document.createElement("resenje:Dispozitiva");
		
		for (String d : resenje.getPodaci().getDispozitiva()) {
			Element clanDispozitive = document.createElement("resenje:Clan_dispozitive");
			clanDispozitive.setTextContent(d);
			dispozitiva.appendChild(clanDispozitive);
		}
		
		novoResenje.appendChild(dispozitiva);

		Element obrazlozenje = document.createElement("resenje:Obrazlozenje");
		
		for (String o : resenje.getPodaci().getObrazlozenje()) {
			Element deoObrazlozenja = document.createElement("resenje:Deo_obrazlozenja");
			deoObrazlozenja.setTextContent(o);
			obrazlozenje.appendChild(deoObrazlozenja);
		}
		
		novoResenje.appendChild(obrazlozenje);
		
		return novoResenje;
	}
	
}
