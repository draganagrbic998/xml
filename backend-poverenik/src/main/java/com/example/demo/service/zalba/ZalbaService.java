package com.example.demo.service.zalba;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.constants.Constants;
import com.example.demo.constants.Namespaces;
import com.example.demo.model.Korisnik;
import com.example.demo.model.enums.TipZalbe;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.rdf.MetadataExtraction;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;
import com.example.demo.ws.zalbepodaci.data.ZalbePodaciData;

@Service
public class ZalbaService {

	@Autowired
	private ZalbaExist zalbaRepository;

	@Autowired
	private DOMParser domParser;

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private XSLTransformer xslTransformer;

	@Autowired
	private MetadataExtraction metadataExtraction;
	
	@Autowired
	private ZalbaMapper zalbaMapper;

	private static final String XSL_FO_PATH_CUTANJE = Constants.XSL_FOLDER + File.separatorChar + "zalba_cutanje_fo.xsl";
	private static final String XSL_PATH_CUTANJE = Constants.XSL_FOLDER + File.separatorChar + "/zalba_cutanje.xsl";
	private static final String XSL_FO_PATH_ODLUKA = Constants.XSL_FOLDER + File.separatorChar + "/zalba_odluka_fo.xsl";
	private static final String XSL_PATH_ODLUKA = Constants.XSL_FOLDER + File.separatorChar + "/zalba_odluka.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "zalbe" + File.separatorChar;

	public void save(String xml) throws ParserConfigurationException, SAXException, IOException, JAXBException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException,
			TransformerException, UnsupportedOperationException, DOMException, SOAPException {
		Document document = this.zalbaMapper.map(xml);
		this.zalbaRepository.save(document, null);
	}

	public String retrieve() throws XMLDBException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, ParserConfigurationException, SAXException, IOException, TransformerException {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.POVERENIK)) {
			xpathExp = "/zalba:Zalba";
		} else {
			xpathExp = String.format("/zalba:Zalba[Gradjanin/Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}

		ResourceSet resources = this.zalbaRepository.retrieve(xpathExp);
		return this.zalbaMapper.map(resources);

	}

	public ZalbePodaciData retrieveZalbePodaci() throws XMLDBException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, ParserConfigurationException, SAXException, IOException {
		String xpathExp = "/zalba:Zalba";

		ResourceSet result = this.zalbaRepository.retrieve(xpathExp);
		ResourceIterator it = result.getIterator();

		ZalbePodaciData ip = new ZalbePodaciData();
		int cutanja = 0;
		int delimicnosti = 0;
		int odbijanja = 0;

		while (it.hasMoreResources()) {
			XMLResource resource = (XMLResource) it.nextResource();
			Document document = this.domParser.buildDocument(resource.getContent().toString());

			String tz = getTipZalbe(((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0))
					.getAttributeNS(Namespaces.XSI, "type")) + "";

			if (tz.equalsIgnoreCase("cutanje"))
				cutanja++;
			else if (tz.equalsIgnoreCase("delimicnost"))
				delimicnosti++;
			else
				odbijanja++;
		}

		ip.setZalbeCutanja(BigInteger.valueOf(cutanja));
		ip.setZalbeDelimicnosti(BigInteger.valueOf(delimicnosti));
		ip.setZalbeOdbijanja(BigInteger.valueOf(odbijanja));

		return ip;
	}

	public String generateHtml(String broj) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.zalbaRepository.load(broj);
		String xslPath;
		TipZalbe tipZalbe = getTipZalbe(((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0))
				.getAttributeNS(Namespaces.XSI, "type"));
		if (tipZalbe.equals(TipZalbe.cutanje)) {
			xslPath = XSL_PATH_CUTANJE;
		} else {
			xslPath = XSL_PATH_ODLUKA;
		}
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, xslPath);
		return out.toString();
	}

	public Resource generatePdf(String broj) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, XMLDBException, TransformerException, SAXException, IOException {
		Document document = this.zalbaRepository.load(broj);
		String xslFoPath;
		TipZalbe tipZalbe = getTipZalbe(((Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0))
				.getAttributeNS(Namespaces.XSI, "type"));
		if (tipZalbe.equals(TipZalbe.cutanje)) {
			xslFoPath = XSL_FO_PATH_CUTANJE;
		} else {
			xslFoPath = XSL_FO_PATH_ODLUKA;
		}
		ByteArrayOutputStream out = this.xslTransformer.generatePdf(document, xslFoPath);
		Path file = Paths.get(GEN_PATH + broj + ".pdf");
		Files.write(file, out.toByteArray());
		return new UrlResource(file.toUri());
	}

	public void extractMetadata() throws IOException, SAXException, TransformerException {
		String xmlFilePath = "data/xml/zahtev1.xml";
		String rdfFilePath = "data/gen/zahtev.rdf";
		this.metadataExtraction.run(xmlFilePath, rdfFilePath);
	}

	public static TipZalbe getTipZalbe(String tipZalbe) {
		if (tipZalbe.contains("TZalbaCutanje")) {
			return TipZalbe.cutanje;
		}
		return TipZalbe.odluka;
	}

}
