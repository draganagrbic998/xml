package com.example.demo.service.zalba;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.constants.Constants;
import com.example.demo.constants.Namespaces;
import com.example.demo.exception.MyException;
import com.example.demo.model.Korisnik;
import com.example.demo.model.enums.StatusZalbe;
import com.example.demo.model.enums.TipZalbe;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.ZalbaRDF;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.email.Email;
import com.example.demo.service.email.EmailService;
import com.example.demo.ws.utils.SOAPService;
import com.example.demo.ws.utils.TipDokumenta;
import com.example.demo.ws.zalbepodaci.data.ZalbePodaciData;

@Service
public class ZalbaService {

	@Autowired
	private ZalbaExist zalbaExist;

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private ZalbaMapper zalbaMapper;
	
	@Autowired
	private ZalbaRDF zalbaRDF;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	@Autowired
	private SOAPService soapService;
	
	@Autowired
	private EmailService emailService;

	private static final String XSL_PATH_CUTANJE = Constants.XSL_FOLDER + File.separatorChar + "/zalba_cutanje.xsl";
	private static final String XSL_FO_PATH_CUTANJE = Constants.XSL_FOLDER + File.separatorChar + "zalba_cutanje_fo.xsl";
	private static final String XSL_PATH_ODLUKA = Constants.XSL_FOLDER + File.separatorChar + "/zalba_odluka.xsl";
	private static final String XSL_FO_PATH_ODLUKA = Constants.XSL_FOLDER + File.separatorChar + "/zalba_odluka_fo.xsl";
	private static final String GEN_PATH = Constants.GEN_FOLDER + File.separatorChar + "zalbe" + File.separatorChar;

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

	public ZalbePodaciData retrieveZalbePodaci() {
		try {
			String xpathExp = "/zalba:Zalba";
			ResourceSet result = this.zalbaExist.retrieve(xpathExp);
			ResourceIterator it = result.getIterator();

			ZalbePodaciData ip = new ZalbePodaciData();
			int cutanja = 0;
			int delimicnosti = 0;
			int odbijanja = 0;

			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				TipZalbe tipZalbe = ZalbaMapper.getTipZalbe(document);
				if (tipZalbe.equals(TipZalbe.cutanje))
					cutanja++;
				else
					odbijanja++;
			}

			ip.setZalbeCutanja(BigInteger.valueOf(cutanja));
			ip.setZalbeDelimicnosti(BigInteger.valueOf(delimicnosti));
			ip.setZalbeOdbijanja(BigInteger.valueOf(odbijanja));

			return ip;
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

	public String generateHtml(String broj) {
		Document document = this.zalbaExist.load(broj);
		String xslPath;
		if (ZalbaMapper.getTipZalbe(document).equals(TipZalbe.cutanje)) {
			xslPath = XSL_PATH_CUTANJE;
		} else {
			xslPath = XSL_PATH_ODLUKA;
		}
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(document, xslPath);
		return out.toString();
	}

	public Resource generatePdf(String broj) {
		try {
			Document document = this.zalbaExist.load(broj);
			String xslFoPath;
			if (ZalbaMapper.getTipZalbe(document).equals(TipZalbe.cutanje)) {
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
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public void odustani(String broj) {
		Document document = this.zalbaExist.load(broj);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odustato + "");
		this.zalbaExist.save(broj, document);
		//dodaj slanje mejla
		//Email email = new Email();
		//email.setTo(this.korisnikService.currentUser().getOsoba().getMejl());
		//email.setSubject("Odustaja od žalbe");

	}
	
	public void obustavi(String broj) {
		Document document = this.zalbaExist.load(broj);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.obustaljveno + "");
		this.zalbaExist.save(broj, document);
	}

	public void prosledi(String broj) {
		Document document = this.zalbaExist.load(broj);
		document.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.prosledjeno + "");
		this.soapService.sendSOAPMessage(document, TipDokumenta.zalba);
		
		Element zalba = (Element) document.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		Node datumProsledjivanja = document.createElementNS(Namespaces.ZALBA, "zalba:datumProsledjivanja");
		datumProsledjivanja.setTextContent(ZalbaMapper.sdf.format(new Date()));
		zalba.insertBefore(datumProsledjivanja, document.getElementsByTagNameNS(Namespaces.ZALBA, "datumZahteva").item(0));
		this.zalbaExist.save(broj, document);
	}

}
