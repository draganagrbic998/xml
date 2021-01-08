package com.example.demo.service.odluka;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.constants.Constants;
import com.example.demo.constants.Namespaces;
import com.example.demo.exception.MyException;
import com.example.demo.fuseki.MetadataType;
import com.example.demo.model.Korisnik;
import com.example.demo.model.enums.StatusZahteva;
import com.example.demo.model.enums.TipOdluke;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.OdlukaRDF;
import com.example.demo.repository.xml.OdlukaExist;
import com.example.demo.repository.xml.ZahtevExist;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.email.Email;
import com.example.demo.service.email.EmailService;
import com.ibm.icu.text.SimpleDateFormat;

@Service
public class OdlukaService {
		
	@Autowired
	private OdlukaExist odlukaExist;
	
	@Autowired
	private ZahtevExist zahtevExist;

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private OdlukaRDF odlukaRDF;
		
	@Autowired
	private OdlukaMapper odlukaMapper;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private EmailService emailService;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy.");

	private static final String XSL_FO_PATH_ODBIJANJE = Constants.XSL_FOLDER + File.separatorChar + "odbijanje_fo.xsl";
	private static final String XSL_PATH_ODBIJANJE = Constants.XSL_FOLDER + File.separatorChar + "/odbijanje.xsl";
	private static final String XSL_FO_PATH_OBAVESTENJE = Constants.XSL_FOLDER + File.separatorChar + "obavestenje_fo.xsl";
	private static final String XSL_PATH_OBAVESTENJE = Constants.XSL_FOLDER + File.separatorChar + "/obavestenje.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "odluke" + File.separatorChar;

	public void add(String xml) {
		Document document = this.odlukaMapper.map(xml);
		String brojZahteva = document.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).getTextContent();
		Document zahtevDocument = this.zahtevExist.load(brojZahteva);
		if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
			zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odobreno + "");
		}
		else {
			zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odbijeno + "");
		}
		this.odlukaExist.save(null, document);
		this.zahtevExist.save(brojZahteva, zahtevDocument);
		this.odlukaRDF.save(this.odlukaMapper.map(document));
		this.notifyOdluka(document);
	}
	
	public String load(String broj) {
		try {
			return this.domParser.buildXml(this.odlukaExist.load(broj));
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public void notifyOdluka(Document document) {
		try {
			String brojOdluke = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
			String mejl = document.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0).getTextContent();
			String ime = document.getElementsByTagNameNS(Namespaces.OSNOVA, "ime").item(0).getTextContent();
			String prezime = document.getElementsByTagNameNS(Namespaces.OSNOVA, "prezime").item(0).getTextContent();
			String naziv = document.getElementsByTagNameNS(Namespaces.OSNOVA, "naziv").item(0).getTextContent();
			String mesto = document.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(1).getTextContent();
			String ulica = document.getElementsByTagNameNS(Namespaces.OSNOVA, "ulica").item(1).getTextContent();
			String broj = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(1).getTextContent();
			String sediste = ulica + " " + broj + ", " + mesto;
			String datumZahteva = sdf.format(OdlukaMapper.sdf.parse(document.getElementsByTagNameNS(Namespaces.ODLUKA, "datumZahteva").item(0).getTextContent()));
			
			
			Email email = new Email();
			email.setTo(mejl);
			email.setSubject("Odgovor na zahtev za informacije od javnog značaja");
			String text = "Poštovani/a " + ime + " " + prezime + ", \n\n"
					+ "Odgovor/i na zahtev za informacijama od javnog značaja koji ste podneli dana " 
					+ datumZahteva + "nalaze se u linkovima ispod: \n"
					+ Constants.BACKEND_URL + "/api/odluke/" + brojOdluke + "/html\n"
					+ Constants.BACKEND_URL + "/api/odluke/" + brojOdluke + "/pdf\n\n"
					+ "Svako dobro, \n\n"
					+ naziv + "\n" 
					+ sediste;
			email.setText(text);
			this.emailService.sendEmail(email);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			xpathExp = "/odluka:Odluka";
		}
		else {
			xpathExp = String.format("/odluka:Odluka[Gradjanin/Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}
		ResourceSet resources = this.odlukaExist.list(xpathExp);
		return this.odlukaMapper.map(resources);
	}
	
	public String html(String broj) {
		Document document = this.odlukaExist.load(broj);
		String xslPath;
		if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
			xslPath = XSL_PATH_OBAVESTENJE;
		}
		else {
			xslPath = XSL_PATH_ODBIJANJE;
		}
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, xslPath);
		return out.toString();
	}
	
	public Resource generateHtml(String broj) {
		try {
			Document document = this.odlukaExist.load(broj);
			String xslPath;
			if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
				xslPath = XSL_PATH_OBAVESTENJE;
			}
			else {
				xslPath = XSL_PATH_ODBIJANJE;
			}
			ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, xslPath);
			Path file = Paths.get(GEN_PATH + broj + ".html");
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public Resource generatePdf(String broj) {
		try {
			Document document = this.odlukaExist.load(broj);
			String xslFoPath;
			if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
				xslFoPath = XSL_FO_PATH_OBAVESTENJE;
			}
			else {
				xslFoPath = XSL_FO_PATH_ODBIJANJE;
			}
			ByteArrayOutputStream out = this.xslTransformer.generatePdf(document, xslFoPath);
			Path file = Paths.get(GEN_PATH + broj + ".pdf");
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public Resource generateMetadata(String broj, MetadataType type) {
		try {
			ResultSet results = this.odlukaRDF.retrieve(broj);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			if (type.equals(MetadataType.xml)) {
				ResultSetFormatter.outputAsXML(out, results);
			}
			else {
				ResultSetFormatter.outputAsJSON(out, results);
			}
			Path file = Paths.get(GEN_PATH + broj + "_metadata." + type);
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

}
