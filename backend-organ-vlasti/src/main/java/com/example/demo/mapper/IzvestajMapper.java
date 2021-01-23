package com.example.demo.mapper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.exception.MyException;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.xml.IzvestajExist;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.repository.xml.OdlukaExist;
import com.example.demo.repository.xml.ZahtevExist;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.OrganVlastiService;

@Component
public class IzvestajMapper implements MapperInterface {

	public static final String STUB_FILE = Constants.STUB_FOLDER + "izvestaj.xml";

	@Autowired
	private DOMParser domParser;
				
	@Autowired
	private IzvestajExist izvestajExist;

	@Autowired
	private ZahtevExist zahtevExist;

	@Autowired
	private OdlukaExist odlukaExist;

	@Autowired
	private ZalbaExist zalbaExist;

	@Autowired
	private KorisnikExist korisnikExist;

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private OrganVlastiService organVlastiService;

	@Override
	public Document map(String godina) {
		try {
			Document document = this.domParser.buildDocumentFromFile(STUB_FILE);
			Element izvestaj = (Element) document.getElementsByTagNameNS(Namespaces.IZVESTAJ, "Izvestaj").item(0);
			DocumentFragment documentFragment = document.createDocumentFragment();
			
			document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).setTextContent(this.izvestajExist.nextDocumentId());
			document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).setTextContent(Constants.sdf.format(new Date()));
			document.getElementsByTagNameNS(Namespaces.IZVESTAJ, "godina").item(0).setTextContent(godina);
			izvestaj.setAttribute("about", Namespaces.IZVESTAJ + "/" + Utils.getBroj(document));
			izvestaj.setAttribute("href", Namespaces.KORISNIK + "/" + this.korisnikService.currentUser().getMejl());
			
			documentFragment.appendChild(document.importNode(this.korisnikExist.load(this.korisnikService.currentUser().getMejl()).getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
			documentFragment.appendChild(document.importNode(this.organVlastiService.load().getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
			izvestaj.insertBefore(documentFragment, izvestaj.getElementsByTagNameNS(Namespaces.IZVESTAJ, "godina").item(0));

			Node bzNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahteva");
			bzNode.setTextContent(this.zahtevExist.retrieve("/zahtev:Zahtev/datum[contains(text(), \"" + godina + "\")]").getSize() + "");
			
			Node bzoNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaObavestenje");
			bzoNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipZahteva = 'obavestenje']/datum[contains(text(), \"" + godina + "\")]").getSize() + "");

			Node bzuNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaUvid");
			bzuNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipZahteva = 'uvid']/datum[contains(text(), \"" + godina + "\")]").getSize() + "");

			Node bzkNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaKopija");
			bzkNode.setTextContent(
					(this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipZahteva = 'kopija']/datum[contains(text(), \"" + godina + "\")]").getSize() + ""));

			Node bzdNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaDostava");
			bzdNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipZahteva = 'dostava']/datum[contains(text(), \"" + godina + "\")]").getSize() + "");

			Node bzpNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaPosta");
			bzpNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipDostave = 'posta']/datum[contains(text(), \"" + godina + "\")]").getSize() + "");

			Node bzeNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaEmail");
			bzeNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipDostave = 'email']/datum[contains(text(), \"" + godina + "\")]").getSize() + "");

			Node bzfNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaFaks");
			bzfNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipDostave = 'faks']/datum[contains(text(), \"" + godina + "\")]").getSize() + "");

			Node bzosNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZahtevaOstalo");
			bzosNode.setTextContent(
					this.zahtevExist.retrieve("/zahtev:Zahtev[zahtev:tipDostave = 'ostalo']/datum[contains(text(), \"" + godina + "\")]").getSize() + "");

			Node boNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojOdluka");
			boNode.setTextContent(this.odlukaExist.retrieve("/odluka:Odluka/datum[contains(text(), \"" + godina + "\")]").getSize() + "");

			Node bopriNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojOdlukaOdobreno");
			bopriNode.setTextContent(
					this.odlukaExist.retrieve("/odluka:Odluka[@xsi:type='odluka:TObavestenje']/datum[contains(text(), \"" + godina + "\")]").getSize() + "");

			Node boodbNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojOdlukaOdbijeno");
			boodbNode.setTextContent(
					this.odlukaExist.retrieve("/odluka:Odluka[@xsi:type='odluka:TOdbijanje']/datum[contains(text(), \"" + godina + "\")]").getSize() + "");

			Node bzalNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZalbi");
			bzalNode.setTextContent(this.zalbaExist.retrieve("/zalba:Zalba/datum[contains(text(), \"" + godina + "\")]").getSize() + "");

			Node bzcutNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZalbiCutanje");
			bzcutNode.setTextContent(
					this.zalbaExist.retrieve("/zalba:Zalba[zalba:tipCutanja = 'nije postupio']/datum[contains(text(), \"" + godina + "\")]").getSize() + "");

			Node bzdelNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZalbiDelimicnost");
			bzdelNode.setTextContent(
					this.zalbaExist.retrieve("/zalba:Zalba[zalba:tipCutanja = 'nije postupio u celosti']/datum[contains(text(), \"" + godina + "\")]").getSize()
							+ "");

			Node bzodlNode = document.createElementNS(Namespaces.IZVESTAJ, "izvestaj:brojZalbiOdluka");
			bzodlNode.setTextContent(
					this.zalbaExist.retrieve("/zalba:Zalba[@xsi:type='zalba:TZalbaOdluka']/datum[contains(text(), \"" + godina + "\")]").getSize() + "");

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

			return document;
		} 
		catch (Exception e) {
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
				Document izvestajDocument = this.domParser.buildDocument(resource.getContent().toString());
				Node izvestaj = izvestajiDocument.createElementNS(Namespaces.IZVESTAJ, "Izvestaj");
				
				izvestaj.appendChild(izvestajiDocument
						.importNode(izvestajDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				izvestaj.appendChild(izvestajiDocument
						.importNode(izvestajDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				izvestaj.appendChild(izvestajiDocument
						.importNode(izvestajDocument.getElementsByTagNameNS(Namespaces.IZVESTAJ, "godina").item(0), true));
				izvestaji.appendChild(izvestaj);
			}

			return this.domParser.buildXml(izvestajiDocument);
		} 
		catch (Exception e) {
			throw new MyException(e);
		}
	}

}
