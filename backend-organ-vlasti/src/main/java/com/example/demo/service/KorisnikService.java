package com.example.demo.service;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

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
	private TokenUtils tokenUtils;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) {
		return this.korisnikRepository.findByEmail(username);
	}
		
	public Korisnik currentUser() {
		try {
			return (Korisnik) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public String login(String xml) throws ParserConfigurationException, SAXException, IOException {
		Document document = this.domParser.buildDocument(xml);
		String email = document.getElementsByTagName("email").item(0).getTextContent();
		String password = document.getElementsByTagName("password").item(0).getTextContent();
		this.authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		return this.tokenUtils.generateToken(email);
	}
	
	public void register(String xml) throws JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		Korisnik korisnik = (Korisnik) this.jaxbParser.unmarshal(xml, Korisnik.class);
		if (this.korisnikRepository.findByEmail(korisnik.getEmail()) != null) {
			throw new EmailTakenException();
		}
		korisnik.setLozinka(this.passwordEncoder.encode(korisnik.getLozinka()));
		this.korisnikRepository.save(korisnik);
	}
	
}
