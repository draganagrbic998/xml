package com.example.demo.parser;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.demo.model.Kopija;
import com.example.demo.model.PodaciOdavestenja;
import com.example.demo.model.Uvid;

@Component
public class PodaciObavestenjaParser implements Parser<PodaciOdavestenja> {
	
	@Autowired
	private UvidParser uvidParser;
	
	@Autowired
	private KopijaParser kopijaParser;

	@Override
	public PodaciOdavestenja parse(Element element) throws ParseException  {
		Uvid uvid = null;
		NodeList uvidNode = element.getElementsByTagName("obavestenje:Uvid");
		if (uvidNode.getLength() > 0) {
			uvid = this.uvidParser.parse((Element) uvidNode.item(0));
		}
		Kopija kopija = null;
		NodeList kopijaNode = element.getElementsByTagName("obavestenje:Kopija");
		if (kopijaNode.getLength() > 0) {
			kopija = this.kopijaParser.parse((Element) kopijaNode.item(0));
		}
		return new PodaciOdavestenja(uvid, kopija);
	}

	@Override
	public Element parse(PodaciOdavestenja type, Document document) {
		Element podaciOdluke = document.createElement("obavestenje:Podaci_odluke");
		Element odgovor = document.createElement("obavestenje:odgovor");
		if (type.getUvid() != null) {
			Element uvid = this.uvidParser.parse(type.getUvid(), document);
			podaciOdluke.appendChild(uvid);
		}
		if (type.getKopija() != null) {
			Element kopija = this.kopijaParser.parse(type.getKopija(), document);
			podaciOdluke.appendChild(kopija);
		}
		podaciOdluke.appendChild(odgovor);
		return podaciOdluke;
	}

}
