package com.example.demo.service;

import java.util.Date;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import com.example.demo.model.OrganVlasti;
import com.example.demo.model.Zahtev;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.OrganVlastiRepository;
import com.example.demo.repository.ZahtevRepository;

@Service
public class ZahtevService {
	
	@Autowired
	private ZahtevRepository zahtevRepository;
	
	@Autowired
	private OrganVlastiRepository organVlastiRepository;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	public void save(String xml) throws XMLDBException, JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException {		
		OrganVlasti organVlasti = this.organVlastiRepository.load();
		Zahtev zahtev = (Zahtev) this.jaxbParser.unmarshal(xml, Zahtev.class);
		zahtev.setDatum(new Date());
		zahtev.setPotpis("");
		zahtev.setOdgovoreno(false);
		zahtev.setOrganVlasti(organVlasti);
		this.zahtevRepository.save(this.jaxbParser.marshal(zahtev, Zahtev.class));
	}
	
}
