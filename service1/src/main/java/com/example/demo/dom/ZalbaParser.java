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
import com.example.demo.model.Odluka;
import com.example.demo.model.Zahtev;
import com.example.demo.model.Zalba;
import com.example.demo.model.ZalbaCutanje;
import com.example.demo.model.ZalbaDelimicnost;
import com.example.demo.model.enums.TipCutanja;
import com.example.demo.model.enums.TipDelimicnosti;

@Component
public class ZalbaParser implements Parser<Zalba> {

	@Autowired
	private ZahtevParser zahtevParser;
	
	@Autowired
	private OdlukaParser odlukaParser;
			
	@Override
	public Zalba parse(Element element) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String broj = element.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		Date datum = format.parse(element.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		if ("zalba:TZalba_cutanje".equals(element.getAttributeNS(Namespaces.XSI, "type"))) {
			Zahtev zahtev = this.zahtevParser.parse((Element) element.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0));
			TipCutanja tipCutanja = TipCutanja.valueOf(element.getElementsByTagNameNS(Namespaces.ZALBA, "tip_cutanja").item(0).getTextContent());
			return new ZalbaCutanje(broj, datum, zahtev, tipCutanja);			
		}
		Odluka odluka = this.odlukaParser.parse((Element) element.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").item(0));
		TipDelimicnosti tipDelimicnosti = TipDelimicnosti.valueOf(element.getElementsByTagNameNS(Namespaces.ZALBA, "tip_delimicnosti").item(0).getTextContent());
		String obrazlozenje = element.getElementsByTagNameNS(Namespaces.ZALBA, "obrazlozenje").item(0).getTextContent();
		return new ZalbaDelimicnost(broj, datum, odluka, tipDelimicnosti, obrazlozenje);			
	}

	@Override
	public Element parse(Zalba type, Document document) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Element zalba = document.createElementNS(Namespaces.ZALBA, "zalba:Zalba");
		zalba.setAttributeNS(Namespaces.XMLNS, "xmlns:osnova", Namespaces.OSNOVA);
		zalba.setAttributeNS(Namespaces.XMLNS, "xmlns:zalba", Namespaces.ZALBA);
		Element broj = document.createElementNS(Namespaces.OSNOVA, "osnova:broj");
		broj.setTextContent(type.getBroj());
		Element datum = document.createElementNS(Namespaces.OSNOVA, "osnova:datum");
		datum.setTextContent(format.format(type.getDatum()));
		zalba.appendChild(broj);
		zalba.appendChild(datum);
		if (type instanceof ZalbaCutanje) {
			zalba.setAttributeNS(Namespaces.XSI, "xsi:type", "zalba:TZalba_cutanje");
			ZalbaCutanje cutanjeType = (ZalbaCutanje) type;
			Element zahtev = this.zahtevParser.parse(cutanjeType.getZahtev(), document);
			Element tipCutanja = document.createElementNS(Namespaces.ZALBA, "zalba:tip_cutanja");
			tipCutanja.setTextContent(cutanjeType.getTipCutanja() + "");
			zalba.appendChild(zahtev);
			zalba.appendChild(tipCutanja);
		}
		else {
			zalba.setAttributeNS(Namespaces.XSI, "xsi:type", "zalba:TZalba_delimicnost");
			ZalbaDelimicnost delimicnostType = (ZalbaDelimicnost) type;
			Element odluka = this.odlukaParser.parse(delimicnostType.getOdluka(), document);
			Element tipDelimicnosti = document.createElementNS(Namespaces.ZALBA, "zalba:tip_delimicnosti");
			tipDelimicnosti.setTextContent(delimicnostType.getTipDelimicnosti() + "");
			Element obrazlozenje = document.createElementNS(Namespaces.ZALBA, "zalba:obrazlozenje");
			obrazlozenje.setTextContent(delimicnostType.getObrazlozenje());
			zalba.appendChild(odluka);
			zalba.appendChild(tipDelimicnosti);
			zalba.appendChild(obrazlozenje);
		}
		return zalba;
	}

	@Override
	public String getSchema() {
		return Schemas.ZALBA;
	}

}
