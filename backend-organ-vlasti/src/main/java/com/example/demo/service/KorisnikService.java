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
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.controller.TokenDTO;
import com.example.demo.exception.EmailTakenException;
import com.example.demo.model.Korisnik;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.security.TokenUtils;

@Service
public class KorisnikService implements UserDetailsService {

	@Autowired
	private KorisnikRepository korisnikRepository;
	
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
		this.korisnikRepository.save(this.jaxbParser.marshal(korisnik), korisnik.getOsoba().getMejl());
	}
	
	public Korisnik load(String documentId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, JAXBException, XMLDBException {
		return (Korisnik) this.jaxbParser.unmarshal(this.korisnikRepository.load(documentId), Korisnik.class);
	}
	
	public TokenDTO login(String xml) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, JAXBException, XMLDBException {
		Document document = this.domParser.buildDocument(xml);
		String email = document.getElementsByTagName("mejl").item(0).getTextContent();
		String password = document.getElementsByTagName("lozinka").item(0).getTextContent();
		this.authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		return new TokenDTO(this.tokenUtils.generateToken(email), this.load(email).getUloga());
	}
	
	public void register(String xml) throws JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, ParserConfigurationException, SAXException, IOException, TransformerException {
		Korisnik korisnik = (Korisnik) this.jaxbParser.unmarshal(this.domParser.buildDocument(xml), Korisnik.class);
		if (this.loadUserByUsername(korisnik.getOsoba().getMejl()) != null) {
			throw new EmailTakenException();
		}
		korisnik.setAktivan(true);
		korisnik.setLozinka(this.passwordEncoder.encode(korisnik.getLozinka()));
		this.save(korisnik);
	}
		
}
