package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
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
import com.example.demo.model.Korisnik;
import com.example.demo.model.enums.StatusZalbe;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.ResenjeRepository;
import com.example.demo.repository.ZalbaRepository;

@Service
public class ResenjeService {
	
	@Autowired
	private ResenjeRepository resenjeRepository;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	@Autowired
	private ZalbaRepository zalbaRepository;
	
	@Autowired
	private JAXBParser jaxbParser;

	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "/resenje_fo.xsl";
	private static final String XSL_PATH = Constants.XSL_FOLDER + "/resenje.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "resenja" + File.separatorChar;
	
	public void save(String xml) throws ParserConfigurationException, SAXException, IOException, JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		Document document = this.domParser.buildDocument(xml);
		Element resenje = (Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "Resenje").item(0);
		String brojZalbe = resenje.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0).getTextContent();
		resenje.removeChild(resenje.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0));
		this.domParser.removeXmlSpace(document);
		Document zalbaDocument = this.zalbaRepository.load(brojZalbe);
		Element zalba = (Element) zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);

		DocumentFragment documentFragment = document.createDocumentFragment();
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		datum.setTextContent(sdf.format(new Date()));
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
		documentFragment.appendChild(datum);
		resenje.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0));
		resenje.appendChild(document.importNode(this.jaxbParser.marshal(this.korisnikService.currentUser().getOsoba()).getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		resenje.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));

		
		Node podaciZahteva = document.createElementNS(Namespaces.RESENJE, "resenje:PodaciZahteva");
		podaciZahteva.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0), true));
		Node datumZahteva = document.createElementNS(Namespaces.RESENJE, "resenje:datumZahteva");
		datumZahteva.setTextContent(zalba.getElementsByTagNameNS(Namespaces.ZALBA, "datumZahteva").item(0).getTextContent());
		podaciZahteva.appendChild(datumZahteva);
		Node datumZalbe = document.createElementNS(Namespaces.RESENJE, "resenje:datumZalbe");
		datumZalbe.setTextContent(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());
		podaciZahteva.appendChild(datumZalbe);
		podaciZahteva.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		resenje.appendChild(podaciZahteva);
		
		//moram opet da stavim prefiks :(
		//dodaj logiku za preuzimanje podataka obavestenje/odluke...
				
		zalba.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odgovoreno + "");
		this.zalbaRepository.save(zalbaDocument, brojZalbe);
		this.resenjeRepository.save(document, null);	
	}
	
	public String retrieve() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException, TransformerException{
		
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp = null;
		if (korisnik.getUloga().equals(Constants.POVERENIK)) {
			xpathExp = "/resenje:Resenje";
		}
		else {
			xpathExp = String.format("/resenje:Resenje[resenje:PodaciZahteva/mejl='%s']", korisnik.getOsoba().getMejl());
		}		
		
		Document resenjaDocument = this.domParser.emptyDocument();
		Node resenja = resenjaDocument.createElementNS(Namespaces.RESENJE, "Resenja");
		resenjaDocument.appendChild(resenja);
		ResourceSet result = this.resenjeRepository.list(xpathExp);
		ResourceIterator it = result.getIterator();
		
		while (it.hasMoreResources()) {
			XMLResource resource = (XMLResource) it.nextResource();
			Document document = this.domParser.buildDocument(resource.getContent().toString());
			Node resenje = resenjaDocument.createElementNS(Namespaces.RESENJE, "Resenje");
			resenje.appendChild(resenjaDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0), true));
			resenje.appendChild(resenjaDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
			resenje.appendChild(resenjaDocument.importNode(document.getElementsByTagNameNS(Namespaces.RESENJE, "status").item(0), true));
			resenje.appendChild(resenjaDocument.importNode(document.getElementsByTagNameNS(Namespaces.OSNOVA, "naziv").item(0), true));
			resenja.appendChild(resenje);
		}
		
		return this.domParser.buildXml(resenjaDocument);
		
	}
	
	public String generateHtml(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.resenjeRepository.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, XSL_PATH);
		return out.toString();
	}
	
	public Resource generatePdf(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.resenjeRepository.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generatePdf(document, XSL_FO_PATH);
		Path file = Paths.get(GEN_PATH + broj + ".pdf");
		Files.write(file, out.toByteArray());
		return new UrlResource(file.toUri());
	}
	
}
