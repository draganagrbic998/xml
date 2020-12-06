package com.example.demo.dom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.demo.common.Namespaces;
import com.example.demo.common.Schemas;
import com.example.demo.model.Kopija;
import com.example.demo.model.Obavestenje;
import com.example.demo.model.Odbijanje;
import com.example.demo.model.Odgovor;
import com.example.demo.model.Odluka;
import com.example.demo.model.Uvid;
import com.example.demo.model.Zahtev;

@Component
public class OdlukaParser implements Parser<Odluka> {
		
	@Autowired
	private ZahtevParser zahtevParser;
	
	@Autowired
	private UvidParser uvidParser;
	
	@Autowired
	private KopijaParser kopijaParser;
	
	@Override
	public Odluka parse(Element element) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String broj = element.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		Date datum = format.parse(element.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		Zahtev zahtev = this.zahtevParser.parse((Element) element.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0));
		String potpisSluzbenika = element.getElementsByTagNameNS(Namespaces.ODLUKA, "potpis_sluzbenika").item(0).getTextContent();
		if ("odluka:TObavestenje".equals(element.getAttributeNS(Namespaces.XSI, "type"))){
			Uvid uvid = this.uvidParser.parse((Element) element.getElementsByTagNameNS(Namespaces.ODLUKA, "Uvid").item(0));
			Kopija kopija = this.kopijaParser.parse((Element) element.getElementsByTagNameNS(Namespaces.ODLUKA, "Kopija").item(0));
			return new Obavestenje(broj, datum, zahtev, potpisSluzbenika, uvid, kopija);			
		}
		else if ("odluka:TOdgovor".equals(element.getAttributeNS(Namespaces.XSI, "type"))){
			List<String> pasusi = new ArrayList<>();
			NodeList pasusiNodes = element.getElementsByTagNameNS(Namespaces.ODLUKA, "pasus");
			for (int i = 0; i < pasusiNodes.getLength(); ++i) {
				pasusi.add(pasusiNodes.item(i).getTextContent());
			}
			return new Odgovor(broj, datum, zahtev, potpisSluzbenika, pasusi);
		}			
		else if ("odluka:TOdbijanje".equals(element.getAttributeNS(Namespaces.XSI, "type"))) {
			String obrazlozenje = element.getElementsByTagNameNS(Namespaces.ODLUKA, "obrazlozenje").item(0).getTextContent();
			return new Odbijanje(broj, datum, zahtev, potpisSluzbenika, obrazlozenje);
		}
		return new Odluka(broj, datum, zahtev, potpisSluzbenika);
	}

	@Override
	public Element parse(Odluka type, Document document) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Element odluka = document.createElementNS(Namespaces.ODLUKA, "odluka:Odluka");
		odluka.setAttributeNS(Namespaces.XMLNS, "xmlns:osnova", Namespaces.OSNOVA);
		odluka.setAttributeNS(Namespaces.XMLNS, "xmlns:odluka", Namespaces.ODLUKA);
		Element broj = document.createElementNS(Namespaces.OSNOVA, "osnova:broj");
		broj.setTextContent(type.getBroj());
		Element datum = document.createElementNS(Namespaces.OSNOVA, "osnova:datum");
		datum.setTextContent(format.format(type.getDatum()));
		Element zahtev = this.zahtevParser.parse(type.getZahtev(), document);
		Element potpisSluzbenika = document.createElementNS(Namespaces.ODLUKA, "odluka:potpis_sluzbenika");
		odluka.appendChild(broj);
		odluka.appendChild(datum);
		odluka.appendChild(zahtev);
		odluka.appendChild(potpisSluzbenika);
		if (type instanceof Obavestenje) {
			odluka.setAttributeNS(Namespaces.XSI, "xsi:type", "odluka:TObavestenje");
			Obavestenje obavestenjeType = (Obavestenje) type;
			Element uvid = this.uvidParser.parse(obavestenjeType.getUvid(), document);
			Element kopija = this.kopijaParser.parse(obavestenjeType.getKopija(), document);
			odluka.appendChild(uvid);
			odluka.appendChild(kopija);
		}
		else if (type instanceof Odgovor) {
			odluka.setAttributeNS(Namespaces.XSI, "xsi:type", "odluka:TOdgovor");
			Odgovor odgovorType = (Odgovor) type;
			Element pasusi = document.createElementNS(Namespaces.ODLUKA, "odluka:Pasusi");
			for (String pasusText: odgovorType.getPasusi()) {
				Node pasus = document.createElementNS(Namespaces.ODLUKA, "odluka:pasus");
				pasus.setTextContent(pasusText);
				pasusi.appendChild(pasus);
			}
			odluka.appendChild(pasusi);
		}
		else if (type instanceof Odbijanje) {
			odluka.setAttributeNS(Namespaces.XSI, "xsi:type", "odluka:TOdbijanje");
			Odbijanje odbijanjeType = (Odbijanje) type;
			Element obrazlozenje = document.createElementNS(Namespaces.ODLUKA, "odluka:obrazlozenje");
			obrazlozenje.setTextContent(odbijanjeType.getObrazlozenje());
			odluka.appendChild(obrazlozenje);
		}
		return odluka;
	}

	@Override
	public String getSchema() {
		return Schemas.ODLUKA;
	}

}
