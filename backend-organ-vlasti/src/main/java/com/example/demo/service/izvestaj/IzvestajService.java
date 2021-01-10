package com.example.demo.service.izvestaj;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.enums.MetadataType;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.IzvestajRDF;
import com.example.demo.repository.xml.IzvestajExist;
import com.example.demo.service.KorisnikService;
import com.example.demo.ws.utils.SOAPService;
import com.example.demo.ws.utils.TipDokumenta;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Service
public class IzvestajService {

	@Autowired
	private IzvestajExist izvestajExist;

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private IzvestajRDF izvestajRDF;

	@Autowired
	private IzvestajMapper izvestajMapper;

	@Autowired
	private DOMParser domParser;

	@Autowired
	private XSLTransformer xslTransformer;

	@Autowired
	private SOAPService soapService;

	private static final String XSL_PATH = Constants.XSL_FOLDER + File.separatorChar + "izvestaj.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + File.separatorChar + "izvestaj_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "izvestaji" + File.separatorChar;

	public void save(String xml) throws XMLDBException {
		Document document = this.izvestajMapper.map(xml);
		this.izvestajExist.save(null, document);
		// Model model = this.izvestajMapper.map(document);
		// this.izvestajRDF.save(model);

		this.soapService.sendSOAPMessage(document, TipDokumenta.izvestaj);
	}

	public String load(String broj) {
		return this.domParser.buildXml(this.izvestajExist.load(broj));
	}

	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			xpathExp = "/zahtev:Zahtev";
		} else {
			xpathExp = String.format("/zahtev:Zahtev[Gradjanin/Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}
		ResourceSet resources = this.izvestajExist.retrieve(xpathExp);
		return this.izvestajMapper.map(resources);
	}

	public String generateHtml(String broj) {
		Document document = this.izvestajExist.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(this.domParser.buildXml(document), XSL_PATH);
		return out.toString();
	}

	public Resource generatePdf(String broj) {
		try {
			Document document = this.izvestajExist.load(broj);
			ByteArrayOutputStream out = this.xslTransformer.generatePdf(this.domParser.buildXml(document), XSL_FO_PATH);
			Path file = Paths.get(GEN_PATH + broj + ".pdf");
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	public Resource generateMetadata(String broj, MetadataType type) {
		try {
			ResultSet results = this.izvestajRDF.retrieve(broj);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			if (type.equals(MetadataType.xml)) {
				ResultSetFormatter.outputAsXML(out, results);
			} else {
				ResultSetFormatter.outputAsJSON(out, results);
			}
			Path file = Paths.get(GEN_PATH + broj + "_metadata." + type);
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

}
