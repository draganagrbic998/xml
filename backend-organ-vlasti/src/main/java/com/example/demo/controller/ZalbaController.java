package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.MetadataType;
import com.example.demo.service.ZalbaService;
import com.example.demo.transformer.ZalbaTransformer;

@RestController
@RequestMapping(value = "/api/zalbe")
@PreAuthorize("hasAuthority('sluzbenik')")
public class ZalbaController {

	@Autowired
	private ZalbaService zalbaService;

	@Autowired
	private ZalbaTransformer zalbaTransformer;

	@GetMapping(produces = "text/xml; charset=utf-8")
	public ResponseEntity<String> findAll() {
		return new ResponseEntity<>(this.zalbaService.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/{broj}")
	public ResponseEntity<Object> find(@PathVariable String broj, @RequestHeader("Accept") String format) {
		if (format.equals("text/html")) {
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8")
					.body(this.zalbaTransformer.html(broj));
		}
		Resource resource = this.zalbaTransformer.pdf(broj);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/pdf")
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping(value = "/{broj}/metadata")
	public ResponseEntity<String> metadata(@PathVariable String broj, @RequestHeader("Accept") String format) {
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, format + "; charset=utf-8").body(
				this.zalbaTransformer.metadata(broj, format.equals("text/xml") ? MetadataType.xml : MetadataType.json));
	}

	@PostMapping(value = "obicna_pretraga", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<String> regularSearch(@RequestBody String xml) {
		return new ResponseEntity<>(this.zalbaService.regularSearch(xml), HttpStatus.OK);
	}

	@PostMapping(value = "napredna_pretraga", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<String> advancedSearch(@RequestBody String xml) {
		return new ResponseEntity<>(this.zalbaService.advancedSearch(xml), HttpStatus.OK);
	}

}
