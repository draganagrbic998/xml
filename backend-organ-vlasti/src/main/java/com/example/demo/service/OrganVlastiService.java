package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.OrganVlasti;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.xml.OrganVlastiExist;

import org.springframework.stereotype.Service;

@Service
public class OrganVlastiService {

	@Autowired
	private OrganVlastiExist organVlastiExist;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	public OrganVlasti load() {
		return (OrganVlasti) this.jaxbParser.unmarshal(this.organVlastiExist.load(), OrganVlasti.class);
	}
	
}
