package com.example.demo.dom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.common.Namespaces;
import com.example.demo.common.Schemas;
import com.example.demo.model.Adresa;
import com.example.demo.model.Uvid;

@Component
public class UvidParser implements Parser<Uvid> {

	@Autowired
	private AdresaParser adresaParser;

	@Override
	public Uvid parse(Element element) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Adresa adresa = this.adresaParser.parse((Element) element.getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0));
		String kancelarija = element.getElementsByTagNameNS(Namespaces.ODLUKA, "kancelarija").item(0).getTextContent();
		Date uvidOd = format.parse(element.getElementsByTagNameNS(Namespaces.ODLUKA, "uvid_od").item(0).getTextContent());
		Date uvidDo = format.parse(element.getElementsByTagNameNS(Namespaces.ODLUKA, "uvid_do").item(0).getTextContent());
		return new Uvid(adresa, kancelarija, uvidOd, uvidDo);
	}

	@Override
	public Element parse(Uvid type, Document document) {
		Element adresa = this.adresaParser.parse(type.getAdresa(), document);
		Element kancelarija = document.createElementNS(Namespaces.ODLUKA, "obavestenje:kancelarija");
		kancelarija.setTextContent(type.getKancelarija());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Element uvid = document.createElementNS(Namespaces.ODLUKA, "obavestenje:Uvid");
		Element uvidOd = document.createElementNS(Namespaces.ODLUKA, "obavestenje:uvid_od");
		uvidOd.setTextContent(format.format(type.getUvidOd()));
		Element uvidDo = document.createElementNS(Namespaces.ODLUKA, "obavestenje:uvid_do");
		uvidDo.setTextContent(format.format(type.getUvidDo()));
		uvid.appendChild(adresa);
		uvid.appendChild(kancelarija);
		uvid.appendChild(uvidOd);
		uvid.appendChild(uvidDo);
		return uvid;
	}

	@Override
	public String getSchema() {
		return Schemas.ODLUKA;
	}
	
}
