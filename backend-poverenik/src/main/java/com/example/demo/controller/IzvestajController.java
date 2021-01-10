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

import com.example.demo.enums.MetadataTip;
import com.example.demo.service.IzvestajService;

@RestController
@RequestMapping(value = "/api/izvestaji")
public class IzvestajController {

	@Autowired
	private IzvestajService izvestajService;

	@GetMapping(produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<String> list() {
		return new ResponseEntity<>(this.izvestajService.retrieve(), HttpStatus.OK);
	}

	@GetMapping(value = "/{broj}", produces = "text/html; charset=utf-8")
	public ResponseEntity<String> html(@PathVariable String broj) {
		return new ResponseEntity<>(this.izvestajService.generateHtml(broj), HttpStatus.OK);
	}

	@GetMapping(value = "/{broj}/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Object> pdf(@PathVariable String broj) {
		Resource resource = this.izvestajService.generatePdf(broj);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping(value = "/{broj}/metadata/xml", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Object> xmlMetadata(@PathVariable String broj) {
		Resource resource = this.izvestajService.generateMetadata(broj, MetadataTip.xml);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping(value = "/{broj}/metadata/json", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Object> jsonMetadata(@PathVariable String broj) {
		Resource resource = this.izvestajService.generateMetadata(broj, MetadataTip.json);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
