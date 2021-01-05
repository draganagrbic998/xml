package com.example.demo.service;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.OrganVlasti;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.OrganVlastiExist;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

@Service
public class OrganVlastiService {

	@Autowired
	private OrganVlastiExist organVlastiRepository;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	public OrganVlasti load() throws ClassNotFoundException, InstantiationException, IllegalAccessException, JAXBException, XMLDBException {
		return (OrganVlasti) this.jaxbParser.unmarshal(this.organVlastiRepository.load(), OrganVlasti.class);
	}
	
}
