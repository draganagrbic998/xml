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
import com.example.demo.model.Korisnik;
import com.example.demo.model.OrganVlasti;
import com.example.demo.model.Zahtev;
import com.example.demo.model.ZahtevUvid;
import com.example.demo.model.ZahtevDostava;
import com.example.demo.model.enums.TipDostave;
import com.example.demo.model.enums.TipObavestenja;

@Component
public class ZahtevParser implements Parser<Zahtev> {
	
	@Autowired
	private KorisnikParser korisnikParser;

	@Autowired
	private OrganVlastiParser organVlastiParser;
			
	@Override
	public Zahtev parse(Element element) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String broj = element.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		Date datum = format.parse(element.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		Korisnik gradjanin = this.korisnikParser.parse((Element) element.getElementsByTagNameNS(Namespaces.KORISNIK, "Korisnik").item(0));
		OrganVlasti organVlasti = this.organVlastiParser.parse((Element) element.getElementsByTagNameNS(Namespaces.ORGAN_VLASTI, "Organ_vlasti").item(0));
		String trazenaInformacija = element.getElementsByTagNameNS(Namespaces.ZAHTEV, "trazena_informacija").item(0).getTextContent();
		if ("zahtev:TZahtev_uvid".equals(element.getAttributeNS(Namespaces.XSI, "type"))) {
			TipObavestenja tipObavestenja = TipObavestenja.valueOf(element.getElementsByTagNameNS(Namespaces.ZAHTEV, "tip_obavestenja").item(0).getTextContent());
			return new ZahtevUvid(broj, datum, gradjanin, organVlasti, trazenaInformacija, tipObavestenja);			
		}
		TipDostave tipDostave = TipDostave.valueOf(element.getElementsByTagNameNS(Namespaces.ZAHTEV, "tip_dostave").item(0).getTextContent());
		String opisDostave = element.getElementsByTagNameNS(Namespaces.ZAHTEV, "opis_dostave").item(0).getTextContent();
		return new ZahtevDostava(broj, datum, gradjanin, organVlasti, trazenaInformacija, tipDostave, opisDostave);
	}

	@Override
	public Element parse(Zahtev type, Document document) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Element zahtev = document.createElementNS(Namespaces.ZAHTEV, "zahtev:Zahtev");
		zahtev.setAttributeNS(Namespaces.XMLNS, "xmlns:osnova", Namespaces.OSNOVA);
		zahtev.setAttributeNS(Namespaces.XMLNS, "xmlns:zahtev", Namespaces.ZAHTEV);
		Element broj = document.createElementNS(Namespaces.OSNOVA, "osnova:broj");
		broj.setTextContent(type.getBroj());
		Element datum = document.createElementNS(Namespaces.OSNOVA, "osnova:datum");
		datum.setTextContent(format.format(type.getDatum()));
		Element gradjanin = this.korisnikParser.parse(type.getGradjanin(), document);
		Element organVlasti = this.organVlastiParser.parse(type.getOrganVlasti(), document);
		Element trazenaInformacija = document.createElementNS(Namespaces.ZAHTEV, "zahtev:trazena_informacija");
		trazenaInformacija.setTextContent(type.getTrazenaInformacija());
		zahtev.appendChild(broj);
		zahtev.appendChild(datum);
		zahtev.appendChild(gradjanin);
		zahtev.appendChild(organVlasti);
		zahtev.appendChild(trazenaInformacija);
		if (type instanceof ZahtevUvid) {
			zahtev.setAttributeNS(Namespaces.XSI, "xsi:type", "zahtev:TZahtev_uvid");
			ZahtevUvid obavestenjeType = (ZahtevUvid) type;
			Element tipObavestenja = document.createElementNS(Namespaces.ZAHTEV, "zahtev:tip_obavestenja");
			tipObavestenja.setTextContent(obavestenjeType.getTipObavestenja() + "");
			zahtev.appendChild(tipObavestenja);
		}
		else {
			zahtev.setAttributeNS(Namespaces.XSI, "xsi:type", "zahtev:TZahtev_dostava");
			ZahtevDostava odgovorType = (ZahtevDostava) type;
			Element tipDostave = document.createElementNS(Namespaces.ZAHTEV, "zahtev:tip_dostave");
			tipDostave.setTextContent(odgovorType.getTipDostave() + "");
			Element opisDostave = document.createElementNS(Namespaces.ZAHTEV, "zahtev:opis_dostave");
			opisDostave.setTextContent(odgovorType.getOpisDostave());
			zahtev.appendChild(tipDostave);
			zahtev.appendChild(opisDostave);
		}
		return zahtev;
	}

	@Override
	public String getSchema() {
		return Schemas.ZAHTEV;
	}
	
}
