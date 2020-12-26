package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.constants.Constants;
import com.example.demo.constants.Namespaces;
import com.example.demo.controller.ZahtevDTO;
import com.example.demo.model.Korisnik;
import com.example.demo.model.OrganVlasti;
import com.example.demo.model.enums.StatusZahteva;
import com.example.demo.model.enums.TipZahteva;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.ZahtevRepository;
import com.ibm.icu.text.SimpleDateFormat;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Service
public class ZahtevService {
	
	@Autowired
	private ZahtevRepository zahtevRepository;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "/zahtev_fo.xsl";
	private static final String XSL_PATH = Constants.XSL_FOLDER + "/zahtev.xsl";

	public Resource generatePdf(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.zahtevRepository.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generatePdf(document, XSL_FO_PATH);
		Path file = Paths.get(broj + ".pdf");
		Files.write(file, out.toByteArray());
		return new UrlResource(file.toUri());
	}
	
	public Resource generateHtml(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.zahtevRepository.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, XSL_PATH);
		Path file = Paths.get(broj + ".html");
		Files.write(file, out.toByteArray());
		return new UrlResource(file.toUri());
	}
	
	
	public OrganVlasti defaultOrganVlasti() {
		//izmeni ovo kasnije tako da ucita podatke iz nekog xml fajla
		OrganVlasti organVlasti = new OrganVlasti();
		organVlasti.setNaziv("TEST NAZIV");
		organVlasti.setSediste("TEST SEDISTE");
		return organVlasti;
	}
	
	public String defaultMesto() {
		//izmeni ovo na ono sto on kaze
		return "TEST MESTO";
	}
	
	public String testPotpis() {
		//izmeni ovo na ono sto on kaze
		return "TEST POTPIS";
	}
	
	public List<ZahtevDTO> list() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException{
		
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp = null;
		if (korisnik.getGradjanin() != null) {
			xpathExp = String.format("/dokument:Zahtev[mejl='%s']", korisnik.getMejl());
		}
		else {
			xpathExp = "/dokument:Zahtev";
		}
		ResourceSet result = this.zahtevRepository.list(xpathExp);
		
		
		List<ZahtevDTO> zahtevi = new ArrayList<>();
		ResourceIterator i = result.getIterator();
		while (i.hasMoreResources()) {
			XMLResource resource = (XMLResource) i.nextResource();
			Document document = domParser.buildDocument(resource.getContent().toString());	//a sto da ne uradim getContentAsDom???
			String broj = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
			String datum = document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent();
			TipZahteva tipZahteva = TipZahteva.valueOf(document.getElementsByTagNameNS(Namespaces.DOKUMENT, "tipZahteva").item(0).getTextContent());
			
			StatusZahteva status = StatusZahteva.valueOf(document.getElementsByTagNameNS(Namespaces.OSNOVA, "status").item(0).getTextContent());
			zahtevi.add(new ZahtevDTO(broj, datum, tipZahteva, status));
		}
		return zahtevi;
		
	}
	
	public void save(String xml) throws ParserConfigurationException, SAXException, IOException, JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException {
		Document document = this.domParser.buildDocument(xml);
		
		((Element) document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0)).removeAttribute("xml:space");
		NodeList bolds = document.getElementsByTagNameNS(Namespaces.OSNOVA, "bold");
		for (int i = 0; i < bolds.getLength(); ++i) {
			Element bold = (Element) bolds.item(i);
			bold.removeAttribute("xml:space");
		}
		
		NodeList italics = document.getElementsByTagNameNS(Namespaces.OSNOVA, "italic");
		for (int i = 0; i < italics.getLength(); ++i) {
			Element italic = (Element) italics.item(i);
			italic.removeAttribute("xml:space");
		}
		
		Element zahtev = (Element) document.getElementsByTagNameNS(Namespaces.DOKUMENT, "Zahtev").item(0);
		DocumentFragment documentFragment = document.createDocumentFragment();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		
		datum.setTextContent(sdf.format(new Date()));
		Node mesto = document.createElementNS(Namespaces.OSNOVA, "mesto");
		mesto.setTextContent(this.defaultMesto());
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
		documentFragment.appendChild(datum);
		documentFragment.appendChild(mesto);
		
		Node gradjanin = document.importNode(this.jaxbParser.marshal(this.korisnikService.currentUser().getGradjanin()).getFirstChild(), true);
		Node organVlasti = document.importNode(this.jaxbParser.marshal(this.defaultOrganVlasti()).getFirstChild(), true);
		
		documentFragment.appendChild(gradjanin);
		documentFragment.appendChild(organVlasti);
		
		zahtev.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		DocumentFragment documentFragment2 = document.createDocumentFragment();

		Node mejl = document.createElementNS(Namespaces.OSNOVA, "mejl");
		mejl.setTextContent(this.korisnikService.currentUser().getMejl());
		documentFragment2.appendChild(mejl);
		
		Node potpis = document.createElementNS(Namespaces.OSNOVA, "potpis");
		potpis.setTextContent(this.testPotpis());
		documentFragment2.appendChild(potpis);
		Node status = document.createElementNS(Namespaces.OSNOVA, "status");
		status.setTextContent(StatusZahteva.cekanje + "");
		documentFragment2.appendChild(status);
		zahtev.insertBefore(documentFragment2, document.getElementsByTagNameNS(Namespaces.DOKUMENT, "tipZahteva").item(0));
		this.zahtevRepository.save(document);
	}
	
}
