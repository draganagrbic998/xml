package com.example.demo.parser;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.demo.model.Kopija;
import com.example.demo.model.PodaciOdluke;
import com.example.demo.model.Uvid;

@Component
public class PodaciOdlukeParser implements Parser<PodaciOdluke> {
	
	@Autowired
	private UvidParser uvidParser;
	
	@Autowired
	private KopijaParser kopijaParser;

	@Override
	public PodaciOdluke parse(Element element) throws ParseException  {
		String odgovor = element.getElementsByTagName("odluka:Odgovor").item(0).getTextContent();
		Uvid uvid = null;
		NodeList uvidNode = element.getElementsByTagName("odluka:Uvid");
		if (uvidNode.getLength() > 0) {
			uvid = this.uvidParser.parse((Element) uvidNode.item(0));
		}
		Kopija kopija = null;
		NodeList kopijaNode = element.getElementsByTagName("odluka:Kopija");
		if (kopijaNode.getLength() > 0) {
			kopija = this.kopijaParser.parse((Element) kopijaNode.item(0));
		}
		return new PodaciOdluke(odgovor, uvid, kopija);
	}

	@Override
	public Element parse(String namespace, PodaciOdluke type, Document document) {
		Element podaciOdluke = document.createElement(namespace + ":Podaci_odluke");
		Element odgovor = document.createElement(namespace + ":Odgovor");
		odgovor.setTextContent(type.getOdgovor());
		if (type.getUvid() != null) {
			Element uvid = this.uvidParser.parse(namespace, type.getUvid(), document);
			podaciOdluke.appendChild(uvid);
		}
		if (type.getKopija() != null) {
			Element kopija = this.kopijaParser.parse(namespace, type.getKopija(), document);
			podaciOdluke.appendChild(kopija);
		}
		podaciOdluke.appendChild(odgovor);
		return podaciOdluke;
	}

}
