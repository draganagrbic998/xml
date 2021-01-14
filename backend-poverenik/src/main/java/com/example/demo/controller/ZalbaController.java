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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.MetadataTip;
import com.example.demo.service.ZalbaService;
import com.example.demo.transformer.ZalbaTransformer;

@RestController
@RequestMapping(value = "/api/zalbe")
public class ZalbaController {

	@Autowired
	private ZalbaService zalbaService;
	
	@Autowired
	private ZalbaTransformer zalbaTransformer;
			
	@PostMapping(consumes = MediaType.TEXT_XML_VALUE)
	@PreAuthorize("hasAuthority('gradjanin')")
	public ResponseEntity<Void> add(@RequestBody String xml) {		
		this.zalbaService.add(xml);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(produces = MediaType.TEXT_XML_VALUE)
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> retrieve() {
		return new ResponseEntity<>(this.zalbaService.retrieve(), HttpStatus.OK);
	}
	
	@PostMapping(value = "advanced_search", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
	@PreAuthorize("hasAuthority('poverenik')")
	public ResponseEntity<String> advancedSearch(@RequestBody String xml) {
		return new ResponseEntity<>(this.zalbaService.advancedSearch(xml), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{broj}", produces = "text/html; charset=utf-8")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> html(@PathVariable String broj) {
		return new ResponseEntity<>(this.zalbaTransformer.html(broj), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{broj}/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> generatePdf(@PathVariable String broj) {
		Resource resource = this.zalbaTransformer.generatePdf(broj);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping(value = "/{broj}/metadata/xml", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> xmlMetadata(@PathVariable String broj) {
		Resource resource = this.zalbaTransformer.generateMetadata(broj, MetadataTip.xml);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping(value = "/{broj}/metadata/json", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> jsonMetadata(@PathVariable String broj) {
		Resource resource = this.zalbaTransformer.generateMetadata(broj, MetadataTip.json);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@PostMapping(value = "/odustani/{broj}")
	@PreAuthorize("hasAuthority('gradjanin')")
	public ResponseEntity<Void> odustani(@PathVariable String broj){
		this.zalbaService.odustani(broj);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/obustavi/{broj}")
	@PreAuthorize("hasAuthority('poverenik')")
	public ResponseEntity<Void> obustavi(@PathVariable String broj) {
		this.zalbaService.obustavi(broj);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/prosledi/{broj}")
	@PreAuthorize("hasAuthority('poverenik')")
	public ResponseEntity<Void> prosledi(@PathVariable String broj) {
		this.zalbaService.prosledi(broj);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
