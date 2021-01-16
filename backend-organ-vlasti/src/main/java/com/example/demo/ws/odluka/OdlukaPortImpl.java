
package com.example.demo.ws.odluka;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.common.Namespaces;
import com.example.demo.common.WrongPasswordException;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.service.OdlukaService;
import com.example.demo.transformer.OdlukaTransformer;

@javax.jws.WebService(serviceName = "OdlukaService", portName = "OdlukaPort", targetNamespace = "http://demo.example.com/ws/odluka", wsdlLocation = "classpath:wsdl/Odluka.wsdl", endpointInterface = "com.example.demo.ws.odluka.Odluka")

@Component
public class OdlukaPortImpl implements Odluka {

	private static final Logger LOG = Logger.getLogger(OdlukaPortImpl.class.getName());

	@Autowired
	private OdlukaService odlukaService;

	@Autowired
	private DOMParser domParser;

	@Autowired
	private OdlukaTransformer odlukaTransformer;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private KorisnikExist korisnikExist;

	public java.lang.String getOdluka(java.lang.String getOdlukaRequest) {
		LOG.info("Executing operation getOdluka");
		try {
			String lozinka = this.domParser.buildDocument(getOdlukaRequest).getElementsByTagName("lozinka").item(0)
					.getTextContent();
			String documentId = this.domParser.buildDocument(getOdlukaRequest).getElementsByTagName("broj").item(0)
					.getTextContent();
			Document document = this.odlukaService.load(documentId);
			Element zahtev = (Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "Odluka").item(0);
			Document korisnik = this.korisnikExist.load(zahtev.getAttribute("href").replace(Namespaces.KORISNIK + "", ""));
			if (!this.passwordEncoder.matches(lozinka, korisnik.getElementsByTagNameNS(Namespaces.OSNOVA, "lozinka").item(0).getTextContent())) {
				throw new WrongPasswordException();
			}
			java.lang.String _return = this.domParser.buildXml(document);
			return _return;
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
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
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public byte[] getOdlukaPdf(java.lang.String getOdlukaPdfRequest) {
		LOG.info("Executing operation getOdlukaPdf");
		try {
			String documentId = this.domParser.buildDocument(getOdlukaPdfRequest).getElementsByTagName("broj").item(0)
					.getTextContent();
			return this.odlukaTransformer.plainPdf(documentId);
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}


}
