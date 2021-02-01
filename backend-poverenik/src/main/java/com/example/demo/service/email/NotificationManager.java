package com.example.demo.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.exception.MyException;
import com.example.demo.model.Email;

@Component
public class NotificationManager {

	@Autowired
	private EmailService emailService;
	
	public void sendActivationEmail(String mejl, String potpis) {
		Email email = new Email();
		email.setTo(mejl);
		email.setSubject("Aktivacija naloga");
		String text = "Uspešno ste se registrovali na servis poverenika za informacije od javnog značaja"
				+ ". \nKlikon na link ispod možete aktivirati svoj nalog: \n"
				+ Constants.BACKEND_URL + "/auth/activate/" + potpis + "\n\n"
				+ "Svako dobro, \n"
				+ "Poverenik za informacije od javnog značaja i zaštitu podataka o ličnosti";
		email.setText(text);
		this.emailService.sendEmail(email);
	}
	
	public void notifyResenje(Document document, byte[] html, byte[] pdf) {
		try {
			String mejl = ((Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "Resenje").item(0))
					.getAttribute("href").replace(Namespaces.KORISNIK + "/", "");
			String naziv = document.getElementsByTagNameNS(Namespaces.OSNOVA, "naziv").item(0).getTextContent();
			String datum = Constants.sdf2.format(Constants.sdf.parse(document.getElementsByTagNameNS(Namespaces.RESENJE, "datumZalbe").item(0).getTextContent()));
			
			Email email = new Email();
			email.setTo(mejl);
			email.setSubject("Rešenje na žalbu za uskraćeno pravo na pristup informacijama od javnog značaja");
			String text = "Poštovani/a, \n\n"
					+ "Rešenje na žalbu koju ste podneli protiv organa vlasti " + naziv
					+ " dana " + datum + " nalazi se u prilozima ispod. \n\n"
					+ "Svako dobro, \n"
					+ "Poverenik za informacije od javnog značaja i zaštitu podataka o ličnosti";
			email.setText(text);
			this.emailService.sendEmail(email, html, pdf);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

}
