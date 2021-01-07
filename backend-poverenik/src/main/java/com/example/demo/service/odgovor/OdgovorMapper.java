package com.example.demo.service.odgovor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.example.demo.exception.MyException;
import com.example.demo.parser.DOMParser;

@Component
public class OdgovorMapper {

	@Autowired
	private DOMParser domParser;
	
	public Document map(String xml) {
		try {
			return this.domParser.buildDocument(xml);
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
