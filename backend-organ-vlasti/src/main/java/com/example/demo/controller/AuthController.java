package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.Constants;
import com.example.demo.service.KorisnikService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
			
	@Autowired
	private KorisnikService korisnikService;

	@PostMapping(value = "/login", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<String> login(@RequestBody String xml) {
		return new ResponseEntity<>(this.korisnikService.login(xml), HttpStatus.OK);
	}
	
	@PostMapping(value = "/register", consumes = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<Void> register(@RequestBody String xml) {
		this.korisnikService.register(xml);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/activate/{potpis}", produces = "text/html; charset=utf-8")
	public ResponseEntity<String> activate(@PathVariable String potpis){
		String html;
		try {
			this.korisnikService.activate(potpis);
			html = "<div style='height: 100%; width: 100%; display: flex;"
					+ "flex-direction: column; justify-content: center; align-items: center;"
					+ "font-weight: bold; font-size: 24px;'><h1>NALOG USPEŠNO AKTIVIRAN</h1><br>"
					+ "Kliknite na link ispod da biste se prijavili:<br>"
					+ "<a href='" + Constants.FRONTEND_URL + "'>PRIJAVI SE</a>" + "</div>";
		}
		catch(Exception e) {
			html = "<div style='height: 100%; width: 100%; display: flex;"
					+ "flex-direction: column; justify-content: center; align-items: center;"
					+ "font-weight: bold; font-size: 24px;'><h1>GREŠKA</h1><br>"
					+ "Kontaktirajte server za više informacija.";
		}
		return new ResponseEntity<>(html, HttpStatus.OK);
	}

}
