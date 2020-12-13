package com.example.demo.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.constants.Constants;
import com.example.demo.database.ExistManager;

@RestController
@RequestMapping(value = "/test")
public class TestController {
	
	@Autowired
	private ExistManager existManager;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@GetMapping(value = "/test3")
	public void test3() {
		String email = "asd";
		String lozinka = "asd";
		try {
			this.authManager.authenticate(new UsernamePasswordAuthenticationToken(email, lozinka));
			System.out.println("Authentikacija prosla");
		}
		catch(Exception e) {
			System.out.println("Authentikacija pala");
		}
		
	}
	
	@GetMapping(value = "/test4")
	public void test4() {
		String email = "asd";
		String lozinka = "asd2";
		try {
			this.authManager.authenticate(new UsernamePasswordAuthenticationToken(email, lozinka));
			System.out.println("Authentikacija prosla");
		}
		catch(Exception e) {
			System.out.println("Authentikacija pala");
		}
		
	}
	
	@GetMapping(value = "/test5")
	public void test5() {
		String email = "asd2";
		String lozinka = "asd";
		try {
			this.authManager.authenticate(new UsernamePasswordAuthenticationToken(email, lozinka));
			System.out.println("Authentikacija prosla");
		}
		catch(Exception e) {
			System.out.println("Authentikacija pala");
		}
		
	}
	
	@GetMapping(value = "/test1")
	public void test1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		File file = new File("data/korisnik1.xml");
		this.existManager.saveFile(Constants.COLLECTIONS_PREFIX + "/korisnici", "asd.xml", file);
	}
	
	@GetMapping(value = "/test2")
	public void test2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		XMLResource resource = this.existManager.load(Constants.COLLECTIONS_PREFIX + "/test", "test1.xml");
		System.out.println(resource.getContent());
	}


}
