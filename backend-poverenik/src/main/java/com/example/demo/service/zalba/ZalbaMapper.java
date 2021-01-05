package com.example.demo.service.zalba;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.constants.Constants;
import com.example.demo.constants.Namespaces;
import com.example.demo.model.enums.StatusZalbe;
import com.example.demo.model.enums.TipZalbe;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.service.KorisnikService;

@Component
public class ZalbaMapper {

	@Autowired
	private DOMParser domParser;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private KorisnikService korisnikService;
	
	public Document map(String xml) throws JAXBException, ParserConfigurationException, SAXException, IOException {
		
		Document document = this.domParser.buildDocument(xml);
		Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);

		DocumentFragment documentFragment = document.createDocumentFragment();
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		datum.setTextContent(sdf.format(new Date()));
		Element korisnik = (Element) this.jaxbParser.marshal(this.korisnikService.currentUser())
				.getElementsByTagNameNS(Namespaces.OSNOVA, "Korisnik").item(0);
		Node gradjanin = document.createElementNS(Namespaces.OSNOVA, "Gradjanin");
		gradjanin.appendChild(
				document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		gradjanin.appendChild(
				document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0), true));
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
		documentFragment.appendChild(datum);
		documentFragment.appendChild(gradjanin);
		zalba.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0));

		Node status = document.createElementNS(Namespaces.ZALBA, "zalba:status");
		status.setTextContent(StatusZalbe.cekanje + "");
		zalba.insertBefore(status, document.getElementsByTagNameNS(Namespaces.ZALBA, "datumZahteva").item(0));
		return document;
		

		//SOAPService.sendSOAPMessage(document, "zalba");

	}
	
	public String map(ResourceSet resources) throws ParserConfigurationException, XMLDBException, SAXException, IOException, TransformerException {
		Document zalbeDocument = this.domParser.emptyDocument();
		Node zalbe = zalbeDocument.createElementNS(Namespaces.ZALBA, "Zalbe");
		zalbeDocument.appendChild(zalbe);
		ResourceIterator it = resources.getIterator();

		while (it.hasMoreResources()) {
			XMLResource resource = (XMLResource) it.nextResource();
			Document document = this.domParser.buildDocument(resource.getContent().toString());
			Node zalba = zalbeDocument.createElementNS(Namespaces.ZALBA, "Zalba");
			Node tipZalbe = zalbeDocument.createElementNS(Namespaces.ZALBA, "tipZalbe");
			tipZalbe.setTextContent(getTipZalbe(document) + "");
			zalba.appendChild(tipZalbe);
			zalba.appendChild(
					zalbeDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
			zalba.appendChild(zalbeDocument
					.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
			zalba.appendChild(zalbeDocument
					.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "naziv").item(0), true));
			zalba.appendChild(zalbeDocument
					.importNode(document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0), true));
			zalbe.appendChild(zalba);
		}

		return this.domParser.buildXml(zalbeDocument);

	}
	
	public static TipZalbe getTipZalbe(Document document) {
		String tipZalbe = ((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0)).getAttributeNS(Namespaces.XSI, "type");
		
		if (tipZalbe.contains("TZalbaCutanje")) {
			return TipZalbe.cutanje;
		}
		return TipZalbe.odluka;
	}
	
}
