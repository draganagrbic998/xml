package com.example.demo.parser;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.model.PodaciZahteva;
import com.example.demo.model.TipDostave;

@Component
public class PodaciZahtevaParser implements Parser<PodaciZahteva> {

	@Override
	public PodaciZahteva parse(Element element) {
		String trazenaInformacija = element.getElementsByTagName("zahtev:Trazena_informacija").item(0).getTextContent();
		TipDostave tipDostave = TipDostave.valueOf(element.getElementsByTagName("zahtev:Tip_dostave").item(0).getTextContent());
		String opisDostave = element.getElementsByTagName("zahtev:Opis_dostave").item(0).getTextContent();
		return new PodaciZahteva(trazenaInformacija, tipDostave, opisDostave);
	}

	@Override
	public Element parse(PodaciZahteva type, Document document) {
		Element podaciZahteva = document.createElement("zahtev:Podaci_zahteva");
		Element trazenaInformacija = document.createElement("zahtev:Trazena_informacija");
		trazenaInformacija.setTextContent(type.getTrazenaInformacija());
		Element tipDostave = document.createElement("zahtev:Tip_dostave");
		tipDostave.setTextContent(type.getTipDostave() + "");
		Element opisDostave = document.createElement("zahtev:Opis_dostave");
		opisDostave.setTextContent(type.getOpisDostave());
		podaciZahteva.appendChild(trazenaInformacija);
		podaciZahteva.appendChild(tipDostave);
		podaciZahteva.appendChild(opisDostave);
		return podaciZahteva;
	}
	
}
