package com.example.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.StatusZahteva;
import com.example.demo.exception.MyException;
import com.example.demo.mapper.ZahtevMapper;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.rdf.ZahtevRDF;
import com.example.demo.repository.xml.ZahtevExist;
import com.example.demo.service.email.Email;
import com.example.demo.service.email.EmailService;

@Service
public class ZahtevService implements ServiceInterface {
	
	@Autowired
	private ZahtevExist zahtevExist;
			
	@Autowired
	private ZahtevRDF zahtevRDF;

	@Autowired
	private ZahtevMapper zahtevMapper;

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private DOMParser domParser;
				
	private static final long CUTANJE_MILISECONDS_LIMIT = 10000;

	@Override
	public void add(String xml) {
		Document document = this.zahtevMapper.map(xml);
		this.zahtevExist.add(document);
		this.zahtevRDF.add(document);		
	}

	@Override
	public void update(String documentId, Document document) {
		this.zahtevExist.update(documentId, document);
		this.zahtevRDF.update(documentId, document);
	}
	
	@Override
	public void delete(String documentId) {
		this.zahtevExist.delete(documentId);
		this.zahtevRDF.delete(documentId);
	}

	@Override
	public Document load(String documentId) {
		return this.zahtevExist.load(documentId);
	}

	@Override
	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			xpathExp = "/zahtev:Zahtev";
		}
		else {
			xpathExp = String.format("/zahtev:Zahtev[@href='%s']", Namespaces.KORISNIK + "/" + korisnik.getMejl());
		}
		return this.zahtevMapper.map(this.zahtevExist.retrieve(xpathExp));
	}

	@Override
	public String regularSearch(String xml) {
		return null;
	}

	@Override
	public String advancedSearch(String xml) {
		return null;
	}

	//@Scheduled(fixedDelay = CUTANJE_MILISECONDS_LIMIT, initialDelay = CUTANJE_MILISECONDS_LIMIT)
	public void cutanjeUprave() {
		try {
			String xpathExp = String.format("/zahtev:Zahtev[(%d - zahtev:vreme) >= %d][zahtev:status = 'cekanje']", new Date().getTime(), CUTANJE_MILISECONDS_LIMIT);
			ResourceSet resources = this.zahtevExist.retrieve(xpathExp);
			ResourceIterator it = resources.getIterator();
			while (it.hasMoreResources()) {
				XMLResource resource = (XMLResource) it.nextResource();
				Document document = this.domParser.buildDocument(resource.getContent().toString());
				document.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odbijeno + "");
				this.zahtevExist.update(Utils.getBroj(document), document);

				Element zahtev = (Element) document.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
				Email email = new Email();
				email.setTo(zahtev.getAttribute("href").replace(Namespaces.KORISNIK + "/", ""));
				email.setSubject("Ćutanje uprave na zahtev za informacijama od javnog značaja");
				String text = "Poštovani/a, \n\n"
						+ "Zahtev za informacijama od javnog značaja koji ste podneli dana "
						+ Constants.sdf2.format(Constants.sdf.parse(document.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent()))
						+ " nije primio odgovor u zakonskom roku od 15 dana. \nMožete podneti žalbu na ćutanje uprave. \n\n" 
						+ "Svako dobro, \n"
						+ document.getElementsByTagNameNS(Namespaces.OSNOVA, "naziv").item(0).getTextContent(); 
				email.setText(text);
				this.emailService.sendEmail(email);
				System.out.println("ULOVLJEN ZAHTEV BROJ " + Utils.getBroj(document));
			}
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

}
