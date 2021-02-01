package com.example.demo.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.exception.MyException;
import com.example.demo.model.Email;
import com.example.demo.service.OrganVlastiService;

@Component
public class NotificationManager {

	@Autowired
	private OrganVlastiService organVlastiService;
	
	@Autowired
	private EmailService emailService;
	
	public void sendActivationEmail(String mejl, String potpis) {
		Document document = this.organVlastiService.load();
		String naziv = document.getElementsByTagNameNS(Namespaces.OSNOVA, "naziv").item(0).getTextContent();
		String sediste = document.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0).getTextContent();
		
		Email email = new Email();
		email.setTo(mejl);
		email.setSubject("Aktivacija naloga");
		String text = "Uspešno ste se registrovali na portal organa vlasti " + naziv
				+ ". \nKlikon na link ispod možete aktivirati svoj nalog: \n"
				+ Constants.BACKEND_URL + "/auth/activate/" + potpis + "\n\n"
				+ "Svako dobro, \n"
				+ naziv + "\n" 
				+ sediste;
		email.setText(text);
		this.emailService.sendEmail(email);
	}
	
	public void notifyOdluka(Document document, byte[] html, byte[] pdf) {
		try {
			String mejl = ((Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").item(0))
					.getAttribute("href").replace(Namespaces.KORISNIK + "/", "");
			String naziv = document.getElementsByTagNameNS(Namespaces.OSNOVA, "naziv").item(0).getTextContent();
			String datum = Constants.sdf2.format(Constants.sdf.parse(document.getElementsByTagNameNS(Namespaces.ODLUKA, "datumZahteva").item(0).getTextContent()));
			
			Email email = new Email();
			email.setTo(mejl);
			email.setSubject("Odgovor na zahtev za informacijama od javnog značaja");
			String text = "Poštovani/a, \n\n"
					+ "Odgovor/i na zahtev za informacijama od javnog značaja koji ste podneli dana " 
					+ datum + " nalaze se u prilozima. \n\n"
					+ "Svako dobro, \n"
					+ naziv; 
			email.setText(text);
			this.emailService.sendEmail(email, html, pdf);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	public void notifyCutanjeUprave(Document document) {
		try {
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
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
