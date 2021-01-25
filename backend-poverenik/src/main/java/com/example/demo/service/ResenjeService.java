package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.exception.ResourceTakenException;
import com.example.demo.exist.SearchUtil;
import com.example.demo.mapper.ResenjeMapper;
import com.example.demo.mapper.ZalbaMapper;
import com.example.demo.model.Korisnik;
import com.example.demo.model.ObicnaPretraga;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.rdf.ResenjeRDF;
import com.example.demo.repository.xml.ResenjeExist;
import com.example.demo.service.email.NotificationManager;
import com.example.demo.transformer.ResenjeTransformer;
import com.example.demo.ws.utils.SOAPActions;
import com.example.demo.ws.utils.SOAPService;

@Service
public class ResenjeService implements ServiceInterface {
	
	@Autowired
	private ResenjeExist resenjeExist;
	
	@Autowired
	private ResenjeRDF resenjeRDF;

	@Autowired
	private ResenjeMapper resenjeMapper;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private ZalbaService zalbaService;
				
	@Autowired
	private SOAPService soapService;
	
	@Autowired
	private NotificationManager notificationManager;
	
	@Autowired
	private ResenjeTransformer resenjeTransformer;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Override
	public void add(String xml) {
		Document document = this.resenjeMapper.map(xml);
		String brojZalbe = ((Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "datumZalbe").item(0))
				.getAttribute("href").replace(Namespaces.ZALBA + "/", "");
		Document zalbaDocument = this.zalbaService.load(brojZalbe);

		if (!ZalbaMapper.getStatusZalbe(zalbaDocument).equals(StatusZalbe.prosledjeno) 
				&& !ZalbaMapper.getStatusZalbe(zalbaDocument).equals(StatusZalbe.odgovoreno)
				&& !ZalbaMapper.getStatusZalbe(zalbaDocument).equals(StatusZalbe.obavesteno)
				&& !ZalbaMapper.getStatusZalbe(zalbaDocument).equals(StatusZalbe.odustato)) {
			throw new ResourceTakenException();
		}
		
		if (zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "datumProsledjivanja").getLength() > 0) {
			this.soapService.sendSOAPMessage(document, SOAPActions.create_resenje);
		}
		this.resenjeExist.add(document);
		this.resenjeRDF.add(document);
		
		zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(ResenjeMapper.getStatusResenja(document) + "");
		Element podaciZahteva = (Element) zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciZahteva").item(0);
		podaciZahteva.removeChild(podaciZahteva.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		try {
			Element podaciOdluke = (Element) zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "PodaciOdluke").item(0);
			podaciOdluke.removeChild(podaciOdluke.getElementsByTagNameNS(Namespaces.OSNOVA, "Detalji").item(0));
		}
		catch(Exception e) {
			;
		}
		
		this.zalbaService.update(brojZalbe, zalbaDocument);
		this.notificationManager.notifyResenje(document, this.resenjeTransformer.byteHtml(Utils.getBroj(document)), this.resenjeTransformer.bytePdf(Utils.getBroj(document)));
	}

	@Override
	public void update(String documentId, Document document) {
		this.resenjeExist.update(documentId, document);
		this.resenjeRDF.update(documentId, document);
	}
	
	@Override
	public void delete(String documentId) {
		this.resenjeExist.delete(documentId);
		this.resenjeRDF.delete(documentId);
	}
	
	@Override
	public String retrieve() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp = null;
		if (korisnik.getUloga().equals(Constants.POVERENIK)) {
			xpathExp = "/resenje:Resenje";
		}
		else {
			xpathExp = String.format("/resenje:Resenje[@href='%s']", Namespaces.KORISNIK + "/" + korisnik.getMejl());
		}		
		return this.resenjeMapper.map(this.resenjeExist.retrieve(xpathExp));		
	}
	
	@Override
	public Document load(String documentId) {
		return this.resenjeExist.load(documentId);
	}
	
	@Override
	public String nextDocumentId() {
		return this.resenjeExist.nextDocumentId();
	}

	@Override
	public String regularSearch(String xml) {
		return null;
	}

	@Override
	public String advancedSearch(String xml) {
		return null;
	}
	
}
