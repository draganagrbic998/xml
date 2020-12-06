package com.example.demo.dom;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.demo.common.Namespaces;
import com.example.demo.model.Adresa;

@Component
public class AdresaParser implements Parser<Adresa> {

	@Override
	public Adresa parse(Element element) {
		String mesto = element.getElementsByTagNameNS(Namespaces.OSNOVA, "mesto").item(0).getTextContent();
		String postanskiBroj = element.getElementsByTagNameNS(Namespaces.OSNOVA, "postanski_broj").item(0).getTextContent();
		String ulica = element.getElementsByTagNameNS(Namespaces.OSNOVA, "ulica").item(0).getTextContent();
		String broj = element.getElementsByTagNameNS(Namespaces.OSNOVA, "broj").item(0).getTextContent();
		return new Adresa(mesto, postanskiBroj, ulica, broj);
	}

	@Override
	public Element parse(Adresa type, Document document) {
		Element adresa = document.createElementNS(Namespaces.OSNOVA, "osnova:Adresa");
		adresa.setAttributeNS(Namespaces.XMLNS, "xmlns:osnova", Namespaces.OSNOVA);
		Element mesto = document.createElementNS(Namespaces.OSNOVA, "osnova:mesto");
		mesto.setTextContent(type.getMesto());
		Element postanskiBroj = document.createElementNS(Namespaces.OSNOVA, "osnova:postanski_broj");
		postanskiBroj.setTextContent(type.getPostanskiBroj());
		Element ulica = document.createElementNS(Namespaces.OSNOVA, "osnova:ulica");
		ulica.setTextContent(type.getUlica());
		Element broj = document.createElementNS(Namespaces.OSNOVA, "osnova:broj");
		broj.setTextContent(type.getBroj());
		adresa.appendChild(mesto);
		adresa.appendChild(postanskiBroj);
		adresa.appendChild(ulica);
		adresa.appendChild(broj);
		return adresa;
	}

	@Override
	public String getSchema() {
		return Namespaces.OSNOVA;
	}

}
