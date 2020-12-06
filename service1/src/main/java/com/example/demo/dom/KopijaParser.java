package com.example.demo.dom;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.common.Namespaces;
import com.example.demo.common.Schemas;
import com.example.demo.model.Kopija;

@Component
public class KopijaParser implements Parser<Kopija> {

	@Override
	public Kopija parse(Element element) {
		String iznos = element.getElementsByTagNameNS(Namespaces.ODLUKA, "iznos").item(0).getTextContent();
		String racun = element.getElementsByTagNameNS(Namespaces.ODLUKA, "racun").item(0).getTextContent();
		String pozivNaBroj = element.getElementsByTagNameNS(Namespaces.ODLUKA, "poziv_na_broj").item(0).getTextContent();
		String model = element.getElementsByTagNameNS(Namespaces.ODLUKA, "model").item(0).getTextContent();
		return new Kopija(iznos, racun, pozivNaBroj, model);
	}

	@Override
	public Element parse(Kopija type, Document document) {
		Element kopija = document.createElementNS(Namespaces.ODLUKA, "obavestenje:Kopija");
		Element iznos = document.createElementNS(Namespaces.ODLUKA, "obavestenje:iznos");
		iznos.setTextContent(type.getIznos());
		Element racun = document.createElementNS(Namespaces.ODLUKA, "obavestenje:racun");
		racun.setTextContent(type.getBrojRacuna());
		Element pozivNaBroj = document.createElementNS(Namespaces.ODLUKA, "obavestenje:poziv_na_broj");
		pozivNaBroj.setTextContent(type.getPozivNaBroj());
		Element model = document.createElementNS(Namespaces.ODLUKA, "obavestenje:model");
		model.setTextContent(type.getModel());
		kopija.appendChild(iznos);
		kopija.appendChild(racun);
		kopija.appendChild(pozivNaBroj);
		kopija.appendChild(model);
		return kopija;
	}

	@Override
	public String getSchema() {
		return Schemas.ODLUKA;
	}

}
