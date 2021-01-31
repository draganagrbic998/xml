package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.Constants;
import com.example.demo.parser.DOMParser;
import com.example.demo.ws.utils.SOAPActions;
import com.example.demo.ws.utils.SOAPService;

@RestController
@RequestMapping(value = "/api/odluke")
@PreAuthorize("isAuthenticated()")
public class OdlukaController {

	@Autowired
	private SOAPService soapService;
	
	@Autowired
	private DOMParser domParser;
	
	@GetMapping(value = "/{broj}")
	public ResponseEntity<Object> find(@PathVariable String broj, @RequestHeader("Accept") String format) throws IOException {
		if (format.equals("text/html")) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8")
					.body(this.soapService.sendSOAPMessage(
							this.domParser.buildDocument("<broj>" + broj + "</broj>"),
							SOAPActions.odluka_html));
		}
		else if (format.equals("application/pdf")) {
			String temp = this.soapService.sendSOAPMessage(this.domParser.buildDocument("<broj>" + broj + "</broj>"),
					SOAPActions.odluka_pdf);
			byte[] decodedString = Base64.getDecoder().decode(temp);
			Path file = Paths.get(Constants.GEN_FOLDER + "odluke" + File.separatorChar + broj + ".pdf");
			Files.write(file, decodedString);
			Resource resource = new UrlResource(file.toUri());
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, "application/pdf")
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		}
		return ResponseEntity.notFound().build();
	}

}
