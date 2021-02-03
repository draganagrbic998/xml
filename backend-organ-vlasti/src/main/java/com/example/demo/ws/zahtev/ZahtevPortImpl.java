package com.example.demo.ws.zahtev;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.common.Namespaces;
import com.example.demo.enums.StatusZahteva;
import com.example.demo.exception.ResourceTakenException;
import com.example.demo.exception.WrongPasswordException;
import com.example.demo.mapper.ZahtevMapper;
import com.example.demo.parser.DOMParser;
import com.example.demo.repository.xml.KorisnikExist;
import com.example.demo.service.ZahtevService;
import com.example.demo.transformer.ZahtevTransformer;

@javax.jws.WebService(serviceName = "ZahtevService", portName = "ZahtevPort", targetNamespace = "http://demo.example.com/ws/zahtev", wsdlLocation = "classpath:wsdl/Zahtev.wsdl", endpointInterface = "com.example.demo.ws.zahtev.Zahtev")
@Component
public class ZahtevPortImpl implements Zahtev {

	private static final Logger LOG = Logger.getLogger(ZahtevPortImpl.class.getName());

	@Autowired
	private DOMParser domParser;

	@Autowired
	private ZahtevService zahtevService;

	@Autowired
	private ZahtevTransformer zahtevTransformer;
	
	@Autowired
	private KorisnikExist korisnikExist;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public java.lang.String getZahtev(java.lang.String getZahtevRequest) {
		LOG.info("Executing operation getZahtev");
		try {
			Document request = this.domParser.buildDocument(getZahtevRequest);
			String documentId = request.getElementsByTagName("broj").item(0).getTextContent();
			Document document = this.zahtevService.find(documentId);
			if (request.getElementsByTagName("cutanje").getLength() > 0 && !ZahtevMapper.getStatusZahteva(document).equals(StatusZahteva.odbijeno)) {
				throw new ResourceTakenException();
			}
			Element zahtev = (Element) document.getElementsByTagNameNS(Namespaces.ZAHTEV, "Zahtev").item(0);
			Document korisnik = this.korisnikExist.find(zahtev.getAttribute("href").replace(Namespaces.KORISNIK + "", ""));
			String lozinka = this.domParser.buildDocument(getZahtevRequest).getElementsByTagName("lozinka").item(0)
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

	public java.lang.String getZahtevHtml(java.lang.String getZahtevHtmlRequest) {
		LOG.info("Executing operation getZahtevView");
		try {
			String documentId = this.domParser.buildDocument(getZahtevHtmlRequest)
					.getElementsByTagName("broj").item(0)
					.getTextContent();
			return this.zahtevTransformer.html(documentId);
		} catch (java.lang.Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public byte[] getZahtevPdf(java.lang.String getZahtevPdfRequest) {
		LOG.info("Executing operation getZahtevPdf");
		try {
			String documentId = this.domParser.buildDocument(getZahtevPdfRequest)
					.getElementsByTagName("broj").item(0)
					.getTextContent();
			return this.zahtevTransformer.bytePdf(documentId);
		} catch (java.lang.Exception ex) {
			throw new RuntimeException(ex);
		}

	}
}
