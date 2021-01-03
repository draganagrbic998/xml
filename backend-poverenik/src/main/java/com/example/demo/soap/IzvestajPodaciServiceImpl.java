package com.example.demo.soap;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.service.ZalbaService;

public class IzvestajPodaciServiceImpl implements IzvestajPodaciService {

	@Autowired
	private ZalbaService zalbaService;

	@Override
	public IzvestajPodaci getIzvestajPodaci(int godina) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, ParserConfigurationException, SAXException, IOException {
		return this.zalbaService.retrieveIzvestajData();
	}

}
