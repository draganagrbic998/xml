package com.example.demo.service.odgovor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.enums.MetadataType;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.OdgovorRDF;
import com.example.demo.repository.xml.OdgovorExist;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;

@Service
public class OdgovorService {
	
	@Autowired
	private OdgovorExist odgovorExist;
	
	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private OdgovorMapper odgovorMapper;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private OdgovorRDF odgovorRDF;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	@Autowired
	private KorisnikService korisnikService;
	
	private static final String XSL_PATH = Constants.XSL_FOLDER + "odgovor.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "odgovor_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "odgovori" + File.separatorChar;
	
	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.POVERENIK)) {
			xpathExp = "/odgovor:Odgovor";
		} 
		else {
			xpathExp = String.format("/odgovor:Odgovor[Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}
		ResourceSet resources = this.odgovorExist.retrieve(xpathExp);
		return this.odgovorMapper.map(resources);
	}
	
	public void save(String xml) {
		Document document = this.odgovorMapper.map(xml);
		this.odgovorExist.save(null, document);
		String brojZalbe = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		Document zalbaDocument = this.zalbaExist.load(brojZalbe);
		zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odgovoreno + "");
		this.zalbaExist.save(brojZalbe, zalbaDocument);
		Model model = this.odgovorMapper.map(document);
		this.odgovorRDF.save(model);
	}
	
	public String generateHtml(String broj) {
		Document document = this.odgovorExist.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(this.domParser.buildXml(document), XSL_PATH);
		return out.toString();
	}
	
	public Resource generatePdf(String broj) {
		try {
			Document document = this.odgovorExist.load(broj);
			ByteArrayOutputStream out = this.xslTransformer.generatePdf(this.domParser.buildXml(document), XSL_FO_PATH);
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
			ResultSet results = this.odgovorRDF.retrieve(broj);
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
