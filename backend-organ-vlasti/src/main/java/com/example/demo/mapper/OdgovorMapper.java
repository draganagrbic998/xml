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
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.rdf.OdgovorRDF;
import com.example.demo.repository.xml.ZalbaExist;

@Component
public class OdgovorMapper implements MapperInterface {

	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private OdgovorRDF odgovorRDF;
	
	private static final String STUB_FILE = Constants.STUB_FOLDER + "odgovor.xml";
	
	@Override
	public Document map(String xml) {
		Document dto = this.domParser.buildDocument(xml);
		String broj = dto.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		Document document = this.domParser.buildDocumentFromFile(STUB_FILE);
		Element odgovor = (Element) document.getElementsByTagNameNS(Namespaces.ODGOVOR, "Odgovor").item(0);
		odgovor.setAttribute("about", Namespaces.ODGOVOR + "/" + broj);
		Element zalba = (Element) this.zalbaExist.load(broj).getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();
				
		odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).setTextContent(broj);
		odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).setTextContent(Constants.sdf.format(new Date()));
		documentFragment.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		documentFragment.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		documentFragment.appendChild(document.importNode(dto.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		Element datumZalbe = (Element) document.getElementsByTagNameNS(Namespaces.ODGOVOR, "datumZalbe").item(0);
		datumZalbe.setAttribute("href", Namespaces.ZALBA + "/" + broj);
		odgovor.insertBefore(documentFragment, datumZalbe);
		datumZalbe.setTextContent(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		//osoba.getElementsByTagNameNS(Namespaces.OSNOVA, "potpis").item(0).setTextContent(this.korisnikService.currentUser().getOsoba().getPotpis());
		odgovor.setAttribute("href", Namespaces.KORISNIK + "/" + zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0).getTextContent());
		return document;
	}

	@Override
	public String map(ResourceSet resources) {
		try {
			Document odgovoriDocument = this.domParser.emptyDocument();
			Node odgovori = odgovoriDocument.createElementNS(Namespaces.ODGOVOR, "Odgovori");
			odgovoriDocument.appendChild(odgovori);
			ResourceIterator it = resources.getIterator();

			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				Node broj = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0);
				Node odgovor = odgovoriDocument.createElementNS(Namespaces.ODGOVOR, "Odgovor");
				odgovor.appendChild(odgovoriDocument.importNode(broj, true));
				odgovor.appendChild(odgovoriDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				odgovor.appendChild(odgovoriDocument.importNode(document.getElementsByTagNameNS(Namespaces.ODGOVOR, "datumZalbe").item(0), true));
				
				Node reference = odgovoriDocument.createElementNS(Namespaces.OSNOVA, "Reference");
				this.domParser.addReference(odgovoriDocument, reference, this.odgovorRDF.resenja(broj.getTextContent()), "resenja");
				odgovor.appendChild(reference);
				
				odgovori.appendChild(odgovor);
			}

			return this.domParser.buildXml(odgovoriDocument);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

}
