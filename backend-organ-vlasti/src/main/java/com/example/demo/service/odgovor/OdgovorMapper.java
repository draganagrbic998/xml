package com.example.demo.service.odgovor;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.example.demo.constants.Constants;
import com.example.demo.constants.Namespaces;
import com.example.demo.exception.MyException;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.service.KorisnikService;
import com.ibm.icu.text.SimpleDateFormat;

@Component
public class OdgovorMapper {

	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private DOMParser domParser;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		
	public Document map(String xml) {
		try {
			Document document = this.domParser.buildDocument(xml);
			Element odgovor = (Element) document.getElementsByTagNameNS(Namespaces.ODGOVOR, "Odgovor").item(0);
			String brojZalbe = odgovor.getElementsByTagNameNS(Namespaces.ODGOVOR, "brojZalbe").item(0).getTextContent();
			Element zalba = (Element) this.zalbaExist.load(brojZalbe).getElementsByTagNameNS(Namespaces.ZALBA, "Zalba").item(0);
			DocumentFragment documentFragment = document.createDocumentFragment();
			
			Node datum = document.createElementNS(Namespaces.OSNOVA, "datum");
			datum.setTextContent(sdf.format(new Date()));
			Element gradjanin = (Element) document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "Gradjanin").item(0), true);
			Element organVlasti = (Element) document.importNode(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "OrganVlasti").item(0), true);		
			gradjanin.getElementsByTagNameNS(Namespaces.OSNOVA, "potpis").item(0).setTextContent(this.korisnikService.currentUser().getOsoba().getPotpis());
			documentFragment.appendChild(document.createElementNS(Namespaces.OSNOVA, "broj"));			
			documentFragment.appendChild(datum);
			documentFragment.appendChild(gradjanin);
			documentFragment.appendChild(organVlasti);
			odgovor.insertBefore(documentFragment, document.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));	

			Node datumZalbe = document.createElementNS(Namespaces.ODGOVOR, "odgovor:datumZalbe");
			datumZalbe.setTextContent(zalba.getElementsByTagNameNS(Namespaces.OSNOVA, "datum").item(0).getTextContent());		
			return document;
		}
		catch(Exception e) {
			throw new MyException(e);
		}
	}
	
}
