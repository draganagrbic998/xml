package com.example.demo.service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

import com.example.demo.common.EmailTakenException;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.security.TokenUtils;

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
		Random random = ThreadLocalRandom.current();
		byte[] bytes = new byte[256];
		random.nextBytes(bytes);
		korisnik.getOsoba().setPotpis(Base64.encodeBase64String(bytes));
		korisnik.setAktivan(true);
		korisnik.setLozinka(this.passwordEncoder.encode(korisnik.getLozinka()));
		this.korisnikExist.update(korisnik.getOsoba().getMejl(), this.jaxbParser.marshal(korisnik));			
	}
		
}
