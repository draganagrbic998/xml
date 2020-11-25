package com.example.demo.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.dom.Constants;
import com.example.demo.model.Adresa;
import com.example.demo.model.OrganVlasti;

@Component
public class OrganVlastiParser implements Parser<OrganVlasti> {
	
	@Autowired
	private AdresaParser adresaParser;

	@Override
	public OrganVlasti parse(Element element) {
		String naziv = element.getElementsByTagName("organ_vlasti:naziv").item(0).getTextContent();
		Adresa adresa = this.adresaParser.parse((Element) element.getElementsByTagName("osnova:Adresa").item(0));
		return new OrganVlasti(naziv, adresa);
	}

	@Override
	public Element parse(OrganVlasti type, Document document) {
		Element organVlasti = document.createElement("organ_vlasti:Organ_vlasti");
		organVlasti.setAttributeNS(Constants.XSI_NAMESPACE, "xsi:schemaLocation", "https://github.com/draganagrbic998/xml/organ_vlasti ../xsd/organ_vlasti.xsd");
		organVlasti.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:osnova", "https://github.com/draganagrbic998/xml/osnova ../xsd/osnova.xsd");
		organVlasti.setAttributeNS(Constants.XMLNS_NAMESPACE, "xmlns:organ_vlasti", "https://github.com/draganagrbic998/xml/organ_vlasti ../xsd/organ_vlasti.xsd");
		Element naziv = document.createElement("organ_vlasti:naziv");
		naziv.setTextContent(type.getNaziv());
		Element adresa = this.adresaParser.parse(type.getAdresa(), document);
		organVlasti.appendChild(naziv);
		organVlasti.appendChild(adresa);
		return organVlasti;
	}
	
}
