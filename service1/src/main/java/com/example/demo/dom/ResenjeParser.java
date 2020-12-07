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
import com.example.demo.model.Korisnik;
import com.example.demo.model.Zalba;
import com.example.demo.model.resenje.ItemType;
import com.example.demo.model.resenje.Pasus;
import com.example.demo.model.resenje.PasusItem;
import com.example.demo.model.resenje.Resenje;
import com.example.demo.model.resenje.StatusResenja;

@Component
public class ResenjeParser implements Parser<Resenje> {

	@Autowired
	private KorisnikParser korisnikParser;
	
	@Autowired
	private ZalbaParser zalbaParser;

	@Override
	public Resenje parse(Element element) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String broj = element.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		Date datum = format.parse(element.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		StatusResenja status = StatusResenja.valueOf(element.getAttributeNS(Namespaces.RESENJE, "status"));
		Korisnik poverenik = this.korisnikParser.parse((Element) element.getElementsByTagNameNS(Namespaces.KORISNIK, "Korisnik").item(0));
		Zalba zalba = this.zalbaParser.parse((Element) element.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0));
		List<Pasus> dispozitiva = new ArrayList<>();
		NodeList dispozitivaNodes = element.getElementsByTagNameNS(Namespaces.RESENJE, "Clan");
		for (int i = 0; i < dispozitivaNodes.getLength(); ++i) {
			dispozitiva.add(this.getPasus(dispozitivaNodes.item(i).getChildNodes()));
		}
		List<Pasus> odbrana = new ArrayList<>();
		NodeList odbranaNodes = ((Element) element.getElementsByTagNameNS(Namespaces.RESENJE, "Odbrana").item(0)).getElementsByTagNameNS(Namespaces.RESENJE, "pasus");
		for (int i = 0; i < odbranaNodes.getLength(); ++i) {
			odbrana.add(this.getPasus(odbranaNodes.item(i).getChildNodes()));
		}
		List<Pasus> odluka = new ArrayList<>();
		NodeList odlukaNodes = ((Element) element.getElementsByTagNameNS(Namespaces.RESENJE, "Odluka").item(0)).getElementsByTagNameNS(Namespaces.RESENJE, "pasus");
		for (int i = 0; i < odlukaNodes.getLength(); ++i) {
			odluka.add(this.getPasus(odlukaNodes.item(i).getChildNodes()));
		}
		List<Pasus> tuzba = new ArrayList<>();
		NodeList tuzbaNodes = ((Element) element.getElementsByTagNameNS(Namespaces.RESENJE, "Tuzba").item(0)).getElementsByTagNameNS(Namespaces.RESENJE, "pasus");
		for (int i = 0; i < tuzbaNodes.getLength(); ++i) {
			tuzba.add(this.getPasus(tuzbaNodes.item(i).getChildNodes()));
		}
		return new Resenje(broj, datum, status, poverenik, zalba, dispozitiva, odbrana, odluka, tuzba);
	}

	@Override
	public Element parse(Resenje type, Document document) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Element resenje = document.createElementNS(Namespaces.RESENJE, "resenje:Resenje");
		resenje.setAttributeNS(Namespaces.XMLNS, "xmlns:osnova", Namespaces.OSNOVA);
		resenje.setAttributeNS(Namespaces.XMLNS, "xmlns:resenje", Namespaces.RESENJE);
		resenje.setAttributeNS(Namespaces.RESENJE, "resenje:status", type.getStatus() + "");
		Element broj = document.createElementNS(Namespaces.OSNOVA, "osnova:broj");
		broj.setTextContent(type.getBroj());
		Element datum = document.createElementNS(Namespaces.OSNOVA, "osnova:datum");
		datum.setTextContent(format.format(type.getDatum()));
		Element poverenik = this.korisnikParser.parse(type.getPoverenik(), document);
		Element zalba = this.zalbaParser.parse(type.getZalba(), document);
		resenje.appendChild(broj);
		resenje.appendChild(datum);
		resenje.appendChild(poverenik);
		resenje.appendChild(zalba);
		Element dispozitiva = document.createElementNS(Namespaces.RESENJE, "resenje:Dispozitiva");
		int counter = 0;
		for (Pasus p: type.getDispozitiva()) {
			Element clan = document.createElementNS(Namespaces.RESENJE, "resenje:Clan");
			clan.setAttributeNS(Namespaces.RESENJE, "resenje:broj", (++counter) + "");
			for (PasusItem pi: p.getItems()) {
				clan.appendChild(this.getNode(pi, document));
			}
			dispozitiva.appendChild(clan);
		}
		Element obrazlozenje = document.createElementNS(Namespaces.RESENJE, "resenje:Obrazlozenje");
		Element odbrana = document.createElementNS(Namespaces.RESENJE, "resenje:Odbrana");
		for (Pasus p: type.getOdbrana()) {
			Element pasus = document.createElementNS(Namespaces.RESENJE, "resenje:pasus");
			for (PasusItem pi: p.getItems()) {
				pasus.appendChild(this.getNode(pi, document));
			}
			odbrana.appendChild(pasus);
		}
		Element odluka = document.createElementNS(Namespaces.RESENJE, "resenje:Odluka");
		for (Pasus p: type.getOdluka()) {
			Element pasus = document.createElementNS(Namespaces.RESENJE, "resenje:pasus");
			for (PasusItem pi: p.getItems()) {
				pasus.appendChild(this.getNode(pi, document));
			}
			odluka.appendChild(pasus);
		}
		Element tuzba = document.createElementNS(Namespaces.RESENJE, "resenje:Tuzba");
		for (Pasus p: type.getTuzba()) {
			Element pasus = document.createElementNS(Namespaces.RESENJE, "resenje:pasus");
			for (PasusItem pi: p.getItems()) {
				pasus.appendChild(this.getNode(pi, document));
			}
			tuzba.appendChild(pasus);
		}
		obrazlozenje.appendChild(odbrana);
		obrazlozenje.appendChild(odluka);
		obrazlozenje.appendChild(tuzba);
		resenje.appendChild(dispozitiva);
		resenje.appendChild(obrazlozenje);
		return resenje;
	}

	@Override
	public String getSchema() {
		return Schemas.RESENJE;
	}
	
	private Pasus getPasus(NodeList nodes) {
		List<PasusItem> items = new ArrayList<>();
		for (int i = 0; i < nodes.getLength(); ++i) {
			items.add(this.getItem(nodes.item(i)));
		}
		return new Pasus(items);
	}
	
	private PasusItem getItem(Node node) {
		if (node.getNodeName().equals("#text")) {
			return new PasusItem(ItemType.tekts, node.getTextContent().trim());
		}
		else if (node.getNodeName().equals("resenje:datum")) {
			return new PasusItem(ItemType.datum, node.getTextContent().trim());
		}
		Element element = (Element) node;
		String clan = element.getAttributeNS(Namespaces.RESENJE, "clan");
		String stav = element.getAttributeNS(Namespaces.RESENJE, "stav");
		return new PasusItem(ItemType.zakon, node.getTextContent().trim(), clan, stav);
	}
	
	private Node getNode(PasusItem item, Document document) {
		if (item.getType().equals(ItemType.tekts)) {
			return document.createTextNode(item.getValue());
		}
		else if (item.getType().equals(ItemType.datum)) {
			Node node = document.createElementNS(Namespaces.RESENJE, "resenje:datum");
			node.setTextContent(item.getValue());
			return node;
		}
		Node node = document.createElementNS(Namespaces.RESENJE, "resenje:zakon");
		node.setTextContent(item.getValue());
		if (!item.getClan().equals("")) {
			((Element) node).setAttributeNS(Namespaces.RESENJE, "resenje:clan", item.getClan());			
		}
		if (!item.getStav().equals("")) {
			((Element) node).setAttributeNS(Namespaces.RESENJE, "resenje:stav", item.getStav());
		}
		return node;
	}
	
}
