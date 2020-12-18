package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.constants.Constants;
import com.example.demo.model.Korisnik;
import com.example.demo.model.OrganVlasti;
import com.example.demo.model.Zahtev;
import com.example.demo.model.ZahtevDostava;
import com.example.demo.model.ZahtevUvid;
import com.example.demo.model.enums.TipDostave;
import com.example.demo.model.enums.TipUvida;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.OrganVlastiRepository;
import com.example.demo.repository.ZahtevRepository;

@Service
public class ZahtevService {
	
	@Autowired
	private ZahtevRepository zahtevRepository;
	
	@Autowired
	private OrganVlastiRepository organVlastiRepository;
		
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	private static final String XSL_PATH = Constants.XSL_FOLDER + "/zahtev.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "/zahtev_fo.xsl";

	public String getHtml(int index) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, TransformerException { 
		XMLResource resource = this.zahtevRepository.load(index);
		try {
			this.makePdf(index);	//samo da bih testirala, nek stoji zasad ovde
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return this.xslTransformer.xml2html(resource, XSL_PATH);
	}
	
	public void makePdf(int index) throws Exception {
		Node document = this.zahtevRepository.load(index).getContentAsDOM();
		String xml = this.domParser.buildXml(document);
		ByteArrayOutputStream outputStream = xslTransformer.xml2pdf(xml, XSL_FO_PATH);
		Path file = Paths.get("data/temp.pdf");
		Files.write(file, outputStream.toByteArray());
		//treba da dodamo da vratimo rezultat klijentu pa da moze da vidi i on
	}
	
	public void save(String xml) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException {		
		Korisnik korisnik = this.korisnikService.currentUser();
		OrganVlasti organVlasti = this.organVlastiRepository.load();
		Document document = this.domParser.buildDocument(xml);
		Zahtev zahtev;
		if (document.getElementsByTagName("tipUvida").getLength() > 0) {
			TipUvida tipUvida = TipUvida.valueOf(document.getElementsByTagName("tipUvida").item(0).getTextContent());
			zahtev = new ZahtevUvid(tipUvida);
		}
		else {
			TipDostave tipDostave = TipDostave.valueOf(document.getElementsByTagName("tipDostave").item(0).getTextContent());
			String opisDostave = document.getElementsByTagName("opisDostave").item(0).getTextContent();
			zahtev = new ZahtevDostava(tipDostave, opisDostave);
		}
		zahtev.setGradjanin(korisnik.getGradjanin());
		zahtev.setOrganVlasti(organVlasti);
		zahtev.setDatum(new Date());
		zahtev.setDetalji(document.getElementsByTagName("detalji").item(0).getTextContent());
		zahtev.setKontakt(korisnik.getEmail());
		zahtev.setPotpis(Constants.SIGNATURE);
		zahtev.setOdgovoreno(false);
		this.zahtevRepository.save(this.jaxbParser.marshal(zahtev, Zahtev.class));
	}
	
}
