package com.example.demo.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import com.example.demo.dom.DOMParser;
import com.example.demo.model.Korisnik;

@Component
public class KorisnikParser implements Parser<Korisnik> {

	@Autowired
	private DOMParser domParser;

	@Override
	public Korisnik parse(Node node) {
		
		return null;
	}

	
	
}
