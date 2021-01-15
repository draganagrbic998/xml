package com.example.demo.parser;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;

import org.springframework.stereotype.Component;

import com.example.demo.exception.MyException;

@Component
public class JAXBParser {

	public Object unmarshal(String xml, Class<?> cl) {
		try {
			return JAXBContext.newInstance(cl).createUnmarshaller().unmarshal(new StringReader(xml));
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}

}
