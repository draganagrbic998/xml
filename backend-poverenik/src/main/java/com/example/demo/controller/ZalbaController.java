package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.zalba.ZalbaService;

@RestController
@RequestMapping(value = "/api/zalbe")
public class ZalbaController {

	@Autowired
	private ZalbaService zalbaService;

	@PostMapping(consumes = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<Void> save(@RequestBody String xml) {
		this.zalbaService.save(xml);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping(produces = MediaType.TEXT_XML_VALUE)
	public ResponseEntity<String> list() {
		return new ResponseEntity<>(this.zalbaService.retrieve(), HttpStatus.OK);
	}

	@GetMapping(value = "/{broj}", produces = "text/html; charset=utf-8")
	public ResponseEntity<String> html(@PathVariable String broj) {
		return new ResponseEntity<>(this.zalbaService.generateHtml(broj), HttpStatus.OK);
	}

	@GetMapping(value = "/{broj}/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Object> pdf(@PathVariable String broj) {
		Resource resource = this.zalbaService.generatePdf(broj);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@PostMapping(value = "/prosledi/{broj}")
	public ResponseEntity<Void> prosledi(@PathVariable String broj) {
		this.zalbaService.prosledi(broj);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/odustani/{broj}")
	public ResponseEntity<Void> odustani(@PathVariable String broj){
		this.zalbaService.odustani(broj);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/obustavi/{broj}")
	public ResponseEntity<Void> obustavi(@PathVariable String broj) {
		this.zalbaService.obustavi(broj);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
