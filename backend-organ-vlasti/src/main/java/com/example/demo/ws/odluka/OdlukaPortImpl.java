
package com.example.demo.ws.odluka;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.common.Namespaces;
import com.example.demo.enums.TipOdluke;
import com.example.demo.exception.ResourceTakenException;
import com.example.demo.exception.WrongPasswordException;
import com.example.demo.mapper.OdlukaMapper;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.service.OdlukaService;
import com.example.demo.transformer.OdlukaTransformer;

@javax.jws.WebService(serviceName = "OdlukaService", portName = "OdlukaPort", targetNamespace = "http://demo.example.com/ws/odluka", wsdlLocation = "classpath:wsdl/Odluka.wsdl", endpointInterface = "com.example.demo.ws.odluka.Odluka")

@Component
public class OdlukaPortImpl implements Odluka {

	private static final Logger LOG = Logger.getLogger(OdlukaPortImpl.class.getName());

	@Autowired
	private DOMParser domParser;

	@Autowired
	private OdlukaService odlukaService;

	@Autowired
	private OdlukaTransformer odlukaTransformer;
	
	@Autowired
	private KorisnikExist korisnikExist;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public java.lang.String getOdluka(java.lang.String getOdlukaRequest) {
		LOG.info("Executing operation getOdluka");
		try {
			Document request = this.domParser.buildDocument(getOdlukaRequest);
			String documentId = this.domParser.buildDocument(getOdlukaRequest).getElementsByTagName("broj").item(0)
					.getTextContent();
			Document document = this.odlukaService.find(documentId);
			if (request.getElementsByTagName("delimicnost").getLength() > 0 && !OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
				throw new ResourceTakenException();
			}
			if (request.getElementsByTagName("delimicnost").getLength() == 0 && !OdlukaMapper.getTipOdluke(document).equals(TipOdluke.odbijanje)) {
				throw new ResourceTakenException();
			}
			Element odluka = (Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").item(0);
			Document korisnik = this.korisnikExist.find(odluka.getAttribute("href").replace(Namespaces.KORISNIK + "/", ""));
			String lozinka = this.domParser.buildDocument(getOdlukaRequest).getElementsByTagName("lozinka").item(0)
					.getTextContent();
			if (!this.passwordEncoder.matches(lozinka, korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "lozinka").item(0).getTextContent())) {
				throw new WrongPasswordException();
			}
			java.lang.String _return = this.domParser.buildXml(document);
			return _return;
		} catch (java.lang.Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public java.lang.String getOdlukaHtml(java.lang.String getOdlukaHtmlRequest) {
		LOG.info("Executing operation getOdlukaHtml");
		try {
			String documentId = this.domParser.buildDocument(getOdlukaHtmlRequest).getElementsByTagName("broj").item(0)
					.getTextContent();
			return this.odlukaTransformer.html(documentId);
		} catch (java.lang.Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public byte[] getOdlukaPdf(java.lang.String getOdlukaPdfRequest) {
		LOG.info("Executing operation getOdlukaPdf");
		try {
			String documentId = this.domParser.buildDocument(getOdlukaPdfRequest).getElementsByTagName("broj").item(0)
					.getTextContent();
			return this.odlukaTransformer.bytePdf(documentId);
		} catch (java.lang.Exception ex) {
			throw new RuntimeException(ex);
		}
	}


}
