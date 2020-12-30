package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import com.example.demo.controller.ZalbaDTO;
import com.example.demo.model.Korisnik;
import com.example.demo.model.enums.StatusZalbe;
import com.example.demo.model.enums.TipZalbe;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.ZalbaRepository;

@Service
public class ZalbaService {
	
	@Autowired
	private ZalbaRepository zalbaRepository;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	private static final String XSL_FO_PATH_CUTANJE = Constants.XSL_FOLDER + File.separatorChar + "zalba_cutanje_fo.xsl";
	private static final String XSL_PATH_CUTANJE = Constants.XSL_FOLDER + File.separatorChar + "/zalba_cutanje.xsl";
	private static final String XSL_FO_PATH_ODLUKA = Constants.XSL_FOLDER + File.separatorChar + "/zalba_odluka_fo.xsl";
	private static final String XSL_PATH_ODLUKA = Constants.XSL_FOLDER + File.separatorChar + "/zalba_odluka.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "zalbe" + File.separatorChar;
	
	public void save(String xml) throws ParserConfigurationException, SAXException, IOException, JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		Document document = this.domParser.buildDocument(xml);
		Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		this.domParser.removeXmlSpace(document);
		
		DocumentFragment documentFragment = document.createDocumentFragment();
		Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
		datum.setTextContent(sdf.format(new Date()));
		Node mesto = document.createElementNS(Namespaces.OSNOVA, "mesto");
		mesto.setTextContent(Constants.TEST_MESTO);
		Node potpis = document.createElementNS(Namespaces.OSNOVA, "potpis");
		potpis.setTextContent(Constants.TEST_POTPIS);
		Element korisnik = (Element) this.jaxbParser.marshal(this.korisnikService.currentUser()).getElementsByTagNameNS(Namespaces.OSNOVA, "Korisnik").item(0);
		Node gradjanin = document.createElementNS(Namespaces.OSNOVA, "Gradjanin");
		gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Osoba").item(0), true));
		gradjanin.appendChild(document.importNode(korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0), true));
		documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));
		documentFragment.appendChild(datum);
		documentFragment.appendChild(mesto);
		documentFragment.appendChild(potpis);
		documentFragment.appendChild(gradjanin);
		zalba.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0));
		
		//nzm sto moram da stavim zalba prefiks pre
		Node status = document.createElementNS(Namespaces.ZALBA, "zalba:status");
		status.setTextContent(StatusZalbe.cekanje + "");
		zalba.insertBefore(status, document.getElementsByTagNameNS(Namespaces.ZALBA, "datumZahteva").item(0));
				
		this.zalbaRepository.save(document, null);
	}
	
	public List<ZalbaDTO> retrieve() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException{
		
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.POVERENIK)) {
			xpathExp = "/zalba:Zalba";
		}
		else {
			xpathExp = String.format("/zalba:Zalba[Gradjanin/Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}
				
		List<ZalbaDTO> zalbe = new ArrayList<>();
		ResourceSet result = this.zalbaRepository.retrieve(xpathExp);
		ResourceIterator it = result.getIterator();
		while (it.hasMoreResources()) {
			XMLResource resource = (XMLResource) it.nextResource();
			Document document = this.domParser.buildDocument(resource.getContent().toString());
			TipZalbe tipZalbe = getTipZalbe(((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0)).getAttributeNS(Namespaces.XSI, "type"));
			String broj = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
			String datum = document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent();
			String organVlasti = document.getElementsByTagNameNS(Namespaces.OSNOVA, "naziv").item(0).getTextContent();
			StatusZalbe status = StatusZalbe.valueOf(document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).getTextContent());			
			zalbe.add(new ZalbaDTO(tipZalbe, broj, datum, organVlasti, status));
		}
		return zalbe;
		
	}
	
	public String generateHtml(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.zalbaRepository.load(broj);
		String xslPath;
		TipZalbe tipZalbe = getTipZalbe(((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0)).getAttributeNS(Namespaces.XSI, "type"));
		if (tipZalbe.equals(TipZalbe.cutanje)) {
			xslPath = XSL_PATH_CUTANJE;
		}
		else {
			xslPath = XSL_PATH_ODLUKA;
		}
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, xslPath);
		return out.toString();
	}
	
	public Resource generatePdf(String broj) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.zalbaRepository.load(broj);
		String xslFoPath;
		TipZalbe tipZalbe = getTipZalbe(((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0)).getAttributeNS(Namespaces.XSI, "type"));
		if (tipZalbe.equals(TipZalbe.cutanje)) {
			xslFoPath = XSL_FO_PATH_CUTANJE;
		}
		else {
			xslFoPath = XSL_FO_PATH_ODLUKA;
		}
		ByteArrayOutputStream out = this.xslTransformer.generatePdf(document, xslFoPath);
		Path file = Paths.get(GEN_PATH + broj + ".pdf");
		Files.write(file, out.toByteArray());
		return new UrlResource(file.toUri());
	}
	
	public static TipZalbe getTipZalbe(String tipZalbe) {
		if (tipZalbe.equals("zalba:TZalbaCutanje")) {
			return TipZalbe.cutanje;
		}
		return TipZalbe.odluka;
	}
	
}

