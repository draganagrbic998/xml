package com.example.demo.parser;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.model.Kopija;

@Component
public class KopijaParser implements Parser<Kopija> {

	@Override
	public Kopija parse(Element element) {
		String iznos = element.getElementsByTagName("obavestenje:iznos").item(0).getTextContent();
		String racun = element.getElementsByTagName("obavestenje:racun").item(0).getTextContent();
		String pozivNaBroj = element.getElementsByTagName("obavestenje:poziv_na_broj").item(0).getTextContent();
		String model = element.getElementsByTagName("obavestenje:model").item(0).getTextContent();
		return new Kopija(iznos, racun, pozivNaBroj, model);
	}

	@Override
	public Element parse(Kopija type, Document document) {
		Element kopija = document.createElementNS("https://github.com/draganagrbic998/xml/obavestenje", "obavestenje:Kopija");
		Element iznos = document.createElementNS("https://github.com/draganagrbic998/xml/obavestenje", "obavestenje:iznos");
		iznos.setTextContent(type.getIznos());
		Element brojRacuna = document.createElementNS("https://github.com/draganagrbic998/xml/obavestenje", "obavestenje:racun");
		brojRacuna.setTextContent(type.getBrojRacuna());
		Element pozivNaBroj = document.createElementNS("https://github.com/draganagrbic998/xml/obavestenje", "obavestenje:poziv_na_broj");
		pozivNaBroj.setTextContent(type.getPozivNaBroj());
		Element model = document.createElementNS("https://github.com/draganagrbic998/xml/obavestenje", "obavestenje:model");
		model.setTextContent(type.getModel());
		kopija.appendChild(iznos);
		kopija.appendChild(brojRacuna);
		kopija.appendChild(pozivNaBroj);
		kopija.appendChild(model);
		return kopija;
	}

}
