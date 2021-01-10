package com.example.demo.service.odgovor;

import java.io.ByteArrayOutputStream;

import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.enums.StatusZalbe;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.XSLTransformer;
import com.example.demo.repository.rdf.OdgovorRDF;
import com.example.demo.repository.xml.OdgovorExist;
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
	
	@Autowired
	private OdgovorExist odgovorExist;
	
	@Autowired
	private OdgovorRDF odgovorRDF;
	
	@Autowired
	private XSLTransformer xslTransformer;
	
	@Autowired
	private DOMParser domParser;
	
	private static final String XSL_PATH = Constants.XSL_FOLDER + "odgovor.xsl";
			
	public void save(String xml) {
		Document document = this.odgovorMapper.map(xml);
		this.odgovorExist.save(null, document);
		String brojZalbe = document.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		Document zalbaDocument = this.zalbaExist.load(brojZalbe);
		zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(StatusZalbe.odgovoreno + "");
		this.zalbaExist.save(brojZalbe, zalbaDocument);
		Model model = this.odgovorMapper.map(document);
		this.odgovorRDF.save(model);
		this.soapService.sendSOAPMessage(document, TipDokumenta.odgovor);
	}
	
	public String generateHtml(String broj) {
		Document document = this.odgovorExist.load(broj);
		ByteArrayOutputStream out = this.xslTransformer.generateHtml(this.domParser.buildXml(document), XSL_PATH);
		return out.toString();
	}
	
}
