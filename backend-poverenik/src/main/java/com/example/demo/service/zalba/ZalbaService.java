package com.example.demo.service.zalba;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.enums.MetadataType;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.enums.TipZalbe;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.ZalbaRDF;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;
import com.example.demo.ws.utils.SOAPService;
import com.example.demo.ws.utils.TipDokumenta;

@Service
public class ZalbaService {

	@Autowired
	private ZalbaExist zalbaExist;

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private ZalbaRDF zalbaRDF;

	@Autowired
	private ZalbaMapper zalbaMapper;

	@Autowired
	private DOMParser domParser;

	@Autowired
	private XSLTransformer xslTransformer;

	@Autowired
	private SOAPService soapService;

	private static final String XSL_PATH_CUTANJE = Constants.XSL_FOLDER + "zalba_cutanje.xsl";
	private static final String XSL_FO_PATH_CUTANJE = Constants.XSL_FOLDER + "zalba_cutanje_fo.xsl";
	private static final String XSL_PATH_ODLUKA = Constants.XSL_FOLDER + "zalba_odluka.xsl";
	private static final String XSL_FO_PATH_ODLUKA = Constants.XSL_FOLDER + "zalba_odluka_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + "zalbe" + File.separatorChar;

	public void save(String xml) {
		Document document = this.zalbaMapper.map(xml);
		this.zalbaExist.save(null, document);
		Model model = this.zalbaMapper.map(document);
		this.zalbaRDF.save(model);
	}

	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.POVERENIK)) {
			xpathExp = "/zalba:Zalba";
		} else {
			xpathExp = String.format("/zalba:Zalba[Gradjanin/Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}
		ResourceSet resources = this.zalbaExist.retrieve(xpathExp);
		return this.zalbaMapper.map(resources);
	}

	public String generateHtml(String broj) {
		Document document = this.zalbaExist.load(broj);
		String xslPath;
		if (ZalbaMapper.getTipZalbe(document).equals(TipZalbe.odluka)) {
			xslPath = XSL_PATH_ODLUKA;
		} else {
			xslPath = XSL_PATH_CUTANJE;
		}
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(this.domParser.buildXml(document), xslPath);
		return out.toString();
	}

	public Resource generatePdf(String broj) {
		try {
			Document document = this.zalbaExist.load(broj);
			String xslFoPath;
			if (ZalbaMapper.getTipZalbe(document).equals(TipZalbe.odluka)) {
				xslFoPath = XSL_FO_PATH_ODLUKA;
			} else {
				xslFoPath = XSL_FO_PATH_CUTANJE;
			}
			ByteArrayOutputStream out = this.xslTransformer.generatePdf(this.domParser.buildXml(document), xslFoPath);
			Path file = Paths.get(GEN_PATH + broj + ".pdf");
			Files.write(file, out.toByteArray());
			return new UrlResource(file.toUri());
		} catch (Exception e) {
			throw new MyException(e);
		}
	}

	public Resource generateMetadata(String broj, MetadataType type) {
		try {
			ResultSet results = this.zalbaRDF.retrieve(broj);
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

	public void odustani(String broj) {
		Document document = this.zalbaExist.load(broj);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odustato + "");
		this.zalbaExist.save(broj, document);
		// dodaj slanje mejla
	}

	public void obustavi(String broj) {
		Document document = this.zalbaExist.load(broj);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0)
				.setTextContent(StatusZalbe.obustaljveno + "");
		this.zalbaExist.save(broj, document);
	}

	public void prosledi(String broj) {
		Document document = this.zalbaExist.load(broj);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0)
				.setTextContent(StatusZalbe.prosledjeno + "");
		this.soapService.sendSOAPMessage(this.domParser.buildXml(document), TipDokumenta.zalba);

		Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		Node datumProsledjivanja = document.createElementNS(Namespaces.ZALBA, "zalba:datumProsledjivanja");
		datumProsledjivanja.setTextContent(ZalbaMapper.sdf.format(new Date()));
		zalba.insertBefore(datumProsledjivanja, document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0));
		this.zalbaExist.save(broj, document);
	}

}
