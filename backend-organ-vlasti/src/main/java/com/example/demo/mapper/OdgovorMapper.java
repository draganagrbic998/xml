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
import com.example.demo.repository.rdf.OdgovorRDF;
import com.example.demo.repository.xml.ZalbaExist;

@Component
public class OdgovorMapper implements MapperInterface {

	@Autowired
	private ZalbaExist zalbaExist;
		
	@Autowired
	private OdgovorRDF odgovorRDF;
	
	@Autowired
	private DOMParser domParser;

	private static final String STUB_FILE = Constants.STUB_FOLDER + "odgovor.xml";
	
	@Override
	public Document map(String xml) {
		Document dto = this.domParser.buildDocument(xml);
		String broj = Utils.getBroj(dto);
		Document document = this.domParser.buildDocumentFromFile(STUB_FILE);
		Element odgovor = (Element) document.getElementsByTagNameNS(Namespaces.ODGOVOR, "Odgovor").item(0);
		Element zalba = (Element) this.zalbaExist.load(broj).getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();

		odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).setTextContent(broj);
		odgovor.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).setTextContent(Constants.sdf.format(new Date()));
		odgovor.setAttribute("about", Namespaces.ODGOVOR + "/" + broj);
		odgovor.setAttribute("href", zalba.getAttribute("href"));
		
		documentFragment.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		documentFragment.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		documentFragment.appendChild(document.importNode(dto.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		
		Element datumZalbe = (Element) document.getElementsByTagNameNS(Namespaces.ODGOVOR, "datumZalbe").item(0);
		datumZalbe.setTextContent(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		datumZalbe.setAttribute("href", zalba.getAttribute("about"));
		odgovor.insertBefore(documentFragment, datumZalbe);
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
				Document odgovorDocument = this.domParser.buildDocument(resource.getContent().toString());
				Node odgovor = odgovoriDocument.createElementNS(Namespaces.ODGOVOR, "Odgovor");

				odgovor.appendChild(odgovoriDocument.importNode(odgovorDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
				odgovor.appendChild(odgovoriDocument.importNode(odgovorDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
				odgovor.appendChild(odgovoriDocument.importNode(odgovorDocument.getElementsByTagNameNS(Namespaces.ODGOVOR, "datumZalbe").item(0), true));
				
				Node reference = odgovoriDocument.createElementNS(Namespaces.OSNOVA, "Reference");
				Utils.setReferences(odgovoriDocument, reference, this.odgovorRDF.resenja(Utils.getBroj(odgovorDocument)), "resenja");
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
