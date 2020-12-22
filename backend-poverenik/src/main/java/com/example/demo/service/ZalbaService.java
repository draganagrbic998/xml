package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.constants.Constants;
import com.example.demo.constants.Namespaces;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Zalba;
import com.example.demo.model.ZalbaCutanje;
import com.example.demo.model.ZalbaOdbijanje;
import com.example.demo.model.enums.TipCutanja;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.ZalbaRepository;

@Service
public class ZalbaService {
	
	@Autowired
	private ZalbaRepository zalbaRepository;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private JAXBParser jaxbParser;
		
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	private static final String XSL_PATH_CUTANJE = Constants.XSL_FOLDER + "/zalba_cutanje.xsl";
	private static final String XSL_PATH_ODBIJANJE = Constants.XSL_FOLDER + "/zalba_odbijanje.xsl";

	private static final String XSL_FO_PATH_CUTANJE = Constants.XSL_FOLDER + "/zalba_cutanje_fo.xsl";
	private static final String XSL_FO_PATH_ODBIJANJE = Constants.XSL_FOLDER + "/zalba_odbijanje_fo.xsl";
	
	private static final String XSL_PATH_OBAVESTENJE = Constants.XSL_FOLDER + "/obavestenje.xsl";
	private static final String XSL_FO_PATH_OBAVESTENJE = Constants.XSL_FOLDER + "/obavestenje_fo.xsl";
	
	private SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	public void save(String xml) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, ParserConfigurationException, SAXException, IOException, DOMException, ParseException {		
		Korisnik korisnik = this.korisnikService.currentUser();
		Document document = this.domParser.buildDocument(xml);
		Zalba zalba;
		if (document.getElementsByTagName("tipCutanja").getLength() > 0) {
			TipCutanja tipCutanja = TipCutanja.valueOf(document.getElementsByTagName("tipCutanja").item(0).getTextContent());
			zalba = new ZalbaCutanje(tipCutanja);
		}
		else {
			String brojOdluke = document.getElementsByTagName("brojOdluke").item(0).getTextContent();
			Date datumOdluke = this.format.parse(document.getElementsByTagName("datumOdluke").item(0).getTextContent());
			zalba = new ZalbaOdbijanje(brojOdluke, datumOdluke);
		}
		zalba.setGradjanin(korisnik.getGradjanin());
		zalba.setDatum(new Date());
		zalba.setDetalji(document.getElementsByTagName("detalji").item(0).getTextContent());
		zalba.setKontakt(korisnik.getEmail());
		zalba.setPotpis(Constants.SIGNATURE);
		zalba.setOdgovoreno(false);
		zalba.setOrganVlasti(document.getElementsByTagName("organVlasti").item(0).getTextContent());
		zalba.setDatumZahteva(this.format.parse(document.getElementsByTagName("datumZahteva").item(0).getTextContent()));
		zalba.setKopijaZahteva(document.getElementsByTagName("kopijaZahteva").item(0).getTextContent());
		if (document.getElementsByTagName("kopijaOdluke").getLength() > 0) {
			zalba.setKopijaOdluke(document.getElementsByTagName("kopijaOdluke").item(0).getTextContent());
		}
		
		this.zalbaRepository.save(this.jaxbParser.marshal(zalba, Zalba.class));		
	}
	
	public String getHtml(int index) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException { 
		XMLResource resource = this.zalbaRepository.load(index);
		System.out.println(resource);
		System.out.println(resource.getContentAsDOM());
		//boolean cutanje = resource.getContentAsDOM().getAttributes().getNamedItemNS(Namespaces.XSI, "type").getNodeValue().equals("TZalbaCutanje");
		boolean cutanje = true;
		
		try {
			this.makePdf(index, cutanje);	//samo da bih testirala, nek stoji zasad ovde
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if (cutanje) {
			return this.xslTransformer.xml2html(resource, XSL_PATH_OBAVESTENJE);	//ovo treba zakomentarisati i prebaciti u backend organa vlasti
			//return this.xslTransformer.xml2html(resource, XSL_PATH_CUTANJE);

		}
		return this.xslTransformer.xml2html(resource, XSL_PATH_ODBIJANJE);
	}
	
	public void makePdf(int index, boolean cutanje) throws Exception {
		Node document = this.zalbaRepository.load(index).getContentAsDOM();
		String xml = this.domParser.buildXml(document);
		//String path = cutanje ? XSL_FO_PATH_CUTANJE : XSL_FO_PATH_ODBIJANJE;
		String path = XSL_FO_PATH_OBAVESTENJE;	//ovo treba zakomentarisati i prebaciti na backend organa vlasti
		ByteArrayOutputStream outputStream = xslTransformer.xml2pdf(xml, path);
		Path file = Paths.get("data/temp.pdf");
		Files.write(file, outputStream.toByteArray());
		//treba da dodamo da vratimo rezultat klijentu pa da moze da vidi i on
	}

	
}
