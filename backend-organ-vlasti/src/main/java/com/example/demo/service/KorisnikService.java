package com.example.demo.service;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Constants;
import com.example.demo.common.EmailTakenException;
import com.example.demo.common.MyException;
import com.example.demo.common.Namespaces;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.email.Email;
import com.example.demo.service.email.EmailService;

@Service
public class KorisnikService implements UserDetailsService {

	@Autowired
	private KorisnikExist korisnikExist;
	
	@Autowired
	private DOMParser domParser;

	@Autowired
	private JAXBParser jaxbParser;

	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private AuthenticationManager authManager;
		
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private OrganVlastiService organVlastiService;
	
	@Autowired
	private EmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String username) {
		try {
			return (Korisnik) this.jaxbParser.unmarshal(this.korisnikExist.load(username), Korisnik.class);
		}
		catch(Exception e) {
			return null;
		}
	}
		
	public Korisnik currentUser() {
		try {
			return (Korisnik) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception e) {
			return null;
		}
	}
		
	public String login(String xml) {
		Document loginDocument = this.domParser.buildDocument(xml);
		String email = loginDocument.getElementsByTagName("mejl").item(0).getTextContent();
		String password = loginDocument.getElementsByTagName("lozinka").item(0).getTextContent();
		this.authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		Korisnik korisnik = (Korisnik) this.loadUserByUsername(email);
		Document profilDocument = this.domParser.emptyDocument();
		Node profil = profilDocument.createElement("Profil");
		profilDocument.appendChild(profil);
		
		Node token = profilDocument.createElement("token");
		token.setTextContent(this.tokenUtils.generateToken(email));
		profil.appendChild(token);
		Node uloga = profilDocument.createElement("uloga");
		uloga.setTextContent(korisnik.getUloga());
		profil.appendChild(uloga);
		Node mejl = profilDocument.createElement("mejl");
		mejl.setTextContent(korisnik.getOsoba().getMejl());
		profil.appendChild(mejl);
		Node ime = profilDocument.createElement("ime");
		ime.setTextContent(korisnik.getOsoba().getIme());
		profil.appendChild(ime);
		Node prezime = profilDocument.createElement("prezime");
		prezime.setTextContent(korisnik.getOsoba().getPrezime());
		profil.appendChild(prezime);
		return this.domParser.buildXml(profilDocument);
	}
	
	public void register(String xml) {
		Korisnik korisnik = (Korisnik) this.jaxbParser.unmarshal(this.domParser.buildDocument(xml), Korisnik.class);
		if (this.loadUserByUsername(korisnik.getOsoba().getMejl()) != null) {
			throw new EmailTakenException();
		}
		korisnik.getOsoba().setPotpis(this.generatePotpis());
		korisnik.setAktivan(false);
		korisnik.setLozinka(this.passwordEncoder.encode(korisnik.getLozinka()));
		this.korisnikExist.update(korisnik.getOsoba().getMejl(), this.jaxbParser.marshal(korisnik));			
		this.sendActivationEmail(korisnik.getOsoba().getMejl(), korisnik.getOsoba().getPotpis());
	}
	
	public void activate(String potpis) {
		try {
			String xpathExp = String.format("/Korisnik[Osoba/potpis='%s']", potpis);
			XMLResource resource = (XMLResource) this.korisnikExist.retrieve(xpathExp).getIterator().nextResource();
			Korisnik korisnik = (Korisnik) this.jaxbParser.unmarshal(this.domParser.buildDocument(resource.getContent().toString()), Korisnik.class);
			korisnik.setAktivan(true);
			this.korisnikExist.update(korisnik.getOsoba().getMejl(), this.jaxbParser.marshal(korisnik));
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
	private String generatePotpis() {
        UUID uuid = UUID.randomUUID();
        byte[] bytes = ByteBuffer.wrap(new byte[256])
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits())
                .array();
        return Base64.encodeBase64String(bytes);
	}
	
	private void sendActivationEmail(String mejl, String potpis) {
		Document organVlasti = this.organVlastiService.load();
		String naziv = organVlasti.getElementsByTagNameNS(Namespaces.OSNOVA, "naziv").item(0).getTextContent();
		String mesto = organVlasti.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0).getTextContent();
		String ulica = organVlasti.getElementsByTagNameNS(Namespaces.OSNOVA, "ulica").item(0).getTextContent();
		String broj = organVlasti.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		String sediste = ulica + " " + broj + ", " + mesto;
		
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
		
}
