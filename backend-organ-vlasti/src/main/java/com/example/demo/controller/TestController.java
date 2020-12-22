package com.example.demo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.example.demo.constants.Constants;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;

@RestController
@RequestMapping(value = "/test")
public class TestController {

	@Autowired
	private XSLTransformer xslTransformer;
	
	@Autowired
	private DOMParser domParser;
	
	@GetMapping(value = "/test_zahtev")
	public ResponseEntity<String> testZahtev() throws ParserConfigurationException, SAXException, IOException, TransformerException{
		Document document = this.domParser.buildDocumentFromFile("data/test/zahtev1.xml");
		this.xslTransformer.testTransforming(document, Constants.XSL_FOLDER + "/zahtev.xsl", Constants.XSL_FOLDER + "/zahtev_fo.xsl", "data/gen/zahtev.html", "data/gen/zahtev.pdf");
		return new ResponseEntity<>("Success!", HttpStatus.OK);
	}
	
	@GetMapping(value = "/test_obavestenje")
	public ResponseEntity<String> testObavestenje() throws ParserConfigurationException, SAXException, IOException, TransformerException{
		Document document = this.domParser.buildDocumentFromFile("data/test/obavestenje1.xml");
		this.xslTransformer.testTransforming(document, Constants.XSL_FOLDER + "/obavestenje.xsl", Constants.XSL_FOLDER + "/obavestenje_fo.xsl", "data/gen/obavestenje.html", "data/gen/obavestenje.pdf");
		return new ResponseEntity<>("Success!", HttpStatus.OK);
	}
	
	@GetMapping(value = "/test_zalba_cutanje")
	public ResponseEntity<String> testZalbaCutanje() throws ParserConfigurationException, SAXException, IOException, TransformerException{
		Document document = this.domParser.buildDocumentFromFile("data/test/zalba_cutanje1.xml");
		this.xslTransformer.testTransforming(document, Constants.XSL_FOLDER + "/zalba_cutanje.xsl", Constants.XSL_FOLDER + "/zalba_cutanje_fo.xsl", "data/gen/zalba_cutanje.html", "data/gen/zalba_cutanje.pdf");
		return new ResponseEntity<>("Success!", HttpStatus.OK);
	}
	
	@GetMapping(value = "/test_zalba_odbijanje")
	public ResponseEntity<String> testZalbaOdbijanje() throws FileNotFoundException, TransformerException, IOException, SAXException, ParserConfigurationException{
		Document document = this.domParser.buildDocumentFromFile("data/test/zalba_odbijanje1.xml");
		this.xslTransformer.testTransforming(document, Constants.XSL_FOLDER + "/zalba_odbijanje.xsl", Constants.XSL_FOLDER + "/zalba_odbijanje_fo.xsl", "data/gen/zalba_odbijanje.html", "data/gen/zalba_odbijanje.pdf");
		return new ResponseEntity<>("Success!", HttpStatus.OK);
	}
	
}
