package com.example.demo.service;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.example.demo.model.Korisnik;
import com.example.demo.parser.DOMParser;
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
	
}
