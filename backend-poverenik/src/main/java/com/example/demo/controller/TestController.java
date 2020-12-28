package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.example.demo.constants.Constants;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;

@RestController
@RequestMapping(value = "/test")
public class TestController {
	
	@Autowired
	private XSLTransformer xsl;
	
	private static final String XSL_PATH = Constants.XSL_FOLDER + "/resenje.xsl";
	private static final String XSL_FO_PATH = Constants.XSL_FOLDER + "/resenje_fo.xsl";
	
	@Autowired
	private DOMParser domParser;

	@GetMapping
	public void test() throws IOException, TransformerException, ParserConfigurationException, SAXException {
		
		FileOutputStream fout = new FileOutputStream(new File("data/test/resenje.html"));
		fout.write(this.xsl.generateHtml(this.domParser.buildDocumentFromFile("data/test/test.xml"), XSL_PATH).toByteArray());
		fout.close();
		
		FileOutputStream fout2 = new FileOutputStream(new File("data/test/resenje.pdf"));
		fout2.write(this.xsl.generatePdf(this.domParser.buildDocumentFromFile("data/test/test.xml"), XSL_FO_PATH).toByteArray());
		fout2.close();

	}
	
}
