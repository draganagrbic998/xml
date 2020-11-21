package com.example.demo.parser;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.model.Kopija;

@Component
public class KopijaParser implements Parser<Kopija> {

	@Override
	public Kopija parse(Element element) {
		String iznos = element.getElementsByTagName("odluka:Iznos").item(0).getTextContent();
		String racun = element.getElementsByTagName("odluka:Racun").item(0).getTextContent();
		String pozivNaBroj = element.getElementsByTagName("odluka:Poziv_na_broj").item(0).getTextContent();
		String model = element.getElementsByTagName("odluka:Model").item(0).getTextContent();
		return new Kopija(iznos, racun, pozivNaBroj, model);
	}

	@Override
	public Element parse(String namespace, Kopija type, Document document) {
		Element kopija = document.createElement(namespace + ":Kopija");
		Element iznos = document.createElement(namespace + ":Iznos");
		iznos.setTextContent(type.getIznos());
		Element brojRacuna = document.createElement(namespace + ":Racun");
		brojRacuna.setTextContent(type.getBrojRacuna());
		Element pozivNaBroj = document.createElement(namespace + ":Poziv_na_broj");
		pozivNaBroj.setTextContent(type.getPozivNaBroj());
		Element model = document.createElement(namespace + ":Model");
		model.setTextContent(type.getModel());
		kopija.appendChild(iznos);
		kopija.appendChild(brojRacuna);
		kopija.appendChild(pozivNaBroj);
		kopija.appendChild(model);
		return kopija;
	}

}
