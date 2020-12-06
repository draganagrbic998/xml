package com.example.demo.dom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.common.Namespaces;
import com.example.demo.common.Schemas;
import com.example.demo.model.Adresa;
import com.example.demo.model.OrganVlasti;

@Component
public class OrganVlastiParser implements Parser<OrganVlasti> {
	
	@Autowired
	private AdresaParser adresaParser;

	@Override
	public OrganVlasti parse(Element element) {
		String naziv = element.getElementsByTagNameNS(Namespaces.ORGAN_VLASTI, "naziv").item(0).getTextContent();
		Adresa adresa = this.adresaParser.parse((Element) element.getElementsByTagNameNS(Namespaces.OSNOVA, "Adresa").item(0));
		return new OrganVlasti(naziv, adresa);
	}

	@Override
	public Element parse(OrganVlasti type, Document document) {
		Element organVlasti = document.createElementNS(Namespaces.ORGAN_VLASTI, "organ_vlasti:Organ_vlasti");
		organVlasti.setAttributeNS(Namespaces.XMLNS, "xmlns:organ_vlasti", Namespaces.ORGAN_VLASTI);
		Element naziv = document.createElementNS(Namespaces.ORGAN_VLASTI, "organ_vlasti:naziv");
		naziv.setTextContent(type.getNaziv());
		Element adresa = this.adresaParser.parse(type.getAdresa(), document);
		organVlasti.appendChild(naziv);
		organVlasti.appendChild(adresa);
		return organVlasti;
	}

	@Override
	public String getSchema() {
		return Schemas.ORGAN_VLASTI;
	}

}
