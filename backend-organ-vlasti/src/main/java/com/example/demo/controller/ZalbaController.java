package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ZalbaService;
import com.example.demo.transformer.ZalbaTransformer;

@RestController
@RequestMapping(value = "/api/zalbe")
public class ZalbaController {

	@Autowired
	private ZalbaService zalbaService;
	
	@Autowired
	private ZalbaTransformer zalbaTransformer;

	@GetMapping(produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<String> retrieve() {
		return new ResponseEntity<>(this.zalbaService.retrieve(), HttpStatus.OK);
	}

	@GetMapping(value = "/{broj}", produces = "text/html; charset=utf-8")
	public ResponseEntity<String> html(@PathVariable String broj) {
		return new ResponseEntity<>(this.zalbaTransformer.html(broj), HttpStatus.OK);
	}

	@GetMapping(value = "/{broj}/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Object> generatePdf(@PathVariable String broj) {
		Resource resource = this.zalbaTransformer.generatePdf(broj);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
