package com.example.demo.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.common.Constants;
import com.example.demo.common.TestFiles;
import com.example.demo.database.AuthenticationUtilites;
import com.example.demo.database.ExistManager;

@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

	@Autowired
	private AuthenticationUtilites authUtilities;
	
	@Autowired
	private ExistManager existManager;
	
	@GetMapping(value = "/test1")
	public void test1() {
		System.out.println(this.authUtilities.getUser());
		System.out.println(this.authUtilities.getPassword());
		System.out.println(this.authUtilities.getHost());
		System.out.println(this.authUtilities.getPort());
		System.out.println(this.authUtilities.getDriver());
		System.out.println(this.authUtilities.getUri());
	}
	
	@GetMapping(value = "/test2")
	public void test2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		
		File file = new File(TestFiles.ORGAN_VLASTI);
		this.existManager.saveFile(Constants.COLLECTIONS_PREFIX + "/test_documents", "organ_vlasti1.xml", file);
		
	}
	
	@GetMapping(value = "/test3")
	public void test3() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		
		XMLResource xml = this.existManager.load(Constants.COLLECTIONS_PREFIX + "/test_documents", "organ_vlasti1.xml");
		System.out.println(xml.getContent());
		
	}
	
}
