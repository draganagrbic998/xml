package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.xml.OrganVlastiExist;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class OrganVlastiService {

	@Autowired
	private OrganVlastiExist organVlastiExist;
		
	public Document load() {
		return this.organVlastiExist.load("1");
	}
	
}
