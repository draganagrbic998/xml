package com.example.demo.controller;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.common.MyException;
import com.example.demo.parser.DOMParser;
import com.example.demo.ws.utils.SOAPDocument;
import com.example.demo.ws.utils.SOAPService;

@RestController
@RequestMapping(value = "/api/zahtevi")
public class ZahtevController {
	
	@Autowired
	private SOAPService soapService;
	
	@Autowired
	private DOMParser domParser;

	@GetMapping(value = "/{broj}", produces = "text/html; charset=utf-8")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> html(@PathVariable String broj) {
		Document document = this.domParser.buildDocument(String.format("<pregled><broj>%s</broj><tip>html</tip></pregled>", broj));
		return new ResponseEntity<>(this.soapService.sendSOAPMessage(document, SOAPDocument.zahtev_view), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{broj}/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> generatePdf(@PathVariable String broj) {
		try {
			Document document = this.domParser.buildDocument(String.format("<pregled><broj>%s</broj><tip>pdf</tip></pregled>", broj));
			String plainPdf = this.domParser.buildDocument(this.soapService.sendSOAPMessage(document, SOAPDocument.zahtev_view)).getElementsByTagName("pdf").item(0).getTextContent();
			
			Path file = Paths.get(Constants.GEN_FOLDER + "zahtevi" + broj + ".pdf");
			Files.write(file, plainPdf.getBytes(StandardCharsets.UTF_8));
			Resource resource = new UrlResource(file.toUri());
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
