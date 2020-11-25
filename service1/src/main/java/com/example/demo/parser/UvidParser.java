package com.example.demo.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.model.Adresa;
import com.example.demo.model.Uvid;

@Component
public class UvidParser implements Parser<Uvid> {

	@Autowired
	private AdresaParser adresaParser;

	@Override
	public Uvid parse(Element element) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date uvidOd = format.parse(element.getElementsByTagName("odluka:uvid_od").item(0).getTextContent());
		Date uvidDo = format.parse(element.getElementsByTagName("odluka:uvid_do").item(0).getTextContent());
		Adresa adresa = this.adresaParser.parse((Element) element.getElementsByTagName("osnova:Adresa").item(0));
		String kancelarija = element.getElementsByTagName("odluka:kancelarija").item(0).getTextContent();
		return new Uvid(uvidOd, uvidDo, adresa, kancelarija);
	}

	@Override
	public Element parse(Uvid type, Document document) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Element uvid = document.createElement("odluka:Uvid");
		Element uvidOd = document.createElement("odluka:uvid_od");
		uvidOd.setTextContent(format.format(type.getUvidOd()));
		Element uvidDo = document.createElement("odluka:uvid_do");
		uvidDo.setTextContent(format.format(type.getUvidDo()));
		Element adresa = this.adresaParser.parse(type.getAdresa(), document);
		Element kancelarija = document.createElement("odluka:kancelarija");
		kancelarija.setTextContent(type.getKancelarija());
		uvid.appendChild(uvidOd);
		uvid.appendChild(uvidDo);
		uvid.appendChild(adresa);
		uvid.appendChild(kancelarija);
		return uvid;
	}
	
}
