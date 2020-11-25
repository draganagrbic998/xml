package com.example.demo.parser;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.model.Kopija;

@Component
public class KopijaParser implements Parser<Kopija> {

	@Override
	public Kopija parse(Element element) {
		String iznos = element.getElementsByTagName("odluka:iznos").item(0).getTextContent();
		String racun = element.getElementsByTagName("odluka:racun").item(0).getTextContent();
		String pozivNaBroj = element.getElementsByTagName("odluka:poziv_na_broj").item(0).getTextContent();
		String model = element.getElementsByTagName("odluka:model").item(0).getTextContent();
		return new Kopija(iznos, racun, pozivNaBroj, model);
	}

	@Override
	public Element parse(Kopija type, Document document) {
		Element kopija = document.createElement("odluka:Kopija");
		Element iznos = document.createElement("odluka:iznos");
		iznos.setTextContent(type.getIznos());
		Element brojRacuna = document.createElement("odluka:racun");
		brojRacuna.setTextContent(type.getBrojRacuna());
		Element pozivNaBroj = document.createElement("odluka:poziv_na_broj");
		pozivNaBroj.setTextContent(type.getPozivNaBroj());
		Element model = document.createElement("odluka:model");
		model.setTextContent(type.getModel());
		kopija.appendChild(iznos);
		kopija.appendChild(brojRacuna);
		kopija.appendChild(pozivNaBroj);
		kopija.appendChild(model);
		return kopija;
	}

}
