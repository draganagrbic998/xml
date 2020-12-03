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
		String broj = attributes.getNamedItem("resenje:broj").getNodeValue();
		Date datum = format.parse(attributes.getNamedItem("resenje:datum").getNodeValue());
		TipResenja tip = TipResenja.valueOf(attributes.getNamedItem("resenje:tip_resenja").getNodeValue());

		Korisnik korisnik = this.korisnikParser
				.parse((Element) element.getElementsByTagName("korisnik:Korisnik").item(0));
		Zalba zalba = this.zalbaParser.parse((Element) element.getElementsByTagName("zalba:Zalba").item(0));

		Date datumPrijema = format
				.parse(element.getElementsByTagName("resenje:Datum_prijema").item(0).getTextContent());
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
		Element novoResenje = document.createElementNS("https://github.com/draganagrbic998/xml/resenje", "resenje:Resenje");
		novoResenje.setAttributeNS(Constants.XSI_NAMESPACE, "xsi:schemaLocation",
				"https://github.com/draganagrbic998/xml/resenje ../xsd/resenje.xsd");
		novoResenje.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:resenje", "https://github.com/draganagrbic998/xml/resenje ../xsd/resenje.xsd");
		novoResenje.setAttributeNS("https://github.com/draganagrbic998/xml/resenje", "resenje:broj", resenje.getBroj());
		novoResenje.setAttributeNS("https://github.com/draganagrbic998/xml/resenje", "resenje:datum", format.format(resenje.getDatum()));
		novoResenje.setAttributeNS("https://github.com/draganagrbic998/xml/resenje", "resenje:tip_resenja", resenje.getTip() + "");

		Element korisnik = this.korisnikParser.parse(resenje.getPoverenik(), document);
		novoResenje.appendChild(korisnik);

		Element zalba = this.zalbaParser.parse(resenje.getZalba(), document);
		novoResenje.appendChild(zalba);

		Element datumPrijema = document.createElementNS("https://github.com/draganagrbic998/xml/resenje", "resenje:Datum_prijema");
		datumPrijema.setTextContent(format.format(resenje.getPodaci().getOdbrana().getDatumPrijema()));
		Element odgovor = document.createElementNS("https://github.com/draganagrbic998/xml/resenje", "resenje:Odgovor");
		odgovor.setTextContent(resenje.getPodaci().getOdbrana().getOdgovor());

		Element podaciResenja = document.createElementNS("https://github.com/draganagrbic998/xml/resenje", "resenje:Podaci_resenja");

		Element odbrana = document.createElementNS("https://github.com/draganagrbic998/xml/resenje", "resenje:Odbrana");
		odbrana.appendChild(datumPrijema);
		odbrana.appendChild(odgovor);

		podaciResenja.appendChild(odbrana);

		Element dispozitiva = document.createElementNS("https://github.com/draganagrbic998/xml/resenje", "resenje:Dispozitiva");

		for (String d : resenje.getPodaci().getDispozitiva()) {
			Element clanDispozitive = document.createElementNS("https://github.com/draganagrbic998/xml/resenje", "resenje:Clan_dispozitive");
			clanDispozitive.setTextContent(d);
			dispozitiva.appendChild(clanDispozitive);
		}

		podaciResenja.appendChild(dispozitiva);

		Element obrazlozenje = document.createElementNS("https://github.com/draganagrbic998/xml/resenje", "resenje:Obrazlozenje");

		for (String o : resenje.getPodaci().getObrazlozenje()) {
			Element deoObrazlozenja = document.createElementNS("https://github.com/draganagrbic998/xml/resenje", "resenje:Deo_obrazlozenja");
			deoObrazlozenja.setTextContent(o);
			obrazlozenje.appendChild(deoObrazlozenja);
		}

		podaciResenja.appendChild(obrazlozenje);

		novoResenje.appendChild(podaciResenja);

		return novoResenje;
	}

}
