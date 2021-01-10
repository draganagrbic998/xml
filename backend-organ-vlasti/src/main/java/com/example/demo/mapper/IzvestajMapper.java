package com.example.demo.mapper;

import java.util.Date;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.xml.OdlukaExist;
import com.example.demo.repository.xml.ZahtevExist;
import com.example.demo.repository.xml.ZalbaExist;

@Component
public class IzvestajMapper implements MapperInterface {

	@Autowired
	private ZahtevExist zahtevExist;

	@Autowired
	private OdlukaExist odlukaExist;

	@Autowired
	private ZalbaExist zalbaExist;

	@Autowired
	private DOMParser domParser;

	@Override
	public Document map(String godina) {
		try {
			Document document = this.domParser.emptyDocument();
			Element izvestaj = document.createElement("izvestaj:Izvestaj");
			izvestaj.setAttribute("xmlns", Namespaces.OSNOVA);
			izvestaj.setAttribute("xmlns:izvestaj", Namespaces.IZVESTAJ);

			Node godinaNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:godina");
			godinaNode.setTextContent(godina);

			Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
			datum.setTextContent(Constants.sdf.format(new Date()));

			izvestaj.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
			izvestaj.appendChild(datum);
			izvestaj.appendChild(godinaNode);

			Node bzNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahteva");
			bzNode.setTextContent(this.zahtevExist.retrieve("/zahtev:Zahtev").getSize() + "");

			Node bzoNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaObavestenje");
			bzoNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipZahteva = 'obavestenje']").getSize() + "");

			Node bzuNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaUvid");
			bzuNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipZahteva = 'uvid']").getSize() + "");

			Node bzkNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaKopija");
			bzkNode.setTextContent(
					(this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipZahteva = 'kopija']").getSize() + ""));

			Node bzdNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaDostava");
			bzdNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipZahteva = 'dostava']").getSize() + "");

			Node bzpNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaPosta");
			bzpNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipDostave = 'posta']").getSize() + "");

			Node bzeNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaEmail");
			bzeNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipDostave = 'email']").getSize() + "");

			Node bzfNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaFaks");
			bzfNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipDostave = 'faks']").getSize() + "");

			Node bzosNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaOstalo");
			bzosNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipDostave = 'ostalo']").getSize() + "");

			Node boNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojOdluka");
			boNode.setTextContent(this.odlukaExist.retrieve("/odluka:Odluka").getSize() + "");

			Node bopriNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojOdlukaOdobreno");
			bopriNode.setTextContent(
					this.odlukaExist.retrieve("/odluka:Odluka[@xsi:type='odluka:TObavestenje']").getSize() + "");

			Node boodbNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojOdlukaOdbijeno");
			boodbNode.setTextContent(
					this.odlukaExist.retrieve("/odluka:Odluka[@xsi:type='odluka:TOdbijanje']").getSize() + "");

			Node bzalNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZalbi");
			bzalNode.setTextContent(this.zalbaExist.retrieve("/zalba:Zalba").getSize() + "");

			Node bzcutNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZalbiCutanje");
			bzcutNode.setTextContent(
					this.zalbaExist.retrieve("/zalba:Zalba[zalba:tipCutanja = 'nije postupio']").getSize() + "");

			Node bzdelNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZalbiDelimicnost");
			bzdelNode.setTextContent(
					this.zalbaExist.retrieve("/zalba:Zalba[zalba:tipCutanja = 'nije postupio u celosti']").getSize()
							+ "");

			Node bzodlNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZalbiOdluka");
			bzodlNode.setTextContent(
					this.zalbaExist.retrieve("/zalba:Zalba[@xsi:type='zalba:TZalbaOdluka']").getSize() + "");

			izvestaj.appendChild(bzNode);
			izvestaj.appendChild(bzoNode);
			izvestaj.appendChild(bzuNode);
			izvestaj.appendChild(bzkNode);
			izvestaj.appendChild(bzdNode);

			izvestaj.appendChild(bzpNode);
			izvestaj.appendChild(bzeNode);
			izvestaj.appendChild(bzfNode);
			izvestaj.appendChild(bzosNode);

			izvestaj.appendChild(boNode);
			izvestaj.appendChild(bopriNode);
			izvestaj.appendChild(boodbNode);

			izvestaj.appendChild(bzalNode);
			izvestaj.appendChild(bzcutNode);
			izvestaj.appendChild(bzdelNode);
			izvestaj.appendChild(bzodlNode);

			document.appendChild(izvestaj);

			System.out.println(this.domParser.buildXml(document));
			return document;
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	@Override
	public String map(ResourceSet resources) {
		try {
			Document izvestajiDocument = this.domParser.emptyDocument();
			Node izvestaji = izvestajiDocument.createElementNS(Namespaces.IZVESTAJ, "Izvestaji");
			izvestajiDocument.appendChild(izvestaji);
			ResourceIterator it = resources.getIterator();

			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				Node izvestaj = izvestajiDocument.createElementNS(Namespaces.IZVESTAJ, "Izvestaj");
				izvestaj.appendChild(izvestajiDocument
						.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				izvestaj.appendChild(izvestajiDocument
						.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				izvestaj.appendChild(izvestajiDocument
						.importNode(document.getElementsByTagNameNS(Namespaces.IZVESTAJ, "godina").item(0), true));
				izvestaji.appendChild(izvestaj);
			}

			return this.domParser.buildXml(izvestajiDocument);
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	@Override
	public Model map(Document document) {
		Model model = ModelFactory.createDefaultModel();
		return model;
	}

}
