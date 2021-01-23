package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.enums.StatusZahteva;
import com.example.demo.enums.TipOdluke;
import com.example.demo.exception.MyException;
import com.example.demo.exception.ResourceTakenException;
import com.example.demo.mapper.OdlukaMapper;
import com.example.demo.mapper.ZahtevMapper;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.rdf.OdlukaRDF;
import com.example.demo.repository.rdf.ZahtevRDF;
import com.example.demo.repository.xml.OdlukaExist;
import com.example.demo.service.email.Email;
import com.example.demo.service.email.EmailService;
import com.example.demo.ws.utils.SOAPActions;
import com.example.demo.ws.utils.SOAPService;

@Service
public class OdlukaService implements ServiceInterface {
		
	@Autowired
	private OdlukaExist odlukaExist;
	
	@Autowired
	private OdlukaRDF odlukaRDF;
		
	@Autowired
	private OdlukaMapper odlukaMapper;

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private ZahtevService zahtevService;
	
	@Autowired
	private ZahtevRDF zahtevRDF;
	
	@Autowired
	private ZalbaService zalbaService;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private SOAPService soapService;
	
	@Autowired
	private DOMParser domParser;
		
	@Override
	public void add(String xml) {
		Document document = this.odlukaMapper.map(xml);
		String brojZahteva = document.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).getTextContent();
		Document zahtevDocument = this.zahtevService.load(brojZahteva);
		
		if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
			if (ZahtevMapper.getStatusZahteva(zahtevDocument).equals(StatusZahteva.odobreno)) {
				throw new ResourceTakenException();
			}
			zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odobreno + "");
		}
		else {
			if (!ZahtevMapper.getStatusZahteva(zahtevDocument).equals(StatusZahteva.cekanje)) {
				throw new ResourceTakenException();
			}
			zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odbijeno + "");
		}

		this.odlukaExist.add(document);
		this.odlukaRDF.add(document);
		this.zahtevService.update(brojZahteva, zahtevDocument);
		this.zahtevRDF.update(brojZahteva, zahtevDocument);
		this.notifyOdluka(document);		
		if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
			this.zalbaService.otkazi(brojZahteva);
			this.soapService.sendSOAPMessage(this.domParser.buildDocument("<broj>" + brojZahteva + "</broj>"), SOAPActions.otkazi_zalbu);
		}
	}

	@Override
	public void update(String documentId, Document document) {
		this.odlukaExist.update(documentId, document);
		this.odlukaRDF.update(documentId, document);
	}

	@Override
	public void delete(String documentId) {
		this.odlukaExist.delete(documentId);
		this.odlukaRDF.delete(documentId);
	}
	
	@Override
	public Document load(String documentId) {
		return this.odlukaExist.load(documentId);
	}

	@Override
	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			xpathExp = "/odluka:Odluka";
		}
		else {
			xpathExp = String.format("/odluka:Odluka[@href='%s']", Namespaces.KORISNIK + "/" + korisnik.getMejl());
		}
		return this.odlukaMapper.map(this.odlukaExist.retrieve(xpathExp));
	}
	
	@Override
	public String regularSearch(String xml) {
		return null;
	}

	@Override
	public String advancedSearch(String xml) {
		return null;
	}
	
	private void notifyOdluka(Document document) {
		try {
			String brojOdluke = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
			String datumZahteva = Constants.sdf2.format(Constants.sdf.parse(document.getElementsByTagNameNS(Namespaces.ODLUKA, "datumZahteva").item(0).getTextContent()));
			String mejl = ((Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").item(0))
					.getAttribute("href").replace(Namespaces.KORISNIK + "/", "");
			String naziv = document.getElementsByTagNameNS(Namespaces.OSNOVA, "naziv").item(0).getTextContent();
			
			Email email = new Email();
			email.setTo(mejl);
			email.setSubject("Odgovor na zahtev za informacijama od javnog značaja");
			String text = "Poštovani/a, \n\n"
					+ "Odgovor/i na zahtev za informacijama od javnog značaja koji ste podneli dana " 
					+ datumZahteva + " nalaze se u linkovima ispod: \n"
					+ Constants.BACKEND_URL + "/api/odluke/" + brojOdluke + "/html\n"
					+ Constants.BACKEND_URL + "/api/odluke/" + brojOdluke + "/pdf\n\n"
					+ "Svako dobro, \n"
					+ naziv; 
			email.setText(text);
			this.emailService.sendEmail(email);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

}
