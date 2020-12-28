package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.example.demo.controller.ResenjeDTO;
import com.example.demo.model.Korisnik;
import com.example.demo.model.enums.StatusResenja;
import com.example.demo.model.enums.StatusZalbe;
import com.example.demo.model.enums.TipZalbe;
import com.example.demo.parser.DOMParser;
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
	
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "/resenje_fo.xsl";
	private static final String XSL_PATH = Constants.XSL_FOLDER + "/resenje.xsl";
	
	@Autowired
	private ZalbaRepository zalbaRepository;

	
	public Resource generatePdf(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.resenjeRepository.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generatePdf(document, XSL_FO_PATH);
		Path file = Paths.get(Constants.GEN_FOLDER + "/" + broj + ".pdf");
		Files.write(file, out.toByteArray());
		return new UrlResource(file.toUri());
	}
	
	public Resource generateHtml(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.resenjeRepository.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, XSL_PATH);
		Path file = Paths.get(Constants.GEN_FOLDER + "/" + broj + ".html");
		Files.write(file, out.toByteArray());
		return new UrlResource(file.toUri());
	}
	
	public List<ResenjeDTO> list() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException{
		
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp = null;
		if (korisnik.getGradjanin() != null) {
			xpathExp = String.format("/dokument:Resenje[dokument:PodaciZahteva/mejl='%s']", korisnik.getMejl());
		}
		else {
			xpathExp = "/dokument:Resenje";
		}
		ResourceSet result = this.resenjeRepository.list(xpathExp);
		
		
		List<ResenjeDTO> resenja = new ArrayList<>();
		ResourceIterator i = result.getIterator();
		while (i.hasMoreResources()) {
			XMLResource resource = (XMLResource) i.nextResource();
			Document document = domParser.buildDocument(resource.getContent().toString());	//a sto da ne uradim getContentAsDom???
			String broj = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
			String datum = document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent();
			StatusResenja status = StatusResenja.valueOf(document.getElementsByTagNameNS(Namespaces.DOKUMENT,"status").item(0).getTextContent());
			String organVlasti = document.getElementsByTagNameNS(Namespaces.OSNOVA, "naziv").item(0).getTextContent();
			resenja.add(new ResenjeDTO(broj, datum, status, organVlasti));
		}
		return resenja;
		
	}
	
	public void save(String brojZalbe, String xml) throws ParserConfigurationException, SAXException, IOException, JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException {
		Document document = this.domParser.buildDocument(xml);
		Element resenje = (Element) document.getElementsByTagNameNS(Namespaces.DOKUMENT, "Resenje").item(0);
		this.domParser.removeXmlSpace(document);
		DocumentFragment documentFragment = document.createDocumentFragment();
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		datum.setTextContent(sdf.format(new Date()));
		Node mesto = document.createElementNS(Namespaces.OSNOVA, "mesto");
		mesto.setTextContent(Constants.TEST_MESTO);
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
		documentFragment.appendChild(datum);
		documentFragment.appendChild(mesto);
		resenje.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.DOKUMENT, "status").item(0));
		Node podaciZahteva = document.createElementNS(Namespaces.DOKUMENT, "PodaciZahteva");
		
		Document zalbaDocument = this.zalbaRepository.load(brojZalbe);
		zalbaDocument.getElementsByTagNameNS(Namespaces.OSNOVA, "status").item(0).setTextContent(StatusZalbe.odobreno + "");
		this.zalbaRepository.save(zalbaDocument);
		
		Element zalba = (Element) this.zalbaRepository.load(brojZalbe).getElementsByTagNameNS(Namespaces.DOKUMENT, "Zalba").item(0);
		podaciZahteva.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0), true));
		podaciZahteva.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true));
		podaciZahteva.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
		resenje.appendChild(podaciZahteva);
		
		Node podaciZalbe = document.createElementNS(Namespaces.DOKUMENT, "PodaciZalbe");
		podaciZalbe.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0), true));
		podaciZalbe.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0), true));
		resenje.appendChild(podaciZalbe);
		
		TipZalbe tipZalbe = ZalbaService.getTipZalbe(zalba.getAttributeNS(Namespaces.XSI, "type"));
		if (tipZalbe.equals(TipZalbe.odluka)) {
			Node podaciObavestenja = document.createElementNS(Namespaces.DOKUMENT, "PodaciObavestenja");
			podaciObavestenja.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.DOKUMENT, "brojOdluke").item(0), true));
			podaciObavestenja.appendChild(document.importNode(zalba.getElementsByTagNameNS(Namespaces.DOKUMENT, "datumOdluke").item(0), true));
			resenje.appendChild(podaciObavestenja);
		}
				
		this.resenjeRepository.save(document);
	}
	
}
