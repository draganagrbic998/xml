package com.example.demo.service;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

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
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.constants.Constants;
import com.example.demo.exception.EmailTakenException;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.security.TokenUtils;

@Service
public class KorisnikService implements UserDetailsService {

	@Autowired
	private KorisnikExist korisnikRepository;
	
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
			return this.load(username);
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
	
	public void save(Korisnik korisnik) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException, TransformerException {
		this.korisnikRepository.save(korisnik.getOsoba().getMejl(), this.jaxbParser.marshal(korisnik));
	}
	
	public Korisnik load(String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, JAXBException, XMLDBException {
		return (Korisnik) this.jaxbParser.unmarshal(this.korisnikRepository.load(documentId), Korisnik.class);
	}
	
	public String login(String xml) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, JAXBException, XMLDBException, TransformerException {
		Document prijavaDocument = this.domParser.buildDocument(xml);
		String email = prijavaDocument.getElementsByTagName("mejl").item(0).getTextContent();
		String password = prijavaDocument.getElementsByTagName("lozinka").item(0).getTextContent();
		this.authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		Korisnik korisnik = this.load(email);
		Document profilDocument = this.domParser.emptyDocument();
		Node profil = profilDocument.createElement("Profil");
		profilDocument.appendChild(profil);
		Node token = profilDocument.createElement("token");
		token.setTextContent(this.tokenUtils.generateToken(email));
		profil.appendChild(token);
		Node uloga = profilDocument.createElement("uloga");
		uloga.setTextContent(korisnik.getUloga());
		profil.appendChild(uloga);
		Node ime = profilDocument.createElement("ime");
		ime.setTextContent(korisnik.getOsoba().getIme());
		profil.appendChild(ime);
		Node prezime = profilDocument.createElement("prezime");
		prezime.setTextContent(korisnik.getOsoba().getPrezime());
		profil.appendChild(prezime);
		return this.domParser.buildXml(profilDocument);
	}
	
	public void register(String xml) throws JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, ParserConfigurationException, SAXException, IOException, TransformerException {
		Korisnik korisnik = (Korisnik) this.jaxbParser.unmarshal(this.domParser.buildDocument(xml), Korisnik.class);
		if (this.loadUserByUsername(korisnik.getOsoba().getMejl()) != null) {
			throw new EmailTakenException();
		}
		korisnik.getOsoba().setPotpis(Constants.TEST_POTPIS);
		korisnik.setAktivan(true);
		korisnik.setLozinka(this.passwordEncoder.encode(korisnik.getLozinka()));
		this.save(korisnik);
	}
		
}
