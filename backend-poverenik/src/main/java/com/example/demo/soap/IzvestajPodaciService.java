package com.example.demo.soap;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

public interface IzvestajPodaciService {

	public IzvestajPodaci getIzvestajPodaci(int godina) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, ParserConfigurationException, SAXException, IOException;

}
