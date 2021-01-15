package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.mapper.ResenjeMapper;
import com.example.demo.model.Korisnik;
import com.example.demo.repository.rdf.ResenjeRDF;
import com.example.demo.repository.xml.ResenjeExist;
import com.example.demo.service.email.Email;
import com.example.demo.service.email.EmailService;
import com.example.demo.ws.utils.SOAPActions;
import com.example.demo.ws.utils.SOAPService;

@Service
public class ResenjeService implements ServiceInterface {
	
	@Autowired
	private ResenjeExist resenjeExist;
	
	@Autowired
	private ResenjeRDF resenjeRDF;

	@Autowired
	private ResenjeMapper resenjeMapper;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private ZalbaService zalbaService;
			
	@Autowired
	private EmailService emailService;

	@Autowired
	private SOAPService soapService;

	@Override
	public void add(String xml) {
		Document document = this.resenjeMapper.map(xml);
		this.resenjeExist.add(document);
		this.resenjeRDF.add(document);
		String brojZalbe = document.getElementsByTagNameNS(Namespaces.RESENJE, "brojZalbe").item(0).getTextContent();
		Document zalbaDocument = this.zalbaService.load(brojZalbe);
		Element zalba = (Element) zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
		
		zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.reseno + "");
		Element podaciZahteva = (Element) zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0);
		podaciZahteva.removeChild(podaciZahteva.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		try {
			Element podaciOdluke = (Element) zalba.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0);
			podaciOdluke.removeChild(podaciOdluke.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		}
		catch(Exception e) {
			;
		}
		this.zalbaService.update(brojZalbe, zalbaDocument);
		
		this.soapService.sendSOAPMessage(document, SOAPActions.create_resenje);
		this.notifyResenje(document);
	}

	@Override
	public void update(String documentId, Document document) {
		this.resenjeExist.update(documentId, document);
		this.resenjeRDF.update(documentId, document);
	}
	
	@Override
	public void delete(String documentId) {
		this.resenjeExist.delete(documentId);
		this.resenjeRDF.delete(documentId);
	}

	@Override
	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp = null;
		if (korisnik.getUloga().equals(Constants.POVERENIK)) {
			xpathExp = "/resenje:Resenje";
		}
		else {
			xpathExp = String.format("/resenje:Resenje[Osoba/mejl='%s']", korisnik.getOsoba().getMejl());
		}		
		ResourceSet resouces = this.resenjeExist.retrieve(xpathExp);
		return this.resenjeMapper.map(resouces);		
	}

	@Override
	public Document load(String documentId) {
		return this.resenjeExist.load(documentId);
	}
	
	private void notifyResenje(Document document) {
		try {
			String mejl = document.getElementsByTagNameNS(Namespaces.OSNOVA, "mejl").item(0).getTextContent();
			String naziv = document.getElementsByTagNameNS(Namespaces.OSNOVA, "naziv").item(0).getTextContent();
			String brojResenja = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
			String datumZalbe = Constants.sdf2.format(Constants.sdf.parse(document.getElementsByTagNameNS(Namespaces.RESENJE, "datumZalbe").item(0).getTextContent()));
			
			Email email = new Email();
			email.setTo(mejl);
			email.setSubject("Rešenje na žalbu za uskraćeno pravo na pristup informacijama od javnog značaja");
			String text = "Poštovani/a, \n\n"
					+ "Rešenje na žalbu koju ste podneli protiv organa vlasti " + naziv
					+ " dana " + datumZalbe + " nalazi se u linkovima ispod: \n"
					+ Constants.BACKEND_URL + "/api/resenja/" + brojResenja + "/html\n"
					+ Constants.BACKEND_URL + "/api/resenja/" + brojResenja + "/pdf\n\n"
					+ "Svako dobro, \n"
					+ "Poverenik za informacije od javnog značaja i zaštitu podataka o ličnosti";
			email.setText(text);
			this.emailService.sendEmail(email);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

	@Override
	public String advancedSearch(String xml) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
