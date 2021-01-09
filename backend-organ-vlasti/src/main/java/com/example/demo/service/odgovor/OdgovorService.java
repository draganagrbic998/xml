package com.example.demo.service.odgovor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.example.demo.common.Namespaces;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.repository.xml.ZalbaExist;
import com.example.demo.ws.utils.SOAPService;
import com.example.demo.ws.utils.TipDokumenta;

@Service
public class OdgovorService {

	@Autowired
	private OdgovorMapper odgovorMapper;

	@Autowired
	private ZalbaExist zalbaExist;
	
	@Autowired
	private SOAPService soapService;
			
	public void save(String xml) {
		Document document = this.odgovorMapper.map(xml);
		String brojZalbe = document.getElementsByTagNameNS(Namespaces.ODGOVOR, "brojZalbe").item(0).getTextContent();
		Document zalbaDocument = this.zalbaExist.load(brojZalbe);
		zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odgovoreno + "");
		this.zalbaExist.save(brojZalbe, zalbaDocument);
		this.soapService.sendSOAPMessage(document, TipDokumenta.odgovor);
	}
	
}
