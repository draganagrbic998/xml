package com.example.demo.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.demo.constants.Constants;
import com.example.demo.database.ExistManager;

@RestController
@RequestMapping(value = "/test")
public class TestController {
	
	@Autowired
	private ExistManager existManager;
	
	@GetMapping(value = "/test1")
	public void test1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		File file = new File("data/test1.xml");
		this.existManager.save(Constants.COLLECTIONS_PREFIX + "/test", "test1.xml", file);
	}
	
	@GetMapping(value = "/test2")
	public void test2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		XMLResource resource = this.existManager.load(Constants.COLLECTIONS_PREFIX + "/test", "test1.xml");
		System.out.println(resource.getContent());
	}

}
