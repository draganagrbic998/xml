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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.exception.EmailTakenException;
import com.example.demo.exception.MyException;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Prijava;
import com.example.demo.model.Profil;
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
	private TokenUtils tokenUtils;
	
	@Autowired
	private AuthenticationManager authManager;
		
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private OrganVlastiService organVlastiService;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private DOMParser domParser;

	@Override
	public UserDetails loadUserByUsername(String username) {
		try {
			return (Korisnik) this.jaxbParser.unmarshalFromDoc(this.korisnikExist.load(username), Korisnik.class);
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
		Prijava prijava = (Prijava) this.jaxbParser.unmarshalFromXml(xml, Prijava.class);
		this.authManager.authenticate(new UsernamePasswordAuthenticationToken(prijava.getMejl(), prijava.getLozinka()));
		Korisnik korisnik = (Korisnik) this.loadUserByUsername(prijava.getMejl());
		return this.jaxbParser.marshalToXml(new Profil(
			this.tokenUtils.generateToken(korisnik.getMejl()),
			korisnik.getUloga(),
			korisnik.getMejl(),
			korisnik.getOsoba().getIme(),
			korisnik.getOsoba().getPrezime()
		));
	}
	
	public void register(String xml) {
		Korisnik korisnik = (Korisnik) this.jaxbParser.unmarshalFromXml(xml, Korisnik.class);
		if (this.loadUserByUsername(korisnik.getMejl()) != null) {
			throw new EmailTakenException();
		}
		korisnik.getOsoba().setPotpis(this.generatePotpis());
		korisnik.setAktivan(false);
		korisnik.setLozinka(this.passwordEncoder.encode(korisnik.getLozinka()));
		Document document = this.jaxbParser.marshalToDoc(korisnik);
		Node mesto = (Element) document.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0);
		Node updatedMesto = document.importNode((Element) this.domParser.buildDocument("<mesto property=\"pred:mesto\" datatype=\"xs:string\">" + mesto.getTextContent() + "</mesto>")
				.getElementsByTagName("mesto").item(0), true);
		document.getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0).replaceChild(updatedMesto, mesto);
		document.renameNode(updatedMesto, Namespaces.OSNOVA, "mesto");
		this.korisnikExist.update(korisnik.getMejl(), document);			
		this.sendActivationEmail(korisnik.getMejl(), korisnik.getOsoba().getPotpis());
	}
	
	public void activate(String potpis) {
		try {
			String xpathExp = String.format("/Korisnik[Osoba/potpis='%s']", potpis);
			XMLResource resource = (XMLResource) this.korisnikExist.retrieve(xpathExp).getIterator().nextResource();
			Korisnik korisnik = (Korisnik) this.jaxbParser.unmarshalFromXml(resource.getContent().toString(), Korisnik.class);
			korisnik.setAktivan(true);
			this.korisnikExist.update(korisnik.getMejl(), this.jaxbParser.marshalToDoc(korisnik));
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
		
}
