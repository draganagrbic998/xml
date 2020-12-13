package com.example.demo.service;

import java.util.Date;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.model.Zalba;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.ZalbaRepository;

@Service
public class ZalbaService {
	
	@Autowired
	private ZalbaRepository zalbaRepository;
	
	@Autowired
	private JAXBParser jaxbParser;
		
	public void save(String xml) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, JAXBException {		
		Zalba zalba = (Zalba) this.jaxbParser.unmarshal(xml, Zalba.class);
		zalba.setDatum(new Date());
		zalba.setPotpis("");
		zalba.setOdgovoreno(false);
		this.zalbaRepository.save(this.jaxbParser.marshal(zalba, Zalba.class));
	}
	
}
