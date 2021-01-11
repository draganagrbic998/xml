package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.enums.StatusZahteva;
import com.example.demo.enums.TipOdluke;
import com.example.demo.mapper.OdlukaMapper;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.OdlukaRDF;
import com.example.demo.repository.xml.OdlukaExist;
import com.example.demo.service.email.Email;
import com.example.demo.service.email.EmailService;

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
	private EmailService emailService;

	@Autowired
	private XSLTransformer xslTransformer;
	
	@Override
	public void add(String xml) {
		Document document = this.odlukaMapper.map(xml);
		String brojZahteva = document.getElementsByTagNameNS(Namespaces.ODLUKA, "brojZahteva").item(0).getTextContent();
		Document zahtevDocument = this.zahtevService.load(brojZahteva);
		if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
			zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odobreno + "");
		}
		else {
			zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odbijeno + "");
		}
		this.odlukaExist.add(document);
		this.odlukaRDF.add(this.xslTransformer.generateMetadata(document));
		this.zahtevService.update(brojZahteva, zahtevDocument);
		this.notifyOdluka(document);		
	}

	@Override
	public void update(String documentId, Document document) {
		this.odlukaExist.update(documentId, document);
	}

	@Override
	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			xpathExp = "/odluka:Odluka";
		}
		else {
			xpathExp = String.format("/odluka:Odluka[Gradjanin/Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}
		ResourceSet resources = this.odlukaExist.retrieve(xpathExp);
		return this.odlukaMapper.map(resources);
	}

	@Override
	public Document load(String documentId) {
		return this.odlukaExist.load(documentId);
	}
	
	private void notifyOdluka(Document document) {
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
			String datumZahteva = Constants.sdf2.format(Constants.sdf.parse(document.getElementsByTagNameNS(Namespaces.ODLUKA, "datumZahteva").item(0).getTextContent()));
			
			Email email = new Email();
			email.setTo(mejl);
			email.setSubject("Odgovor na zahtev za informacijama od javnog značaja");
			String text = "Poštovani/a " + ime + " " + prezime + ", \n\n"
					+ "Odgovor/i na zahtev za informacijama od javnog značaja koji ste podneli dana " 
					+ datumZahteva + " nalaze se u linkovima ispod: \n"
					+ Constants.BACKEND_URL + "/api/odluke/" + brojOdluke + "/html\n"
					+ Constants.BACKEND_URL + "/api/odluke/" + brojOdluke + "/pdf\n\n"
					+ "Svako dobro, \n"
					+ naziv + "\n" 
					+ sediste;
			email.setText(text);
			this.emailService.sendEmail(email);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

}
